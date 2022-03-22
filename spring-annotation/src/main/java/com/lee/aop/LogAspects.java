package com.lee.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//@Component
//@Aspect
public class LogAspects {


    @Pointcut("execution(* com.lee.aop.CalculatorImpl.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void startMethod(JoinPoint joinPoint) {
        System.out.println("方法" + joinPoint.getSignature().getName() + Arrays.asList(joinPoint.getArgs()) + "开始");
    }

    @After("pointCut()")
    public void endMethod(JoinPoint joinPoint) {
        System.out.println("方法" + joinPoint.getSignature().getName() + Arrays.asList(joinPoint.getArgs()) + "结束");
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void returnMethod(JoinPoint joinPoint, Object result) {
        System.out.println("方法" + joinPoint.getSignature().getName() + Arrays.asList(joinPoint.getArgs()) + "返回值为" + result);
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void exceptionMethod(JoinPoint joinPoint, Exception exception) {
        System.out.println("方法" + joinPoint.getSignature().getName() + Arrays.asList(joinPoint.getArgs()) + "异常");
    }
}
