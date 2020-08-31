package com.lee.algorithm;

import org.junit.Test;

public class Algorithm {


    /**
     * 翻转字符串
     */
    @Test
    public void test() {
        String str = "abcdefg";
        StringBuilder result = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            result = result.insert(0, str.charAt(i));
        }
        System.out.println(result);
        System.out.println(result.reverse());
    }


    class Tree {
        Node head;


        public void add(Node root, Integer e) {

            // 空的添加到头部
            if (head == null) {
                head = new Node(e);
            }

            if (root == null) {
                root = new Node(e);
            }

            add(e < root.e ? root.left : root.right, e);

        }
    }

    class Node {
        Integer e;
        Node next;
        Node left;
        Node right;

        public Node(Integer e) {
            this.e = e;
        }
    }

    /**
     * 二叉树层次遍历
     */
    @Test
    public void test2() {
        System.out.println("");
    }
}
