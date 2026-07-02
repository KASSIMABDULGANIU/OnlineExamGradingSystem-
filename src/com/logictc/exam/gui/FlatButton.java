package com.logictc.exam.gui;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * A flat, rounded, colour-filled button. Painting the background ourselves keeps
 * the look identical across every platform look-and-feel, which native Swing
 * buttons do not guarantee.
 */
public class FlatButton extends JButton {

    private final Color base;

    public FlatButton(String text, Color base, Color foreground) {
        super(text);
        this.base = base;
        setForeground(foreground);
        setFont(Theme.BODY_BOLD);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color fill = base;
        if (getModel().isPressed()) {
            fill = base.darker();
        } else if (getModel().isRollover()) {
            fill = brighten(base);
        }
        g2.setColor(fill);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
        g2.dispose();
        super.paintComponent(g);
    }

    private static Color brighten(Color c) {
        int r = Math.min(255, (int) (c.getRed() * 1.1));
        int g = Math.min(255, (int) (c.getGreen() * 1.1));
        int b = Math.min(255, (int) (c.getBlue() * 1.1));
        return new Color(r, g, b);
    }
}
