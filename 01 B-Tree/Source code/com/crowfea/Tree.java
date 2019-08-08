package com.crowfea;

interface Tree<K extends Comparable<K>, E> {
    boolean isEmpty();

    BTNode<Pair<K, E>> getRoot();

    int getTreeSize();

    int getHeight();

    String toString();
}

