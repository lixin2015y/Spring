package com.lee.juc.CountDownLatch;

import com.sun.jmx.snmp.internal.SnmpOutgoingRequest;
import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.concurrent.CountDownLatch;

/**
 * 六个同学离开教室之后，班长锁门
 */
public class CountDownLatchDemo {


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "走了");
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "班长锁门了");
    }
}
