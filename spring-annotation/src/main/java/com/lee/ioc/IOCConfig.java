package com.lee.ioc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@Import(MyImportSelector.class)
@ComponentScan("com.lee.ioc")
public class IOCConfig {

    @Bean
    Person person() {
        return new Person("lixin", car());
    }

    @Bean
    Car car() {
        return new Car("宾利");
    }

}
