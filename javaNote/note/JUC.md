## JUC

### 1.什么是JUC

#### 1.1 进程和线程

+ 进程：系统中正在运行的一个应用程序，进程是资源分配的最小单位
+ 线程：系统分配处理器时间的最小单元，程序执行的最小单位

#### 1.2 线程的状态

+ NEW:新建
+ RUNNABLE：可运行
+ BLOCKED：阻塞状态
+ WAITING：等待状态
+ TIMED_WAITING：等待状态
+ TERMINATED：终止状态

#### 1.3 wait和sleep的区别

+ wait会释放锁，sleep不会释放锁
+ sleep在Thread类种，wait是Object类种的
+ 他们都可以被interrupted方法中断

#### 1.4 并发和并行

+ 串行模式：一个任务一个任务的去执行
+ 并行模式：多个任务一起执行，最后再汇总，例如泡方便面，一遍撕做料带，一边烧水
+ 并发：同一个时刻，多个线程访问同一资源



#### 1.5管程（monitor）

+ 监视器，保证同一资源，在同一时间只有一个线程进行访问
+ java中的锁就是通过管程对象通过对临界区进行加锁，进入临界区加锁，退出解锁

#### 1.6 用户线程和守护线程

+ 用户线程：用户自定义线程
  + 主线程已经结束，用户线程还在运行，jvm存在

```java
public static void main(String[] args) {
    Thread thread = new Thread(() -> {
        System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
        while (true) {

        }
    }, "aa");
    thread.start();
    System.out.println(Thread.currentThread().getName() + ":over");
}
```

+ 守护线程：例如垃圾回收，特殊线程，运行在后台
  + 主线程结束，没有了用户线程，jvm结束运行

```java
thread.setDaemon(true);
thread.start();
System.out.println(Thread.currentThread().getName() + ":over");
```

### 2. 锁接口

#### 2.1 Synchronized

+ 修饰代码块

  ​	作用对象是当前锁定对象，范围是同步语句块范围

+ 修饰方法

  ​	作用对象是调用该方法对象，作用范围是整个方法

+ 修饰静态方法

  ​	作用对象是这个类的所有对象，范围是这个静态方法

+ 修饰一个类

  ​	作用对象是这个类所有对象，范围是Synchronized括号范围



#### 2.2 Lock锁和synchronized有什么区别

+ Lock是一个接口，synchronized是java关键字
+ lock不会自动释放锁，synchronized会自动释放锁
+ lock可以让等待的线程相应终端，而synchronized不行，使用synchronized，线程会一直等待下去，不能沟进行中断

### 3. 线程间通信

#### 3.1 虚假唤醒

wait在哪里等待，就会在哪里醒，所以要是用

所以要使用while来替代if，循环判断

#### 3.2 线程间的定制化通信

启动三个线程，10轮循环以下

+ AA执行，打印5次
+ BB执行，打印10次
+ CC线程，打印15次

### 4. 线程安全的集合类

#### 4.1 List线程安全解决办法

+ List<String> list = new Vector<>();
+ Collections.synchronizedList(list);
+ CopyOnWriteArrayList ，写时赋值技术
  + 复制、写入、合并、读取新内容

#### 4.2 HashSet线程安全解决办法

+ CopyOnWriteArraySet

#### 4.3 HashMap线程安全解决办法

+ ConcurrentHashMap
+ HashTable

### 5. 死锁

#### 5.1 重现死锁

```java
new Thread(() -> {
    synchronized (a) {
        System.out.println(Thread.currentThread().getName() + "持有锁A，获取锁B");
        synchronized (b) {
            System.out.println(Thread.currentThread().getName() + "获取到锁B");
        }
    }
}, "AA").start();

new Thread(() -> {
    synchronized (b) {
        System.out.println(Thread.currentThread().getName() + "持有锁B，获取锁A");
        synchronized (a) {
            System.out.println(Thread.currentThread().getName() + "获取到锁A");
        }
    }
}, "BB").start();
```

#### 5.2 检测死锁的方法

使用jps找到线程号，使用jstack 线程号 来看堆栈信息

Java stack information for the threads listed above:

"BB":
        at com.lee.juc.deadLock.DeadLock.lambda$main$1(DeadLock.java:22)
        - waiting to lock <0x000000076b0aebf0> (a java.lang.Object)
                - locked <0x000000076b0aec00> (a java.lang.Object)
                at com.lee.juc.deadLock.DeadLock$$Lambda$2/1922154895.run(Unknown Source)
                at java.lang.Thread.run(Thread.java:748)
"AA":
                at com.lee.juc.deadLock.DeadLock.lambda$main$0(DeadLock.java:13)
                - waiting to lock <0x000000076b0aec00> (a java.lang.Object)
                        - locked <0x000000076b0aebf0> (a java.lang.Object)
                        at com.lee.juc.deadLock.DeadLock$$Lambda$1/793589513.run(Unknown Source)
                        at java.lang.Thread.run(Thread.java:748)

Found 1 deadlock.

### 6. Callable接口

#### 6.1 callable和runnable接口的区别

+ callable接口有返回值
+ callable可以跑出异常
+ callable使用call方法，runnable使用run方法

#### 6.2 代码示例

```java
public class CallableDemo implements Callable {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        return 100;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableDemo callableDemo = new CallableDemo();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(callableDemo);
        new Thread(futureTask, "AA").start();
        Integer integer = futureTask.get();
        System.out.println(integer);
    }
}
```

### 7. JUC辅助类

#### 7.1 CountDownLatch 减少计数

```java
public static void main(String[] args) throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(6);
    for (int i = 0; i < 6; i++) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "走了");
            countDownLatch.countDown();
        }).start();
    }
    countDownLatch.await();
    System.out.println(Thread.currentThread().getName() + "班长锁门了");
}
```

#### 7.2 CyclicBarrier

```java
CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
    System.out.println(Thread.currentThread().getName() + "找齐七科龙珠，召唤神龙");
});

for (int i = 0; i < 7; i++) {
    new Thread(() -> {
        System.out.println(Thread.currentThread().getName() + "找到龙珠");
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }, String.valueOf(i)).start();
}
```

#### 7.3 Semaphore信号灯

```java
// 创建许可
Semaphore semaphore = new Semaphore(3);
for (int i = 0; i < 6; i++) {
    new Thread(() -> {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + "抢到车位");
            TimeUnit.SECONDS.sleep(new Random().nextInt(5));
            System.out.println(Thread.currentThread().getName() + "离开车位");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }, String.valueOf(i)).start();
}
```

### 8. 读写锁

### 9. 阻塞队列

#### 9.1 阻塞队列概述

+ 当队列是空的时候，从队列中获取元素会阻塞
+ 当队列是满的时候，向队列中放入元素会阻塞

好处：不需要关系什么时候进行阻塞，什么时候需要唤醒

#### 9.2 阻塞队列架构

+ ArrayBlockingQueue 数组结构组成有界队列，获取和添加使用同一个锁，不能同时进行
+ LinkedBlockingQueue链表组成的无界的队列，大小默认值长度为Integer.MAX_VALUE
+ DelayQueue 延迟队列
+ SynchronousQueue单个元素队列
+ PriorityBlockingQueue 优先队列

#### 9.3 核心方法

+ add()，如果队列已经满则跑出异常
+ remove()，如果对列是空的则会抛出异常



+ offer(): 添加成功返回true，队列满添加失败返回false 。
  + 此方法可以设置阻塞时间
+ poll()，队列为空则返回空，不为空则返回元素
  + 此方法可以设置阻塞时间



+ put()，队列满则会阻塞
+ take(), 队列为空则阻塞



### 10. 线程池

#### 10.1 线程池的优点

+ 降低创建销毁线程的资源消耗
+ 提高响应速度，任务到达即可执行，不需要等待创建线程
+ 提高线程的可管理性

#### 10.2 线程池的架构

<img src="../image/image-20211024093132446.png" alt="image-20211024093132446" style="zoom:80%;" />

#### 10.3 线程池的使用

##### 10.3.1 newFixedThreadPool

固定线程数的线程池，超过线程数的线程会被提交到阻塞队列等待

##### 10.3.2 newSingleThreadExecutor

一池一线程，

##### 10.3.3 newCachedThreadPool

线程池数量可以扩容，遇强则强

#### 10.4 线程池的七大参数 ThreadPoolExecutor

+ int corePoolSize
  + 核心线程数量，如果当前线程数未达到这个数，则优先创建新的线程
+ int maximumPoolSize
  + 最大线程数，超过这个数量的线程，会先进入阻塞队列中
+ long keepAliveTime 
  + 超过核心线程数的线程无任务状态存活时间
+ TimeUnit unit
+ BlockingQueue<Runnable> workQueue
  + 阻塞队列
+ ThreadFactory threadFactory
  + 线程创建工厂
+ RejectedExecutionHandler handler
  + 拒绝策略

#### 10.5 四种基本拒绝策略

+ 默认，直接抛出异常
+ 将任务回退到调用者
+ 摒弃队列中等待最久的任务，将当前任务加入到队列中
+ 默默丢弃当前任务，什么也不做

### 11. fork/join分支合并框架

> 可以将一个大的任务拆分，分成多个子任务，并将子任务结果进行合并输出



```java
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
```

### 12. 异步回调

```java
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
```

