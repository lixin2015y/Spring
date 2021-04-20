package com.provider.impl;

import com.api.service.DemoService;
import org.springframework.stereotype.Service;

@Service("demoService")
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "hello" + name;
    }
}
