package com.lee.algorithm;

import com.lee.algorithm.utils.ArrayUtils;
import org.junit.Test;

import java.util.Arrays;

public class Algorithm {


    /**
     * 翻转字符串
     */
    @Test
    public void test() {
        String str = "abcdefg";
        StringBuilder result = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            result = result.insert(0, str.charAt(i));
        }
        System.out.println(result);
        System.out.println(result.reverse());
    }


    class Tree {
        Node head;


        public void add(Node root, Integer e) {

            // 空的添加到头部
            if (head == null) {
                head = new Node(e);
            }

            if (root == null) {
                root = new Node(e);
            }

            add(e < root.e ? root.left : root.right, e);

        }
    }

    class Node {
        Integer e;
        Node next;
        Node left;
        Node right;

        public Node(Integer e) {
            this.e = e;
        }
    }

    /**
     * 二叉树层次遍历
     */
    @Test
    public void test2() {
        Solution solution = new Solution();
        int[] array = new int[]{1, 5, 11, 5};
        boolean b = solution.canPartition2(array);
        System.out.println(b);
    }

    class Solution {
        public boolean canPartition(int[] nums) {
            // 目标target的计算
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
            }
            if (sum % 2 == 1) return false;
            int target = sum / 2;
            int dp[] = new int[12];
            dp[0] = 0;
            for (int i = 0; i < nums.length; i++) {
                for (int j = target; j >= nums[i]; j--) {
                    dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
                }
            }

            if (dp[target] == target) {
                return true;
            }
            return false;

        }


        public boolean canPartition2(int[] nums) {
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
            }
            if (sum % 2 == 1) return false;
            int target = sum / 2;

            // 利用二维数组进行分析
            // dp[i][j]表示0到i-1编号数随便选，和不超过j的最大和
            int dp[][] = new int[nums.length][12];
            // 初始化
            for (int i = 0; i < nums.length; i++) {
                dp[i][0] = 0;
            }
            //
            for (int i = 1; i <= target; i++) {
                if (target >= nums[0]) {
                    dp[0][i] = nums[0];
                }
            }

            for (int i = 1; i < nums.length; i++) {
                for (int j = 1; j <= target; j++) {
                    if (j < nums[i]) {
                        dp[i][j] = dp[i- 1][j];
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - nums[i]] + nums[i]);
                    }
                }
            }
            return false;
        }
    }
}
