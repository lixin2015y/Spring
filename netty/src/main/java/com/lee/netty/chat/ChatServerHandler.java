package com.lee.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        for (Channel channel : channels) {
            channel.writeAndFlush("[" + ctx.channel().remoteAddress() + "]" + "进来啦"  + "\n");
        }
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
        for (Channel channel : channels) {
            channel.writeAndFlush("[" + ctx.channel().remoteAddress() + "]" + "离开啦" + "\n");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[" + ctx.channel().remoteAddress() + "]" + "上线了" + "\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[" + ctx.channel().remoteAddress() + "]" + "下线了" + "\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
        for (Channel channel1 : channels) {
            if (channel == channel1) {
                channel.writeAndFlush("我说：" + s + "\n");
            } else {
                channel1.writeAndFlush("[" + channel.remoteAddress() + "]说" + s + "\n");
            }
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegister!!!s");
        super.channelRegistered(ctx);
    }
}
