package com.lee.algorithm.dongtaiguihua;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Problem2 {


    /**
     * 问题：给定n个台阶，一个青蛙一次可以跳1个台阶，或者两个台阶，问跳上n个台阶一共有多少种跳法
     * <p>
     * 思路：f(n) = f(n-1) + f(n-2) f(1) = 1
     * 最后一跳要么两节，要么一节，所以f(n) = f(n-1) + f(n-2)
     */


    Map<Integer, Integer> memo = new HashMap<>();

    @Test
    public void test() {
        memo.put(1, 1);
        memo.put(2, 2);
        System.out.println(solve(10));
        System.out.println(solve2(10));

    }

    int solve(int n) {
        if (!memo.containsKey(n)) {
            memo.put(n, solve(n - 2) + solve(n - 1));
        }
        return memo.get(n);
    }

    int solve2(int n) {
        for (int i = 3; i <= n; i++) {
            memo.put(i, memo.get(i - 1) + memo.get(i - 2));
        }
        return memo.get(n);
    }


}
