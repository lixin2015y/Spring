package com.lee.algorithm.problem.LinkedList;

import com.alibaba.excel.metadata.Head;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ListNode {
    public Integer value;
    public ListNode next;
    public ListNode pre;

    public ListNode(int v) {
        this.value = v;
    }
}

class MyLinkedList {

    public ListNode dummyHead;

    public ListNode dummyTail;

    public int size;

    public MyLinkedList() {
        dummyHead  = new ListNode(-1);
        dummyTail  = new ListNode(-1);
        // 这里将虚拟头尾相连
        dummyHead.next = dummyTail;
        dummyTail.pre = dummyHead;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            return -1;
        }
        ListNode cur = dummyHead.next;
        while (index > 0) {
            cur = cur.next;
            index--;
        }
        return cur.value;
    }

    public void addAtHead(int val) {
        ListNode node = new ListNode(val);
        ListNode next = dummyHead.next;
        // 维护虚拟头和insertNode的双指针
        dummyHead.next = node;
        node.pre = dummyHead;
        // 维护insertNode和head的双指针
        node.next = next;
        next.pre = node;
        size++;
    }

    public void addAtTail(int val) {
        ListNode node = new ListNode(val);
        ListNode pre = dummyTail.pre;
        // 维护真实尾节点和node的双指针
        pre.next = node;
        node.pre = pre;
        // 维护虚拟尾节点和node的双引用
        node.next = dummyTail;
        dummyTail.pre = node;
        size++;
    }

    public void addAtIndex(int index, int val) {
        if(index <=0) {
            addAtHead(val);
        } else if (index == size) {
            addAtTail(val);
        } else if (index > size) {
        } else {
            ListNode cur = dummyHead.next;
            while (index > 0) {
                cur = cur.next;
                index--;
            }
            ListNode insert = new ListNode(val);
            ListNode pre = cur.pre;
            pre.next = insert;
            insert.pre = pre;

            insert.next = cur;
            cur.pre = insert;
            size++;
        }

    }

    public void deleteAtIndex(int index) {
        if (index >=0 && index < size) {
            ListNode cur = dummyHead.next;
            while (index > 0) {
                cur = cur.next;
                index--;
            }
            ListNode next = cur.next;
            ListNode pre = cur.pre;
            pre.next = next;
            next.pre = pre;
            size--;
        }
    }

    public static void main(String[] args) {
        // ["MyLinkedList","addAtHead","get","addAtIndex","addAtIndex","deleteAtIndex","addAtHead","addAtHead","deleteAtIndex","addAtIndex","addAtHead","deleteAtIndex"]
        //[[],[9],[1],[1,1],[1,7],[1],[7],[4],[1],[1,4],[2],[5]]

        MyLinkedList linkedList = new MyLinkedList();
        linkedList.addAtHead(9);
        print(linkedList);

        linkedList.addAtIndex(1, 1);
        print(linkedList);

        linkedList.addAtIndex(1, 7);
        print(linkedList);

        linkedList.deleteAtIndex(1);
        print(linkedList);

        linkedList.addAtHead(7);
        print(linkedList);

        linkedList.addAtHead(4);
        print(linkedList);

        linkedList.deleteAtIndex(1);
        print(linkedList);

        linkedList.addAtIndex(1, 4);
        print(linkedList);

        linkedList.addAtHead(2);
        print(linkedList);

        linkedList.deleteAtIndex(5);
        print(linkedList);

    }

    private static void print(MyLinkedList myLinkedList) {
        ListNode curr = myLinkedList.dummyHead;
        List<Integer> list = new ArrayList<>();
        while (curr != null) {
            list.add(curr.value);
            curr = curr.next;
        }
        System.out.println(list.toString() + "当前size：" + myLinkedList.size);

    }
}
