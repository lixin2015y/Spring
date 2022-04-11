package com.lee.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ChatClient {

    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup workGroup = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
                    channel.pipeline()
                            .addLast("framer", new DelimiterBasedFrameDecoder(2048, Delimiters.lineDelimiter()))
                            .addLast("decoder", new StringDecoder(CharsetUtil.UTF_8))
                            .addLast("encoder", new StringEncoder(CharsetUtil.UTF_8))
                            .addLast("MyNettyClientHandler", new ChatClientHandler());
                }
            });
            Channel channel = bootstrap.connect("localhost", 9000).sync().channel();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                channel.writeAndFlush(reader.readLine() + "\n");
            }

        } finally {
            workGroup.shutdownGracefully();
        }
    }
}
