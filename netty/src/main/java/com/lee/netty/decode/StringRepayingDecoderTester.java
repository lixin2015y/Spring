package com.lee.netty.decode;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

public class StringRepayingDecoderTester {

    public static void main(String[] args) throws InterruptedException {
        ChannelInitializer<Channel> i = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) {
                ch.pipeline().addLast(new StringRepayingDecoder());
                ch.pipeline().addLast(new StringProcessDecoder());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        String content = "疯狂创客圈";
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        for (int j = 0; j < 100; j++) {
            Random random = new Random();
            // 1-3随机
            int r = random.nextInt(3) + 1;
            ByteBuf buffer = Unpooled.buffer();
            buffer.writeInt(r * bytes.length);
            for (int c = 0; c < r; c++) {
                buffer.writeBytes(bytes);
            }
            channel.writeInbound(buffer);
        }

        Thread.sleep(Integer.MAX_VALUE);


    }
}
