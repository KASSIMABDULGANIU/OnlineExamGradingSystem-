package com.logictc.exam.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The result of a completed exam attempt: who sat it, which exam, the score,
 * a per-question breakdown, and the time of submission. Derives percentage,
 * letter grade and pass/fail through its own methods (encapsulated behaviour).
 */
public class Result {

    private final Student student;
    private final Exam exam;
    private final int score;
    private final int total;
    private final List<GradedAnswer> answers;
    private final LocalDateTime submittedAt;

    public Result(Student student, Exam exam, int score, int total, List<GradedAnswer> answers) {
        this.student = student;
        this.exam = exam;
        this.score = score;
        this.total = total;
        this.answers = List.copyOf(answers);
        this.submittedAt = LocalDateTime.now();
    }

    public Student getStudent() {
        return student;
    }

    public Exam getExam() {
        return exam;
    }

    public int getScore() {
        return score;
    }

    public int getTotal() {
        return total;
    }

    public List<GradedAnswer> getAnswers() {
        return answers;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public double getPercentage() {
        return total == 0 ? 0.0 : (score * 100.0) / total;
    }

    /** Ternary/relational operators applied to derive a letter grade. */
    public String getGrade() {
        double p = getPercentage();
        if (p >= 80) return "A";
        if (p >= 70) return "B";
        if (p >= 60) return "C";
        if (p >= 50) return "D";
        return "F";
    }

    public boolean isPass() {
        return getPercentage() >= 50.0;
    }
}
