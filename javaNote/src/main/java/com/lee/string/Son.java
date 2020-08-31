package com.lee.string;

public class Son extends Parent {

    static {
        System.out.println("son....static");
    }

    public Son() {
        System.out.println("son constructor");
    }

    {
        System.out.println("son....public");
    }


    public static void main(String[] args) {
        new Son();
    }
}
