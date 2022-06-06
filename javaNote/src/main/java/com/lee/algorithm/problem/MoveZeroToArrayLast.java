package com.lee.algorithm.problem;

import java.util.Arrays;

/**
 * 将所有0移动到数组末尾,保持其他元素相对位置不变
 * <p>
 * <p>
 * <p>
 * 2、
 * 3、
 */
public class MoveZeroToArrayLast {

    // 1、遍历数组，找到所有非0的填充到另一数组，剩下的元素补0
    public static int[] move1(int[] arr) {
        int[] result = new int[arr.length];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                result[index++] = arr[i];
            }
        }
        for (int i = index; i < result.length; i++) {
            result[i] = 0;
        }

        return result;
    }

    // 双指针移动，不使用额外的内存空间,时间复杂度为O(n)，空间复杂度为O(1)级别
    public static int[] move2(int[] arr) {

        int nextZeroIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                arr[nextZeroIndex++] = arr[i];
            }
        }
        for (int i = nextZeroIndex; i < arr.length; i++) {
            arr[i] = 0;
        }
        return arr;
    }

    // 双指针交换思路，
    public static int[] move3(int[] arr) {
        int zeroIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                // 这里避免所有都是非0元素，自己和自己交换
                if (i != zeroIndex) {
                    swap(zeroIndex, i, arr);
                }
                zeroIndex++;
            }
        }
        return arr;
    }

    private static void swap(int i, int j, int[] arr) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 30, 90, 0, 2, 3, 1, 0, 4, 0};
        int[] result = move1(arr);
        System.out.println(Arrays.toString(result));
        int[] result2 = move2(arr);
        System.out.println(Arrays.toString(result2));

        int[] result3 = move3(arr);
        System.out.println(Arrays.toString(result3));

    }

}
