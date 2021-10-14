package com.lee.proxy;

import java.lang.reflect.Proxy;

/**
 *
 *
 * @className: Inteceptor
 * @author: li xin
 * @date: 2021-10-14
 **/
public class Interceptor {


    public static void main(String[] args) {
        UserService userService = new UserServiceImply();
        UserServiceProxy userServiceProxy = new UserServiceProxy(userService);
        UserService proxyUserService = (UserService)Proxy.newProxyInstance(UserService.class.getClassLoader(), new Class[]{UserService.class}, userServiceProxy);
        proxyUserService.sayHello("li xin");
    }


}
