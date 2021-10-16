package com.lee.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @className: LockSaleTicket
 * @author: li xin
 * @date: 2021-10-16
 **/
class Ticket {
    private int number = 30;

    private ReentrantLock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                number--;
                System.out.println(Thread.currentThread().getName() + "卖票,剩下" + number + "张");
            }
        }finally {
            lock.unlock();
        }

    }
}

public class LockSaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        Thread aaThread = new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "AA");

        Thread bbThread = new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "BB");

        Thread ccThread = new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "CC");

        aaThread.start();
        bbThread.start();
        ccThread.start();
    }
}
