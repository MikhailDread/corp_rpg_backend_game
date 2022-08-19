package com.example.hrm_game.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggerAspect {
    @Pointcut(value = "within(com.example.hrm_game.controller.*) && execution(* add*(..))")
    public void addAnythingPointcut() {
    }

    @Before(value = "addAnythingPointcut()")
    public void loggerAdding(JoinPoint joinPoint) {
        log.info("User add object in method " + joinPoint.getSignature().getName());
    }

    @Pointcut(value = "within(org.springframework.data.repository.Repository) && execution(* *User*(..))")
    public void userPointcut() {
    }

    @After(value = "userPointcut()")
    public void logUser() {
        log.info("User is found");
    }

    @Pointcut(value = "this(org.springframework.data.repository.Repository) && execution(* find*(..))")
    public void findInDbPointcut() {
    }

    @AfterReturning(value = "findInDbPointcut()", returning = "returnValue")
    public void logGetObjectOutDb(JoinPoint joinPoint, Object returnValue) {
        if (returnValue != null) {
            log.info("Application is found: {} in jpa method: {}",
                    returnValue.getClass().getSimpleName(),
                    joinPoint.getSignature().getName()
            );
        } else {
            log.info("Application not found: null in jpa method: {}",
                    joinPoint.getSignature().getName()
            );
        }
    }
}
