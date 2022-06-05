package com.provider.impl;

import com.api.service.DemoService;
import com.api.service.ServiceContext;
import org.springframework.stereotype.Service;

@com.alibaba.dubbo.config.annotation.Service
@Service("demoService")
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("ServiceContext.getContext().getName() = " + ServiceContext.getContext().getName());
        return "hello" + name;
    }
}
