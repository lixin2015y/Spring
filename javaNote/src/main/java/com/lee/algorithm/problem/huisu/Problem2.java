package com.lee.algorithm.problem.huisu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 回溯法2，给定一个整形数组，元素各不相同，返回这些元素排列的可能。
 */
public class Problem2 {

    private static List<List<Integer>> result = new ArrayList<>();

    private static boolean[] unUserArray;

    private static List<List<Integer>> solve(int[] target, int index, LinkedList<Integer> curr) {
        // 回溯条件
        if (index == target.length) {
            result.add(curr);
            return result;
        }

        //
        for (int i = 0; i < target.length; i++) {
            // 沒有被使用過
            if (!unUserArray[i]) {
                curr.addLast(target[i]);
                unUserArray[i] = false;
                solve(target, index + 1, curr);
//                curr.removeLast();
                unUserArray[i] = true;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] target = new int[]{1, 2, 3, 4, 56, 7, 234, 6,};
        unUserArray = new boolean[target.length];
        List<List<Integer>> solve = solve(target, 0, new LinkedList<>());
        System.out.println(solve);

    }



}
