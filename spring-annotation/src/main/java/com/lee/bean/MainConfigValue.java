package com.lee.bean;

import com.lee.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {"classpath:/person.properties"})
@Configuration
public class MainConfigValue {

    @Bean
    public Person person() {
        return new Person();
    }

}
