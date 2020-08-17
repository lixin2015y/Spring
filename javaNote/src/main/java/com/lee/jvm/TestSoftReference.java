package com.lee.jvm;

import org.junit.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Arrays;

public class TestSoftReference {


    static class Person {

        private String name;
        private Byte[] bytes = new Byte[1024 * 1024];

        public Person(String name) {
            this.name = name;
        }
    }

    /**
     * 测试软引用
     * -Xms8m -Xmx8m -XX:+PrintGCDetails 限定堆的大小，
     */
    @Test
    public void test() throws InterruptedException {

        Person person = new Person("张三");
//        Person person2 = new Person("张三"); 我们设定的内存同时创建两个person就会报oom
        SoftReference<Person> softReference = new SoftReference<>(person);

        person = null;//去掉强引用，new Person("张三")的这个对象就只有软引用了

        //在此处创建一个对象，使得内存不足需要回收软引用 如果不创建这个对象，软引用是不会被回收的，线面还是能得到李四对象。
        Person anotherPerson = new Person("李四");

        Thread.sleep(1000);

        System.err.println("软引用的对象 ------->" + softReference.get());

    }

    /**
     * 利用软引用队列，来回收SoftReference对象
     */
    @Test
    public void test2() throws InterruptedException {
        Person person = new Person("张三");
        ReferenceQueue<Person> queue = new ReferenceQueue<>();
        SoftReference<Person> softReference = new SoftReference<Person>(person, queue);

        person = null;//去掉强引用，new Person("张三")的这个对象就只有软引用了

        Person anotherPerson = new Person("李四");
        Thread.sleep(1000);

        System.err.println("软引用的对象 ------->" + softReference.get());

        Reference softPollRef = queue.poll();
        if (softPollRef != null) {
            System.err.println("SoftReference对象中保存的软引用对象已经被GC，准备清理SoftReference对象");
            //清理softReference
        }

    }


    @Test
    public void test5(){
        int[] arr = {1, 4, 2};
        Arrays.sort(arr);

    }

}
