package com.task.task.Aspects.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {
    
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Logging.class);

    // @Before(value = "execution(* com.task.task.service.EmployeeServices.get*(..))")
    // public void logBeforeControllerGet(JoinPoint joinPoint) {
    //    String methodName = joinPoint.getSignature().getName();
    //    String className = joinPoint.getTarget().getClass().getSimpleName();
    //    String logMessage = "\nMethod {}\nCalled inside class {}";
    //     log.info(logMessage, methodName, className);
    // }

    @Around(value = "execution(* com.task.task.controller.Employee*.get*(..))")
    public Object servicePerformanceLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            long end = System.currentTimeMillis();
            long time = end - start;
            String logMessage = "\nMethod {}\nCalled inside class {}\nTook {} ms to execute";
            log.info(logMessage, methodName, className, time);
            

            if (time > 1000) {
                String warning = "\nWARNING!\nMethod {} took longer than 1000ms to complete";
                log.error(warning, methodName);
            }
        }
    }

    @AfterThrowing(pointcut = "execution(* com.task.task.controller.Department*.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String logMessage = "\nMethod {}\nCalled inside class {}\nException thrown: {}";
        log.error(logMessage, methodName, className, exception);
    }

    

}
