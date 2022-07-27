package com.lee.trace;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

public class TraceComsumer {

    DefaultMQPushConsumer defaultMQPushConsumer;

    @Before
    public void before() throws MQClientException {
        defaultMQPushConsumer = new DefaultMQPushConsumer("consumer_group_order", true);
        defaultMQPushConsumer.setNamesrvAddr("172.16.2.218:9876");

    }

    @Test
    public void test() throws MQClientException, InterruptedException {
        defaultMQPushConsumer.subscribe("test-trace-1", "*");
        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            for (MessageExt messageExt : list) {
                System.out.println(messageExt);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        defaultMQPushConsumer.start();
        new Scanner(System.in).nextLine();
    }


    @After
    public void after() {
        defaultMQPushConsumer.shutdown();
    }
}
