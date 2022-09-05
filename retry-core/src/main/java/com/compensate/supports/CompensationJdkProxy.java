package com.compensate.supports;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.compensate.annotation.Compensation;
import com.compensate.annotation.CompensationInfo;
import com.compensate.annotation.WithResultInfo;
import com.compensate.api.CompensationCallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CompensationJdkProxy implements InvocationHandler, Serializable {

    private static final long serialVersionUID = 8515205906715817885L;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Object proxyBean;

    private Method proxyMethod;

    private Class<?>[] parameterTypes;

    private Class<?>[] interfaces;

    private CompensationInfo compensationInfo;

    public CompensationJdkProxy(Object proxyBean, Method proxyMethod, Class<?>[] parameterTypes, Compensation annotation) {
        try {
            this.proxyBean = proxyBean;
        } catch (Exception e) {
            logger.error("clone proxy bean fail...", e);
        }

        this.proxyMethod = proxyMethod;
        this.parameterTypes = parameterTypes;
        this.interfaces = new Class[]{CompensationCallBack.class};
        this.compensationInfo = CompensationAnnoUtil.parse(annotation);

    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object[] params = null;
        Throwable throwable = null;
        Object result = null;
        try {
            if (args == null) {
                return CallBackResponse.fail("参数不可为空！");
            }
            JSONArray paramData = null;
            try {
                paramData = JSON.parseArray(args[0].toString());
            } catch (Exception e) {
                return CallBackResponse.fail("入参格式不正确，参数转换数组失败！请使用JSON.toJSONString(Object[] param)结果为入参");
            }
            Integer actualParamNum = paramData.size();
            Integer methodParamNum = this.parameterTypes.length;
            ;
            String methodName = this.proxyMethod.getName();
            if (actualParamNum != methodParamNum) {
                return CallBackResponse.fail(String.format("参数不匹配，代理执行%s方法失败！代理参数个数:%d,实际传参个数:%d", methodName, methodParamNum, actualParamNum));
            }
            params = new Object[actualParamNum];
            for (int i = 0; i < actualParamNum; i++) {
                params[i] = JSON.parseObject(JSON.toJSONString(paramData.get(i), SerializerFeature.WriteMapNullValue), this.parameterTypes[i]);
            }

            result = this.proxyMethod.invoke(this.proxyBean, params);
        } catch (InvocationTargetException e) {
            throwable = e.getTargetException();
        } catch (Exception e) {
            throwable = e;
        } catch (Throwable e) {
            throwable = e;
        } finally {

        }

        try {
//            List<Policy> policyList = new ArrayList<>();
//            policyList.add(new BasedExceptionPolicy(this.compensationInfo.getWithExceptionInfo(), throwable));
//            policyList.add(new BasedResultPolicy(this.compensationInfo.getWithResultInfo(), result));
//            WithResultListInfo withResultListInfo = compensationInfo.getWithResultListInfo();
//            List<WithResultInfo> withResultInfoList = withResultListInfo.getWithResultInfoList();
//            for (WithResultInfo withResultInfo : withResultInfoList) {
//                policyList.add(new BasedResultPolicy(withResultInfo, result));
//            }
//
//            ActionPolicy<AtomicBoolean> actionPolicy = new ActionPolicy<>(t -> ((AtomicBoolean) t).set(true), policyList);
//            AtomicBoolean matchResult = new AtomicBoolean(false);
//            actionPolicy.matchOneOfPolicies(matchResult);
//
//            if (matchResult.get()) {
//                LogInfo logInfo = compensationInfo.getLogInfo();
//                LogEntity logEntity = new LogEntity(logInfo.getMsgSpel(),
//                        logInfo.getLevel(),
//                        throwable,
//                        params,
//                        proxyMethod);
//                LogSystem.print(logEntity);
//            }

//            return !matchResult.get() ? CallBackResponse.success() : CallBackResponse.fail("仍满足需要补偿的条件!");
            return CallBackResponse.success();
        } finally {
//            LogRecordContext.clear();
        }

    }

    public CompensationCallBack newInstance() {

        ClassLoader classLoader = this.proxyBean.getClass().getClassLoader();
        if (classLoader == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }
        CompensationCallBack proxyBean = (CompensationCallBack) Proxy.newProxyInstance(classLoader, this.interfaces, this);
        return proxyBean;
    }


}
