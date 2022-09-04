package com.compensate.advisor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.compensate.annotation.Compensation;
import com.compensate.annotation.CompensationInfo;
import com.compensate.dto.CompensationEntity;
import com.compensate.supports.CompensationAnnoUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Slf4j
@Aspect
public class CompensationAspect {


    @Pointcut("@annotation(com.compensate.annotation.Compensation)")
    public void pointCut() {
        log.info("构造方法");
    }

    @Around("pointCut()")
    public void around(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object res = null;
        try {
            res = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println(res);
        System.out.println(args);


        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Compensation annotation = method.getAnnotation(Compensation.class);

        if (annotation != null) {
            // 调用dubbo服务
            CompensationInfo compensationInfo = getCompensationInfo(method);
            CompensationEntity compensationEntity = new CompensationEntity(compensationInfo.getSystemCode(),
                    compensationInfo.getCode(),
                    compensationInfo.getName(),
                    compensationInfo.getGroup(),
                    compensationInfo.getErrorTag(),
                    compensationInfo.getErrorMessage(),
                    compensationInfo.getLimit(),
                    compensationInfo.getLogKeepDays(),
                    compensationInfo.getGapMinuter(),
                    compensationInfo.getGapPolicy().getCode(),
                    generateDataFormatDesc(method),
                    JSON.toJSONString(joinPoint.getArgs(), SerializerFeature.WriteMapNullValue)
            );

            // 发送数据
            log.info("发送数据{}", compensationEntity);
        }
    }


    private String generateDataFormatDesc(Method method){
        Parameter[] parameters = method.getParameters();
        StringBuilder sb  = new StringBuilder("[");
        for (Parameter parameter : parameters){
            if (sb.length() > 1){
                sb.append(",");
            }
            sb.append(parameter.getName() + "(" + parameter.getType().getName()+")");
        }
        sb.append("]");
        return sb.toString();
    }

    private CompensationInfo getCompensationInfo(Method method) {
        CompensationInfo compensationInfo = null;
        Compensation annotation = method.getAnnotation(Compensation.class);
        if (annotation != null){
            compensationInfo = CompensationAnnoUtil.parse(annotation);
        }
        return compensationInfo;
    }
}
