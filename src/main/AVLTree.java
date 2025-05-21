package main;

public class AVLTree<T extends Comparable<T>> implements Dictionary<T> {
    public Node<T> root;

    public AVLTree() {
        root = null;
    }

    public int height(Node<T> node) {
        return node == null ? 0 : node.height;
    }

    public Node<T> findmin(Node<T> root) {
        if (root == null) return null;
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    @Override
    public boolean Search(Node<T> root, T key) {
        if (root == null) return false;
        int cmp = key.compareTo(root.val);
        if (cmp == 0) return true;
        if (cmp < 0) return Search(root.left, key);
        return Search(root.right, key);
    }

    @Override
    public Node<T> insert(Node<T> root, T key) {
        if (root == null) return new Node<>(key);

        int cmp = key.compareTo(root.val);
        if (cmp < 0)
            root.left = insert(root.left, key);
        else if (cmp > 0)
            root.right = insert(root.right, key);
        else
            return root; // Duplicate keys not allowed

        root.height = 1 + Math.max(height(root.left), height(root.right));
        int balance = getbalance(root);

        // Rotations
        if (balance > 1 && key.compareTo(root.left.val) < 0)
            return rotateright(root);

        if (balance > 1 && key.compareTo(root.left.val) > 0) {
            root.left = rotateleft(root.left);
            return rotateright(root);
        }

        if (balance < -1 && key.compareTo(root.right.val) > 0)
            return rotateleft(root);

        if (balance < -1 && key.compareTo(root.right.val) < 0) {
            root.right = rotateright(root.right);
            return rotateleft(root);
        }

        return root;
    }

    @Override
    public Node<T> delete(Node<T> root, T key) {
        if (root == null) return root;

        int cmp = key.compareTo(root.val);
        if (cmp < 0) {
            root.left = delete(root.left, key);
        } else if (cmp > 0) {
            root.right = delete(root.right, key);
        } else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            Node<T> temp = findmin(root.right);
            root.val = temp.val;
            root.right = delete(root.right, temp.val);
        }

        root.height = 1 + Math.max(height(root.left), height(root.right));
        int balance = getbalance(root);

        if (balance > 1 && getbalance(root.left) >= 0)
            return rotateright(root);

        if (balance > 1 && getbalance(root.left) < 0) {
            root.left = rotateleft(root.left);
            return rotateright(root);
        }

        if (balance < -1 && getbalance(root.right) <= 0)
            return rotateleft(root);

        if (balance < -1 && getbalance(root.right) > 0) {
            root.right = rotateright(root.right);
            return rotateleft(root);
        }

        return root;
    }

    public int getbalance(Node<T> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    public Node<T> rotateright(Node<T> y) {
        Node<T> x = y.left;
        Node<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    public Node<T> rotateleft(Node<T> x) {
        Node<T> y = x.right;
        Node<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }

    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();

        tree.root = tree.insert(tree.root, 10);
        tree.root = tree.insert(tree.root, 20);
        tree.root = tree.insert(tree.root, 30);
        tree.root = tree.insert(tree.root, 40);
        tree.root = tree.insert(tree.root, 50);
        tree.root = tree.insert(tree.root, 60);
        tree.root = tree.insert(tree.root, 70);
        tree.root = tree.insert(tree.root, 80);

        tree.root = tree.delete(tree.root, 10);

        System.out.println(tree.Search(tree.root, 10)); // false
        System.out.println(tree.Search(tree.root, 20)); // true

        System.out.println(tree.height(tree.root)); // e.g. 4
    }
}
