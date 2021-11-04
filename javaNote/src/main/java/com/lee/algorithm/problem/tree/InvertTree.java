package com.lee.algorithm.problem.tree;

import com.lee.algorithm.utils.TreeNode;
import com.lee.algorithm.utils.TreeUtils;

/**
 * 翻转二叉树
 */
public class InvertTree {

    private static TreeNode invertTree(TreeNode root) {

        if (root == null) {
            return null;
        }
        invertTree(root.left);
        invertTree(root.right);

        // 翻转
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        return root;
    }

    public static void main(String[] args) {
        TreeNode root = TreeUtils.createTree();
        TreeUtils.print(root);
        invertTree(root);
        System.out.println();
        TreeUtils.print(root);
    }
}
