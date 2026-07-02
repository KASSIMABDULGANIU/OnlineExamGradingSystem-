package com.logictc.exam.gui;

import com.logictc.exam.model.Exam;
import com.logictc.exam.model.MultipleChoiceQuestion;
import com.logictc.exam.model.Question;
import com.logictc.exam.model.Result;
import com.logictc.exam.model.Student;
import com.logictc.exam.service.GradingService;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The exam-taking screen. Renders every question with the appropriate control
 * (radio buttons for choices, a text area for theory), runs a countdown timer,
 * and on submit gathers the responses and hands them to {@link GradingService}.
 */
public class ExamPanel extends JPanel {

    /** Reads the current answer for one question, whatever its input type. */
    private interface AnswerProvider {
        String questionId();
        String response();
    }

    private final AppWindow app;
    private final Exam exam;
    private final GradingService grading;
    private final Student student;

    private final List<AnswerProvider> providers = new ArrayList<>();
    private final JLabel timerLabel = new JLabel();
    private Timer timer;
    private int remainingSeconds;

    public ExamPanel(AppWindow app, Exam exam, GradingService grading, Student student) {
        this.app = app;
        this.exam = exam;
        this.grading = grading;
        this.student = student;

        setLayout(new BorderLayout());
        setBackground(Theme.BG);

        add(topBar(), BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(Theme.BG);
        body.setBorder(BorderFactory.createEmptyBorder(22, 26, 22, 26));

        int number = 1;
        for (Question question : exam.getQuestions()) {
            body.add(buildQuestion(number++, question));
            body.add(Box.createVerticalStrut(16));
        }
        body.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(body);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        add(footer(), BorderLayout.SOUTH);

        startTimer();
    }

    private JComponent topBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(Theme.PRIMARY_DARK);
        bar.setBorder(BorderFactory.createEmptyBorder(16, 26, 16, 26));
        bar.add(Ui.label(exam.getTitle(), Theme.H2, Color.WHITE), BorderLayout.WEST);
        timerLabel.setFont(Theme.BODY_BOLD);
        timerLabel.setForeground(Color.WHITE);
        bar.add(timerLabel, BorderLayout.EAST);
        return bar;
    }

    private JComponent footer() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        footer.setBackground(Theme.BG);
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> {
            stopTimer();
            app.showStudentDashboard(student);
        });
        JButton submit = Ui.successButton("Submit Exam");
        submit.addActionListener(e -> confirmAndSubmit());
        footer.add(cancel);
        footer.add(submit);
        return footer;
    }

    private JComponent buildQuestion(int number, Question question) {
        JPanel card = Ui.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel head = Ui.label("Q" + number + ".   " + question.getText(), Theme.BODY_BOLD, Theme.TEXT);
        head.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel tag = Ui.label(question.getType() + "   •   " + question.getPoints() + " marks",
                Theme.SMALL, Theme.MUTED);
        tag.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(head);
        card.add(Box.createVerticalStrut(4));
        card.add(tag);
        card.add(Box.createVerticalStrut(12));

        if (question instanceof MultipleChoiceQuestion mcq) {
            ButtonGroup group = new ButtonGroup();
            List<String> options = mcq.getOptions();
            for (int i = 0; i < options.size(); i++) {
                JRadioButton radio = new JRadioButton(options.get(i));
                radio.setBackground(Theme.CARD);
                radio.setFont(Theme.BODY);
                radio.setAlignmentX(Component.LEFT_ALIGNMENT);
                radio.setActionCommand(String.valueOf(i));
                group.add(radio);
                card.add(radio);
            }
            providers.add(new AnswerProvider() {
                @Override
                public String questionId() {
                    return question.getId();
                }

                @Override
                public String response() {
                    ButtonModel selected = group.getSelection();
                    return selected == null ? null : selected.getActionCommand();
                }
            });
        } else {
            JTextArea area = new JTextArea(4, 40);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setFont(Theme.BODY);
            area.setBorder(BorderFactory.createLineBorder(Theme.BORDER));
            JScrollPane wrap = new JScrollPane(area);
            wrap.setAlignmentX(Component.LEFT_ALIGNMENT);
            wrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
            card.add(wrap);
            providers.add(new AnswerProvider() {
                @Override
                public String questionId() {
                    return question.getId();
                }

                @Override
                public String response() {
                    return area.getText();
                }
            });
        }
        return card;
    }

    private void confirmAndSubmit() {
        long answered = providers.stream()
                .filter(p -> p.response() != null && !p.response().isBlank())
                .count();
        int total = providers.size();
        if (answered < total) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "You have answered " + answered + " of " + total
                            + " questions.\nSubmit anyway?",
                    "Confirm Submission", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
        }
        submit();
    }

    private void submit() {
        stopTimer();
        Map<String, String> responses = new HashMap<>();
        for (AnswerProvider provider : providers) {
            responses.put(provider.questionId(), provider.response());
        }
        Result result = grading.grade(student, exam, responses);
        app.showResult(result);
    }

    // ---- countdown timer ----

    private void startTimer() {
        remainingSeconds = exam.getDurationMinutes() * 60;
        updateTimerLabel();
        timer = new Timer(1000, e -> {
            remainingSeconds--;
            updateTimerLabel();
            if (remainingSeconds <= 0) {
                stopTimer();
                JOptionPane.showMessageDialog(this,
                        "Time is up! Your exam will be submitted automatically.",
                        "Time Up", JOptionPane.INFORMATION_MESSAGE);
                submit();
            }
        });
        timer.start();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void updateTimerLabel() {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        timerLabel.setText(String.format("Time left  %02d:%02d", minutes, seconds));
    }
}
