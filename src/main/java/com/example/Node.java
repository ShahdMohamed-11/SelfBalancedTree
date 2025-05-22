package com.example;

public class Node<T extends Comparable<T>> {
    T val;
    int height;
    public Node<T> left;
    public Node<T> right;

    public Node(T val) {
        this.val = val;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}
