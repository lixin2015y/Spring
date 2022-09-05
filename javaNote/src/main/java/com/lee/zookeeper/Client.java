package com.lee.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

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
        zk = new ZooKeeper("172.16.2.218:2181", 5000, null);
        log.info("链接中。。。。。。");
        countDownLatch.wait();
        log.info("链接成功！！！！当前会话id：{}, 当前会话秘钥：{}", zk.getSessionId(), zk.getSessionPasswd());

    }

    @Test
    public void test() throws InterruptedException, KeeperException {
        Stat stat = zk.setData("/lixin/test1", "lixin".getBytes(), 2);
        System.out.println(stat);

    }


    @Test
    public void test2() throws InterruptedException, KeeperException {
        zk.getData("/lixin/credit", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event.getPath());
            }
        }, null);
    }

    @After
    public void close() throws InterruptedException, KeeperException {
        zk.close();

    }
}
