package com.lee.config;

import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissionConfig {

    @Bean
    Config config() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://172.16.2.204:6379");
        return config;
    }
}
