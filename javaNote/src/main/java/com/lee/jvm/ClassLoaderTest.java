package com.lee.jvm;

import org.junit.Test;

public class ClassLoaderTest {


    @Test
    public void test1() throws ClassNotFoundException {
        ClassLoader.getSystemClassLoader().loadClass("");
    }
}
