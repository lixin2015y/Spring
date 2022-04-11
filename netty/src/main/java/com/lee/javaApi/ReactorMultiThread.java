package com.lee.javaApi;

import com.lee.javaApi.handler.AcceptHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @className: ReactorMultiThread
 * @author: li xin
 * @date: 2022-04-11
 **/
public class ReactorMultiThread {
    ServerSocketChannel serverSocketChannel;
    AtomicInteger next = new AtomicInteger(0);
    Selector[] selectors = new Selector[2];
    SubReactor[] subReactors;


    public ReactorMultiThread() throws IOException {
        // 用于监听新链接事件
        selectors[0] = Selector.open();
        // 用于监听传输事件
        selectors[1] = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9000));
        serverSocketChannel.configureBlocking(false);
        // 注册链接事件
        SelectionKey selectionKey = serverSocketChannel.register(selectors[0], SelectionKey.OP_ACCEPT);
        selectionKey.attach(new AcceptHandler());
        SubReactor subReactor1 = new SubReactor(selectors[0]);
        SubReactor subReactor2 = new SubReactor(selectors[1]);
        subReactors = new SubReactor[]{subReactor1, subReactor2};

    }

    private void startService() {
        new Thread(subReactors[0]).start();
        new Thread(subReactors[1]).start();
    }

    // 负责处理连接事件
     class AcceptHandler implements Runnable{

        @Override
        public void run() {
            SocketChannel socketChannel = null;
            try {
                socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new MultiThreadEchoHandler(selectors[1], channel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ReactorMultiThread reactorMultiThread = new ReactorMultiThread();
        reactorMultiThread.startService();
    }
}
