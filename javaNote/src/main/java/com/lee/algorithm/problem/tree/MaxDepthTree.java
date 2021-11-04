package com.lee.algorithm.problem.tree;


import com.lee.algorithm.utils.TreeNode;
import com.lee.algorithm.utils.TreeUtils;

/**
 * 树的最大深度
 */
public class MaxDepthTree {

    private static int maxDepth(TreeNode root) {
        if (root == null) {
            return 1;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    public static void main(String[] args) {
        TreeNode treeNode = TreeUtils.createTree();
        System.out.println(maxDepth(treeNode));
    }


}
