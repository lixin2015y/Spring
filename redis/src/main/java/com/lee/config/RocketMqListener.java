package com.lee.config;

import com.alibaba.fastjson.JSON;
import com.lee.entity.GenerateCouponConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageQueueListener;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;
import redis.clients.util.JedisClusterCRC16;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class RocketMqListener {

    @Resource
    JedisClusterPlus jedisClusterPlus;

    @Bean
    DefaultMQPushConsumer defaultMQPushConsumer() throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("consumer_group_1");
        defaultMQPushConsumer.setNamesrvAddr("172.16.2.218:9876");
        defaultMQPushConsumer.subscribe("ams-coupon", "coupon-generate");
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                log.info("收到消息{}条", list.size());
                for (MessageExt messageExt : list) {
                    GenerateCouponConfig generateCouponConfig = JSON.parseObject(new String(messageExt.getBody()), GenerateCouponConfig.class);
                    log.info(JSON.toJSONString(generateCouponConfig));
                    String key = generateCouponConfig.getCouponId();
                    int slot = JedisClusterCRC16.getSlot(key);
                    JedisPool jedisPool = jedisClusterPlus.getConnectionHandler().getJedisPoolFromSlot(slot);
                    try (Jedis jedis = jedisPool.getResource()) {
                        log.info("当前key对应的节点{}", jedis);
                        Pipeline pipelined = jedis.pipelined();
                        for (Integer i = 0; i < generateCouponConfig.getNumber(); i++) {
                            pipelined.lpush(key, UUID.randomUUID().toString());
                        }
                        Response<List<Object>> exec = pipelined.exec();
                        System.out.println(exec);
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        defaultMQPushConsumer.start();
        return defaultMQPushConsumer;
    }

}
