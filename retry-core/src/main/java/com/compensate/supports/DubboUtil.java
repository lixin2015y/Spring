package com.compensate.supports;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;

public class DubboUtil{

    private static final String VERSION = "0.0.1";

    public static <T> void register(T bean,String group,Class<T> interfaceClazz){
        register(bean,group,interfaceClazz,VERSION);
    }

    public static <T> void register(T bean,String group,Class<T> interfaceClazz,String version){

        ServiceConfig<T> serviceConfig = new ServiceConfig<>();
        RegistryConfig registryConfig = SpringContextHolder.getBean(RegistryConfig.class);
        ProtocolConfig protocolConfig = SpringContextHolder.getBean(ProtocolConfig.class);
        ApplicationConfig applicationConfig = SpringContextHolder.getBean(ApplicationConfig.class);
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setRef(bean);
        serviceConfig.setInterface(interfaceClazz);
        serviceConfig.setGroup(group);
        serviceConfig.setVersion(version);
        serviceConfig.export();
    }

    public static <T> T getReference(Class<T> interfaceClazz){
        return getReference(interfaceClazz,null,false,0,null);
    }

    public static <T> T getReference(Class<T> interfaceClazz,String group){
        return getReference(interfaceClazz,group,false,0,VERSION);
    }

    public static <T> T gerReferenceWithNoVersion(Class<T> interfaceClazz,String group){
        return getReference(interfaceClazz,group,false,0,null);
    }

    public static <T> T getReference(Class<T> interfaceClazz,String group,Boolean async,Integer retries,String version){

        ReferenceConfig<T> referenceConfig = new ReferenceConfig<>();
        ApplicationConfig applicationConfig = SpringContextHolder.getBean(ApplicationConfig.class);
        RegistryConfig registryConfig = SpringContextHolder.getBean(RegistryConfig.class);
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(interfaceClazz);
        referenceConfig.setTimeout(30000);
        referenceConfig.setCheck(false);
        referenceConfig.setGroup(group);
        referenceConfig.setRetries(retries);
        referenceConfig.setVersion(version);
        referenceConfig.setAsync(async);
        return ReferenceConfigCache.getCache().get(referenceConfig);
    }


}
