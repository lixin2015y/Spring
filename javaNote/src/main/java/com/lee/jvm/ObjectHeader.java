package com.lee.jvm;

import org.openjdk.jol.info.ClassLayout;

public class ObjectHeader {
    public static void main(String[] args) {
        Object monitor = new Object();
        synchronized (monitor) {
            System.out.println(ClassLayout.parseInstance(monitor).toPrintable());
        }
    }
}
