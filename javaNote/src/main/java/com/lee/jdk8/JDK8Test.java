package com.lee.jdk8;

import org.junit.Test;

public class JDK8Test {

    @FunctionalInterface
    public interface Converter<T, F> {
        T convert(F v);
    }

    @Test
    public void test() {
        Converter<Integer, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert(1);
        System.out.println(converted);   // 123
    }

}
