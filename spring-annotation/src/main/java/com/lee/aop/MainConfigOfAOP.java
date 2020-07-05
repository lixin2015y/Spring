package com.lee.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 程序运行期间动态的将某段代码切入到某些方法的指定位置进行执行的编程方式
 */
@EnableAspectJAutoProxy
@ComponentScan(value = "com.lee.aop")
@Configuration
public class MainConfigOfAOP {

}
