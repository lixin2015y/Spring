package com.lee.javaApi;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// 单线程，等待连接，只能处理一个连接
// 同时在等待连接和read的时候都会阻塞线程
public class BioDemoSingleThread {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            System.out.println("客戶端等待连接。。。。。");
            Socket socket = serverSocket.accept();
            System.out.println("客户端获取连接");
            byte[] bytes = new byte[1024];
//            int read;
//            while ((read = socket.getInputStream().read(bytes)) != -1) {
//                System.out.println(new String(bytes, 0, read));
//            }
            int read = socket.getInputStream().read(bytes);
            System.out.println(new String(bytes, 0, read));
            socket.getOutputStream().write("你好".getBytes());
        }
    }
}
