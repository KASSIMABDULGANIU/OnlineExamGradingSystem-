package com.logictc.exam.gui;

import com.logictc.exam.model.GradedAnswer;
import com.logictc.exam.model.Result;

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
import java.awt.FlowLayout;

/**
 * Shows the outcome of an attempt: headline score, percentage, letter grade and
 * pass/fail, followed by a per-question breakdown built from the result's
 * {@link GradedAnswer} list.
 */
public class ResultPanel extends JPanel {

    public ResultPanel(AppWindow app, Result result) {
        setLayout(new BorderLayout());
        setBackground(Theme.BG);

        add(header(result), BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(Theme.BG);
        body.setBorder(BorderFactory.createEmptyBorder(22, 26, 22, 26));

        body.add(summaryCard(result));
        body.add(Box.createVerticalStrut(16));

        JLabel breakdown = Ui.label("Answer Breakdown", Theme.H2, Theme.TEXT);
        breakdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(breakdown);
        body.add(Box.createVerticalStrut(10));

        for (GradedAnswer answer : result.getAnswers()) {
            body.add(answerCard(answer));
            body.add(Box.createVerticalStrut(10));
        }
        body.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(body);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        add(footer(app, result), BorderLayout.SOUTH);
    }

    private JComponent header(Result result) {
        JPanel bar = new JPanel(new BorderLayout());
        Color band = result.isPass() ? Theme.SUCCESS : Theme.DANGER;
        bar.setBackground(band);
        bar.setBorder(BorderFactory.createEmptyBorder(18, 26, 18, 26));
        String verdict = result.isPass() ? "PASS" : "FAIL";
        bar.add(Ui.label(result.getExam().getTitle() + "  —  " + verdict, Theme.H2, Color.WHITE),
                BorderLayout.WEST);
        return bar;
    }

    private JComponent summaryCard(Result result) {
        JPanel card = Ui.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel score = Ui.label(
                "Score: " + result.getScore() + " / " + result.getTotal(), Theme.H1, Theme.PRIMARY_DARK);
        score.setAlignmentX(Component.LEFT_ALIGNMENT);

        String line = String.format("Percentage: %.1f%%      Grade: %s      Candidate: %s",
                result.getPercentage(), result.getGrade(), result.getStudent().getName());
        JLabel details = Ui.label(line, Theme.BODY, Theme.TEXT);
        details.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(score);
        card.add(Box.createVerticalStrut(8));
        card.add(details);
        return card;
    }

    private JComponent answerCard(GradedAnswer answer) {
        JPanel card = Ui.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel q = Ui.label(answer.getQuestion().getText(), Theme.BODY_BOLD, Theme.TEXT);
        q.setAlignmentX(Component.LEFT_ALIGNMENT);

        Color markColor = answer.getAwarded() >= answer.getQuestion().getMaxPoints()
                ? Theme.SUCCESS
                : (answer.getAwarded() == 0 ? Theme.DANGER : Theme.MUTED);
        JLabel mark = Ui.label(
                "Marks awarded: " + answer.getAwarded() + " / " + answer.getQuestion().getMaxPoints(),
                Theme.BODY_BOLD, markColor);
        mark.setAlignmentX(Component.LEFT_ALIGNMENT);

        String given = answer.getResponse().isBlank() ? "(no answer)" : answer.getResponse();
        JLabel yours = Ui.label("Your answer: " + given, Theme.SMALL, Theme.MUTED);
        yours.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(q);
        card.add(Box.createVerticalStrut(6));
        card.add(mark);
        card.add(Box.createVerticalStrut(4));
        card.add(yours);
        return card;
    }

    private JComponent footer(AppWindow app, Result result) {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        footer.setBackground(Theme.BG);
        JButton dashboard = new JButton("Back to Dashboard");
        dashboard.addActionListener(e -> app.showStudentDashboard(result.getStudent()));
        JButton logout = Ui.primaryButton("Finish & Logout");
        logout.addActionListener(e -> app.showLogin());
        footer.add(dashboard);
        footer.add(logout);
        return footer;
    }
}
