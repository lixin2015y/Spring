package rpc.framework;

import com.alibaba.dubbo.rpc.cluster.LoadBalance;
import rpc.framework.register.RemoteRegister;
import sun.rmi.runtime.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class ProxyFactory {
    public static <T> T getProxy(final Class interfaceClass) {

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(), method.getParameterTypes(), args);

                try {
                    NettyClient nettyClient = new NettyClient();

                    List<URL> urls = RemoteRegister.get(interfaceClass.getName());
                    URL url = urls.get(0);

                    System.out.println("消费者选择的服务提供者地址是："+ url.toString());
                    String result = nettyClient.send(url.getHostname(), url.getPort(), invocation);
                    System.out.println(result);
                    return result;
                } catch (Exception e) {
                    return doMock(invocation);
                }
            }
        });
    }


    private static Object doMock(Invocation invocation) {
        return "容错逻辑";
    }
}
