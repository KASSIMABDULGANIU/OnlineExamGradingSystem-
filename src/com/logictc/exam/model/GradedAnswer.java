package com.logictc.exam.model;

/**
 * The outcome of marking a single question: the question, the student's raw
 * response, and the marks awarded. Used to build a detailed result breakdown.
 */
public class GradedAnswer {

    private final Question question;
    private final String response;
    private final int awarded;

    public GradedAnswer(Question question, String response, int awarded) {
        this.question = question;
        this.response = response;
        this.awarded = awarded;
    }

    public Question getQuestion() {
        return question;
    }

    public String getResponse() {
        return response == null ? "" : response;
    }

    public int getAwarded() {
        return awarded;
    }

    public boolean isCorrect() {
        return awarded >= question.getMaxPoints();
    }
}
