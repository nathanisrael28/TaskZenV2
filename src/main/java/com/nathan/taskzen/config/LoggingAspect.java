package com.nathan.taskzen.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {


    public static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    // Target only your services (replace package if needed)
    @Pointcut("execution(* com.nathan.taskzen.service.*.*(..))")
    public void allServiceMethods() {
    }

    // @Before is called as Advice, (expression is called as Pointcut)
    // pointcut is an expression that tells where the advice should run i.e when the one or more methods in service class
    // mentioned in expression in pointcut, so then the advice method runs.

    //  Log before method call
    @Before("allServiceMethods()")
    public void logMethodCall(JoinPoint joinPoint) {

        LOGGER.info("Method Calling: {}", joinPoint.getSignature().getName());

    }

    //  Log after method call (regardless of success/failure)
    @After("allServiceMethods()")
    public void logMethodExecuted(JoinPoint joinPoint) {
        LOGGER.info("Method Executed: {}", joinPoint.getSignature().getName());
    }


    //  Log only if method returns successfully
    @AfterReturning("allServiceMethods()")
    public void logMethodExecuteSuccess(JoinPoint joinPoint) {

        LOGGER.info("Method Succeeded: {}", joinPoint.getSignature().getName());

    }

    //  Log only if method throws exception
    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "ex")
    public void logMethodCrash(JoinPoint joinPoint, Throwable ex) {
        LOGGER.info("Method Failed: {}", joinPoint.getSignature().toShortString());
        LOGGER.debug("Exception: {}", ex.getMessage());
    }

    @Around("allServiceMethods()")
    // Advice that runs before and after the execution of methods matched by the pointcut
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before and after method: " + joinPoint.getSignature().getName());
        return joinPoint.proceed(); // Proceed with the next advice or target method invocation
    }

    @Before("allServiceMethods()")
    public void logMethodArgs(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        LOGGER.debug("Method {} called with arguments: {}", methodName, Arrays.toString(args));
    }


}
