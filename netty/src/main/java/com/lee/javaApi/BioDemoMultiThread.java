package com.lee.javaApi;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 单线程处理链接，多线程read
public class BioDemoMultiThread {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(9000);

        while (true) {
            System.out.println("客戶端等待连接。。。。。");
            Socket socket = serverSocket.accept();
            System.out.println("客户端获取连接。。。。。");
//            handler(clientSocket);

            executorService.execute(() -> {
                try {
                    handler(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void handler(Socket socket) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("准备read。。");
        //接收客户端的数据，阻塞方法，没有数据可读时就阻塞

        int read;
        while ((read = socket.getInputStream().read(bytes)) != -1) {
            String finishStr = "F";
            String curStr;
            System.out.println(curStr = new String(bytes, 0, read));
            if (finishStr.equals(curStr)) {
                System.out.println("read完毕。。");
                break;
            }
        }
        socket.getOutputStream().write("ACK.........".getBytes());
        socket.getOutputStream().flush();
    }
}
