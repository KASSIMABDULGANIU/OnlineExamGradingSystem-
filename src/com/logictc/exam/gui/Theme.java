package com.logictc.exam.gui;

import java.awt.Color;
import java.awt.Font;

/**
 * Central palette and font definitions so the whole GUI shares one consistent,
 * professional look. Keeping these constants in one place makes restyling easy.
 */
public final class Theme {

    private Theme() {
    }

    public static final Color PRIMARY = new Color(0x1E, 0x88, 0xE5);
    public static final Color PRIMARY_DARK = new Color(0x15, 0x65, 0xC0);
    public static final Color BG = new Color(0xF4, 0xF6, 0xFA);
    public static final Color CARD = Color.WHITE;
    public static final Color BORDER = new Color(0xE0, 0xE4, 0xEA);
    public static final Color TEXT = new Color(0x21, 0x25, 0x2B);
    public static final Color MUTED = new Color(0x6B, 0x72, 0x80);
    public static final Color SUCCESS = new Color(0x2E, 0x7D, 0x32);
    public static final Color DANGER = new Color(0xC6, 0x28, 0x28);

    public static final Font H1 = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font H2 = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BODY_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font SMALL = new Font("Segoe UI", Font.PLAIN, 12);
}
