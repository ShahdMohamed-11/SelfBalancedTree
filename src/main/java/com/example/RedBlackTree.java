package com.example;

// import com.example.Dictionary;
// import com.example.NodeRB;

enum Color {
    RED, BLACK
}

public class RedBlackTree<T extends Comparable<T>> implements Dictionary<T,NodeRB<T>> {
    public NodeRB<T> root;

    public RedBlackTree() {
        root = null;
    }

    public int size(NodeRB<T> node) {
        if (node == null) return 0;
        return 1 + size(node.left) + size(node.right);
    }

    public int height(NodeRB<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    @Override
    public boolean Search(NodeRB<T> node, T key) {
        if (node == null) return false;
        int cmp = key.compareTo(node.val);
        if (cmp == 0) return true;
        if (cmp < 0) return Search(node.left, key);
        return Search(node.right, key);
    }

    @Override
    public NodeRB<T> insert(NodeRB<T> node, T key) {
        root = insertHelper(root, key);
        root.color = Color.BLACK;
        return root;
    }

    private NodeRB<T> insertHelper(NodeRB<T> h, T key) {
        if (h == null) return new NodeRB<>(key, Color.RED);

        int cmp = key.compareTo(h.val);
        if (cmp < 0)
            h.left = insertHelper(h.left, key);
        else if (cmp > 0)
            h.right = insertHelper(h.right, key);
        else
            return h;

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        return h;
    }

    @Override
    public NodeRB<T> delete(NodeRB<T> node, T key) {
        if (!Search(root, key)) return root;

        if (!isRed(root.left) && !isRed(root.right))
            root.color = Color.RED;

        root = deleteHelper(root, key);
        if (root != null) root.color = Color.BLACK;

        return root;
    }

    private NodeRB<T> deleteHelper(NodeRB<T> h, T key) {
        if (key.compareTo(h.val) < 0) {
            if (!isRed(h.left) && !isRed(h.left != null ? h.left.left : null))
                h = moveRedLeft(h);
            h.left = deleteHelper(h.left, key);
        } else {
            if (isRed(h.left))
                h = rotateRight(h);

            if (key.compareTo(h.val) == 0 && (h.right == null))
                return null;

            if (!isRed(h.right) && !isRed(h.right != null ? h.right.left : null))
                h = moveRedRight(h);

            if (key.compareTo(h.val) == 0) {
                NodeRB<T> x = min(h.right);
                h.val = x.val;
                h.right = deleteMin(h.right);
            } else
                h.right = deleteHelper(h.right, key);
        }

        return fixUp(h);
    }

    private NodeRB<T> deleteMin(NodeRB<T> h) {
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return fixUp(h);
    }

    private NodeRB<T> min(NodeRB<T> h) {
        while (h.left != null)
            h = h.left;
        return h;
    }

    private NodeRB<T> moveRedLeft(NodeRB<T> h) {
        flipColors(h);
        if (isRed(h.right != null ? h.right.left : null)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    private NodeRB<T> moveRedRight(NodeRB<T> h) {
        flipColors(h);
        if (isRed(h.left != null ? h.left.left : null)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    private NodeRB<T> fixUp(NodeRB<T> h) {
        if (isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        return h;
    }

    private boolean isRed(NodeRB<T> node) {
        return node != null && node.color == Color.RED;
    }

    private NodeRB<T> rotateLeft(NodeRB<T> h) {
        NodeRB<T> x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = Color.RED;
        return x;
    }

    private NodeRB<T> rotateRight(NodeRB<T> h) {
        NodeRB<T> x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = Color.RED;
        return x;
    }

    private void flipColors(NodeRB<T> h) {
        h.color = (h.color == Color.RED) ? Color.BLACK : Color.RED;
        if (h.left != null)
            h.left.color = (h.left.color == Color.RED) ? Color.BLACK : Color.RED;
        if (h.right != null)
            h.right.color = (h.right.color == Color.RED) ? Color.BLACK : Color.RED;
    }

    public void printInOrder(NodeRB<T> node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print(node.val + " ");
            printInOrder(node.right);
        }
    }

    public static void main(String[] args) {
        RedBlackTree<Integer> rbTree = new RedBlackTree<>();

        rbTree.root = rbTree.insert(rbTree.root, 10);
        rbTree.root = rbTree.insert(rbTree.root, 20);
        rbTree.root = rbTree.insert(rbTree.root, 30);
        rbTree.root = rbTree.insert(rbTree.root, 15);
        rbTree.root = rbTree.insert(rbTree.root, 5);
        rbTree.root = rbTree.insert(rbTree.root, 25);

        System.out.print("Before Deletion: ");
        rbTree.printInOrder(rbTree.root);
        System.out.println();

        rbTree.root = rbTree.delete(rbTree.root, 15);

        System.out.print("After Deletion: ");
        rbTree.printInOrder(rbTree.root);
        System.out.println();

        System.out.println("Search 15: " + rbTree.Search(rbTree.root, 15)); 
    }
}
