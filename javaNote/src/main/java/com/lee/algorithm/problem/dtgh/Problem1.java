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

    /**
     * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     *
     * 说明：每次只能向下或者向右移动一步。
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {

        if(grid == null || grid.length == 0) return 0;


        int[][] dp = new int[grid.length][grid[0].length];

        dp[0][0] = grid[0][0];


        // 初始化第一行dp，这里需要将上面步骤加起来
        for (int i = 1; i < grid[0].length; i++) {
            dp[0][i] = grid[0][i] + dp[0][i-1];
        }

        // 初始化第一列dp
        for (int i = 1; i < grid.length; i++ ) {
            dp[i][0] = grid[i][0] + dp[i-1][0];
        }

        // 从1,1 开始计算每一个dp最小值，f(i,j) = min(f(i,j-1), f(i-1,j)) + grid(i,j);
        // 一直推到f(m-1, n-1)
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j< grid[0].length; j++ ) {
                dp[i][j] = Math.min(dp[i -1][j], dp[i][j-1]) + grid[i][j];
            }
        }

        return dp[grid.length -1][grid[0].length -1];
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
