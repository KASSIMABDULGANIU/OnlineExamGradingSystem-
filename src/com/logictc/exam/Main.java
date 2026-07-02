package com.logictc.exam;

import com.logictc.exam.gui.AppWindow;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Application entry point. Applies the Nimbus look-and-feel (falling back to the
 * default if unavailable) and launches the main window on the Swing event
 * dispatch thread, as required for thread-safe GUI code.
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
            // Any failure here just means we keep the default look-and-feel.
        }
        SwingUtilities.invokeLater(() -> new AppWindow().setVisible(true));
    }
}
