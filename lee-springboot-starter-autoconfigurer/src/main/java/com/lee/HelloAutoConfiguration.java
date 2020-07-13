package com.lee;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(HelloProperties.class)
public class HelloAutoConfiguration {

    @Resource
    HelloProperties helloProperties;

    @Bean
    SayHello sayHello() {
        final SayHello sayHello = new SayHello();
        sayHello.setHelloProperties(helloProperties);
        return sayHello;
    }


}
