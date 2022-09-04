package com.lee.service.impl;

import com.compensate.annotation.Compensation;
import com.compensate.annotation.WithException;
import com.lee.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service("testService")
public class TestServiceImpl implements TestService {

    @Compensation(code = "test",group = "lixin-group", withException = @WithException(value = NullPointerException.class))
    @Override
    public Map<String, String> test(String id) {
        log.info("调用函数id:{}", id);

        throw new NullPointerException();
//        return id;
//        return null;
    }
}
