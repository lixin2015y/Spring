package com.compensate.postProcessor;

import com.compensate.annotation.Compensation;
import com.compensate.api.CompensationCallBack;
import com.compensate.supports.AnnotationMethodCheckInClassResolver;
import com.compensate.supports.CompensateJdkProxy;
import com.compensate.supports.DubboUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CompensateRegisterBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?> beanClass = bean.getClass();
        AnnotationMethodCheckInClassResolver resolver = new AnnotationMethodCheckInClassResolver(Compensation.class);
        ConcurrentHashMap<Method, Annotation> annotationMethods = resolver.getAnnotationMethods(beanClass);
        if (annotationMethods.size() == 0) {
            return bean;
        }

        // 创建代理对象
        Iterator<Map.Entry<Method, Annotation>> iterator = annotationMethods.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Method, Annotation> entry = iterator.next();
            Method method = entry.getKey();
            Compensation compensation = (Compensation) entry.getValue();
            Class<?>[] parameterTypes = method.getParameterTypes();
            CompensationCallBack callBackResponse = new CompensateJdkProxy(bean, method, parameterTypes, compensation).newInstance();
            DubboUtil.register(callBackResponse, compensation.code(), CompensationCallBack.class);
        }
        return bean;
    }
}
