package com.provider.impl;


import com.api.service.ServiceContext;
import com.compensate.annotation.Compensation;
import com.compensate.annotation.WithException;
import com.compensate.annotation.WithResult;
import com.lee.service.DemoService;
import org.springframework.stereotype.Service;

import java.util.Map;

@com.alibaba.dubbo.config.annotation.Service
@Service("demoService")
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("ServiceContext.getContext().getName() = " + ServiceContext.getContext().getName());
        return "hello" + name;
    }


    @Compensation(code = "code", systemCode = "spring", name = "lixin",
            withException = @WithException(value = {NullPointerException.class}),
            withResult = @WithResult(open = true))
    Map<String, String> test() {
        throw new NullPointerException();
    }
}
