package com.lee.aop;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.Transactional;

/**
 * 程序运行期间动态的将某段代码切入到某些方法的指定位置进行执行的编程方式
 */
@EnableAspectJAutoProxy
@Configuration
@ComponentScan("com.lee.aop")
@Transactional
public class MainConfigOfAOP {

    @Bean
    Calculator calculator() {
        return new CalculatorImpl();
    }

    @Bean
    LogAspects logAspects() {
        return new LogAspects();
    }

    @Bean
    MethodInterceptor methodInterceptor() {
        return new MethodInterceptor();
    }

    @Bean
    MethodBeforeAdviser methodBeforeAdviser() {
        return new MethodBeforeAdviser();
    }


    @Bean
    NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor() {
        NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor = new NameMatchMethodPointcutAdvisor();
        nameMatchMethodPointcutAdvisor.setAdvice(methodInterceptor());
        nameMatchMethodPointcutAdvisor.setMappedNames("div");
        return nameMatchMethodPointcutAdvisor;
    }

    @Bean
    ProxyFactoryBean proxyFactoryBean() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
//        proxyFactoryBean.setInterceptorNames("methodBeforeAdviser", "methodInterceptor");
        proxyFactoryBean.setInterceptorNames("nameMatchMethodPointcutAdvisor");
        proxyFactoryBean.setTarget(calculator());
        return proxyFactoryBean;
    }


}
