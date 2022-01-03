package com.lee.test;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Main2 {

    private int num;

    @Test
    public void test() throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threadList.add(new Thread(() -> {
                for (int i1 = 0; i1 < 100; i1++) {
                    num++;
                }
            }));
        }
        for (Thread thread : threadList) {
            thread.start();
        }
        Thread.sleep(1000);
        System.out.println(num);
    }



    @Test
    public void test2(){
        Object c = new Object();
        Object a = c;
        Object b = c;
        System.out.println(a == b);
        HashSet<Object> set = new HashSet<>();
        Iterator<Object> iterator = set.iterator();

        System.out.println("getNextNumber(123) = " + getNextNumber(123));
    }


    public int getNextNumber(int n) {
        int result = 0;
        while (n >0) {
            int tmp = n % 10;
            result += tmp * tmp;
            n = n / 10;
        }
        return result;
    }
}
