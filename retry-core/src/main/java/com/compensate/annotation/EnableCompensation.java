package com.compensate.annotation;


import com.compensate.config.CompensationConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableAspectJAutoProxy
@Import(CompensationConfiguration.class)
@Documented
public @interface EnableCompensation {
}
