package com.lee.netty.decode;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/


import com.lee.utils.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class Byte2StringDecoder extends ByteToMessageDecoder {
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in,
                       List<Object> out) {
        Logger.info(in);
        String s = in.toString(CharsetUtil.UTF_8);
        Logger.info(s);
        // 上层byteToMessage会判断解码之后的可读长度是不是和解码之前相等，如果相等则会抛出异常
        in.readBytes(in.readableBytes());
        out.add(s);
    }
}
