package com.lee.algorithm.problem.dtgh;


import com.lee.algorithm.utils.ArrayUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 偷房子问题，
 * @className: Problem2
 * @author: li xin
 * @date: 2021-12-18
 **/
public class Problem2 {

    /**
     *
     * 给定n个房子，房子中有不同的价值的宝物，不能同时偷取相邻的房子，求可偷取到的最大的宝物数
     */

    Map<Integer, Integer> memo = new HashMap<>(16);

    /**
     *
     * 考虑抢劫num[index:num.size())这个范围的房子
     *
     * @param num
     * @param index
     * @return
     */
    int tryRob(int[] num, int index) {


        if (index >= num.length) {
            return 0;
        }

        if (memo.containsKey(index)) {
            return memo.get(index);
        }

        int res = 0;
        for (int i = index; i < num.length; i++) {
            res = Math.max(res, num[i] + tryRob(num, i + 2));
        }

        // 记忆化搜索
        memo.put(index, res);
        return res;
    }

    public int solve(int[] nums) {
        return tryRob(nums, 0);
    }


    public int solve2(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        // DP数组，最基础的数组dp[n-1] = nums[n-1]，从n-1到n-1偷取一共有一种办法，偷取第n-1个房间
        int[] dp = new int[n];
        dp[n - 1] = nums[n - 1];

        // 递推公式
        for (int i = n - 2; i >= 0; i--) {
            // 在这里从i到n-1还有多个房子，需要循环判断选择最优解
            for (int j = i; j < n; j++) {
                dp[i] = Math.max(dp[i], nums[j] + (j + 2 < n ? dp[j + 2] : 0));
            }
        }

        return dp[0];
    }


    /**
     * 记忆化搜索方式
     */
    @Test
    public void test() {
        int[] array = ArrayUtils.createArray(10);
        System.out.println(Arrays.toString(array));
        System.out.println("solve(array) = " + solve(array));
    }


    /**
     * 动态规划
     */
    @Test
    public void test2(){
        int[] array = ArrayUtils.createArray(10);
        System.out.println(Arrays.toString(array));
        System.out.println("solve(array) = " + solve2(array));
    }


}
