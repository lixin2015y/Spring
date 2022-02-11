package com.lee.test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.size();
        int k = 1;
        for (int i = 0; i < 9; i++) {
            map.put(k = k + 16, i);
        }

        String a = "123";
        StringBuilder sb = new StringBuilder(a);
        StringBuilder reverse = sb.reverse();

    }
}
