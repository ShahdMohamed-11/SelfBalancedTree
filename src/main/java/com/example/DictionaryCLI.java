package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DictionaryCLI {

    private static void handleBatchInsert(AVLTree<String> avlDict, RedBlackTree<String> rbDict, String filepath, String backend) {
        try {
            Scanner fileScanner = new Scanner(new File(filepath));
            int dictSizeBefore = backend.equals("avl") ? avlDict.size(avlDict.root) : rbDict.size(rbDict.root);
            int added = 0, existing = 0;
            while (fileScanner.hasNextLine()) {
                String word = fileScanner.nextLine().trim();
                if (!word.isEmpty()) {
                    boolean wasAdded = false;
                    if (backend.equals("avl")) {
                        if (!avlDict.Search(avlDict.root, word)) {
                            avlDict.root = avlDict.insert(avlDict.root, word);
                            wasAdded = true;
                        }
                    } else {
                        if (!rbDict.Search(rbDict.root, word)) {
                            rbDict.root = rbDict.insert(rbDict.root, word);
                            wasAdded = true;
                        }
                    }
                    
                    if (wasAdded) {
                        added++;
                    } else {
                        existing++;
                    }
                }
            }
            System.out.println("Batch Insert Complete: " + added + " added, " + existing + " already existed.");
            System.out.println("Size before: " + dictSizeBefore + ", size after: " + (dictSizeBefore + added) + ".");
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filepath);
        }
    }

    private static void handleBatchDelete(AVLTree<String> avlDict, RedBlackTree<String> rbDict, String filepath, String backend) {
        try {
            Scanner fileScanner = new Scanner(new File(filepath));
            int dictSizeBefore = backend.equals("avl") ? avlDict.size(avlDict.root) : rbDict.size(rbDict.root);
            int deleted = 0, notFound = 0;
            while (fileScanner.hasNextLine()) {
                String word = fileScanner.nextLine().trim();
                if (!word.isEmpty()) {
                    boolean wasDeleted = false;
                    if (backend.equals("avl")) {
                        if (avlDict.Search(avlDict.root, word)) {
                            avlDict.root = avlDict.delete(avlDict.root, word);
                            wasDeleted = true;
                        }
                    } else {
                        if (rbDict.Search(rbDict.root, word)) {
                            rbDict.root = rbDict.delete(rbDict.root, word);
                            wasDeleted = true;
                        }
                    }
                    
                    if (wasDeleted) {
                        deleted++;
                    } else {
                        notFound++;
                    }
                }
            }
            System.out.println("Batch Delete Complete: " + deleted + " deleted, " + notFound + " not found.");
            System.out.println("Size before: " + dictSizeBefore + ", size after: " + (dictSizeBefore - deleted) + ".");
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filepath);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter backend type (avl or redblack): ");
        String backend = scanner.nextLine().trim().toLowerCase();

        AVLTree<String> avlDict = null;
        RedBlackTree<String> rbDict = null;
        String backendType = "";

        switch (backend) {
            case "avl":
                avlDict = new AVLTree<>();
                backendType = "avl";
                break;
            case "redblack":
                rbDict = new RedBlackTree<>();
                backendType = "rb";
                break;
            default:
                System.out.println("Unknown backend type. Please enter 'avl' or 'redblack'. Exiting.");
                scanner.close();
                return;
        }

        System.out.println("Dictionary using " + backend.toUpperCase() + " backend initialized.");
        System.out.println("Available commands: insert <word>, delete <word>, search <word>, batch_insert <filepath>, batch_delete <filepath>, size, height, exit");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] parts = input.trim().split("\\s+", 2);

            if (parts.length == 0 || parts[0].isEmpty()) continue;
            String command = parts[0].toLowerCase();

            if (command.equals("exit")) {
                System.out.println("Exiting...");
                break;
            }

            // Only check for missing arguments if the command actually needs them
            if (!command.equals("size") && !command.equals("height") && parts.length < 2) {
                System.out.println("Missing argument. Try again.");
                continue;
            }

            String argument = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "insert":
                    boolean insertSuccess = false;
                    if (backendType.equals("avl")) {
                        if (!avlDict.Search(avlDict.root, argument)) {
                            avlDict.root = avlDict.insert(avlDict.root, argument);
                            insertSuccess = true;
                        }
                    } else {
                        if (!rbDict.Search(rbDict.root, argument)) {
                            rbDict.root = rbDict.insert(rbDict.root, argument);
                            insertSuccess = true;
                        }
                    }
                    
                    if (insertSuccess) {
                        System.out.println("Inserted: " + argument);
                    } else {
                        System.out.println("Insert failed: " + argument + " already exists.");
                    }
                    break;

                case "delete":
                    boolean deleteSuccess = false;
                    if (backendType.equals("avl")) {
                        if (avlDict.Search(avlDict.root, argument)) {
                            avlDict.root = avlDict.delete(avlDict.root, argument);
                            deleteSuccess = true;
                        }
                    } else {
                        if (rbDict.Search(rbDict.root, argument)) {
                            rbDict.root = rbDict.delete(rbDict.root, argument);
                            deleteSuccess = true;
                        }
                    }
                    
                    if (deleteSuccess) {
                        System.out.println("Deleted: " + argument);
                    } else {
                        System.out.println("Delete failed: " + argument + " does not exist.");
                    }
                    break;

                case "search":
                    boolean found = false;
                    if (backendType.equals("avl")) {
                        found = avlDict.Search(avlDict.root, argument);
                    } else {
                        found = rbDict.Search(rbDict.root, argument);
                    }
                    
                    if (found) {
                        System.out.println(argument + " exists in the dictionary.");
                    } else {
                        System.out.println(argument + " does not exist.");
                    }
                    break;

                case "batch_insert":
                    handleBatchInsert(avlDict, rbDict, argument, backendType);
                    break;

                case "batch_delete":
                    handleBatchDelete(avlDict, rbDict, argument, backendType);
                    break;

                case "size":
                    int size = 0;
                    if (backendType.equals("avl")) {
                        size = avlDict.size(avlDict.root);
                    } else {
                        size = rbDict.size(rbDict.root);
                    }
                    System.out.println("Size of dictionary: " + size);
                    break;

                case "height":
                    int height = 0;
                    if (backendType.equals("avl")) {
                        height = avlDict.height(avlDict.root);
                    } else {
                        height = rbDict.height(rbDict.root);
                    }
                    System.out.println("Height of dictionary: " + height);
                    break;

                default:
                    System.out.println("Unknown command.");
            }
        }

        scanner.close();
    }
}