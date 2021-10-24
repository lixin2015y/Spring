package com.lee.algorithm.problem;

import com.lee.algorithm.utils.LinkListUtils;
import com.lee.algorithm.utils.Node;
import org.junit.Test;

/**
 * 虚拟头结点， 将链表的单数元素和偶数元素调换位置
 */
public class SwapTheInnerNode {

    public static Node<Integer> swap(Node<Integer> head) {
        if (head == null) {
            return null;
        }
        Node<Integer> dummyNode = new Node<>(-1);
        dummyNode.next = head;

        Node<Integer> p = dummyNode;
        while (p.next != null && p.next.next != null) {
            // 交换完的下个节点
            Node<Integer> next = p.next.next.next;

            Node<Integer> one = p.next;
            Node<Integer> two = p.next.next;

            // 交换
            p.next = two;
            two.next = one;
            one.next = next;

            // 移动P
            p = one;
        }
        return dummyNode.next;


    }

    public static void main(String[] args) {

        Node<Integer> head = LinkListUtils.createLinkList(10);
        LinkListUtils.linkedToString(head);
        Node<Integer> swap = swap(head);
        LinkListUtils.linkedToString(swap);
    }
}
