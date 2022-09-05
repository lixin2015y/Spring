package com.compensate.postProcessor;

import com.compensate.annotation.Compensation;
import com.compensate.api.CompensationCallBack;
import com.compensate.supports.AnnotationMethodCheckInClassResolver;
import com.compensate.supports.CompensateJdkProxy;
import com.compensate.supports.CompensationJdkProxy;
import com.compensate.supports.DubboUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
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

            // dubbo://10.1.6.116:20880/com.compensate.api.CompensationCallBack?anyhost=true&application=provider
            // &dubbo=2.6.0&generic=false&group=lixin-group
            // &interface=com.compensate.api.CompensationCallBack
            // &logger=log4j&methods=callBack&pid=19728&revision=0.0.1
            // &side=provider&timestamp=1662288201054&version=0.0.1

            CompensationCallBack proxyInstance = new CompensationJdkProxy(bean, method, parameterTypes, compensation).newInstance();
            log.info("group:{},", compensation.group());

            DubboUtil.register(proxyInstance, compensation.group(), CompensationCallBack.class);
        }
        return bean;
    }
}
