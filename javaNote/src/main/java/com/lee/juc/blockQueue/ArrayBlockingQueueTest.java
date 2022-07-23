package com.lee.juc.blockQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ArrayBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
        while (!blockingQueue.isEmpty()) {
            blockingQueue.put("");
            blockingQueue.poll();
        }
    }
}
