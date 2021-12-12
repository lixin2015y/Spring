package com.lee.algorithm.problem.dtgh;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @className: Problem1
 * @author: li xin
 * @date: 2021-12-12
 **/
public class Problem1 {


    /**
     * 给定一个三角形的数字矩阵，选择一个自顶向下的路径，使得沿途的所有数字大小和最小
     * 从三角形的最底层开始遍历，如果是最后一层则初始化dp数组，状态转移方程为
     */

    public int minimumTotal(List<List<Integer>> triangle) {
        if(triangle.isEmpty()) return 0;

        int[][] dp = new int[triangle.size()][triangle.get(triangle.size() - 1).size()];


        for(int i = triangle.size()-1 ; i >=0 ; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                if (i == triangle.size() - 1) {
                    dp[i][j] = triangle.get(i).get(j);
                } else {
                    dp[i][j] = Math.min(dp[i+1][j], dp[i+1][j + 1]) + triangle.get(i).get(j);
                }
            }
        }

        return dp[0][0];
    }


    @Test
    public void test(){
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(2));
        list.add(Arrays.asList(3, 4));
        list.add(Arrays.asList(6, 5, 7));
        list.add(Arrays.asList(4, 1, 8, 3));
        int total = minimumTotal(list);
        System.out.println("total = " + total);
    }
}
