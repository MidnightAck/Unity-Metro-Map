package com.crowfea;

import java.io.Serializable;
import java.util.LinkedList;


public class BTree<K extends Comparable<K>, E> implements Tree, Serializable {
    private static final long serialVersionUID = 1267293988171991494L;
    private BTNode<Pair<K, E>> root = null;
    private int order, index, treeSize;
    private final int halfNumber;
    private final BTNode<Pair<K, E>> nullBTNode = new BTNode<Pair<K, E>>();

    /**
     *
     * @param order of B-tree
     */
    public BTree(int order) {
        if (order < 3) {
            try {
                throw new Exception("B-tree's order can not lower than 3");
            } catch (Exception e) {
                e.printStackTrace();
            }
            order = 3;
        }
        this.order = order;
        halfNumber = (order - 1) / 2;
    }

    /**
     * @return true, if tree is empty
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return root node
     */
    public BTNode<Pair<K, E>> getRoot() {
        return root;
    }

    /**
     * @return size of nodes in the tree
     */
    public int getTreeSize() {
        return treeSize;
    }

    /**
     * @return height of tree
     */
    public int getHeight() {
        if (isEmpty()) {
            return 0;
        } else {
            return getHeight(root);
        }
    }

    /**
     * @param node , the node
     * @return the height of the node position
     */
    private int getHeight(BTNode<Pair<K, E>> node) {
        int height = 0;
        BTNode<Pair<K, E>> currentNode = node;
        while (!currentNode.equals(nullBTNode)) {
            currentNode = currentNode.getChild(0);
            height++;
        }
        return height;
    }

    /**
     * @param key , the key to find the match pair
     * @return the pair match the key
     */
    public Pair<K, E> get(K key) {
        BTNode<Pair<K, E>> node = getNode(key);
        if (node != nullBTNode) {
            return node.getKey(index);
        } else {
            return null;
        }
    }

    /**
     * @param key , use key to find pair
     * @return the node which contains of the key
     */
    private BTNode<Pair<K, E>> getNode(K key) {
        if (isEmpty()) {
            return nullBTNode;
        }
        BTNode<Pair<K, E>> currentNode = root;
        while (!currentNode.equals(nullBTNode)) {
            int i = 0;
            while (i < currentNode.getSize()) {
                if (currentNode.getKey(i).first.equals(key)) {
                    index = i;
                    return currentNode;
                } else if (currentNode.getKey(i).first.compareTo(key) > 0) {
                    currentNode = currentNode.getChild(i);
                    i = 0;
                } else {
                    i++;
                }
            }
            if (!currentNode.isNull()) {
                currentNode = currentNode.getChild(currentNode.getSize());
            }
        }
        return nullBTNode;
    }

    /**
     * @param key     , the key to find pair
     * @param element , the element to be replaced
     */
    public void replace(K key, E element) {
        Pair<K, E> pair = new Pair<K, E>(key, element);
        BTNode<Pair<K, E>> currentNode = root;

        if (get(pair.first) == null) {
            return;
        }
        while (!currentNode.equals(nullBTNode)) {
            int i = 0;
            while (i < currentNode.getSize()) {
                if (currentNode.getKey(i).first.equals(pair.first)) {
                    currentNode.getKey(i).second = pair.second;
                    return;
                } else if (currentNode.getKey(i).first.compareTo(pair.first) > 0) {
                    currentNode = currentNode.getChild(i);
                    i = 0;
                } else {
                    i++;
                }
            }
            if (!currentNode.isNull())
                currentNode = currentNode.getChild(currentNode.getSize());
        }
    }

    /**
     * @param pair     , the pair
     * @param fullNode , full node
     * @return half of the full node after inserting pair inside
     */
    private BTNode<Pair<K, E>> getHalfKeys(Pair<K, E> pair, BTNode<Pair<K, E>> fullNode) {
        int fullNodeSize = fullNode.getSize();

        for (int i = 0; i < fullNodeSize; i++) {
            if (fullNode.getKey(i).first.compareTo(pair.first) > 0) {
                fullNode.addKey(i, pair);
                break;
            }
        }
        if (fullNodeSize == fullNode.getSize())
            fullNode.addKey(fullNodeSize, pair);

        return getHalfKeys(fullNode);
    }

    /**
     * @param fullNode , full node
     * @return half of the full node
     */
    private BTNode<Pair<K, E>> getHalfKeys(BTNode<Pair<K, E>> fullNode) {
        BTNode<Pair<K, E>> newNode = new BTNode<Pair<K, E>>(order);
        for (int i = 0; i < halfNumber; i++) {
            newNode.addKey(i, fullNode.getKey(0));
            fullNode.removeKey(0);
        }
        return newNode;
    }

    /**
     * @param halfNode , the rest of the full node
     * @return the left keys of full node
     */
    private BTNode<Pair<K, E>> getRestOfHalfKeys(BTNode<Pair<K, E>> halfNode) {
        BTNode<Pair<K, E>> newNode = new BTNode<Pair<K, E>>(order);
        int halfNodeSize = halfNode.getSize();
        for (int i = 0; i < halfNodeSize; i++) {
            if (i != 0) {
                newNode.addKey(i - 1, halfNode.getKey(1));
                halfNode.removeKey(1);
            }
            newNode.addChild(i, halfNode.getChild(0));
            halfNode.removeChild(0);
        }
        return newNode;
    }

    /**
     * @param childNode , merge childNode with its fatherNode
     * @param index     , where to add node
     */
    private void mergeWithFatherNode(BTNode<Pair<K, E>> childNode, int index) {
        childNode.getFather().addKey(index, childNode.getKey(0));
        childNode.getFather().removeChild(index);
        childNode.getFather().addChild(index, childNode.getChild(0));
        childNode.getFather().addChild(index + 1, childNode.getChild(1));
    }

    /**
     * @param childNode , merge childNode with its fatherNode
     */
    private void mergeWithFatherNode(BTNode<Pair<K, E>> childNode) {
        int fatherNodeSize = childNode.getFather().getSize();
        for (int i = 0; i < fatherNodeSize; i++) {
            if (childNode.getFather().getKey(i).compareTo(childNode.getKey(0)) > 0) {
                mergeWithFatherNode(childNode, i);
                break;
            }
        }
        if (fatherNodeSize == childNode.getFather().getSize()) {
            mergeWithFatherNode(childNode, fatherNodeSize);
        }
        for (int i = 0; i <= childNode.getFather().getSize(); i++)
            childNode.getFather().getChild(i).setFather(childNode.getFather());
    }

    /**
     * @param node , set father for split node
     */
    private void setSplitFatherNode(BTNode<Pair<K, E>> node) {
        for (int i = 0; i <= node.getSize(); i++)
            node.getChild(i).setFather(node);
    }

    /**
     * @param currentNode , process node if the keys size is overflow
     */
    private void processOverflow(BTNode<Pair<K, E>> currentNode) {
        BTNode<Pair<K, E>> newNode = getHalfKeys(currentNode);
        for (int i = 0; i <= newNode.getSize(); i++) {
            newNode.addChild(i, currentNode.getChild(0));
            currentNode.removeChild(0);
        }
        BTNode<Pair<K, E>> originalNode = getRestOfHalfKeys(currentNode);
        currentNode.addChild(0, newNode);
        currentNode.addChild(1, originalNode);
        originalNode.setFather(currentNode);
        newNode.setFather(currentNode);
        setSplitFatherNode(originalNode);
        setSplitFatherNode(newNode);
    }

    /**
     * @param key     , the key to find a place to insert
     * @param element , the element to be inserted
     */
    public void insert(K key, E element) {
        Pair<K, E> pair = new Pair<K, E>(key, element);
        if (isEmpty()) {
            root = new BTNode<Pair<K, E>>(order);
            root.addKey(0, pair);
            treeSize++;
            root.setFather(nullBTNode);
            root.addChild(0, nullBTNode);
            root.addChild(1, nullBTNode);
            return;
        }

        BTNode<Pair<K, E>> currentNode = root;

        if (get(pair.first) != null) {
            replace(key, element);
            return;
        }

        while (!currentNode.isLastInternalNode()) {
            int i = 0;
            while (i < currentNode.getSize()) {
                if (currentNode.isLastInternalNode()) {
                    i = currentNode.getSize();
                } else if (currentNode.getKey(i).first.compareTo(pair.first) > 0) {
                    currentNode = currentNode.getChild(i);
                    i = 0;
                } else {
                    i++;
                }
            }
            if (!currentNode.isLastInternalNode())
                currentNode = currentNode.getChild(currentNode.getSize());
        }

        if (!currentNode.isFull()) {
            int i = 0;
            while (i < currentNode.getSize()) {
                if (currentNode.getKey(i).first.compareTo(pair.first) > 0) {
                    currentNode.addKey(i, pair);
                    currentNode.addChild(currentNode.getSize(), nullBTNode);
                    treeSize++;
                    return;
                } else {
                    i++;
                }
            }
            currentNode.addKey(currentNode.getSize(), pair);
            currentNode.addChild(currentNode.getSize(), nullBTNode);
            treeSize++;
        } else {
            BTNode<Pair<K, E>> newChildNode = getHalfKeys(pair, currentNode);
            for (int i = 0; i < halfNumber; i++) {
                newChildNode.addChild(i, currentNode.getChild(0));
                currentNode.removeChild(0);
            }
            newChildNode.addChild(halfNumber, nullBTNode);
            BTNode<Pair<K, E>> originalFatherNode = getRestOfHalfKeys(currentNode);
            currentNode.addChild(0, newChildNode);
            currentNode.addChild(1, originalFatherNode);
            originalFatherNode.setFather(currentNode);
            newChildNode.setFather(currentNode);
            treeSize++;

            if (!currentNode.getFather().equals(nullBTNode)) {
                while (!currentNode.getFather().isOverflow() && !currentNode.getFather().equals(nullBTNode)) {
                    boolean flag = currentNode.getSize() == 1 && !currentNode.getFather().isOverflow();
                    if (currentNode.isOverflow() || flag) {
                        mergeWithFatherNode(currentNode);
                        currentNode = currentNode.getFather();
                        if (currentNode.isOverflow()) {
                            processOverflow(currentNode);
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }

    /**
     * @param node , the node
     * @return the number of the node's father child index which
     * matches the node
     */
    private int findChild(BTNode<Pair<K, E>> node) {
        if (!node.equals(root)) {
            BTNode<Pair<K, E>> fatherNode = node.getFather();

            for (int i = 0; i <= fatherNode.getSize(); i++) {
                if (fatherNode.getChild(i).equals(node))
                    return i;
            }
        }
        return -1;
    }

    /**
     * @param node , the node's father have different height of right and left subtree
     *             balance the unbalanced tree
     */
    private BTNode<Pair<K, E>> balanceDeletedNode(BTNode<Pair<K, E>> node) {
        boolean flag;
        int nodeIndex = findChild(node);
        Pair<K, E> pair;
        BTNode<Pair<K, E>> fatherNode = node.getFather();
        BTNode<Pair<K, E>> currentNode;
        if (nodeIndex == 0) {
            currentNode = fatherNode.getChild(1);
            flag = true;
        } else {
            currentNode = fatherNode.getChild(nodeIndex - 1);
            flag = false;
        }

        int currentSize = currentNode.getSize();
        if (currentSize > halfNumber) {
            if (flag) {
                pair = fatherNode.getKey(0);
                node.addKey(node.getSize(), pair);
                fatherNode.removeKey(0);
                pair = currentNode.getKey(0);
                currentNode.removeKey(0);
                node.addChild(node.getSize(), currentNode.getChild(0));
                currentNode.removeChild(0);
                fatherNode.addKey(0, pair);
                if (node.isLastInternalNode()) {
                    node.removeChild(0);
                }
            } else {
                pair = fatherNode.getKey(nodeIndex - 1);
                node.addKey(0, pair);
                fatherNode.removeKey(nodeIndex - 1);
                pair = currentNode.getKey(currentSize - 1);
                currentNode.removeKey(currentSize - 1);
                node.addChild(0, currentNode.getChild(currentSize));
                currentNode.removeChild(currentSize);
                fatherNode.addKey(nodeIndex - 1, pair);
                if (node.isLastInternalNode()) {
                    node.removeChild(0);
                }
            }
            return node;
        } else {
            if (flag) {
                currentNode.addKey(0, fatherNode.getKey(0));
                fatherNode.removeKey(0);
                fatherNode.removeChild(0);
                if (root.getSize() == 0) {
                    root = currentNode;
                    currentNode.setFather(nullBTNode);
                }
                if (node.getSize() == 0) {
                    currentNode.addChild(0, node.getChild(0));
                    currentNode.getChild(0).setFather(currentNode);
                }
                for (int i = 0; i < node.getSize(); i++) {
                    currentNode.addKey(i, node.getKey(i));
                    currentNode.addChild(i, node.getChild(i));
                    currentNode.getChild(i).setFather(currentNode);
                }
            } else {
                currentNode.addKey(currentNode.getSize(), fatherNode.getKey(nodeIndex - 1));
                fatherNode.removeKey(nodeIndex - 1);
                fatherNode.removeChild(nodeIndex);
                if (root.getSize() == 0) {
                    root = currentNode;
                    currentNode.setFather(nullBTNode);
                }
                int currentNodeSize = currentNode.getSize();
                if (node.getSize() == 0) {
                    currentNode.addChild(currentNodeSize, node.getChild(0));
                    currentNode.getChild(currentNodeSize).setFather(currentNode);
                }
                for (int i = 0; i < node.getSize(); i++) {
                    currentNode.addKey(currentNodeSize + i, node.getKey(i));
                    currentNode.addChild(currentNodeSize + i, node.getChild(i));
                    currentNode.getChild(currentNodeSize + i).setFather(currentNode);
                }
            }
            return fatherNode;
        }
    }

    /**
     * @param node , use the last internal node to replace the node
     * @return the last internal node
     */
    private BTNode<Pair<K, E>> replaceNode(BTNode<Pair<K, E>> node) {
        BTNode<Pair<K, E>> currentNode = node.getChild(index + 1);
        while (!currentNode.isLastInternalNode()) {
            currentNode = currentNode.getChild(0);
        }
        if (currentNode.getSize() - 1 < halfNumber) {
            currentNode = node.getChild(index);
            int currentNodeSize = currentNode.getSize();
            while (!currentNode.isLastInternalNode()) {
                currentNode = currentNode.getChild(currentNodeSize);
            }
            node.addKey(index, currentNode.getKey(currentNodeSize - 1));
            currentNode.removeKey(currentNodeSize - 1);
            currentNode.addKey(currentNodeSize - 1, node.getKey(index + 1));
            node.removeKey(index + 1);
            index = currentNode.getSize() - 1;
        } else {
            node.addKey(index + 1, currentNode.getKey(0));
            currentNode.removeKey(0);
            currentNode.addKey(0, node.getKey(index));
            node.removeKey(index);
            index = 0;
        }
        return currentNode;
    }

    /**
     * @param key , the key to be deleted
     */
    public void delete(K key) {
        BTNode<Pair<K, E>> node = getNode(key);
        BTNode<Pair<K, E>> deleteNode = null;
        if (node.equals(nullBTNode))
            return;

        if (node.equals(root) && node.getSize() == 1 && node.isLastInternalNode()) {
            root = null;
            treeSize--;
        } else {
            boolean flag = true;
            boolean isReplaced = false;
            if (!node.isLastInternalNode()) {
                node = replaceNode(node);
                deleteNode = node;
                isReplaced = true;
            }

            if (node.getSize() - 1 < halfNumber) {
                node = balanceDeletedNode(node);
                if (isReplaced) {
                    for (int i = 0; i <= node.getSize(); i++) {
                        for (int j = 0; i < node.getChild(i).getSize(); j++) {
                            if (node.getChild(i).getKey(j).first.equals(key)) {
                                deleteNode = node.getChild(i);
                                break;
                            }
                        }
                    }
                }
            } else if (node.isLastInternalNode()) {
                node.removeChild(0);
            }

            while (!node.getChild(0).equals(root) && node.getSize() < halfNumber && flag) {
                if (node.equals(root)) {
                    for (int i = 0; i <= root.getSize(); i++) {
                        if (root.getChild(i).getSize() == 0) {
                            flag = true;
                            break;
                        } else {
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    node = balanceDeletedNode(node);
                }
            }

            if (deleteNode == null) {
                node = getNode(key);
            } else {
                node = deleteNode;
            }

            if (!node.equals(nullBTNode)) {
                for (int i = 0; i < node.getSize(); i++) {
                    if (node.getKey(i).first == key) {
                        node.removeKey(i);
                    }
                }
                treeSize--;
            }
        }
    }

    /**
     * @return a string
     */
    public String toString() {
        if (isEmpty())
            return "NullNode";
        StringBuilder sb = new StringBuilder();
        int height = getHeight();

        LinkedList<BTNode<Pair<K, E>>> queue = new LinkedList<BTNode<Pair<K, E>>>();
        queue.push(root);

        BTNode<Pair<K, E>> temp;
        while ((temp = queue.poll()) != null) {
            for (int i = 0; i <= temp.getSize(); i++) {
                if (!temp.getChild(i).isNull())
                    queue.offer(temp.getChild(i));
            }
            sb.append("[Level: ").append(height - getHeight(temp)).append("] ");
            sb.append(temp.toString()).append("\n");
        }
        return sb.toString();
    }
}

