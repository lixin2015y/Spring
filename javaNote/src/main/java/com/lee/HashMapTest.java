package com.lee;

import org.junit.Test;

public class HashMapTest {


    @Test
    public void test1() {

        String key = "key";

        System.out.println(key.hashCode());
        System.out.println(key.hashCode() >>> 16);

    }
}
