package com.lee.algorithm.problem.dtgh;

/**
 * 01背包问题
 */

import org.junit.Test;
import org.omg.CORBA.INTERNAL;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: Problem3
 * @author: li xin
 * @date: 2021-12-19
 **/
public class Problem3 {


    /**
     * 问题：有一个背包，容量为C，现在又n种不同的物品，编号为0。。。。n-1，每种物品的重量为W(i)，价值为v(i)，
     * 问这个背包中盛放那些物品，使不超过容量基础上，物品的价值最大。
     *
     * 思路：
     *  1、设计行数F(n,C) 将n个物品放进容量为C的背包，使得价值最大
     *  状态转移方程
     *  F(i,c) = F(i-1,c) = V(i) + F(i-1, c-w(i))
     *  F(i,c) = Max(F(i-1,c), F(i-1, c-w(i))
     */


    /**
     * 自定向下
     */

    /**
     * 用0-n的物品，填充容量为c的背包的最优解
     *
     * @param w 重量
     * @param v 价值
     * @param n n
     * @param c 剩余容量
     * @return
     */
    private Map<String, Integer> memo = new HashMap<>();

    public int bestValue(int[] w, int[] v, int n, int c) {
        if (n < 0 || c <= 0) {
            return 0;
        }
        if (memo.containsKey(n + "-" + c)) {
            return memo.get(n + "-" + c);
        }

        int res = bestValue(w, v, n - 1, c);

        if (c > w[n]) {
            res = Math.max(v[n] + bestValue(w, v, n - 1, c - w[n]), res);
        }

        memo.put(n + "-" + c, res);

        return res;
    }

    @Test
    public void test() {

    }

}
