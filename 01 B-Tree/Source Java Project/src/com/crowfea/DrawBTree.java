package com.crowfea;


import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;


public class DrawBTree extends JFrame {

    private int key;
    private MyCanvas canvas;
    private JTextField keyText = new JTextField(10);
    private JTextField elementText = new JTextField(10);
    private JButton previousButton = new JButton("Previous");
    private JButton nextButton = new JButton("Next");

    private int index = 0;
    private LinkedList<BTree<Integer, Double>> bTreeLinkedList = new LinkedList<BTree<Integer, Double>>();

    private BTree<Integer, Double> bTree;

    public DrawBTree(BTree<Integer, Double> tree) {
        super("B-tree GUI");
        bTree = tree;
        final int windowHeight = 720;
        final int windowWidth = 1024;
        canvas = new MyCanvas(windowWidth, windowHeight, bTree);
        bTreeLinkedList.add(CloneUtils.clone(bTree));

        JButton insertButton = new JButton("Insert");
        JButton deleteButton = new JButton("Delete");
        JButton FindButton = new JButton("Find");
        JLabel keyPrompt = new JLabel("key: ");
        JLabel elementPrompt = new JLabel("element: ");
        elementText.setText("0.0");
        checkValid();

        JPanel contentPanel = new JPanel();
        JPanel controlPanel = new JPanel();
        JPanel menuPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        controlPanel.setLayout(new BorderLayout());
        menuPanel.setLayout(new FlowLayout());
        menuPanel.add(keyPrompt);
        menuPanel.add(keyText);
        menuPanel.add(elementPrompt);
        menuPanel.add(elementText);
        menuPanel.add(insertButton);
        menuPanel.add(deleteButton);
        menuPanel.add(FindButton);
        controlPanel.add(previousButton, BorderLayout.WEST);
        controlPanel.add(nextButton, BorderLayout.EAST);
        controlPanel.add(menuPanel, BorderLayout.CENTER);
        contentPanel.add(controlPanel, BorderLayout.NORTH);
        contentPanel.add(canvas);
        setContentPane(contentPanel);

        insertButton.addActionListener(e -> insertValue());
        deleteButton.addActionListener(e -> deleteValue());
        FindButton.addActionListener(e -> findValue());
        previousButton.addActionListener(e -> goPrevious());
        nextButton.addActionListener(e -> goNext());
        setSize(windowWidth, windowHeight);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void checkValid() {
        if (index > 0 && index < bTreeLinkedList.size() - 1) {
            previousButton.setEnabled(true);
            nextButton.setEnabled(true);
        } else if (index > 0 && index == bTreeLinkedList.size() - 1) {
            previousButton.setEnabled(true);
            nextButton.setEnabled(false);
        } else if (index == 0 && index < bTreeLinkedList.size() - 1) {
            previousButton.setEnabled(false);
            nextButton.setEnabled(true);
        } else {
            previousButton.setEnabled(false);
            nextButton.setEnabled(false);
        }
    }

    private void deleteList() {
        for (int i = bTreeLinkedList.size() - 1; i >= index; i--) {
            bTreeLinkedList.removeLast();
        }
    }

    private void insertValue() {
        try {
            key = Integer.parseInt(keyText.getText());
            double element = Double.parseDouble(elementText.getText());
            keyText.setText("");
            elementText.setText("0.0");
            if (index < bTreeLinkedList.size() - 1) {
                deleteList();
                bTreeLinkedList.add(CloneUtils.clone(bTree));
            }
            bTree.insert(key, element);
            bTreeLinkedList.add(CloneUtils.clone(bTree));
            index = bTreeLinkedList.size() - 1;
            checkValid();
            canvas.updateCanvas(bTree);
        } catch (NumberFormatException e) {
            System.out.println("Illegal input data!");
        }
    }

    private void deleteValue() {
        try {
            key = Integer.parseInt(keyText.getText());
            keyText.setText("");
            elementText.setText("0.0");
            if (index < bTreeLinkedList.size() - 1) {
                deleteList();
                bTreeLinkedList.add(CloneUtils.clone(bTree));
            }
            bTree.delete(key);
            bTreeLinkedList.add(CloneUtils.clone(bTree));
            index = bTreeLinkedList.size() - 1;
            checkValid();
            canvas.updateCanvas(bTree);
        } catch (NumberFormatException e) {
            System.out.println("Illegal input data!");
        }
    }
    
    private void findValue() {
        try {
            key = Integer.parseInt(keyText.getText());
            keyText.setText("");
            elementText.setText("0.0");
            if(bTree.get(key)==null) {
            	bTree.setFind(false);
            }
            else {
            	bTree.setFind(true);
            }
            canvas.updateCanvas(bTree);
        } catch (NumberFormatException e) {
            System.out.println("Illegal input data!");
        }
    }

    private void goPrevious() {
        if (index > 0) {
            bTree = bTreeLinkedList.get(index - 1);
            canvas.updateCanvas(bTree);
            index--;
            checkValid();
        }
    }

    private void goNext() {
        if (index < bTreeLinkedList.size() - 1) {
            bTree = bTreeLinkedList.get(index + 1);
            canvas.updateCanvas(bTree);
            index++;
            checkValid();
        }
    }
}

