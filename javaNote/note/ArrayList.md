```java
User[] users = new User[]{
    new User(1, "admin", "admin@qq.com"),
    new User(2, "maco", "maco@qq.com"),
    new User(3, "kitty", "kitty@163.com")
};

```



## 关于toArray()
```java
public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
}
// 直接返回数组整个的copy 所以可以直接修改返回结果
``` 
## 关于remove()
```java
    public E remove(int index) {
        rangeCheck(index); //判断当前索引是否存在

        modCount++; //修改次值为了避免迭代是的修改操作
        E oldValue = elementData(index); //拿到旧的元素

        //将需要删除的元素后面的元素往前移一个索引           
        int numMoved = size - index - 1; 
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // clear to let GC do its work
        return oldValue;
    }
```
## clear() 
```java
    public void clear() {
        modCount++;

        // 把数组中所有的元素的值设为null
        for (int i = 0; i < size; i++)
            elementData[i] = null;
            // 将数组大小置零
        size = 0;
    }

```

## boolean addAll(int index,Collection<? extend E> c)
```java
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index); //检查索引是否存在

        Object[] a = c.toArray();// 将集合转化为数组
        int numNew = a.length; 
        ensureCapacityInternal(size + numNew);  // Increments modCount

        int numMoved = size - index; 
        if (numMoved > 0) //如果插入的位置已经有元素。将插入位置元素后面的数据一到index+numNew(插入数组元素长度)
            System.arraycopy(elementData, index, elementData, index + numNew,
                             numMoved);
        //将要插入的数组元素copy到elementData
        System.arraycopy(a, 0, elementData, index, numNew);
        size += numNew;
        return numNew != 0;
    }
```
## 看两者源代码可以发现`copyOf()`内部调用了`System.arraycopy()`方法
1. arraycopy()需要目标数组，将原数组拷贝到你自己定义的数组里，而且可以选择拷贝的起点和长度以及放入新数组中的位置
2. copyOf()是系统自动在内部新建一个数组，并返回该数组。

## ArrayList的内部类
```java
    (1)private class Itr implements Iterator<E>  
    (2)private class ListItr extends Itr implements ListIterator<E>  
    (3)private class SubList extends AbstractList<E> implements RandomAccess  
    (4)static final class ArrayListSpliterator<E> implements Spliterator<E>  
```
ArrayList有四个内部类，其中的**Itr是实现了Iterator接口**，同时重写了里面的**hasNext()**， **next()**， **remove()** 等方法；其中的**ListItr** 继承 **Itr**，实现了**ListIterator接口**，同时重写了**hasPrevious()**， **nextIndex()**， **previousIndex()**， **previous()**， **set(E e)**， **add(E e)** 等方法，所以这也可以看出了 **Iterator和ListIterator的区别:** ListIterator在Iterator的基础上增加了添加对象，修改对象，逆向遍历等方法，这些是Iterator不能实现的。

## 
