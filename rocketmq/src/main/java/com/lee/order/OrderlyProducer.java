package com.lee.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class OrderlyProducer {

    DefaultMQProducer producer;
    Message msg = new Message();


    @Before
    public void before() throws MQClientException {
        producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("172.16.2.218:9876");
        producer.start();
        msg.setTopic("test-1");
        msg.setTags("tag-1");
        msg.setBody("!hello".getBytes());
    }

    @Test
    public void test1() throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        for (int i = 0; i < 5; i++) {
            Integer orderId = i;
            for (int j = 0; j < 3; j++) {
                Message message = new Message();
                message.setTopic("test-order-1");
                message.setTags("test-order-tag");
                message.setBody(("message===" + i + "===" + j).getBytes());
                producer.send(message, (list, message1, o) -> {
                    Integer id = (Integer) o;
                    int index = id % list.size();
                    return list.get(index);
                }, orderId);
            }
        }
    }



    @After
    public void after() {
        producer.shutdown();
    }


}
