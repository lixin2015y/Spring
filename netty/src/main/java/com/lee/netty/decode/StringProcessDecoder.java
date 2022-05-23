package com.lee.netty.decode;


import com.lee.utils.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class StringProcessDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String s = (String) msg;
        Logger.info("读取到数据" + s);
        super.channelRead(ctx, msg);
    }
}
