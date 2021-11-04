package com.lee.algorithm.utils;

import java.util.LinkedList;
import java.util.Queue;

public class TreeUtils {

    /**
     *      15
     *    3      8
     *  2   4   7  10
     * 1       6   9  11
     * @return
     */
    public static TreeNode createTree() {
        TreeNode treeNode = new TreeNode(15);
        treeNode.left = new TreeNode(3);
        treeNode.right = new TreeNode(8);
        treeNode.left.left = new TreeNode(2);
        treeNode.left.right = new TreeNode(4);
        treeNode.left.left.left = new TreeNode(1);
        treeNode.right.left = new TreeNode(7);
        treeNode.right.left.left = new TreeNode(6);
        treeNode.right.right = new TreeNode(10);
        treeNode.right.right.left = new TreeNode(9);
        treeNode.right.right.right = new TreeNode(11);
        return treeNode;
    }

    /**
     * 层序遍历，
     *
     * @param root
     */
    public static void print(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode treeNode = queue.poll();
            System.out.print(treeNode.e + "-");
            if (treeNode.left != null) {
                queue.add(treeNode.left);
            }
            if (treeNode.right != null) {
                queue.add(treeNode.right);
            }
        }
    }
}
