package com.oopsiedaisy.config.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAuditImpl {

    @Before(value = "@annotation(com.oopsiedaisy.config.annotations.LogAudit)")
    public void doAuditMethods(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.info("Called method: {}.{}()", methodSignature.getMethod().getDeclaringClass().getSimpleName(),
                methodSignature.getMethod().getName());
    }
}
