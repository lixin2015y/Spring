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

### 2. Lock接口

#### 2.1 Synchronized

+ 修饰代码块

  ​	作用对象是当前锁定对象，范围是同步语句块范围

+ 修饰方法

  ​	作用对象是调用该方法对象，作用范围是整个方法

+ 修饰静态方法

  ​	作用对象是这个类的所有对象，范围是这个静态方法

+ 修饰一个类

  ​	作用对象是这个类所有对象，范围是Synchronized括号范围



#### 2.2

#### 2.3