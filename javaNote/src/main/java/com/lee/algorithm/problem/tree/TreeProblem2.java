package com.lee.algorithm.problem.tree;

import com.lee.algorithm.utils.TreeNode;
import com.lee.algorithm.utils.TreeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个二叉树，返回所有从根到叶子节点的路径
 */
public class TreeProblem2 {

    private static List<String> findAllTraceFromTootToLeaf(TreeNode node) {
        List<String> result = new ArrayList<>();


        if (node == null) {
            return result;
        }

        // 叶子节点
        if (node.left == null && node.right == null) {
            result.add(String.valueOf(node.e));
        }

        if (node.left != null) {
            List<String> allTraceFromTootToLeaf = findAllTraceFromTootToLeaf(node.left);
            for (int i = 0; i < allTraceFromTootToLeaf.size(); i++) {
                result.add(node.e + "->" + allTraceFromTootToLeaf.get(i));
            }
        }

        if (node.right != null) {
            List<String> allTraceFromTootToRight = findAllTraceFromTootToLeaf(node.right);
            for (int i = 0; i < allTraceFromTootToRight.size(); i++) {
                result.add(node.e + "->" + allTraceFromTootToRight.get(i));
            }
        }


        return result;
    }

    public static void main(String[] args) {
        TreeNode tree = TreeUtils.createTree();
        List<String> allTraceFromTootToLeaf = findAllTraceFromTootToLeaf(tree);
        System.out.println("allTraceFromTootToLeaf = " + allTraceFromTootToLeaf);
    }
}
