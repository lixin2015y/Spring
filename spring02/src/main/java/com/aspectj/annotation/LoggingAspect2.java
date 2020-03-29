package com.aspectj.annotation;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect //一个切面
@Order(1)
public class LoggingAspect2 {


    @Pointcut("execution(* com.aspectj.annotation.*.*(..))")
    public void cutPoint() {

    }

    @Before("cutPoint()")
    public void afterMethod(JoinPoint joinPoint) {

        final String name = joinPoint.getSignature().getName();
        System.out.println("前置通知22222" + name);
    }


}
