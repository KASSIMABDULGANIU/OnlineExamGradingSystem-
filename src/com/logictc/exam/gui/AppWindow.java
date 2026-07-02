package com.logictc.exam.gui;

import com.logictc.exam.model.Exam;
import com.logictc.exam.model.Result;
import com.logictc.exam.model.Student;
import com.logictc.exam.service.AuthService;
import com.logictc.exam.service.ExamRepository;
import com.logictc.exam.service.GradingService;
import com.logictc.exam.service.ResultStore;

import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.Dimension;

/**
 * The main application window. Owns the shared services and acts as a simple
 * navigation controller, swapping full-screen panels via a {@link CardLayout}.
 */
public class AppWindow extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel container = new JPanel(cardLayout);

    private final ExamRepository examRepository = new ExamRepository();
    private final GradingService gradingService = new GradingService();
    private final AuthService authService = new AuthService();
    private final ResultStore resultStore = new ResultStore();

    private Student currentStudent;

    public AppWindow() {
        super("Online Examination & Grading System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(920, 660));
        setLocationRelativeTo(null);
        container.setBackground(Theme.BG);
        add(container);
        showLogin();
    }

    public void showLogin() {
        currentStudent = null;
        swap("login", new LoginPanel(this, authService));
    }

    public void showStudentDashboard(Student student) {
        this.currentStudent = student;
        swap("dashboard", new DashboardPanel(this, examRepository, student));
    }

    public void showExam(Exam exam) {
        swap("exam", new ExamPanel(this, exam, gradingService, currentStudent));
    }

    public void showResult(Result result) {
        resultStore.add(result);
        swap("result", new ResultPanel(this, result));
    }

    public void showTeacherPanel() {
        swap("teacher", new TeacherPanel(this, resultStore));
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    private void swap(String name, JComponent panel) {
        container.removeAll();
        container.add(panel, name);
        cardLayout.show(container, name);
        container.revalidate();
        container.repaint();
    }
}
