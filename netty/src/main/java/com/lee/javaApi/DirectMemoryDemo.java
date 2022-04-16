package com.lee.javaApi;

import java.nio.ByteBuffer;
import java.security.PublicKey;

public class DirectMemoryDemo {
    public static void main(String[] args) {
        heapAccess();
        directMemory();


        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            ByteBuffer.allocate(100);
        }
        long end = System.currentTimeMillis();
    }

    public static void heapAccess() {
        long start = System.currentTimeMillis();
        ByteBuffer buffer = ByteBuffer.allocate(5000);
        for (int i = 0; i < 1000; i++) {
            buffer.putInt(i);
        }
        buffer.flip();
        for (int i = 0; i < 1000; i++) {
            buffer.getInt();
        }
        buffer.clear();
        long end = System.currentTimeMillis();
        System.out.println("堆内存当前耗时:::" + (end - start));
    }

    public static void directMemory() {
        long start = System.currentTimeMillis();
        ByteBuffer bytebuffer = ByteBuffer.allocateDirect(5000);
        for (int i = 0; i < 1000; i++) {
            bytebuffer.putInt(i);
        }
        bytebuffer.flip();
        for (int i = 0; i < 1000; i++) {
            bytebuffer.getInt();
        }
        bytebuffer.clear();
        long end = System.currentTimeMillis();
        System.out.println("直接内存当前耗时:::" + (end - start));
    }




}
