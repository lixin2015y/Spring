package com.lee.collection;

import org.junit.Test;

import java.util.ArrayList;

/**
 * @className: ArrayListTest
 * @author: li xin
 * @date: 2021-10-14
 **/
public class ArrayListTest {

    @Test
    public void test() {
        ArrayList<Integer> list = new ArrayList<Integer>(10);
        for (int i = 0; i < 11; i++) {
            list.add(i);
        }

        System.out.println(list.size());
    }
}
