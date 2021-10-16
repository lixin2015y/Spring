package com.lee.juc.share;

import org.junit.Test;

/**
 * @className: RunOnOrder
 * @author: li xin
 * @date: 2021-10-16
 **/
public class Run {
    /**
     * 启动三个线程，10轮循环以下
     *
     * + AA执行，打印5次
     * + BB执行，打印10次
     * + CC线程，打印15次
     */
    @Test
    public void test1(){
        ThreadDemo3Resource resource = new ThreadDemo3Resource();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                resource.print5(i);
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                resource.print10(i);
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                resource.print15(i);
            }
        }, "CC").start();
    }
}

