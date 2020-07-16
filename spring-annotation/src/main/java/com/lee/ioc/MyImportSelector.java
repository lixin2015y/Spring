package com.lee.ioc;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {

    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
            "com.lee.ioc.Car",
//            "com.lee.ioc.MyBeanDefinitionRegistryPostProcessor",
            "com.lee.ioc.MyBeanFactoryPostProcessor",
            "com.lee.ioc.MyBeanPostProcessor",
            "com.lee.ioc.MyImportSelector",
            "com.lee.ioc.MyInstantiationAwareBeanPostProcessor",
//            "com.lee.ioc.MyMergedBeanDefinitionPostProcessor",
            "com.lee.ioc.Person"
        };
    }
}
