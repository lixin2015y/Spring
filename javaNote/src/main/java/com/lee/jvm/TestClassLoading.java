package com.lee.jvm;

public class TestClassLoading {
    /**
     * 使用-XX:+TraceClassLoading
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(SubClass.value);
        // 对于访问字段只有直接定义这个字段的类才会被初始化
    }
}

class SupperClass {
    static {
        System.out.println("父类的静态块");
    }

    public static int value = 123;
}

class SubClass extends SupperClass{
    static {
        System.out.println("子类的静态快");
    }
}
