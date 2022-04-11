package com.lee.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufDemo {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer(10);
        // 通过readIndex、writeIndex和capacity将数组分成三份
        System.out.println("byteBuf = " + byteBuf);
        for (int i = 0; i < 8; i++) {
            byteBuf.writeByte(i);
        }
        System.out.println("byteBuf = " + byteBuf);
        for (int i = 0; i < 5; i++) {
            System.out.println(byteBuf.getByte(i));
        }
        System.out.println("byteBuf = " + byteBuf);
        for (int i = 0; i < 5; i++) {
            System.out.println(byteBuf.readByte());
        }
        System.out.println("byteBuf = " + byteBuf);
    }
}
