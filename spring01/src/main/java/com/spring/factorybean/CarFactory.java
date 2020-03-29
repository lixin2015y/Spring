package com.spring.factorybean;

import com.spring.di.Car;
import org.springframework.beans.factory.FactoryBean;

public class CarFactory implements FactoryBean<Car> {


    @Override
    public Car getObject() throws Exception {
        return new Car("宝马", "一汽", 123123.0);
    }

    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
