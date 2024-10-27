package com.example.simplebanking.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Before("execution(* com.example.simplebanking.service.BankAccountService.*(..))")
    public void logBefore() {
        log.info("Method execution started");
    }
    @AfterReturning("execution(* com.example.simplebanking.service.BankAccountService.*(..))")
    public void logAfterReturning() {
        log.info("Method execution completed successfully");
    }
}
