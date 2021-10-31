package com.lee.algorithm.problem.tree;

import com.lee.algorithm.utils.Tree;
import com.lee.algorithm.utils.TreeUtils;
import jdk.nashorn.internal.objects.NativeUint8Array;

import javax.swing.*;

/**
 * 判断一个二叉树中，是否含有一条从根到叶子节点的路径，相加数等于sum
 *
 */
public class TreeProblem1 {

    private static boolean exist(Tree node, Integer sum) {
        if (node == null) {
            return false;
        }
        if (node.left == null && node.right == null) {
            return node.e == sum;
        }

        if (exist(node.left, sum - node.e)) {
            return true;
        }

        if (exist(node.right, sum - node.e)) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        Tree node = TreeUtils.createTree();
        boolean exist = exist(node, 77);
        System.out.println("exist = " + exist);
    }
}
