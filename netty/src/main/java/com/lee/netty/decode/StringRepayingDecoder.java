package com.lee.netty.decode;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class StringRepayingDecoder extends ReplayingDecoder<Integer> {

    public StringRepayingDecoder() {
        // 1 代表1阶段该解析长度
        // 2 代表2阶段，该解析字符串
        super(1);
    }

    private Integer length;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case 1:
                length = in.readInt();
                checkpoint(2);
                break;
            case 2:
                byte[] bytes = new byte[length];
                in.readBytes(bytes, 0, length);
                out.add(new String(bytes, "UTF-8"));
                checkpoint(1);
                break;
            default:
                break;
        }
    }
}
