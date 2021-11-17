package com.lee.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @className: ArrayListTest
 * @author: li xin
 * @date: 2021-10-14
 **/
public class ArrayListTest {

    @Test
    public void test() {
        ArrayList<Integer> list = new ArrayList<Integer>(10);
        for (int i = 0; i < 11; i++) {
            list.add(i);
        }

        System.out.println(list.size());
    }


    @Test
    public void test2(){
        System.out.println("===============add================");
        LinkedList<Integer> l1 = new LinkedList<>();
        LinkedList<Integer> l2 = new LinkedList<>();
        LinkedList<Integer> l3 = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            l1.offer(i); // 这里调用的list的add方法，直接在++size=e
            l2.offerFirst(i); // 这里用链表的头结点后直接插入
            l3.offerLast(i); // 这里使用链表尾节点
        }
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(l3);

        System.out.println("===============poll================");

        // 调用poll方法，如果不为空则返回对应短链后的节点
        Integer p1 = l1.poll();
        Integer p2 = l2.pollFirst();
        Integer p3 = l3.pollLast();
        System.out.println(p1);
        System.out.println(l1);
        System.out.println("===============================");
        System.out.println(p2);
        System.out.println(l2);
        System.out.println("===============================");
        System.out.println(p3);
        System.out.println(l3);


        System.out.println("===========pop====================");
        Integer pop = l1.pop();
        System.out.println(pop);
        System.out.println(l1);

    }
}
