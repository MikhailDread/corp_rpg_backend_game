package com.example.hrm_game.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionAspect {

    @Pointcut(value = "within(com.example.hrm_game.service.*)")
    public void nullPointerPointcut() {
    }

    @AfterThrowing(value = "nullPointerPointcut()")
    public void loggerNullPointerEx(JoinPoint joinPoint) {
        log.error("An exception occurred!",
                new NullPointerException("Essence getting turned null in method: " + joinPoint.getSignature().getName())
        );
    }
}
