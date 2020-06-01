package com.lee.jvm;

import org.junit.Test;

public class TestStringIntern {


    @Test
    public void test() {

        String a = "123";
        String b = "123";
        System.out.println(a == b);

        String c = "d" ;
        String d = new String( "d" ).intern() ;
        System.out.println( c == d);


        Integer e = new Integer(32);
        Integer f = new Integer(32);
        System.out.println(e == f);
        // 在这里不会调用Integer.valueOf方法就不会用到缓存，

        Integer g = 32;
        Integer h = 32;
        // 自动拆装箱，调用Integer.valueOf方法使用缓存
        System.out.println(g == h);

        /**
         * public static Integer valueOf(int i) {
         *         if (i >= IntegerCache.low && i <= IntegerCache.high)
         *             return IntegerCache.cache[i + (-IntegerCache.low)];
         *         return new Integer(i);
         *     }
         */

    }
}
