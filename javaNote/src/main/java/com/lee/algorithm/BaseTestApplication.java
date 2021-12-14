package com.lee.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;

@Slf4j
public class BaseTestApplication {

    public long startTime;
    public long endTime;

    @Before
    public void before() {
        startTime = System.currentTimeMillis();
    }

    @After
    public void after() {
        endTime = System.currentTimeMillis();
        log.info("方法执行耗时，{}秒", (endTime - startTime) / 1000);
    }
}
