package com.aspectj.annotation;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect //一个切面
@Order(2)
public class LoggingAspect {


    /**
     * 前置通知 在目标方法（连接点）执行之前
     */

    @Before("execution(public int com.aspectj.annotation.Calculator.add(int, int))")
    public void beforeMethod() {
        System.out.println("执行前置通知");
    }

    /**
     * 后置通知 不管又没有抛出异常都会执行
     */

    @After("execution(* com.aspectj.annotation.*.*(..))")
    public void afterMethod(JoinPoint joinPoint) {

        final String name = joinPoint.getSignature().getName();
        System.out.println("执行后置通知" + name);
    }

    /**
     * 返回通知 ： 在目标方法正常执行之后
     */
    @AfterReturning(value = "execution(* com.aspectj.annotation.*.*(..))", returning = "result")
    public void returnMethod(Object result) {
        System.out.println("执行return切面");
        System.out.println(result);
    }


    /**
     * 异常通知 抛出异常后运行
     */
    @AfterThrowing(value = "execution(* com.aspectj.annotation.*.*(..))", throwing = "e")
    public void exceptionMethod(Exception e) {
        System.out.println("异常通知");
    }

    /**
     * 环绕通知
     */

    @Around("execution(* com.aspectj.annotation.*.*(..))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) {
        try {
            //前置
            final Object result = joinPoint.proceed();
            //返回
            return result;
        } catch (Throwable throwable) {
            //异常
            throwable.printStackTrace();
        } finally {
            //后置
        }
        return null;
    }

}
