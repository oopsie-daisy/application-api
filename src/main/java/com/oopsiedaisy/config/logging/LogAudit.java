package com.oopsiedaisy.config.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAudit {

    @Around("execution(* *(..)) && " +
            "(within(com.oopsiedaisy..service..*) || " +
            "within(com.oopsiedaisy..controller..*))")
    public Object beforeServiceCall(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.info("Called method: {}.{}()", methodSignature.getMethod().getDeclaringClass().getSimpleName(),
                methodSignature.getMethod().getName());
        return joinPoint.proceed();
    }
}
