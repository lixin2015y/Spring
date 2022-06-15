package com.lee.threadService;

public class TestCountDownLatch2 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch2 waitPoint = new CountDownLatch2(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    waitPoint.await();
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
        Thread.sleep(3000);
        waitPoint.countDown();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
