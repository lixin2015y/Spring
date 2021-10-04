package com.lee.thread.pool;

public class RunnableTest implements Runnable{

    @Override
    public void run() {
        System.out.println("继承runnable的run方法");
    }

    public static void main(String[] args) {
        RunnableTest runnableTest = new RunnableTest();
        new Thread(runnableTest).start();
    }
}
