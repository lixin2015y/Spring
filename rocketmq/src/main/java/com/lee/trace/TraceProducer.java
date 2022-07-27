package com.lee.trace;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TraceProducer {

    DefaultMQProducer producer;
    Message msg = new Message();


    @Before
    public void before() throws MQClientException {
        producer = new DefaultMQProducer("ProducerGroupName", true);
        producer.setNamesrvAddr("172.16.2.218:9876");
        producer.start();
    }

    @Test
    public void test1() throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                Message message = new Message();
                message.setTopic("test-trace-1");
                message.setTags("test-trace-tag");
                message.setBody(("message===" + i + "===" + j).getBytes());
                SendResult send = producer.send(message);
                System.out.println(send);
            }
        }
    }



    @After
    public void after() {
        producer.shutdown();
    }


}
