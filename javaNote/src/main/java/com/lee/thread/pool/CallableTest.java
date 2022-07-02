package com.lee.thread.pool;

import com.lee.algorithm.utils.ArrayUtils;

import java.util.concurrent.Callable;

public class CallableTest implements Callable {

    @Override
    public Object call() throws Exception {
        System.out.println("继承了callable的call方法");
        return ":";
    }

    public static void main(String[] args) {
        new Thread().start();
        CallableTest callableTest = new CallableTest();
        Solution solution = new Solution();
        int i = solution.lastStoneWeightII(new int[]{1, 2});
        System.out.println(i);
    }

    static class Solution {
        public int lastStoneWeightII(int[] stones) {
            int sum = 0;
            for (int i : stones) {
                sum += i;
            }
            int target = sum / 2;
            // dp[i][j]前i个数随便选，和<=j情况下的最大值
            // 初始化第一行，也就是只取第一个数所能取的最大值
            int[][] dp = new int[stones.length][target + 1];
            for (int i = 1; i <= target; i++) {
                if (stones[0] <= i) {
                    dp[0][i] = stones[0];
                }
            }

            for (int i = 1; i < stones.length; i++) {
                for (int j = 1; j <= target; j++) {
                    if (j < stones[i]) {
                        // 当前背包不够stone[i]，那就是上一个背包容量的最大值
                        dp[i][j] = dp[i - 1][j];
                    } else {
                        // 当前物品放与不放
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - stones[i]] + stones[i]);
                    }
                }
            }

            return sum - dp[stones.length - 1][target] - dp[stones.length - 1][target];

        }
    }
}
