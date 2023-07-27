package com.example.springboot_java.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class Log {

    // 공통 포인트 컷
    @Pointcut("execution(* hello(..))")
    private void pointCut() {}

    @Before("pointCut()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("AOP Before: " + joinPoint.getSignature().getName());
    }

    @After("pointCut()")
    public void logAfter(JoinPoint joinPoint){
        log.info("AOP After: " + joinPoint.getSignature().getName());
    }


    @Around("pointCut()")
    public void logAround(JoinPoint joinPoint){
        log.info("AOP After: " + joinPoint.getSignature().getName());
    }


    @AfterReturning("pointCut()")
    public void logAfterReturning(JoinPoint joinPoint){
        log.info("AOP After : " + joinPoint.getSignature().getName());
    }


    @AfterThrowing("pointCut()")
    public void logAThrowing(JoinPoint joinPoint){
        log.info("AOP AfterThrowing : " + joinPoint.getSignature().getName());
    }




}
