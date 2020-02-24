package com.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MyCardLayout implements ItemListener {
    private JPanel cards;
    private JPanel comboBoxPane;
    private JComboBox<String> comboBox;

    public MyCardLayout() {
        comboBoxPane = new JPanel();
        cards = new JPanel(new CardLayout());
        comboBox = new JComboBox<>(new String[]{});
        comboBox.setEditable(true);
        comboBox.addItemListener(this);
        comboBoxPane.add(comboBox);
    }

    public void addCart(JPanel card, String name) {
        comboBox.addItem(name);
        cards.add(card, name);
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        if (cards.getLayout() instanceof CardLayout) {
            CardLayout cardLayout = (CardLayout) (cards.getLayout());
            cardLayout.show(cards, (String) itemEvent.getItem());
        }
    }

    public void show() {
        JFrame frame = new JFrame("CardLayout");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(comboBoxPane, BorderLayout.PAGE_START);
        frame.getContentPane().add(cards, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
