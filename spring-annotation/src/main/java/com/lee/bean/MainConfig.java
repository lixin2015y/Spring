package com.lee.bean;

import com.lee.entity.Person;
import com.lee.filter.MyFilter;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScans({
        @ComponentScan(value = "com.lee", excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = MyFilter.class)
        }, useDefaultFilters = false)

})
public class MainConfig {


    /**
     * 可以修改方法名，修改bean的id，也可通过@bean的注解实现修改id
     */
    @Bean(name = "person1")
    public Person person() {
        return new Person("lixin", 123);
    }

}
