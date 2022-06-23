package com.lee.algorithm.sort;

import com.lee.algorithm.utils.ArrayUtils;
import org.apache.xmlbeans.impl.xb.xmlconfig.impl.QnameconfigImpl;
import org.junit.Before;
import org.junit.Test;

import javax.xml.stream.events.EndDocument;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * @className: QuickSort
 * @author: li xin
 * @date: 2021-12-21
 **/
public class QuickSort {


    int[] arr = new int[]{1, 4, 3, 2, 6, 5};

    /**
     * 冒泡排序
     */
    @Test
    public void test() {

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
//        quickSortMe(arr, 0, arr.length - 1);
//        quickSort2(arr, 0, arr.length - 1);
        quickSort3(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public void quickSortMe(int[] arr, int start, int end) {
        if (start < end) {
            int l = start;
            int r = end;
            int key = arr[l];
            int keyIndex = l;

            while (l < r) {
                while (l < r && arr[r] > key) {
                    r--;
                }
                if (l < r) {
                    ArrayUtils.swap(arr, l, r);
                    keyIndex = r;
                    l++;
                }
                while (l < r && arr[l] < key) {
                    l++;
                }
                if (l < r) {
                    ArrayUtils.swap(arr, l, r);
                    keyIndex = l;
                    r--;
                }
            }
            System.out.println(Arrays.toString(arr) + " ===== " + keyIndex);
            quickSortMe(arr, start, keyIndex - 1);
            quickSortMe(arr, keyIndex + 1, end);
        }
    }

    public void quickSort2(int[] arr, int start, int end) {
        if (start < end) {
            int l = start;
            int r = end;
            int key = arr[start];

            while (l < r) {
                while (l < r && arr[r] > key) {
                    r--;
                }
                if (l < r) {
                    // 这里代表arr[r] <= key了，进行交换
                    ArrayUtils.swap(arr, l, r);
                    l++;
                }
                // 这时候key在右侧，需要和左边的比较，找到左边第一个比key大的
                while (l < r && arr[l] < key) {
                    l++;
                }
                if (l < r) {
                    // 找到一个左边的比基准大的
                    ArrayUtils.swap(arr, l, r);
                    r--;
                }
            }
            quickSort2(arr, start, l);
            quickSort2(arr, l + 1, end);
        }
    }


    public void quickSort3(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int target = arr[start];
        int l = start;
        int r = end;
        while (l < r) {
            while (l < r && arr[r] > target) {
                r--;
            }
            if (l < r) {
                arr[l++] = arr[r];
            }
            while (l < r && arr[l] < target) {
                l++;
            }
            if (l < r) {
                arr[r--] = arr[l];
            }
        }
        arr[l] = target;
        quickSort3(arr, start, l);
        quickSort3(arr, l + 1, end);

    }


}
