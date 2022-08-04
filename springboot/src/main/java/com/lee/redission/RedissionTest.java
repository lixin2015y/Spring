package com.lee.redission;

import com.lee.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.rmi.ConnectIOException;
import java.util.Base64;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class RedissionTest {

    @Resource
    RedissonClient redissonClient;


    @Test
    public void test() {
        RLock lock = redissonClient.getLock("lock_test");
        lock.lock();
        Scanner s = new Scanner(System.in);
        s.nextLine();
        System.out.println("解锁");
        lock.unlock();

    }


    @Test
    public void test2() {
        RLock lock = redissonClient.getLock("lock_test-1");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean b = lock.tryLock();
            if (b) {
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(Thread.currentThread().getId());
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("t1获取锁失败");
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean b = false;
            try {
                b = lock.tryLock(40000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (b) {
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(Thread.currentThread().getId());
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("t2获取锁失败");
            }


        }, "t2").start();
        countDownLatch.countDown();
        new Scanner(System.in).nextLine();
    }



    @Test
    public void test3() {
        RBloomFilter<Long> blackList = redissonClient.getBloomFilter("blackList");
        blackList.tryInit(80000, 0.03);
        long phone = 15022401101l;
        for (int i = 0; i < 100000; i++) {
            blackList.add((long) i + phone);
        }

        boolean contains = blackList.contains(15022401105l);
        System.out.println(contains);
        blackList.dump();


    }

    @Test
    public void test4() {
        RBloomFilter<Long> blackList = redissonClient.getBloomFilter("blackList");
        System.out.println("blackList.contains(15022401125l) = " + blackList.contains(15022401125l));
        blackList.add(1l);
        System.out.println("blackList.contains(15022401125l) = " + blackList.contains(1l));

    }

    @Test
    public void test5() {
        RBloomFilter<Long> blackList = redissonClient.getBloomFilter("blackList");
        blackList.dump();

    }
}
