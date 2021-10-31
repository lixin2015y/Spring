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
    public static Tree createTree() {
        Tree tree = new Tree(15);
        tree.left = new Tree(3);
        tree.right = new Tree(8);
        tree.left.left = new Tree(2);
        tree.left.right = new Tree(4);
        tree.left.left.left = new Tree(1);
        tree.right.left = new Tree(7);
        tree.right.left.left = new Tree(6);
        tree.right.right = new Tree(10);
        tree.right.right.left = new Tree(9);
        tree.right.right.right = new Tree(11);
        return tree;
    }

    /**
     * 层序遍历，
     *
     * @param root
     */
    public static void print(Tree root) {
        if (root == null) {
            return;
        }

        Queue<Tree> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Tree tree = queue.poll();
            System.out.print(tree.e + "-");
            if (tree.left != null) {
                queue.add(tree.left);
            }
            if (tree.right != null) {
                queue.add(tree.right);
            }
        }
    }
}
