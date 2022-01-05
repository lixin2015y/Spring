# HashMap1.7

#### 使用大于capicaty的2的次方数进行初始化（用于计算数组下标）

+ 调用Integer类的higestOneBit()
  + 2的次方数就是1000000000000类似这种1后面全0的（只有一个1剩下全是零）
  + 将一个十进制数转化为2进制数
  + 对此二进制数右移1、2、4、8、16 分别进行或运算（有1出1）从左到右第一个非0 会将后面的数全置1，得到一个 类似0111111的32位二进制数
  + 用0000011111数减去此数无符号数右移1位，既可以得到一个类似00000100000000的二进制数，
+ 调用higestOneBit()方法传递的参数是 capicaty<1 -1（相当于乘2减1） 保证找到的一个是大于当前capicaty的最小的二进制数，减1 的目的是防止本身的capicaty就是一个2的次方数、

1001 0010

0100 1001

#### 计算数组下标

+ hash值和数组长度-1进行与运算。
+ 只用到了hash值得后面n位将前面的都置0 ，得到一个位于0到size-1的值
+ 只要一个2的次方的数才能进行-1变成 01111111直接得到对应hash值得数组下标

+ 如果数组的长度很少，hashcode的高位的变化无法影响计算index，容易发生冲突
  + 解决办法，将高位和低位进行异或，高位和低位的变化都会影响结果，这一操作在hash方法中进行,



#### 扩容

+ 大于阈值 （数组长度乘加载因子）并且当前要插入的地方不是空的
+ 扩容
  + 数组扩容到原来的两倍
  + 遍历老数组的所有不为空的元素
  + 循环一个桶的链表，一个个copy到新的table，使用头插入法，所以原来的链表的顺序会逆转，
  + 第二个线程copy到一定数量的元素后，由于第一个线程的操作，已经将链表翻转，所有第二个线程迭代就会造成死循环



# 1.8
+ 数据结构：数组+链表+红黑树
+ 红黑树的性质
    + 节点是红色或黑色
    + 根节点是黑色的
    + 所有叶子节点都是黑色
    + 每个红节点的两个子节点都是黑色
    + 从任意一个节点到其每个叶子节点包含相同数量的黑色节点
    
+ put方法
    + 判断table是否为空，如果是空的就创建一个table，并获取他的长度
    + 如果计算出来的索引位置之前没有放过数据，就直接放入
    + 判断put的数据和之前的数据是否重复
    + 如果是红黑树插入到红黑树中，
    + 不是红黑树则就遍历每个节点，如果长度大于8了就转化为红黑树
    + 遍历过程中找到了原来的key则替换返回旧值
+ get方法
    + 如果列表为空直接返回空，不是空则判断桶的第一个元素是不是要找的
    + 如果不是判断是树还是链表，在相应的数据结构中获取

## 源码分析

### 3.1 构造方法 new HashMap(16)

```java
public HashMap(int initialCapacity, float loadFactor) {
    // 构造方法一般使用capacity一个参数的，使用默认加载因子，
    if (initialCapacity < 0)
        throw new IllegalArgumentException("Illegal initial capacity: " +
                                           initialCapacity);
    if (initialCapacity > MAXIMUM_CAPACITY)
        initialCapacity = MAXIMUM_CAPACITY;
    if (loadFactor <= 0 || Float.isNaN(loadFactor))
        throw new IllegalArgumentException("Illegal load factor: " +
                                           loadFactor);
    this.loadFactor = loadFactor;
    // 在这里初始化数组数组，数组计算过程
    this.threshold = tableSizeFor(initialCapacity);
}
```

### 3.2 数组size计算过程

```java
// 通过位运算来进行数组大小的计算
static final int tableSizeFor(int cap) {
    // 这里减一的目的是为了防止当前入参就是一个2的n次幂数
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    // 通过移位运算后，n由00000000 00000000 00000000 00010001变成了
    // 00000000 00000000 00000000 00011111 这种，这个数只比目标数小1
  	// 如果最终结果不大于最大值，直接返回n+1即可
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}

// java1.7调用的是Integer类的higestOneBit(cap)
// 入参传递  cap < 1 - 1 乘2减1 为了避免当前传递的就是一个2的n次幂数
    int n = cap;
	n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
	return n - n >>> 1;
```

### 3.3 hashCode和数组下标的计算

