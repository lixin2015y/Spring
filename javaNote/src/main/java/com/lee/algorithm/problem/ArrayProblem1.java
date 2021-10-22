package com.lee.algorithm.problem;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * 给定一个整形数组，和整数k，求是否存在索引i和j，使num[i] = num[k] 且j-i<=k
 * <p>
 * 滑动窗口
 */
public class ArrayProblem1 {

    public static Boolean solve(int[] arr, int k) {
        boolean result = false;
        // 记录滑动窗口中的数据，hash接口用与查找
        LinkedHashMap<Integer, Integer> record = new LinkedHashMap<>();
        // 记录滑动窗口中的数据，用于删除，复杂度均为O(1)
        LinkedList<Integer> recordCopy = new LinkedList<>();
        for (int i = 0; i < arr.length; i++) {
            if (record.containsKey(arr[i])) {
                result = true;
            }
            // 维护两个重叠窗口
            record.put(arr[i], null);
            recordCopy.add(arr[i]);

            // 窗口过大，则删除头结点元素
            if (record.size() > k) {
                Integer head = recordCopy.pop();
                record.remove(head);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1, 41, 31, 24, 12, 41, 3, 15, 5};
        int k = 4;
        Boolean solve = solve(arr, k);
        System.out.println("solve = " + solve);
    }
}
