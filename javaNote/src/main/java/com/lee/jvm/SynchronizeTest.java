package com.lee.jvm;

public class SynchronizeTest {

    public static void main(String[] args) {
        synchronized (new Object()) {
            System.out.println("=====================");
        }
    }

}
