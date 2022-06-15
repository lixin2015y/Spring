package com.spi;

import org.springframework.util.StopWatch;

public class A {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        for (int i = 0; i < 3; i++) {
            stopWatch.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            stopWatch.stop();
            System.out.println("stopWatch.getLastTaskTimeMillis() = " + stopWatch.getLastTaskTimeMillis());
            System.out.println("stopWatch.getTotalTimeMillis() = " + stopWatch.getTotalTimeMillis());
        }
    }
}
