package com.rpc.framework;

import io.netty.channel.ChannelHandlerContext;

public class Task implements Runnable {

    private Invocation invocation;
    private ChannelHandler channelHandler;
    private ChannelHandlerContext ctx;


    public Task(Invocation invocation, ChannelHandler channelHandler, ChannelHandlerContext ctx) {
        this.invocation = invocation;
        this.channelHandler = channelHandler;
        this.ctx = ctx;
    }

    @Override
    public void run() {
        try {
            channelHandler.handler(ctx, invocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
