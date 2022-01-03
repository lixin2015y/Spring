package com.lee.test;

import com.sun.xml.internal.stream.util.ThreadLocalBufferAllocator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.omg.CORBA.StringHolder;

import java.util.Arrays;

@Slf4j
public class Test1 {

    private int[] result;

    private  static int insertIndex = 0;

    public int[] spiralOrder(int[][] matrix) {
        if (matrix.length == 0) {
            return new int[0];
        }

        this.result = new int[100];

        // 对于M*N的矩阵来说，第一行为M[0]的所有元素
        // 对于任意两点 (x1,y1) (x2,y2)可以确定一个矩阵
        int x1 = 0, y1 = 0;
        int x2 = matrix[0].length - 1;
        int y2 = matrix.length - 1;
        while (x1 <= x2 && y1 <= y2) {
            print(matrix, x1++, y1++, x2--, y2--);
        }
        return this.result;

    }

    private void print(int[][] matrix ,int x1, int y1, int x2, int y2) {
        // 从左到右，
        for (int i = x1; i <= x2; i++) {
            result[insertIndex++] = matrix[i][y1];
        }
        // 从上往下打
        for (int i = y1; i <= y2; i++) {
            result[insertIndex++] = matrix[x2][i];
        }
        // 从右往左打
        for (int i = x2; i >= x1; i--) {
            result[insertIndex++] = matrix[i][y2];
        }
        //
        for (int i = y2; i >= y1; i--) {
            result[insertIndex++] = matrix[x1][i];
        }
    }

    @Test
    public void test(){
        int[][] array = {{1, 2, 3}, {4, 5, 6,}, {7, 8, 9}};
        int[] ints = spiralOrder(array);
        System.out.println(Arrays.toString(ints));
    }


    @Test
    public void test2(){
        System.out.println("true = " + Boolean.toString(true));
        log.info("P{}", true);
    }

    class A {
        private String a;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

    }

    class B extends A {

        private String b;

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

    }



    ThreadLocal<A> objectThreadLocal = new ThreadLocal<>();
    @Test
    public void test3(){
        B b = new B();
        b.setB("b");
        b.setA("a");
        A a = new A();
        a.setA("a");
        objectThreadLocal.set(a);
        String s = innerMethod();
        System.out.println("s = " + s);
    }

    private String innerMethod() {
        B bb = (B)objectThreadLocal.get();
        return bb.toString();
    }

}
