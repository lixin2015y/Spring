package com.lee.config;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.EnabledIf;

@EnableDubbo
@PropertySource("classpath:/properties/provider.properties")
@Configuration
@Component
public class DubboConfig {
}
