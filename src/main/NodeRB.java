package main;

class NodeRB<T extends Comparable<T>> {
    T val;
    NodeRB<T> left, right;
    Color color;

    public NodeRB(T val, Color color) {
        this.val = val;
        this.left = null;
        this.right = null;
        this.color = color;
    }
}
