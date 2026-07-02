package com.logictc.exam.model;

/**
 * Abstract base for every kind of exam question.
 *
 * <p>Demonstrates <b>encapsulation</b> (all fields private, exposed only through
 * getters), <b>abstraction</b> (the class cannot be instantiated directly and
 * declares abstract behaviour), and forms the root of the <b>inheritance</b>
 * hierarchy used throughout the system.</p>
 */
public abstract class Question implements Gradable {

    private final String id;
    private final String text;
    private final int points;

    protected Question(String id, String text, int points) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Question id must not be empty");
        }
        if (points <= 0) {
            throw new IllegalArgumentException("Question points must be positive");
        }
        this.id = id;
        this.text = text;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public int getMaxPoints() {
        return points;
    }

    /**
     * A short human-readable label for the question type, e.g. "Multiple Choice".
     * Overridden by each subclass (polymorphism).
     */
    public abstract String getType();

    /**
     * Whether this question can be marked automatically by the system. Theory
     * questions may return {@code false} when they need a teacher's judgement.
     */
    public boolean isAutoMarked() {
        return true;
    }
}
