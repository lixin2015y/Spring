package com.compensate.annotation;


import com.compensate.enums.GapPolicy;
import lombok.extern.java.Log;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Compensation {

    /**
     * 唯一补偿码
     */
    String code();


    /**
     * 系统码,默认将读取dubbo.application.name属性值
     */
    String systemCode() default "";

    /**
     * 补偿名
     * @return
     */
    String name() default "";

    /**
     * dubbo服务分组名，默认系统码+“_”+补偿码
     */
    String group() default "";

    /**
     * 根据异常补偿
     * @return
     */
    WithException withException() default @WithException;

    /**
     * 根据结果补偿
     * @return
     */
    WithResult withResult() default @WithResult;


    /**
     * 自定义日志
     * @return
     */
    Log log() default @Log;


    /**
     * 补偿间隔时间，单位：分钟，默认10分钟
     * @return
     */
    int gapMinuter() default 10;

    /**
     * 补偿间隔策略，默认等增策略
     * @return
     */
    GapPolicy gapPolicy() default GapPolicy.EQUAL_INCREASE_GAP_TIME;

    /**
     * 补偿次数上限,默认4次
     * @return
     */
    int limit() default 4;

    /**
     * 日志保留天数，默认7天
     * @return
     */
    int logKeepDays() default 7;

    /**
     * 错误标签
     */
    String errorTag() default "";

    /**
     * 错误信息
     */
    String errorMessage() default "";

    /**
     * 配置开关，默认开启
     * @return
     */
    boolean open() default true;


}
