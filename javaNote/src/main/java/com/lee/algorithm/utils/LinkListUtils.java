package com.lee.algorithm.utils;

public class LinkListUtils {

    public static Node<Integer> createLinkList(int length) {
        Node<Integer> head = new Node<>(0);
        Node current = head;
        for (int i = 1; i < length; i++) {
            current.next = new Node(i);
            current = current.next;
        }
        return head;
    }

    public static String linkedToString(Node node) {
        StringBuilder sb = new StringBuilder();
        while (node != null) {
            sb.append(node.e + "-");
            node = node.next;
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
