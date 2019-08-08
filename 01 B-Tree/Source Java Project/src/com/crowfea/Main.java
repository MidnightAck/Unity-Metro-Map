package com.crowfea;

public class Main {
    public static void main(String[] args) {
        BTree<Integer, Double> bTree = new BTree<Integer, Double>(3);
        System.out.print("Is B-tree empty: ");
        System.out.println(bTree.isEmpty());

        // test function insert
        /*
        bTree.insert(10, 0.0);
        bTree.insert(20, 0.0);
        bTree.insert(25, 0.0);
        bTree.insert(30, 0.0);
        bTree.insert(40, 0.0);
        bTree.insert(50, 0.0);
        bTree.insert(70, 0.0);
        bTree.insert(80, 0.0);
        bTree.insert(85, 0.0);
        bTree.insert(90, 0.0);
        bTree.insert(95, 0.0);
        bTree.insert(55, 0.0);
        bTree.insert(60, 0.0);
        bTree.insert(35, 0.0);
        bTree.insert(82, 0.0);
        bTree.insert(44, 0.0);

        // test function replace
        bTree.replace(10, 1.0);
        bTree.replace(11, 0.0);

        // test function delete
        bTree.delete(82);
		*/
        // construct new B-tree
        new DrawBTree(bTree);

        System.out.print("Pair for key = 10 is: (");
        System.out.println(bTree.get(10) + ")");
        System.out.print("B-tree size: ");
        System.out.println(bTree.getTreeSize());
        System.out.print("Height of B-tree: ");
        System.out.println(bTree.getHeight());

        System.out.println("B-tree in level order: ");
        System.out.println(bTree);
    }
}

