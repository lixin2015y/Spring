package com.lee.component;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class Blue implements ImportSelector, ImportBeanDefinitionRegistrar {

    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[0];
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

    }
}
