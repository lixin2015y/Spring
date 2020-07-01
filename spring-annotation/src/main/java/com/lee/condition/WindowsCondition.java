package com.lee.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WindowsCondition implements Condition {


    /**
     * @param conditionContext      判断条件使用的上下文环境，
     * @param annotatedTypeMetadata 注释信息
     * @return
     */
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {

        // bean工厂
        final ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();

        // 类加载器
        final ClassLoader classLoader = conditionContext.getClassLoader();

        // 系统环境变量
        final Environment environment = conditionContext.getEnvironment();

        // bean定义的信息
        final BeanDefinitionRegistry registry = conditionContext.getRegistry();

        final String osName = environment.getProperty("os.name");

        if (osName.contains("Windows")) {
            return true;
        }
        return false;
    }
}
