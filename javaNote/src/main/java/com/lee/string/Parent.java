package com.lee.string;

public class Parent {

    static {
        System.out.println("parent...static");
    }

    public Parent() {
        System.out.println("parent constructor");
    }

    {
        System.out.println("parent...public");
    }



}
