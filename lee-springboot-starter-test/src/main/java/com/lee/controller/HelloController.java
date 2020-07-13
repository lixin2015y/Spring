package com.lee.controller;

import com.lee.SayHello;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {


    @Resource
    SayHello sayHello;


    @GetMapping("hello")
    String hello(){
        return sayHello.sayHello();
    }
}
