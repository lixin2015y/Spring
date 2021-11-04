package com.lee.algorithm.problem.tree;

import com.lee.algorithm.utils.TreeNode;
import com.lee.algorithm.utils.TreeUtils;

/**
 * 找到当前树的路径中，和为sum的条数有多少
 */
public class TreeProblem3 {

    /**
     * 以root为根节点，寻找和为sum的路径，返回这样的路径个数
     * @param node
     * @param sum
     * @return
     */
    private static int solve(TreeNode node, int sum) {

        if (node == null) {
            return 0;
        }

        int res = find(node, sum);
        // 此处递归为了变换根节点
        res += solve(node.left, sum);
        res += solve(node.right, sum);
        return res;
    }

    /**
     * 以node为根节点，寻找包含node，和为sum返回这样路径的个数
     * @param node
     * @param sum
     * @return
     */
    private static int find(TreeNode node, int sum) {
        if (node == null) {
            return 0;
        }
        int res = 0;
        if (node.e == sum) {
            res++;
        }

        res += find(node.left, sum - node.e);
        res += find(node.right, sum - node.e);

        return res;
    }

    public static void main(String[] args) {
        TreeNode tree = TreeUtils.createTree();
        int solve = solve(tree, 7);
        System.out.println("solve = " + solve);
    }
}
