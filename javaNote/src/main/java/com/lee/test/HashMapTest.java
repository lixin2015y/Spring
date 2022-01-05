package com.lee.test;

import java.util.HashMap;

public class HashMapTest {
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int k = 1;
        for (int i = 0; i < 9; i++) {
            map.put(k = k + 16, i);
        }
    }
}
