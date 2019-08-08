package com.crowfea;

import java.io.Serializable;
import java.util.ArrayList;


class BTNode<E extends Comparable<E>> implements Serializable {
    private static final long serialVersionUID = 2631590509760908280L;

    private int fullNumber;
    private BTNode<E> father;
    private ArrayList<BTNode<E>> children = new ArrayList<BTNode<E>>();
    private ArrayList<E> keys = new ArrayList<>();

    BTNode() {
    }

    BTNode(int order) {
        fullNumber = order - 1;
    }

    /**
     * @return true, if node is a leaf
     */
    boolean isLastInternalNode() {
        if (keys.size() == 0)
            return false;
        for (BTNode<E> node : children)
            if (node.keys.size() != 0)
                return false;
        return true;
    }

    /**
     * @return the father
     */
    BTNode<E> getFather() {
        return father;
    }

    /**
     * @param father the father to set
     */
    void setFather(BTNode<E> father) {
        this.father = father;
    }

    /**
     * @param index the index to get
     * @return the child
     */
    BTNode<E> getChild(int index) {
        return children.get(index);
    }

    /**
     * @param index the index to add
     * @param node  the node to be added
     */
    void addChild(int index, BTNode<E> node) {
        children.add(index, node);
    }

    /**
     * @param index the index to remove
     */
    void removeChild(int index) {
        children.remove(index);
    }

    /**
     * @param index the index to get
     * @return the key
     */
    E getKey(int index) {
        return keys.get(index);
    }

    /**
     * @param index   the index to add
     * @param element the element be added
     */
    void addKey(int index, E element) {
        keys.add(index, element);
    }

    /**
     * @param index the index to remove
     */
    void removeKey(int index) {
        keys.remove(index);
    }

    /**
     * @return true, if keys.size() == order - 1
     */
    boolean isFull() {
        return fullNumber == keys.size();
    }

    /**
     * @return true, if keys.size() > order - 1
     */
    boolean isOverflow() {
        return fullNumber < keys.size();
    }

    /**
     * @return true, if keys is empty
     */
    boolean isNull() {
        return keys.isEmpty();
    }

    /**
     * @return keys size
     */
    int getSize() {
        return keys.size();
    }

    /**
     * @return a string
     */
    public String toString() {
        if (keys.size() == 0)
            return "NullNode";

        StringBuilder sb = new StringBuilder();
        sb.append("[Numbers: ").append(keys.size()).append("] [values: ");
        for (E e : keys) {
            sb.append(e).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("] [father: ");
        if (father.keys.size() == 0) {
            sb.append("NullNode");
        } else {
            for (E e : father.keys) {
                sb.append(e).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("] [children: ");
        for (BTNode<E> node : children) {
            if (node.getSize() == 0)
                sb.append(node).append(", ");
            else
                sb.append("NotNullNode" + ", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("] [childrenSize: ").append(children.size()).append("]");
        return sb.toString();
    }
}
