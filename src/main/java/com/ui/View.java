package com.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JTextField firstFilePath;
    private JTextField secondFilePath;

    private JButton setFirstFile;
    private JButton setSecondFile;

    private JTextArea logField;

    private JCheckBox printShinglesBox;
    private JButton runButton;

    public View() {
        this.setTitle("Шинглы");
        this.setSize(600, 600);
        this.setResizable(false);

        initComponents();

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        var firstFileLabel = new JLabel("Первый файл:");
        var secondFileLabel = new JLabel("Второй файл:");

        firstFilePath = new JTextField("", 64);
        secondFilePath = new JTextField("", 64);

        setFirstFile = new JButton("Выбрать");
        setSecondFile = new JButton("Выбрать");

        logField = new JTextArea("");
        logField.setEditable(false);

        printShinglesBox = new JCheckBox("Вывести шинглы", false);
        runButton = new JButton("Запустить");

        firstFileLabel.setBounds(10, 10, 140, 15);
        secondFileLabel.setBounds(10, 45, 140, 15);

        firstFilePath.setBounds(140, 10, 350,20);
        secondFilePath.setBounds(140, 45, 350,20);

        setFirstFile.setBounds(490, 10, 100, 20);
        setSecondFile.setBounds(490, 45, 100, 20);

        var scroll = new JScrollPane(logField);
        scroll.setBounds(10, 70, 580, 450);

        printShinglesBox.setBounds(220, 525, 150, 30);
        runButton.setBounds(460, 525, 120, 30);

        var panel = new JPanel();

        panel.setLayout(null);

        panel.add(firstFileLabel);
        panel.add(secondFileLabel);

        panel.add(firstFilePath);
        panel.add(secondFilePath);

        panel.add(setFirstFile);
        panel.add(setSecondFile);

        panel.add(scroll);

        panel.add(printShinglesBox);
        panel.add(runButton);

        this.add(panel);
    }

    public String getFirstFilePath() {
        return firstFilePath.getText();
    }

    public View setFirstFilePath(String path) {
        firstFilePath.setText(path);
        return this;
    }

    public String getSecondFilePath() {
        return secondFilePath.getText();
    }

    public View setSecondFilePath(String path) {
        secondFilePath.setText(path);
        return this;
    }

    public View addSetFirstFileAction(ActionListener listener) {
        setFirstFile.addActionListener(listener);
        return this;
    }

    public View addSetSecondFileAction(ActionListener listener) {
        setSecondFile.addActionListener(listener);
        return this;
    }

    public View setLogFieldText(String text) {
        logField.setText(text);
        return this;
    }

    public View addLogFieldLine(String line) {
        logField.append(line + "\n");
        return this;
    }

    public boolean printShingles() {
        return printShinglesBox.isSelected();
    }

    public View addRunAction(ActionListener listener) {
        runButton.addActionListener(listener);
        return this;
    }
}
