package com.lee.netty.byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AllocDemoHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = ctx.alloc().buffer();
        System.out.println("b.hasArray: " + buf.hasArray());
        System.out.println("b.ByteBufAllocator: " + buf.alloc());
        ByteBuf writeByte = ctx.alloc().buffer();
        writeByte.writeInt(3);
        ctx.pipeline().writeAndFlush(writeByte);
        super.channelRead(ctx, msg);
    }
}
