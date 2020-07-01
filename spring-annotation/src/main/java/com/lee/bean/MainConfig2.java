package com.lee.bean;

import com.lee.component.Color;
import com.lee.component.ColorFactory;
import com.lee.condition.LinuxCondition;
import com.lee.condition.MyImportBeanDefinitionRegistrar;
import com.lee.condition.MyImportSelector;
import com.lee.condition.WindowsCondition;
import com.lee.entity.Person;
import org.springframework.context.annotation.*;

@Import({Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {


    /**
     * 单例的对象是容器启动时就创建，多例的是获取对象的时候才会去创建
     * 懒加载，对应单例bean，让其使用时才进行初始化
     */
    @Scope("prototype")
    @Bean(name = "person")
    public Person person() {
        return new Person("lixin", 123);
    }

    @Bean
    @Lazy
    public Person person2() {
        return new Person("lixin", 123);
    }


    @Conditional(value = WindowsCondition.class)
    @Bean
    public Person bill() {
        return new Person("bill", 123);
    }

    @Conditional(value = LinuxCondition.class)
    @Bean
    public Person linus() {
        return new Person("linus", 123);
    }


    /**
     * 使用FactoryBean进行注册
     */
    @Bean
    public ColorFactory color2() {
        return new ColorFactory();
    }

}
