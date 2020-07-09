package com.lee.entity;

import org.springframework.beans.factory.FactoryBean;

public class PersonFactoryBean implements FactoryBean {

    public Object getObject() throws Exception {
        return new Car();
    }

    public Class<?> getObjectType() {
        return Car.class;
    }
}
