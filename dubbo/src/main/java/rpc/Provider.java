package rpc;

import rpc.api.HelloService;
import rpc.api.HelloServiceImpl;
import rpc.framework.NettyServer;
import rpc.framework.URL;
import rpc.framework.register.LocalRegister;
import rpc.framework.register.RemoteRegister;
import sun.util.locale.provider.TimeZoneNameUtility;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

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
