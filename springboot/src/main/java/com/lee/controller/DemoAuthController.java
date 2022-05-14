package com.lee.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoAuthController {

    @GetMapping("/hello/v1")
    public JSONObject hello() {
        JSONObject data = new JSONObject();
        data.put("hello", "world");
        return data;
    }
}
