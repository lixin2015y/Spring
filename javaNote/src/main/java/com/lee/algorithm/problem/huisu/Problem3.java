package com.lee.algorithm.problem.huisu;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 回溯问题
 * 求解排列组合Cnk问题
 */
public class Problem3 {

    private static List<LinkedList<Integer>> result = new ArrayList<>();

    private static void solve(int n, int k, int index, LinkedList<Integer> curr) {

        // 回溯终止条件
        if (curr.size() == k) {
            result.add(new LinkedList<>(curr));
            return;
        }

        for (int i = index; i <= n; i++) {
            curr.addLast(i);
            solve(n, k, index + 1, curr);
            curr.removeLast();
        }
    }

    public static void main(String[] args) {

        // 计算C52
        solve(5, 2, 1, new LinkedList<>());
        System.out.println(result);
    }
}
