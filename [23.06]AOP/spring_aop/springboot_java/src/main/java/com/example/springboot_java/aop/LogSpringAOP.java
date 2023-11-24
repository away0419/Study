package com.example.springboot_java.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LogSpringAOP {

//     공통 포인트 컷
    @Pointcut("execution(* com.example.springboot_java.domain.CalculateService.doAdd(..))")
    public void pointCut() {}

    @Pointcut("@annotation(com.example.springboot_java.annotation.LogAOP)")
    public void annotationPointCut() {}

    @Before("pointCut()")
    public void logBefore(JoinPoint joinPoint) {
        log.debug("AOP Before Execution : " + joinPoint.getSignature().getName());
    }

    @Before("annotationPointCut()")
    public void logAnnotationBefore(JoinPoint joinPoint) {
        log.info("AOP Before Annotation : " + joinPoint.getSignature().getName());
    }

    @After("pointCut()")
    public void logAfter(JoinPoint joinPoint) {
        log.debug("AOP After Execution : " + joinPoint.getSignature().getName());
    }

    @AfterReturning("pointCut()")
    public void logAfterReturning(JoinPoint joinPoint) {
        log.debug("AOP AfterReturning Execution : " + joinPoint.getSignature().getName());
    }


    @AfterThrowing("pointCut()")
    public void logAThrowing(JoinPoint joinPoint) {
        log.debug("AOP AfterThrowing Execution : " + joinPoint.getSignature().getName());
    }


}
