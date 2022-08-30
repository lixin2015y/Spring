package com.lee.zookeeper;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.*;
import org.checkerframework.checker.guieffect.qual.SafeType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @className: Client
 * @author: li xin
 * @date: 2021-12-11
 **/
@Slf4j
public class Client {
    ZooKeeper zk = null;

    @Before
    public void connect() throws IOException, InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        zk = new ZooKeeper("172.16.2.218:2181", 5000, watchedEvent -> {
            log.info("接收到事件通知，{}", JSON.toJSONString(watchedEvent));
            if (watchedEvent.getType() == Watcher.Event.EventType.None
                    && watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                countDownLatch.countDown();
            }
        });
        log.info("链接中。。。。。。");
        countDownLatch.wait();
        log.info("链接成功！！！！当前会话id：{}, 当前会话秘钥：{}", zk.getSessionId(), zk.getSessionPasswd());

    }

    @Test
    public void test() throws InterruptedException, KeeperException {
        String s = zk.create("/lixin/test1", "lixin".getBytes(), null, CreateMode.EPHEMERAL);

    }


    @Test
    public void test2() throws InterruptedException, KeeperException {
        zk.addWatch("/lixin/test1/", AddWatchMode.PERSISTENT_RECURSIVE);

    }

    @After
    public void close() throws InterruptedException {

        zk.close();
    }
}
