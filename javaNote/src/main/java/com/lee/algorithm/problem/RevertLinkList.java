package com.lee.algorithm.problem;

import com.lee.algorithm.utils.LinkListUtils;
import com.lee.algorithm.utils.Node;

/**
 * 翻转链表
 */
public class RevertLinkList {


    public static Node<Integer> revert(Node<Integer> head) {
        if (head == null) {
            return null;
        }
        Node<Integer> pre = null;
        Node<Integer> curr = head;

        while (curr != null) {
            Node<Integer> next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }


    public static void main(String[] args) {
        Node<Integer> head = LinkListUtils.createLinkList(10);
        LinkListUtils.linkedToString(head);
        Node<Integer> revert = revert(head);
        LinkListUtils.linkedToString(revert);
    }
}
