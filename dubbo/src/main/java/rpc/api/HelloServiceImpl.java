package rpc.api;

import java.util.Arrays;
import java.util.List;

public class HelloServiceImpl implements HelloService{
    @Override
    public String hello(String name) {
        return "hello " + name;
    }

}
