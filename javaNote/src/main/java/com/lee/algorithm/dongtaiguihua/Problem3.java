package com.lee.algorithm.dongtaiguihua;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Problem3 {


    /**
     * 给定一个数字n，分割它，使得到的分割部分乘机最大
     */

    @Test
    public void test() {
        System.out.println("breakInteger(10) = " + breakInteger(10));
    }

    Map<Integer, Integer> memo = new HashMap<>();

    private int breakInteger(int n) {

        if (n == 1) {
            return 1;
        }

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        int result = -1;

        for (int i = 1; i <= n -1; i++) {
            result = Math.max(result, n * breakInteger((n - 1)));
        }

        memo.put(n, result);
        return result;
    }


    // 利用动态规划，
}
