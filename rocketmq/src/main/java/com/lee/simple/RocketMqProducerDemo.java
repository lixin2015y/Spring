package com.lee.simple;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RocketMqProducerDemo {

    DefaultMQProducer producer;
    Message msg = new Message();


    @Before
    public void before() throws MQClientException {
        producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("172.16.2.218:9876");
        producer.setSendMsgTimeout(6000);
        producer.start();
        msg.setTopic("test-1");
        msg.setTags("tag-1");
        msg.setBody("!hello".getBytes());
    }

    @Test
    public void test1() throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        // 同步发送
        producer.send(msg);
    }



    @After
    public void after() {
        producer.shutdown();
    }


}
