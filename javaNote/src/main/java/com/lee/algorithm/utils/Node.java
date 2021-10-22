package com.lee.algorithm.utils;

public class Node<E> {
    public Node<E> next;
    public E e;

    public Node() {
    }

    public Node(E e) {
        this.e = e;
    }
}
