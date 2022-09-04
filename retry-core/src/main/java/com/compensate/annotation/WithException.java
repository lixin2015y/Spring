package com.compensate.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithException {

    /**
     * 开关，默认为开，如果只需要根据结果重试，则可以将该属性设置为false
     * @return
     */
    boolean open() default true;

    /**
     * 指定异常,优先includes()
     * @return
     */
    Class<? extends Throwable>[] value() default {};

    /**
     * 包含异常
     * @return
     */
    Class<? extends Throwable>[] includes() default {};

    /**
     * 排除异常
     * @return
     */
    Class<? extends Throwable>[] excludes() default {};

    /**
     * 根据异常发送补偿数据后，是否仍抛出异常
     * @return
     */
    boolean throwExceptionTag() default true;



}
