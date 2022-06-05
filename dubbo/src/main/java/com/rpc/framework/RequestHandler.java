package com.rpc.framework;

import io.netty.channel.ChannelHandlerContext;
import com.rpc.framework.register.LocalRegister;

import java.lang.reflect.Method;

public class RequestHandler implements ChannelHandler {

    @Override
    public void handler(ChannelHandlerContext ctx, Invocation invocation) throws Exception{

        Class serviceImpl = LocalRegister.get(invocation.getInterfaceName());

        Method method = serviceImpl.getMethod(invocation.getMethodName(), invocation.getParamTypes());
        Object result = method.invoke(serviceImpl.newInstance(), invocation.getParams());

        // 返回服务结果
        ctx.writeAndFlush(result);
    }
}
