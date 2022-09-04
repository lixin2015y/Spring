package com.compensate.annotation;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithResult {

    /**
     * 根据结果重试，默认false，即默认根据异常判断进行补偿
     * @return
     */
    boolean open() default false;

    /**
     * result对象key，使用.分割，每一个.表示一个对象层次。
     * 举例：
     *  .表示当前result对象。
     *  .name 表示result.name对象
     *
     * @return
     */
    String key() default ".";

    /**
     * 如果result对应的key字段满足value()值，则进行补偿
     * @return
     */
    String[] value() default {};



}

