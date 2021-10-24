package com.lee.juc.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            // 这里可以模拟异常情况，
//            int a = 1 / 0;

            return 1024;
        });
        completableFuture.whenComplete((result, exp) -> {
            System.out.println("result = " + result);
            System.out.println("exp.getMessage() = " + exp.getMessage());
        }).get();
    }
}
