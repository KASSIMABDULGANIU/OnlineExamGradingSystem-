package com.logictc.exam.gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Small factory of pre-styled Swing components so the panels stay readable and
 * the styling stays consistent. Modular helpers, reused everywhere.
 */
public final class Ui {

    private Ui() {
    }

    public static JButton primaryButton(String text) {
        return new FlatButton(text, Theme.PRIMARY, Color.WHITE);
    }

    public static JButton successButton(String text) {
        return new FlatButton(text, Theme.SUCCESS, Color.WHITE);
    }

    public static JLabel label(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    /**
     * A white "card" panel with a light border and padding. Its maximum height is
     * clamped to its preferred height so it does not stretch inside a vertical
     * BoxLayout list.
     */
    public static JPanel card() {
        JPanel panel = new JPanel() {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
            }
        };
        panel.setBackground(Theme.CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                BorderFactory.createEmptyBorder(18, 22, 18, 22)));
        return panel;
    }
}
