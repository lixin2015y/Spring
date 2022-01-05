



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

### 3.3 pur方法，hashCode和数组下标的计算

#### 3.3.1 putVal()

```java
// 調用putval之前会调用hash方法，
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}
// 这里将高位与地位进行异或运算，为了防止数组长度比较小，高位的hash无法参与到数组下标的计算
static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
```

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    if ((tab = table) == null || (n = tab.length) == 0)
       	// 这里对数组进行了初始化，调用空构造方法的时候，只是制定了加载因子，未进行初始化
        n = (tab = resize()).length;
    // 这里使用hash值与数组size-1进行与运算计算下标
    if ((p = tab[i = (n - 1) & hash]) == null)
        // 这个桶里面没有元素，直接创建一个node，放进去
        tab[i] = newNode(hash, key, value, null);
    else {
        // 已经有元素，代表出现了hash冲突
        Node<K,V> e; K k;
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            // 这里先比对hash值，在比对key值
            e = p;
        else if (p instanceof TreeNode)
            // 如果顶层节点是红黑树类型，则调用红黑树插入
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            // 不是红黑树，说明还是链表
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    // 如果下一个是null，则可以直接插入，因为首个元素在前面已经判断过了
                    p.next = newNode(hash, key, value, null);
                    // 当前循环走完binCount才会++，也就是说，只有原有的元素>=8的时候
                    // 或者插入新元素完后，链表长度>8才转化为红黑树
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                // 如果当前元素
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        // 这里如果是已经存在的key，需要将原来的值返回
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
    // 维护size，如果大于
    if (++size > threshold)
        resize();
    // 这里是给linkedHashmap插入元素后进行的回调
    afterNodeInsertion(evict);
    return null;
}
```

#### 3.4.2 如果链表长度大于8

```java
final void treeifyBin(Node<K,V>[] tab, int hash) {
    int n, index; Node<K,V> e;
    if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
        // 如果数组长度还小于64呢，优先进行扩容，不转红黑树
        resize();
    else if ((e = tab[index = (n - 1) & hash]) != null) {
        TreeNode<K,V> hd = null, tl = null;
        do {
            TreeNode<K,V> p = replacementTreeNode(e, null);
            if (tl == null)
                hd = p;
            else {
                p.prev = tl;
                tl.next = p;
            }
            tl = p;
        } while ((e = e.next) != null);
        if ((tab[index] = hd) != null)
            hd.treeify(tab);
    }
}
```





### 3.4 扩容方法，兼容了初始化方法

```java
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    int oldThr = threshold;
    int newCap, newThr = 0;
    if (oldCap > 0) {
    	// 之前未进行初始化过，在此处扩容
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        // 扩容两倍
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1; // double threshold
    }
    else if (oldThr > 0) // initial capacity was placed in threshold
        newCap = oldThr;
    else {               // zero initial threshold signifies using defaults
        newCap = DEFAULT_INITIAL_CAPACITY;
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
    if (newThr == 0) {
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
    threshold = newThr;
    @SuppressWarnings({"rawtypes","unchecked"})
    Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
    // 老数组原来有值， 需要进行节点迁移
    if (oldTab != null) {
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            // 遍历所有的桶，如果当前桶不为空
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;
                if (e.next == null)
                    // 当前桶只有一个节点
                	// 直接计算数组下标
                    newTab[e.hash & (newCap - 1)] = e;
                else if (e instanceof TreeNode)
                    // 如果当前桶是红黑树
                    // 
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else { // preserve order
                    // 当前节点是链表，
                    Node<K,V> loHead = null, loTail = null;
                    Node<K,V> hiHead = null, hiTail = null;
                    Node<K,V> next;
                    do {
                        next = e.next;
                        // 这里使用hash值与cap进行与运算
                        // 将此链表上的数据分成两份，
                        if ((e.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        // 高位为0的直接放到对应的index下面
                        newTab[j] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        // 高位为1的放到index+cap下面
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    return newTab;
}
```

### 3.5 插入红黑树方法

+ 红黑树结构定义

```java
static final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
    TreeNode<K,V> parent;  // red-black tree links
    TreeNode<K,V> left;
    TreeNode<K,V> right;
    TreeNode<K,V> prev;    // needed to unlink next upon deletion
    boolean red;
    TreeNode(int hash, K key, V val, Node<K,V> next) {
        super(hash, key, val, next);
    }
}
```

+ 插入红黑树节点

```java
final TreeNode<K,V> putTreeVal(HashMap<K,V> map, Node<K,V>[] tab,
                               int h, K k, V v) {
    Class<?> kc = null;
    boolean searched = false;
    TreeNode<K,V> root = (parent != null) ? root() : this;
    for (TreeNode<K,V> p = root;;) {
        int dir, ph; K pk;
        if ((ph = p.hash) > h)
            dir = -1;
        else if (ph < h)
            dir = 1;
        else if ((pk = p.key) == k || (k != null && k.equals(pk)))
            return p;
        else if ((kc == null &&
                  (kc = comparableClassFor(k)) == null) ||
                 (dir = compareComparables(kc, k, pk)) == 0) {
            if (!searched) {
                TreeNode<K,V> q, ch;
                searched = true;
                if (((ch = p.left) != null &&
                     (q = ch.find(h, k, kc)) != null) ||
                    ((ch = p.right) != null &&
                     (q = ch.find(h, k, kc)) != null))
                    return q;
            }
            dir = tieBreakOrder(k, pk);
        }

        TreeNode<K,V> xp = p;
        if ((p = (dir <= 0) ? p.left : p.right) == null) {
            Node<K,V> xpn = xp.next;
            TreeNode<K,V> x = map.newTreeNode(h, k, v, xpn);
            if (dir <= 0)
                xp.left = x;
            else
                xp.right = x;
            xp.next = x;
            x.parent = x.prev = xp;
            if (xpn != null)
                ((TreeNode<K,V>)xpn).prev = x;
            moveRootToFront(tab, balanceInsertion(root, x));
            return null;
        }
    }
}
```



### 3.6 扩容时处理treeNode，有可能将红黑树转为链表

```java
final void split(HashMap<K,V> map, Node<K,V>[] tab, int index, int bit) {
    TreeNode<K,V> b = this;
    // Relink into lo and hi lists, preserving order
    TreeNode<K,V> loHead = null, loTail = null;
    TreeNode<K,V> hiHead = null, hiTail = null;
    int lc = 0, hc = 0;
    for (TreeNode<K,V> e = b, next; e != null; e = next) {
        next = (TreeNode<K,V>)e.next;
        e.next = null;
        if ((e.hash & bit) == 0) {
            if ((e.prev = loTail) == null)
                loHead = e;
            else
                loTail.next = e;
            loTail = e;
            ++lc;
        }
        else {
            if ((e.prev = hiTail) == null)
                hiHead = e;
            else
                hiTail.next = e;
            hiTail = e;
            ++hc;
        }
    }

    if (loHead != null) {
        // 在这里判断链表长度小于6，分解红黑树
        if (lc <= UNTREEIFY_THRESHOLD)
            tab[index] = loHead.untreeify(map);
        else {
            tab[index] = loHead;
            if (hiHead != null) // (else is already treeified)
                loHead.treeify(tab);
        }
    }
    if (hiHead != null) {
        if (hc <= UNTREEIFY_THRESHOLD)
            tab[index + bit] = hiHead.untreeify(map);
        else {
            tab[index + bit] = hiHead;
            if (loHead != null)
                hiHead.treeify(tab);
        }
    }
}
```

### 3.7 remove方法

```java
public V remove(Object key) {
    Node<K,V> e;
    return (e = removeNode(hash(key), key, null, false, true)) == null ?
        null : e.value;
}
```

```java
final Node<K,V> removeNode(int hash, Object key, Object value,
                           boolean matchValue, boolean movable) {
    Node<K,V>[] tab; Node<K,V> p; int n, index;
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (p = tab[index = (n - 1) & hash]) != null) {
        Node<K,V> node = null, e; K k; V v;
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            // 使用hashCode计算出来的桶的元素就是这个元素
            node = p;
        else if ((e = p.next) != null) {
            if (p instanceof TreeNode)
                // 如果是红黑树，就从红黑树里面找
                node = ((TreeNode<K,V>)p).getTreeNode(hash, key);
            else {
                // 从链表里面找
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key ||
                         (key != null && key.equals(k)))) {
                        node = e;
                        break;
                    }
                    p = e;
                } while ((e = e.next) != null);
            }
        }
        if (node != null && (!matchValue || (v = node.value) == value ||
                             (value != null && value.equals(v)))) {
            if (node instanceof TreeNode)
                // 在此方法内会判断当前树的左右子节点或者左子节点的子节点为空则分解红黑树
                ((TreeNode<K,V>)node).removeTreeNode(this, tab, movable);
            else if (node == p)
                tab[index] = node.next;
            else
                p.next = node.next;
            ++modCount;
            --size;
            afterNodeRemoval(node);
            return node;
        }
    }
    return null;
}
```