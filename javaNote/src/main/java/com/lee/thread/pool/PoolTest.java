package com.lee.thread.pool;

import org.junit.Test;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolTest {


    private static final int QUEUE_SIZE = 4;
    private final long KEEP_ALIVE_TIME = 60;
    private final int CORE_POOL_SIZE = 4;
    private final int MAXIMUM_POOL_SIZE = 6;
    private final int TASK_COUNT = 20;

    @Test
    public void test() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingDeque<>(QUEUE_SIZE));

        for (int i = 0; i < TASK_COUNT; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
    }

}
