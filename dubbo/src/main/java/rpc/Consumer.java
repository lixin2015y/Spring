package rpc;

import rpc.api.HelloService;
import rpc.framework.Invocation;
import rpc.framework.NettyClient;
import rpc.framework.ProxyFactory;

import java.util.List;

public class Consumer {
    public static void main(String[] args) {
//        Invocation invocation = new Invocation(HelloService.class.getName(), "hello", new Class[]{String.class}, new Object[]{"周瑜大都督"});
//        NettyClient nettyClient = new NettyClient();
//        String result = nettyClient.send("127.0.0.1", 8081, invocation);
//        System.out.println(result);



        // 上面是直接创建一个client与provider通信
        // 通过代理实现
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        helloService.hello("我是李新");
//        System.out.println(result);

    }
}
