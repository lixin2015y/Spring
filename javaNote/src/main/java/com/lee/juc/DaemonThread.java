package com.lee.juc;

/**
 * 守护线程
 */
public class DaemonThread {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
            while (true) {

            }
        }, "aa");
        thread.setDaemon(true);
        thread.start();
        System.out.println(Thread.currentThread().getName() + ":over");
    }
}
