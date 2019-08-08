package com.crowfea;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;


class MyCanvas extends Canvas {
    private BTree<Integer, Double> bTree;
    private int width, height;
    private int fontSize = 12;
    private int rectangleWidth = 45;

    MyCanvas(int width, int height, BTree<Integer, Double> bTree) {
        setBackground(Color.white);
        this.width = width;
        this.height = height;
        setSize(width, height);
        this.bTree = bTree;
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("SimHei", Font.BOLD, 16));
        g.drawString("Size : " + bTree.getTreeSize(), 50, 50);
        g.drawString("Height : " + bTree.getHeight(), 50, 70);
        g.drawString("Find : " + bTree.getFindRes(), 50, 90);
        DrawBTree(g);
    }

    void updateCanvas(BTree<Integer, Double> bTree) {
        this.bTree = bTree;
        this.repaint();
    }

    private void DrawNode(Graphics g, String s, int x, int y) {
        String firstString = s.substring(1, s.length() / 2);
        String secondString = s.substring(s.length() / 2 + 1, s.length() - 1);
        g.setFont(new Font("SimHei", Font.BOLD, fontSize));
        g.drawString(firstString, x + 12, y + 15);
        g.drawString(secondString, x + 10, y + 30);
        g.drawRect(x, y, rectangleWidth, 3 * fontSize);
    }

    private void DrawBTree(Graphics g) {
        BTNode<Pair<Integer, Double>> root = bTree.getRoot();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (root != null) {
            int lastSize = 0, keySize = 0;

            LinkedList<BTNode<Pair<Integer, Double>>> queue = new LinkedList<BTNode<Pair<Integer, Double>>>();
            LinkedList<BTNode<Pair<Integer, Double>>> treeNodes = new LinkedList<BTNode<Pair<Integer, Double>>>();
            LinkedList<Integer> nodeSize = new LinkedList<>();
            LinkedList<Integer> lastNodeSize = new LinkedList<>();
            LinkedList<Integer> tempLastSize = new LinkedList<>();
            LinkedList<Integer> lastX = new LinkedList<>();
            LinkedList<Integer> tempLastX = new LinkedList<>();

            queue.push(root);
            BTNode<Pair<Integer, Double>> currentNode;
            while ((currentNode = queue.poll()) != null) {
                treeNodes.add(currentNode);
                if (currentNode.isLastInternalNode()) {
                    lastSize++;
                    keySize += currentNode.getSize();
                }
                nodeSize.push(currentNode.getSize() + 1);
                for (int i = 0; i <= currentNode.getSize(); i++) {
                    if (!currentNode.getChild(i).isNull()) {
                        queue.offer(currentNode.getChild(i));
                    }
                }
            }

            int blockSpace = 90;
            int treeNodeSize = treeNodes.size();
            int x = (width - (keySize * rectangleWidth + (lastSize - 1) * 20)) / 2;
            int y = (height + ((bTree.getHeight() - 3) * blockSpace)) / 2;

            for (int i = 0; i < lastSize; i++) {
                int temp = nodeSize.poll();
                lastNodeSize.offer(temp);
                tempLastSize.offer(temp);
            }

            for (int i = treeNodeSize - lastSize; i < treeNodes.size(); i++) {
                BTNode<Pair<Integer, Double>> node = treeNodes.get(i);
                if (node.isLastInternalNode()) {
                    for (int j = 0; j < node.getSize(); j++) {
                        String string = node.getKey(j).toString();
                        DrawNode(g, string, x, y);
                        lastX.push(x);
                        x += rectangleWidth;
                    }
                    x += 20;
                }
            }

            int m = 1;
            while (nodeSize.size() != 0) {
                int l = 0, n = 0;
                y -= blockSpace;
                tempLastX.clear();
                LinkedList<Integer> nodeX = new LinkedList<>();

                if (nodeSize.size() == 1) {
                    n = 1;
                } else {
                    for (int i = 0; i < nodeSize.size(); i++) {
                        if (n != lastNodeSize.size())
                            n += nodeSize.get(i);
                        else {
                            n = i;
                            break;
                        }
                    }
                }

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < nodeSize.get(i); j++) {
                        for (int k = 0; k < lastNodeSize.get(l) - 1; k++) {
                            nodeX.push(lastX.pollFirst());
                        }
                        l++;
                    }
                    for (int j = nodeX.size() - 1; j > 0; j--) {
                        if (nodeX.get(j) - nodeX.get(j - 1) == rectangleWidth)
                            nodeX.remove(j);
                    }
                    BTNode<Pair<Integer, Double>> node = treeNodes.get(treeNodeSize - lastSize - m);
                    int size = node.getSize();
                    int halfSize = (size + 1) / 2;
                    if ((size + 1) % 2 == 0) {
                        int tempX1 = (tempLastSize.get(node.getSize() - halfSize) - 1) * rectangleWidth;
                        int tempX2 = (tempLastSize.get(node.getSize() - halfSize + 1) - 1) * rectangleWidth;
                        tempX1 = (tempX1 / 2) + nodeX.get(halfSize);
                        tempX2 = (tempX2 / 2) + nodeX.get(halfSize - 1);
                        x = ((tempX1 - tempX2) / 2 + tempX2) - ((nodeSize.get(i) - 1) * rectangleWidth / 2);
                    } else {
                        int tempX1 = (tempLastSize.get(node.getSize() - halfSize) - 1) * rectangleWidth;
                        tempX1 = (tempX1 / 2) + nodeX.get(halfSize);
                        x = tempX1 - ((nodeSize.get(i) - 1) * rectangleWidth / 2);
                    }
                    for (int j = 0; j <= node.getSize(); j++) {
                        int tempXi = (tempLastSize.get(node.getSize() - j) - 1) * rectangleWidth;
                        tempLastSize.remove((node.getSize() - j));
                        g.drawLine(x, y + 3 * fontSize, nodeX.get(j) + (tempXi / 2), y + blockSpace);
                        if (j != node.getSize()) {
                            String string = node.getKey(j).toString();
                            DrawNode(g, string, x, y);
                            tempLastX.push(x);
                            x += rectangleWidth;
                        }
                    }
                    m++;
                }

                lastNodeSize.clear();
                for (int i = 0; i < n; i++) {
                    int temp = nodeSize.pollFirst();
                    lastNodeSize.add(temp);
                    tempLastSize.add(temp);
                }
                Collections.sort(tempLastX);
                for (int i = 0; i < tempLastX.size(); i++) {
                    int temp = tempLastX.pollLast();
                    tempLastX.add(i, temp);
                    lastX.add(i, temp);
                }
            }
        }
    }
}

