package com.lee.algorithm.problem.tree;

import com.lee.algorithm.utils.Tree;
import com.lee.algorithm.utils.TreeUtils;
import org.apache.poi.ss.formula.functions.T;

/**
 * 翻转二叉树
 */
public class InvertTree {

    private static Tree invertTree(Tree root) {

        if (root == null) {
            return null;
        }
        invertTree(root.left);
        invertTree(root.right);

        // 翻转
        Tree tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        return root;
    }

    public static void main(String[] args) {
        Tree root = TreeUtils.createTree();
        TreeUtils.print(root);
        invertTree(root);
        System.out.println();
        TreeUtils.print(root);
    }
}
