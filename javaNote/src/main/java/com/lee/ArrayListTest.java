package com.lee;

import org.junit.Test;

import java.util.ArrayList;

public class ArrayListTest {

    @Test
    public void test() {
        final ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
    }


}
