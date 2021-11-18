package com.lee.atomic;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class Atomic {

//    @Test
//    public void test(){
//        AtomicIntegerFieldUpdater<Student> id = AtomicIntegerFieldUpdater.newUpdater(Student.class, "old");
//        Student student = new Student(1, 25);
//        id.getAndIncrement(student);
//        System.out.println(id.get(student));
//    }

    class Student {
        private Integer id;
        public volatile int old;

        public Student(Integer id, int old) {
            this.id = id;
            this.old = old;
        }
    }


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        long memoryAddress = unsafe.allocateMemory(8);
        System.out.println("memoryAddress = " + memoryAddress);
        // 写入内存
        unsafe.putAddress(memoryAddress, 12321312321L);

        long addressValue = unsafe.getAddress(memoryAddress);
        System.out.println("addressValue = " + addressValue);


        // 释放内存
        unsafe.freeMemory(memoryAddress);
    }
}
