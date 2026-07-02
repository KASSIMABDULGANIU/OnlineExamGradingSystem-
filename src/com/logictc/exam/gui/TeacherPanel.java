package com.logictc.exam.gui;

import com.logictc.exam.model.Result;
import com.logictc.exam.service.ResultStore;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The teacher/administrator view: a live table of every submitted result plus a
 * summary strip with the number of attempts, class average and top score.
 */
public class TeacherPanel extends JPanel {

    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("dd MMM HH:mm");

    public TeacherPanel(AppWindow app, ResultStore store) {
        setLayout(new BorderLayout());
        setBackground(Theme.BG);

        add(topBar(app), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(0, 14));
        content.setBackground(Theme.BG);
        content.setBorder(BorderFactory.createEmptyBorder(22, 26, 22, 26));

        content.add(statsStrip(store), BorderLayout.NORTH);
        content.add(resultsTable(store), BorderLayout.CENTER);

        add(content, BorderLayout.CENTER);
    }

    private JComponent topBar(AppWindow app) {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(Theme.PRIMARY_DARK);
        bar.setBorder(BorderFactory.createEmptyBorder(18, 26, 18, 26));
        bar.add(Ui.label("Teacher Dashboard", Theme.H2, Color.WHITE), BorderLayout.WEST);
        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> app.showLogin());
        bar.add(logout, BorderLayout.EAST);
        return bar;
    }

    private JComponent statsStrip(ResultStore store) {
        JPanel strip = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        strip.setBackground(Theme.BG);
        strip.add(statCard("Attempts", String.valueOf(store.getCount())));
        strip.add(statCard("Class Average", String.format("%.1f%%", store.averagePercentage())));
        strip.add(statCard("Highest Score", String.format("%.1f%%", store.highestPercentage())));
        return strip;
    }

    private JComponent statCard(String label, String value) {
        JPanel card = Ui.card();
        card.setLayout(new BorderLayout(0, 6));
        card.add(Ui.label(label, Theme.SMALL, Theme.MUTED), BorderLayout.NORTH);
        card.add(Ui.label(value, Theme.H1, Theme.PRIMARY_DARK), BorderLayout.CENTER);
        return card;
    }

    private JComponent resultsTable(ResultStore store) {
        String[] columns = {"Student", "ID", "Exam", "Score", "Percentage", "Grade", "Submitted"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Result> results = store.getAll();
        for (Result r : results) {
            model.addRow(new Object[]{
                    r.getStudent().getName(),
                    r.getStudent().getId(),
                    r.getExam().getTitle(),
                    r.getScore() + " / " + r.getTotal(),
                    String.format("%.1f%%", r.getPercentage()),
                    r.getGrade(),
                    r.getSubmittedAt().format(TIME)
            });
        }

        JTable table = new JTable(model);
        table.setFont(Theme.BODY);
        table.setRowHeight(28);
        table.getTableHeader().setFont(Theme.BODY_BOLD);
        table.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(Theme.BORDER));

        if (results.isEmpty()) {
            JPanel empty = new JPanel(new BorderLayout());
            empty.setBackground(Theme.CARD);
            empty.setBorder(BorderFactory.createLineBorder(Theme.BORDER));
            JLabel note = Ui.label("No exam attempts yet. Results will appear here after students submit.",
                    Theme.BODY, Theme.MUTED);
            note.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
            empty.add(note, BorderLayout.CENTER);
            return empty;
        }
        return scroll;
    }
}
