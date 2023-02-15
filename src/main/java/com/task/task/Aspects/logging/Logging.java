package com.task.task.Aspects.logging;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {
    
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Logging.class);

    // Useful pointcuts for reference
     /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.task..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        var cause = e.getCause() != null ? e.getCause() : "NULL";
        log.error("Exception in {}.{}() with cause: ", className, methodName, cause);
    }




    @Around(value = "applicationPackagePointcut()")
    public Object performanceLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String arguments = Arrays.toString(joinPoint.getArgs());
        log.debug("Enter: {}.{}() with argument[s] = {}", className, methodName, arguments);

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            log.debug("Exit: {}.{}() with result = {}", className, methodName, result);
            return result;
        } finally {
            long end = System.currentTimeMillis();
            long time = end - start;

            if (time > 1000) {
                String warning = "\nWARNING!\n{}.{}() took longer than 1000ms to complete";
                log.error(warning, className,methodName);
            }
        }
    }

    // @AfterThrowing(pointcut = "execution(* com.task.task.controller.Department*.*(..))", throwing = "exception")
    // public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
    //     String methodName = joinPoint.getSignature().getName();
    //     String className = joinPoint.getTarget().getClass().getSimpleName();
    //     String logMessage = "\nMethod {}\nCalled inside class {}\nException thrown: {}";
    //     log.error(logMessage, methodName, className, exception);
    // }

    

}
