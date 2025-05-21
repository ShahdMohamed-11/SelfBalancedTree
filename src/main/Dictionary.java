package main;

import main.Node;

public interface Dictionary <T extends Comparable<T>> {
    boolean Search(Node<T> root, T key);
    Node<T> insert(Node<T> root, T key);
    Node<T> delete(Node<T> root, T key);
}
