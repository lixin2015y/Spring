package com.lee.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DemoAuthController {

    @GetMapping("/hello/v1")
    public JSONObject hello() {
        JSONObject data = new JSONObject();
        data.put("hello", "world");
        return data;
    }

    List<List<MessageDto>> list = new ArrayList<>();
    @GetMapping("generate")
    public String generate() {
        ArrayList<MessageDto> l = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            l.add(new MessageDto("1502240110" + i));
        }
        list.add(l);
        return list.size() + "";
    }

    class MessageDto {
        private String mobile;

        public MessageDto(String mobile) {
            this.mobile = mobile;
        }
    }
}
