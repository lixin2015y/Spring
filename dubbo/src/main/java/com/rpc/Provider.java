package com.rpc;

import com.rpc.api.HelloService;
import com.rpc.api.HelloServiceImpl;
import com.rpc.framework.NettyServer;
import com.rpc.framework.URL;
import com.rpc.framework.register.LocalRegister;
import com.rpc.framework.register.RemoteRegister;

import java.net.UnknownHostException;

/**
 * 生产者
 */
public class Provider {
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        String interfaceName = HelloService.class.getName();
        URL url = new URL("127.0.0.1", 8081);

        // 本地注册
        LocalRegister.register(interfaceName, HelloServiceImpl.class);

        // zk注册
        RemoteRegister.register(interfaceName, url);

        // 暴露服务
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(url.getHostname(), url.getPort());

        Thread.sleep(Integer.MAX_VALUE);

    }
}
