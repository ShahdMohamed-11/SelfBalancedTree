package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TreeTest {

    private RedBlackTree<Integer> rbTree;
    private AVLTree<Integer> avlTree;

    private final int N = 1_000_000;
    private int[] insertedValues;

    @BeforeEach
    public void setup() {
        rbTree = new RedBlackTree<>();
        avlTree = new AVLTree<>();

        insertedValues = new int[N];
        Random rand = new Random(42); // fixed seed for reproducibility

        for (int i = 0; i < N; i++) {
            int val = rand.nextInt(Integer.MAX_VALUE);
            insertedValues[i] = val;
            rbTree.root = rbTree.insert(rbTree.root, val);
            avlTree.root = avlTree.insert(avlTree.root, val);
        }
    }

    // -------------------- RedBlackTree Tests --------------------

    @Test
    public void testRB_SearchForExistingValues() {
        for (int i = 0; i < 1000; i++) {
            assertTrue(rbTree.Search(rbTree.root, insertedValues[i]));
        }
    }

    @Test
    public void testRB_SearchForNonExistingValue() {
        assertFalse(rbTree.Search(rbTree.root, -1));
    }

    @Test
    public void testRB_Delete() {
        for (int i = 0; i < 1000; i++) {
            rbTree.root = rbTree.delete(rbTree.root, insertedValues[i]);
            assertFalse(rbTree.Search(rbTree.root, insertedValues[i]));
        }
    }

    @Test
    public void testRB_InsertAndDeletePerformance() {
        long start = System.currentTimeMillis();

        RedBlackTree<Integer> tree = new RedBlackTree<>();
        for (int i = 0; i < N; i++) {
            tree.root = tree.insert(tree.root, i);
        }

        for (int i = 0; i < N; i++) {
            assertTrue(tree.Search(tree.root, i));
        }

        for (int i = 0; i < N; i++) {
            tree.root = tree.delete(tree.root, i);
            assertFalse(tree.Search(tree.root, i));
        }

        long end = System.currentTimeMillis();
        System.out.println("RedBlackTree time: " + (end - start) + "ms");
    }

    // -------------------- AVLTree Tests --------------------

  
}
