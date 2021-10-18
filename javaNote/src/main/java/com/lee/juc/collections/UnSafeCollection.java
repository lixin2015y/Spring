package com.lee.juc.collections;

import java.util.*;

/**
 * 多线程下集合不安全，并发修改异常
 * java.util.ConcurrentModificationException
 * 解决办法
 * 1、List<String> list = new Vector<>();
 * 2、Collections.synchronizedList(list);
 * 3、CopyOnWriteArrayList
 */
public class UnSafeCollection {
    public static void main(String[] args) {
//        List<String> list = new Vector<>();
        List<String> list = new ArrayList<>();
        Collections.synchronizedList(list);
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
