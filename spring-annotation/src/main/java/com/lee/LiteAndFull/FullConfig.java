package com.lee.LiteAndFull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: FullConfig
 * @author: li xin
 * @date: 2022-01-22
 **/
@Configuration
public class FullConfig {


    /**
     * 这里是full类，full类表示被@Configuration注解标注的类，，getBean的时候会返回代理对象，
     * 创建代理对象的时候会将getAFromGetA中调用的getA使用getA已经创建的bean来代替，
     * @return
     */

    @Bean
    A getA() {
        A a = new A();
        return a;
    }

    @Bean
    A getAFromGetA() {
        return getA();
    }

}
