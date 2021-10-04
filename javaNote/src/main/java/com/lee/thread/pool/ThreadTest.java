package com.lee.thread.pool;

public class ThreadTest extends Thread {


    @Override
    public void run() {
        System.out.println("继承thread的run方法");
    }

    public static void main(String[] args) {
        new ThreadTest().start();
    }
}
