package com.compensate.supports;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.compensate.annotation.Compensation;
import com.compensate.annotation.CompensationInfo;
import com.compensate.api.CompensationCallBack;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class CompensateJdkProxy implements InvocationHandler, Serializable {

    private static final long serialVersionUID = 8515205906715817885L;

    private Object proxyBean;

    private Method proxyMethod;

    private Class<?>[] parameterTypes;

    private Class<?>[] interfaces;

    private CompensationInfo compensationInfo;

    public CompensateJdkProxy(Object proxyBean, Method proxyMethod, Class<?>[] parameterTypes, Compensation annotation) {
        this.proxyBean = proxyBean;

        this.proxyMethod = proxyMethod;
        this.parameterTypes = parameterTypes;
        this.interfaces = new Class[]{CompensateCallBack.class};
        this.compensationInfo = CompensationAnnoUtil.parse(annotation);

    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object[] params = null;
        Throwable throwable = null;
        Object result = null;
        try {
            JSONArray paramData = null;
            paramData = JSON.parseArray(args[0].toString());

            Integer actualParamNum = paramData.size();
            Integer methodParamNum = this.parameterTypes.length;
            String methodName = this.proxyMethod.getName();
            params = new Object[actualParamNum];
            for (int i = 0; i < actualParamNum; i++) {
                params[i] = JSON.parseObject(JSON.toJSONString(paramData.get(i)), this.parameterTypes[i]);
            }
            result = this.proxyMethod.invoke(this.proxyBean, params);
        } catch (InvocationTargetException e) {
            throwable = e.getTargetException();
        } catch (Exception e) {
            throwable = e;
        } catch (Throwable e) {
            throwable = e;
        }
        if (throwable != null) {
            throw throwable;
        }
        System.out.println(result);
        return CallBackResponse.success();
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
