package com.lee.algorithm.problem;

import com.lee.algorithm.utils.LinkListUtils;
import com.lee.algorithm.utils.Node;

/**
 * 虚拟头结点，
 */
public class DeleteLinkNode {

    public static void delete(Integer target, Node<Integer> head) {
        if (head == null) {
            return;
        }

        Node<Integer> dummyHead = new Node<>(0);
        dummyHead.next = head;

        Node<Integer> pre = dummyHead;
        Node<Integer> current = dummyHead.next;
        while (current != null) {
            if (current.e == target) {
                pre.next = current.next;
            }
            pre = current;
            current = current.next;
        }

//        return dummyHead.next;
    }

    public static void main(String[] args) {

        Node<Integer> head = LinkListUtils.createLinkList(10);
        LinkListUtils.linkedToString(head);
        delete(7, head);
        LinkListUtils.linkedToString(head);
    }
}
