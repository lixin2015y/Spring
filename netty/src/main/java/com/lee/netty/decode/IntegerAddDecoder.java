package com.lee.netty.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class IntegerAddDecoder extends ReplayingDecoder<IntegerAddDecoder.Process> {

    enum Process {
        PROCESS1,
        PROCESS2
    }

    public IntegerAddDecoder() {
        super(Process.PROCESS1);
    }

    int first;
    int second;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case PROCESS1:
                first = in.readInt();
                checkpoint(Process.PROCESS2);
                break;
            case PROCESS2:
                second = in.readInt();
                out.add(first + second);
                checkpoint(Process.PROCESS1);
                break;
            default:
                break;
        }


    }
}
