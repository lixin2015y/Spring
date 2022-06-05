package com.rpc.api;

public class HelloServiceImpl implements HelloService{
    @Override
    public String hello(String name) {
        return "hello " + name;
    }

}
