package com.example;

public interface Dictionary<T extends Comparable<T>, N> {
    boolean Search(N root, T key);
    N insert(N root, T key);
    N delete(N root, T key);
}
