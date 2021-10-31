package com.lee.algorithm.problem;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个数组，找出前k个出现频率最高的元素
 * 1、优先队列
 */
public class FindKFreeElement {


    private static String[] solve(String[] arr, Integer k) {
        Map<String, Integer> freequent = new HashMap<>();
        String minFreeKey = "";
        for (int i = 0; i < arr.length; i++) {
            if (freequent.size() < k) {
                if (freequent.size() == 0) {
                    minFreeKey = arr[i];
                }

            }
        }

        return new String[1];
    }

    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        System.out.println(false);
    }
}
