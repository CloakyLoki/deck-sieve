package com.cloakyloki.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServicesLoggingAspect {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer() {
    }

    @Before("isServiceLayer() " +
            "&& target(serviceClass)")
    public void addInputParamLogging(JoinPoint joinPoint, Object serviceClass) {
        log.info("Invoked method {} in {} with args {}",
                joinPoint.getSignature().getName(),
                serviceClass, joinPoint.getArgs());
    }

    @AfterReturning(value = "isServiceLayer() && target(serviceClass)",
            returning = "result",
            argNames = "joinPoint,result,serviceClass")
    public void addReturningLogging(JoinPoint joinPoint, Object result, Object serviceClass) {
        log.info("Method {} in {} returned {}",
                joinPoint.getSignature().getName(),
                serviceClass,
                result);
    }
}
