package com.lee.algorithm.sort;

import com.lee.algorithm.utils.ArrayUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * @className: QuickSort
 * @author: li xin
 * @date: 2021-12-21
 **/
public class QuickSort {

    /**
     * 冒泡排序
     */
    @Test
    public void test() {
        int[] arr = new int[]{1, 4, 3, 2, 5, 6};
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    ArrayUtils.swap(arr, i, j);
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 快速排序
     */
    @Test
    public void test2() {

    }




}
