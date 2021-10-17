package com.lee.algorithm.problem;

import java.util.Arrays;

/**
 * 给定一个数组，找出最长的不存在重复元素的连续子数组
 * 返回长度
 * 例如 abcabcb 返回3
 */
public class LongestSubstringWithoutRepeatingWord {
    static int solve(String str) {
        char[] arr = str.toCharArray();
        if (arr.length == 0) {
            return 0;
        }

        // 滑动窗口为 [l,r]
        int l = 0;
        int r = -1;
        int result = 0;
        // 用来记录重复否，O(1)级别操作
        int[] freq = new int[256];
        Arrays.fill(freq, 0);

        while (l < arr.length) {
            if (r + 1 < arr.length && freq[arr[r + 1]] == 0) {
                // 右边界下一个字符没有重复
                r = r + 1;
                freq[arr[r]] = 1;
            } else {
                // 有重复的元素，缩小左边界，
                freq[arr[l]] = 0;
                l++;
            }

            result = Math.max(r - l + 1, result);
        }

        return result;

    }


    public static void main(String[] args) {
        String str = "abcda";
        int solve = solve(str);
        System.out.println(solve);
    }

}
