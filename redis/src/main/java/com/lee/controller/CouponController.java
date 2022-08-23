package com.lee.controller;

import com.alibaba.fastjson.JSON;
import com.lee.entity.GenerateCouponConfig;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("coupon")
@RestController
public class CouponController {

    @Resource
    DefaultMQProducer defaultMQProducer;

    @GetMapping("generate")
    String generate(String couponId, Integer num) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {

        List<Message> messageList = new ArrayList<>();
        int index = 0;
        if (num < 5000) {
            Message message = new Message();
            message.setTopic("ams-coupon");
            message.setTags("ams-coupon-generate");
            GenerateCouponConfig generateCouponConfig = new GenerateCouponConfig();
            generateCouponConfig.setCouponId(couponId + "" + index++);
            generateCouponConfig.setNumber(num);
            message.setBody(JSON.toJSONString(generateCouponConfig).getBytes());
            messageList.add(message);
        } else {
            for (Integer i = 0; i < num / 5000; i++) {
                Message message = new Message();
                message.setTopic("ams-coupon");
                message.setTags("ams-coupon-generate");
                GenerateCouponConfig generateCouponConfig = new GenerateCouponConfig();
                generateCouponConfig.setCouponId(couponId + "" + index++);
                generateCouponConfig.setNumber(num);
                message.setBody(JSON.toJSONString(generateCouponConfig).getBytes());
                messageList.add(message);
            }
            Message message = new Message();
            message.setTopic("ams-coupon");
            message.setTags("ams-coupon-generate");
            GenerateCouponConfig generateCouponConfig = new GenerateCouponConfig();
            generateCouponConfig.setCouponId(couponId + "" + index++);
            generateCouponConfig.setNumber(num % 5000);
            message.setBody(JSON.toJSONString(generateCouponConfig).getBytes());
            messageList.add(message);
        }
        for (int i = 0; i < messageList.size(); i++) {
            defaultMQProducer.send(messageList.get(i));
        }
        return "成功";
    }

}
