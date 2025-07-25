package com.nathan.taskzen.config;

import com.nathan.taskzen.enums.Status;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAspect {
    //    In Spring AOP, ValidationAspect is used to intercept method calls and apply validation logic before or after the method execution.
//    It helps ensure that inputs or outputs meet certain criteria, without cluttering the business logic of the method itself.
//    Typically, it checks for things like parameter validity, ensuring data integrity, and throwing exceptions if the validation fails.
//    In short: It separates validation concerns from business logic, making code cleaner and more maintainable.
//    and also we can modify the inputs also so that the modified inputs values will be go on to service methods which is specified.


    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationAspect.class);

    @Around("execution(* com.nathan.taskzen.service.*.*(..))")
    public Object validateInputs(ProceedingJoinPoint joinPoint) throws Throwable {
        for (Object arg : joinPoint.getArgs()) {
            if (arg == null) {
                LOGGER.error("Invalid argument in method {} - argument is null!", joinPoint.getSignature());
                throw new IllegalArgumentException("Invalid method input - null argument");
            }
        }

        return joinPoint.proceed();
    }
}
