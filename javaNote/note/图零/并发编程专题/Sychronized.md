# Synchronized

### 1.1 原理

> 锁对象，每new一个锁对象，同时会new一个Monitor对象(管程对象)，Monitor对象依赖于OS-Mutex互斥量。OS-Mutex是由操作系统来维护的，使用Phread库来维护，在这里需要切换线程状态，所以效率比较低

<img src="../image/image-20220107214037599.png" alt="image-20220107214037599" style="zoom:80%;" />

### 1.2 使用

+ 修饰静态方法：加锁在当前类的类对象上 JUC.class
+ 修饰普通方法：加载当前对象上面，那个对象调用这个方法，就加载这个对象上
+ 方法内部同步块：加载括号内对象上

### 1.3 底层原理

#### 1.3.1 字节码层面

如果是加在方法上，则字节码编译后的方法的flag上有acc-synchronized

<img src="../image/image-20220107223343272.png" alt="image-20220107223343272" style="zoom:80%;" />

如果是使用同步块，会添加monitorenter和monitorexit，monitorexit有两个，处理异常和return

![image-20220107223439407](../image/image-20220107223439407.png)

#### 1.3.2 对象层面

偏向锁默认开启，但是启动会延迟与虚拟机启动4秒后。

如果开启偏向锁的状态，默认对象是匿名偏向状态(可偏向状态)，此时线程id字段为空

##### 1.3.2.1 对象的内存模型

<img src="../image/image-20220107223745248.png" alt="image-20220107223745248" style="zoom:67%;" />

#### 1.3.2.2 锁标志位

> 1、主要看最后三位，001无锁，00轻量级锁、10重量级锁、101偏向锁
>
> 2、轻量级锁的hashcode记录在栈中，发生轻量级锁的时候会在栈内创建一个区域LockRecord，这个区域会赋值锁对象的markword的部分数据，这个过程使用CAS保证线程安全
>
> 3、偏向锁的hashcode有可能记录在monitor对象中，调用锁对象的hashcode方法可以使偏向锁转化为轻量级锁
>
> 4、重量级锁的hashcode记录在monitor对象中
>
> **当锁的状态为重量级锁10的时候他指针区域会指向Monitor对象**

![image-20220108101759999](..\image\sychronized\image-20220108101759999.png)

### 1.4 锁升级过程

**默认无锁或者匿名偏向**----->有线程访问--------> **偏向锁**---->多线程访问------>**轻量级锁**------>轻量级锁自旋超过一定次数------>**重量级锁**

### 1.5 Monitor对象

> monitor对象是当锁升级为重量级锁后，将对象markword指向monitor对象（记录monitor对象地址的起始位置）

```java
ObjectMonitor() {
    _count        = 0; // 记录个数
    _WaitSet      = NULL; // 处于wait状态的线程，会被加入到_WaitSet
    _EntryList    = NULL ; // 处于等待锁block状态的线程，会被加入到该列表
    _owner        = NULL ; // 用于指向获取到锁的ObjectWaiter对象
  }
ObjectMonitor中有两个队列，_WaitSet 和 _EntryList，用来保存ObjectWaiter对象列表（ 每个等待锁的线程都会被封装成ObjectWaiter对象 ），_owner指向持有ObjectMonitor对象的线程，当多个线程同时访问一段同步代码时：
```

> Monitor对象通过ObjectMonitor类来实现，每个线程都会封装成ObjectWaiter对象
>
> 多线程抢锁步骤
>
> + 当前现场换呢过被封装成ObjectWaiter对象，进入到EntryList集合，获取锁
> + 当前线程竞争到锁之后，将owner指向自己，并且count+1，
> + 调用wait方法，重置monitor对象点，count-1，当前对象进入waitSet
> + 执行完毕，释放monitor锁，复位count，以便其他线程抢锁

### 1.6 六十四位虚拟机对象头的指针压缩

#### 1.6.1 设置参数

> 1 手动设置‐XX:+UseCompressedOops

#### 1.6.2 那些数据会被压缩

> 对象的全局静态变量(即类属性)
>
> 对象头信息
>
> 对象的引用类型
>
> 对象的数组类型

