package com.lee.algorithm.problem;

import java.util.Arrays;

/**
 * 给定一个有序数组，返回和等于target的两个索引
 * 1、循环+二分查找法 复杂度为O(nlogn)
 * 2、对撞指针，复杂度为O(n)
 */
public class FindSumTargetArrIndex {

    public static int[] find(int[] arr, int target) {
        int[] result = new int[2];
        int l = 0;
        int r = arr.length - 1;
        while (l <= r) {
            if (arr[l] + arr[r] == target) {
                result[0] = l;
                result[1] = r;
                return result;
            } else if (arr[l] + arr[r] > target) {
                r--;
            } else {
                l++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 4, 5, 6, 6, 29, 44, 66, 77};
        int target = 12;
        int[] ints = find(arr, target);
        System.out.println(Arrays.toString(ints));

    }

}
