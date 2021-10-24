package com.lee.juc.forkjoin;


import javax.xml.stream.events.ProcessingInstruction;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class MyTask extends RecursiveTask<Integer> {

    // 计算10以内的计算
    private static final Integer VALUE = 10;

    // 拆分开始值
    private int begin;
    // 拆分结束值
    private int end;
    // 返回结果
    private int result;

    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if ((end - begin) < VALUE) {
            for (int i = begin; i <= end; i++) {
                result = result + i;
            }
        } else {
            // 获取数据中间值
            int middle = (begin + end) / 2;
            MyTask taskLeft = new MyTask(begin, middle);
            MyTask taskRight = new MyTask(middle + 1, end);
            taskLeft.fork();
            taskRight.fork();
            result = taskLeft.join() + taskRight.join();
        }
        return result;
    }
}

public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyTask myTask = new MyTask(1, 100);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = forkJoinPool.submit(myTask);
        Integer integer = submit.get();
        System.out.println(integer);

        // 关闭池对象
        forkJoinPool.shutdown();
    }
}
