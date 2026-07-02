package com.logictc.exam.gui;

import com.logictc.exam.model.Exam;
import com.logictc.exam.model.Student;
import com.logictc.exam.service.ExamRepository;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;

/**
 * The student's home screen: a welcome bar and a scrollable list of the exams
 * available to sit. Iterating the repository and building a card per exam shows
 * collections plus modular UI construction.
 */
public class DashboardPanel extends JPanel {

    public DashboardPanel(AppWindow app, ExamRepository repository, Student student) {
        setLayout(new BorderLayout());
        setBackground(Theme.BG);

        add(topBar(app, student), BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setBackground(Theme.BG);
        list.setBorder(BorderFactory.createEmptyBorder(22, 26, 22, 26));

        JLabel heading = Ui.label("Available Exams", Theme.H2, Theme.TEXT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        list.add(heading);
        list.add(Box.createVerticalStrut(14));

        for (Exam exam : repository.getExams()) {
            list.add(examCard(app, exam));
            list.add(Box.createVerticalStrut(14));
        }
        list.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    private JComponent topBar(AppWindow app, Student student) {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(Theme.PRIMARY_DARK);
        bar.setBorder(BorderFactory.createEmptyBorder(18, 26, 18, 26));

        JLabel hello = Ui.label("Welcome, " + student.getName(), Theme.H2, Color.WHITE);
        bar.add(hello, BorderLayout.WEST);

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> app.showLogin());
        bar.add(logout, BorderLayout.EAST);
        return bar;
    }

    private JComponent examCard(AppWindow app, Exam exam) {
        JPanel card = Ui.card();
        card.setLayout(new BorderLayout(12, 0));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel info = new JPanel();
        info.setBackground(Theme.CARD);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel title = Ui.label(exam.getTitle(), Theme.H2, Theme.TEXT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        String metaText = exam.getSubject() + "   |   " + exam.getQuestionCount()
                + " questions   |   " + exam.getTotalPoints() + " marks   |   "
                + exam.getDurationMinutes() + " min";
        JLabel meta = Ui.label(metaText, Theme.BODY, Theme.MUTED);
        meta.setAlignmentX(Component.LEFT_ALIGNMENT);

        info.add(title);
        info.add(Box.createVerticalStrut(6));
        info.add(meta);
        card.add(info, BorderLayout.CENTER);

        JButton start = Ui.primaryButton("Start Exam");
        start.addActionListener(e -> app.showExam(exam));
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(Theme.CARD);
        right.setPreferredSize(new Dimension(150, 60));
        right.add(start);
        card.add(right, BorderLayout.EAST);
        return card;
    }
}
