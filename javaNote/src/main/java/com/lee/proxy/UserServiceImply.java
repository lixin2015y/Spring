package com.lee.proxy;

/**
 *
 *
 * @className: UserServiceImply
 * @author: li xin
 * @date: 2021-10-14
 **/
public class UserServiceImply implements UserService {

    @Override
    public String sayHello(String name) {
        System.out.println("执行目标方法sayHello");
        return "hello" + name;
    }

}
