package com.lee.algorithm.problem.tree;


import com.lee.algorithm.utils.Tree;
import com.lee.algorithm.utils.TreeUtils;

import javax.management.MXBean;

/**
 * 树的最大深度
 */
public class MaxDepthTree {

    private static int maxDepth(Tree root) {
        if (root == null) {
            return 1;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    public static void main(String[] args) {
        Tree tree = TreeUtils.createTree();
        System.out.println(maxDepth(tree));
    }


}
