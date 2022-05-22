package com.lee.netty.echoServer;

import com.lee.utils.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf b = (ByteBuf) msg;
        byte[] bytes = new byte[b.readableBytes()];
        b.getBytes(0, bytes);
        Logger.info("客户到收到数据：" + new String(bytes, "UTF-8"));
        super.channelRead(ctx, msg);
    }
}
