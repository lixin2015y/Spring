package com.lee.simple;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

public class RocketMqConsumerDemo {

    DefaultMQPushConsumer defaultMQPushConsumer;

    @Before
    public void before() throws MQClientException {
        defaultMQPushConsumer = new DefaultMQPushConsumer("consumer_group_1");
        defaultMQPushConsumer.setNamesrvAddr("172.16.2.218:9876");

    }

    @Test
    public void test() throws MQClientException, InterruptedException {
        defaultMQPushConsumer.subscribe("test-1", "*");
        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            System.out.println("123123213");
            for (MessageExt messageExt : list) {
                System.out.println(messageExt);
            }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        });
        defaultMQPushConsumer.start();
        new Scanner(System.in).nextLine();
    }


    @After
    public void after() {
        defaultMQPushConsumer.shutdown();
    }
}
