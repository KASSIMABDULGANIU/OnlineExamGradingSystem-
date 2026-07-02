package com.logictc.exam.gui;

import com.logictc.exam.exception.InvalidInputException;
import com.logictc.exam.model.Student;
import com.logictc.exam.service.AuthService;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;

/**
 * Sign-in screen with two paths: a student form and a teacher PIN. All input is
 * validated through {@link AuthService}; failures are caught and shown as a
 * friendly dialog instead of crashing (exception handling + validation).
 */
public class LoginPanel extends JPanel {

    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JComboBox<String> programBox = new JComboBox<>(new String[]{
            "MSc Information Technology", "BSc Computer Science", "Diploma in ICT"});
    private final JPasswordField pinField = new JPasswordField();

    public LoginPanel(AppWindow app, AuthService auth) {
        setLayout(new GridBagLayout());
        setBackground(Theme.BG);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                BorderFactory.createEmptyBorder(28, 34, 28, 34)));
        card.setPreferredSize(new Dimension(440, 560));

        card.add(left(Ui.label("Online Examination System", Theme.H1, Theme.PRIMARY_DARK)));
        card.add(Box.createVerticalStrut(4));
        card.add(left(Ui.label("Sign in to take an exam", Theme.BODY, Theme.MUTED)));
        card.add(Box.createVerticalStrut(22));

        card.add(fieldLabel("Student ID"));
        card.add(field(idField));
        card.add(Box.createVerticalStrut(14));
        card.add(fieldLabel("Full Name"));
        card.add(field(nameField));
        card.add(Box.createVerticalStrut(14));
        card.add(fieldLabel("Programme"));
        card.add(field(programBox));
        card.add(Box.createVerticalStrut(20));

        JButton studentBtn = Ui.primaryButton("Start as Student");
        studentBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        studentBtn.addActionListener(e -> onStudentLogin(app, auth));
        card.add(studentBtn);

        card.add(Box.createVerticalStrut(26));
        card.add(left(Ui.label("Teacher / Administrator", Theme.BODY_BOLD, Theme.TEXT)));
        card.add(Box.createVerticalStrut(10));
        card.add(fieldLabel("Teacher PIN  (demo: 1234)"));
        card.add(field(pinField));
        card.add(Box.createVerticalStrut(14));

        JButton teacherBtn = Ui.successButton("Teacher Login");
        teacherBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        teacherBtn.addActionListener(e -> onTeacherLogin(app, auth));
        card.add(teacherBtn);

        add(card);
    }

    private void onStudentLogin(AppWindow app, AuthService auth) {
        try {
            Student student = auth.loginStudent(
                    idField.getText(),
                    nameField.getText(),
                    (String) programBox.getSelectedItem());
            app.showStudentDashboard(student);
        } catch (InvalidInputException ex) {
            showError(ex.getMessage());
        }
    }

    private void onTeacherLogin(AppWindow app, AuthService auth) {
        try {
            auth.verifyTeacher(new String(pinField.getPassword()));
            app.showTeacherPanel();
        } catch (InvalidInputException ex) {
            showError(ex.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Sign-in Error", JOptionPane.WARNING_MESSAGE);
    }

    // ---- small layout helpers ----

    private JComponent left(JComponent c) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        return c;
    }

    private JComponent fieldLabel(String text) {
        JLabel label = Ui.label(text, Theme.SMALL, Theme.MUTED);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JComponent field(JComponent input) {
        input.setAlignmentX(Component.LEFT_ALIGNMENT);
        input.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        input.setFont(Theme.BODY);
        return input;
    }
}
