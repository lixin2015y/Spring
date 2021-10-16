package com.lee.juc.share;

/**
 * 线程间的通信
 * @className: Share
 * @author: li xin
 * @date: 2021-10-16
 **/
class ResourceLock {
    private int number = 0;

    public synchronized void incr() throws InterruptedException {
        // 这里的while如果替换成if会出现虚假唤醒现象
        while (number != 0) {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + String.format("当前值：%d，操作后:%d", number++, number));
        this.notifyAll();
    }


    public synchronized void decr() throws InterruptedException {
        // 这里的while如果替换成if会出现虚假唤醒现象
        while (number != 1) {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + String.format("当前值：%d，操作后:%d", number--, number));
        this.notifyAll();
    }
}

public class Share {
    public static void main(String[] args) {
        Resource resource = new Resource();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10000; i++) {
                    resource.incr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AA").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10000; i++) {
                    resource.decr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BB").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10000; i++) {
                    resource.incr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "CC").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10000; i++) {
                    resource.decr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "DD").start();
    }
}
