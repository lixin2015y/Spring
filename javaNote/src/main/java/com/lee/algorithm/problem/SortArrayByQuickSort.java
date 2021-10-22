package com.lee.algorithm.problem;

import com.lee.algorithm.utils.ArrayUtils;

import java.util.Arrays;

/**
 * 给定一个数组，元素只有1,2,3三种元素，
 * 给定数组进行排序
 * 1、三路快排
 */
public class SortArrayByQuickSort {

    public static void sort(int[] arr) {
        // [0,zero] 都是0元素
        int zero = -1;
        // [two, length-1] 都是2
        int two = arr.length;

        for (int i = 0; i < two; ) {
            if (arr[i] == 1) {
                // 一代表他当前是中间位置的元素，
                i++;
            } else if (arr[i] == 2) {
                two--;
                ArrayUtils.swap(arr, two, i);
                // 这里不i++是为了再次判断，因为交换过来的数是未知的
            } else if (arr[i] == 0) {
                zero++;
                ArrayUtils.swap(arr, zero, i);
                i++;
                // 这里+1是因为交换过来的数必然是1
            }
        }
    }


    public static void main(String[] args) {
        int[] arr = {2, 0, 1, 1, 2, 0, 1, 1, 2, 0};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
