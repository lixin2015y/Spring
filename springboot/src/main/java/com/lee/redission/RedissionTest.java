package com.lee.redission;

import com.lee.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.client.RedisClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class RedissionTest {

    @Resource
    Redisson redisson;


    @Test

    public void test() {
        RLock lock = redisson.getLock("lock_test");
        lock.lock();
        Scanner s = new Scanner(System.in);
        s.nextLine();
        System.out.println("解锁");
        lock.unlock();

    }
}
