package com.lee.algorithm;

import com.lee.algorithm.utils.ArrayUtils;

/**
 * 二分查找
 */
public class BinarySearch {


    /**
     * 双索引，
     * @param arr
     * @param n
     * @param target
     * @return
     */
    public static int binarySearch(int[] arr, int n, int target) {
        int l = 0;
        int r = n - 1;
        // 在[l,r]区间中寻找target
        while (l <= r) {
            int mid = (l + r) / 2;
            if (arr[mid] == target) {
                return mid;
            }

            if (target > arr[mid]) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }

        }
        return -1;
    }


    public static void main(String[] args) {
        int[] array = ArrayUtils.createArray(100000);
        int result = binarySearch(array, array.length, 500);
        System.out.println(result);
    }
}
