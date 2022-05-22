package com.lee.netty.echoServer;

import com.lee.utils.Dateutil;
import com.lee.utils.Logger;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.concurrent.CancellationException;

public class ClientDemo {
    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) {
                ch.pipeline().addLast(new ClientHandler());
            }
        });
        bootstrap.remoteAddress("localhost", 9000);
        ChannelFuture f = bootstrap.connect();
        f.addListener((ChannelFuture futureListener) ->
        {
            if (futureListener.isSuccess()) {
                Logger.info("EchoClient客户端连接成功!");

            } else {
                Logger.info("EchoClient客户端连接失败!");
            }

        });
        f.sync();
        Scanner scanner = new Scanner(System.in);
        Logger.tcfo("请输入发送内容:");

        while (scanner.hasNext()) {
            //获取输入的内容
            String next = scanner.next();
            byte[] bytes = (Dateutil.getNow() + " >>" + next).getBytes("UTF-8");
            //发送ByteBuf
            ByteBuf buffer = f.channel().alloc().buffer();
            buffer.writeBytes(bytes);
            f.channel().writeAndFlush(buffer);
            Logger.tcfo("请输入发送内容:");

        }
    }
}
