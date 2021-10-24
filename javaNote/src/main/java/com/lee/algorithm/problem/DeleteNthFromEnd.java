package com.lee.algorithm.problem;

import com.lee.algorithm.utils.LinkListUtils;
import com.lee.algorithm.utils.Node;

/**
 * 删除倒数第N个元素,双指针查找，只遍历一次链表
 */
public class DeleteNthFromEnd {

    public static Node<Integer> delete(Integer n, Node<Integer> head) {
        Node<Integer> dummyNode = new Node<>(-1);
        dummyNode.next = head;

        // 待删除节点的前一个节点
        Node<Integer> p = dummyNode;
        //
        Node<Integer> q = dummyNode;
        for (int i = 0; i < n + 1; i++) {
            q = q.next;
        }
        while (q != null) {
            p = p.next;
            q = q.next;
        }

        // 删除p后的一个元素（目标元素）
        p.next = p.next.next;
        return dummyNode.next;
    }

    public static void main(String[] args) {

        Node<Integer> head = LinkListUtils.createLinkList(10);
        LinkListUtils.linkedToString(head);
        Node<Integer> delete = delete(4, head);
        LinkListUtils.linkedToString(delete);
    }
}
