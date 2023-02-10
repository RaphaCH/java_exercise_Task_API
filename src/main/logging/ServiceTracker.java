package com.task.task.logging;



@Aspect
@Component
public class ServiceTracker {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ServiceTracker.class);

    @Before("execution(* service.EmployeeServices.get*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methoName = joinPoint.getSignature().getName();
        log.info(methodName + " is being called right now on " + joinPoint.getTarget());
    }

}
