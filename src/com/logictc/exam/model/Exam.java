package com.logictc.exam.model;

import java.util.List;

/**
 * An exam: a titled, timed collection of {@link Question}s. Because it stores a
 * list of the abstract {@code Question} type it can hold any mixture of
 * question subclasses without knowing their concrete types (polymorphism).
 */
public class Exam {

    private final String code;
    private final String title;
    private final String subject;
    private final int durationMinutes;
    private final List<Question> questions;

    public Exam(String code, String title, String subject,
                int durationMinutes, List<Question> questions) {
        if (questions == null || questions.isEmpty()) {
            throw new IllegalArgumentException("An exam must contain at least one question");
        }
        this.code = code;
        this.title = title;
        this.subject = subject;
        this.durationMinutes = durationMinutes;
        this.questions = List.copyOf(questions);
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getQuestionCount() {
        return questions.size();
    }

    /** Sum of the marks of every question in the exam. */
    public int getTotalPoints() {
        int total = 0;
        for (Question question : questions) {
            total += question.getPoints();
        }
        return total;
    }

    @Override
    public String toString() {
        return title;
    }
}
