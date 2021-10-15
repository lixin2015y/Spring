package com.lee.algorithm.problem;

import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;
import org.apache.xmlbeans.impl.regex.REUtil;

import javax.print.attribute.standard.RequestingUserName;

/**
 * 题目：给定一个整形数组和一个数字s，找到数组中最短的连续子数组，似的子数组数字和>=s,返回这个最短的连续子数组的长度
 * 如给定[2,3,1,2,4,3] s=7 则结果为2 [4,3]
 * <p>
 * <p>
 * 定义：
 * 1、子数组，不一定连续
 * 2、如果没有解，怎么办
 * 3、如果有多个解，该如何返回
 * <p>
 * <p>
 * 解题思路，滑动窗口
 */
public class MinimumSizeSubarraySum {

    static int minSubArrLength(int[] arr, int s) {
        // [l,r]为滑动窗口
        int l = 0;
        int r = -1;
        int result = arr.length + 1;

        // 表示窗口中元素加和
        int sum = 0;
        // 数组取值，注意边界问题
        while (l < arr.length && r + 1 < arr.length) {
            if (sum < s) {
                
                r = r + 1;
                sum += arr[r];
            } else {
                // 这里相当于是把
                sum -= arr[l];
                l++;
            }
            if (sum >= s) {
                // 判断
                sum = Math.min(r - l + 1, result);
            }
        }

        if (result == arr.length + 1) {
            return 0;
        }

        return result;
    }


    static int minSubArray(int[] arr, int s) {
        int l = 0;
        int r = -1;
        int result = arr.length + 1;
        int sum = 0;
        while (l < arr.length && l + 1 < arr.length && r + 1 < arr.length) {
            if (sum > s) {
                // 最短已经找到，将l向右移
                sum = sum - arr[l];
                l = l + 1;
            } else {
                r = r + 1;
                sum = sum + arr[r];
            }

            if (sum > s) {
                result = Math.min(sum, r - l + 1);
            }

        }
        if (result == arr.length + 1) {
            return 0;
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 2, 4, 3};
        int s = 7;
        int i = minSubArray(arr, s);
        int j = minSubArrLength(arr, s);
        System.out.println(i);
        System.out.println(j);
    }


}
