package com.compensate.supports;


import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class AnnotationMethodCheckInClassResolver {

    private Class<? extends Annotation> annotationType;

    public AnnotationMethodCheckInClassResolver(Class<? extends Annotation> annotationType){

        this.annotationType = annotationType;
    }

    public Boolean hasAnnotationMethod(Class<?> clazz){

        final AtomicBoolean found = new AtomicBoolean(false);
        ReflectionUtils.doWithMethods(clazz, method -> {
            if (found.get()){
                return;
            }
            Annotation annotation = AnnotationUtils.findAnnotation(method, this.annotationType);
            if (annotation != null){
                found.set(true);
            }

        });
        return found.get();

    }

    public ConcurrentHashMap<Method,Annotation> getAnnotationMethods(Class<?> clazz){

        final ConcurrentHashMap<Method,Annotation> methods = new ConcurrentHashMap<>(8);
        ReflectionUtils.doWithLocalMethods(clazz, method -> {
            Annotation annotation = AnnotationUtils.findAnnotation(method, this.annotationType);
            if (annotation != null){
                methods.put(method,annotation);
            }
        });
        return methods;
    }
}
