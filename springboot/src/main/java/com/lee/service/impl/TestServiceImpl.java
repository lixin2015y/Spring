package com.lee.service.impl;

import com.compensate.annotation.Compensation;
import com.compensate.annotation.WithException;
import com.lee.service.TestService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("testService")
public class TestServiceImpl implements TestService {

    @Compensation(code = "test", withException = @WithException(value = NullPointerException.class))
    @Override
    public Map<String, String> test() {
        throw new NullPointerException();
//        return null;
    }
}
