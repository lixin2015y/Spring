package com.lee.LiteAndFull;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @className: LiteConfig
 * @author: li xin
 * @date: 2022-01-22
 **/
@Component
public class LiteConfig {

    @Bean
    A getA() {
        return new A();
    }

    @Bean
    A getAFromGetA() {
        return getA();
    }
}
