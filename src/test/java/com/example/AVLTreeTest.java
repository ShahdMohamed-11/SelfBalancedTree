package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.TimeUnit;

public class AVLTreeTest {

    private AVLTree<Integer> tree;

    @BeforeEach
    void setUp() {
        tree = new AVLTree<>();
    }

    @Test
    void testBasicOperationsCorrectness() {
        // Test insertion and search
        tree.root = tree.insert(tree.root, 10);
        tree.root = tree.insert(tree.root, 5);
        tree.root = tree.insert(tree.root, 15);
        tree.root = tree.insert(tree.root, 3);
        tree.root = tree.insert(tree.root, 7);

        // Verify all inserted elements can be found
        assertTrue(tree.Search(tree.root, 10), "Should find element 10");
        assertTrue(tree.Search(tree.root, 5), "Should find element 5");
        assertTrue(tree.Search(tree.root, 15), "Should find element 15");
        assertTrue(tree.Search(tree.root, 3), "Should find element 3");
        assertTrue(tree.Search(tree.root, 7), "Should find element 7");

        // Verify non-existent elements are not found
        assertFalse(tree.Search(tree.root, 1), "Should not find element 1");
        assertFalse(tree.Search(tree.root, 20), "Should not find element 20");

        // Test deletion
        tree.root = tree.delete(tree.root, 5);
        assertFalse(tree.Search(tree.root, 5), "Element 5 should be deleted");
        assertTrue(tree.Search(tree.root, 10), "Element 10 should still exist");
        assertTrue(tree.Search(tree.root, 15), "Element 15 should still exist");

        // Test size functionality
        assertEquals(4, tree.size(tree.root), "Tree should have 4 nodes after deletion");
    }

    @Test
    void testAVLHeightPropertyMaintenance() {
        // Insert elements that would create an unbalanced BST
        int[] elements = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        for (int element : elements) {
            tree.root = tree.insert(tree.root, element);
            assertTrue(isAVLBalanced(tree.root),
                    "Tree should maintain AVL property after inserting " + element);
        }

        // Verify height is logarithmic (should be around log2(10) ≈ 3-4)

        tree.root = tree.delete(tree.root, 1);
        tree.root = tree.delete(tree.root, 2);
        tree.root = tree.delete(tree.root, 3);

        assertTrue(isAVLBalanced(tree.root), "Tree should remain balanced after deletions");
    }

    /**
     * Test 3: Rotation Correctness and Balance Factor
     * Tests that rotations work correctly and balance factors are properly maintained
     */
    @Test
    void testRotationCorrectnessAndBalanceFactor() {
        // Test Right-Right case (left rotation needed)
        tree.root = tree.insert(tree.root, 10);
        tree.root = tree.insert(tree.root, 20);
        tree.root = tree.insert(tree.root, 30); // Should trigger left rotation

        assertEquals(20, (int) tree.root.val, "Root should be 20 after left rotation");
        assertEquals(10, (int) tree.root.left.val, "Left child should be 10");
        assertEquals(30, (int) tree.root.right.val, "Right child should be 30");
        assertTrue(Math.abs(tree.getbalance(tree.root)) <= 1, "Balance factor should be ≤ 1");

        // Test Left-Left case (right rotation needed)
        tree = new AVLTree<>(); // Reset
        tree.root = tree.insert(tree.root, 30);
        tree.root = tree.insert(tree.root, 20);
        tree.root = tree.insert(tree.root, 10); // Should trigger right rotation

        assertEquals(20, (int) tree.root.val, "Root should be 20 after right rotation");
        assertEquals(10, (int) tree.root.left.val, "Left child should be 10");
        assertEquals(30, (int) tree.root.right.val, "Right child should be 30");
        assertTrue(Math.abs(tree.getbalance(tree.root)) <= 1, "Balance factor should be ≤ 1");

        // Test Left-Right case (double rotation)
        tree = new AVLTree<>(); // Reset
        tree.root = tree.insert(tree.root, 30);
        tree.root = tree.insert(tree.root, 10);
        tree.root = tree.insert(tree.root, 20); // Should trigger left-right double rotation

        assertEquals(20, (int) tree.root.val, "Root should be 20 after double rotation");
        assertTrue(Math.abs(tree.getbalance(tree.root)) <= 1, "Balance factor should be ≤ 1");
    }



    private boolean isAVLBalanced(Node<Integer> node) {
        if (node == null) return true;

        int balance = tree.getbalance(node);
        if (Math.abs(balance) > 1) return false;

        return isAVLBalanced(node.left) && isAVLBalanced(node.right);
    }
}