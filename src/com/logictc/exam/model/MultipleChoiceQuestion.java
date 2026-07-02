package com.logictc.exam.model;

import java.util.List;

/**
 * A question with several options where exactly one is correct.
 *
 * <p>Extends {@link Question} (<b>inheritance</b>) and overrides {@link #grade}
 * and {@link #getType} to provide its own behaviour (<b>polymorphism</b>).</p>
 */
public class MultipleChoiceQuestion extends Question {

    private final List<String> options;
    private final int correctIndex;

    public MultipleChoiceQuestion(String id, String text, int points,
                                  List<String> options, int correctIndex) {
        super(id, text, points);
        if (options == null || options.size() < 2) {
            throw new IllegalArgumentException("A multiple choice question needs at least two options");
        }
        if (correctIndex < 0 || correctIndex >= options.size()) {
            throw new IllegalArgumentException("correctIndex is out of range");
        }
        this.options = List.copyOf(options);
        this.correctIndex = correctIndex;
    }

    public List<String> getOptions() {
        return options; // already an immutable copy
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    /**
     * Accepts either the selected option's index ("2") or the option text.
     * Awards full marks for a match, otherwise zero.
     */
    @Override
    public int grade(String response) {
        if (response == null || response.isBlank()) {
            return 0;
        }
        String trimmed = response.trim();
        try {
            int chosen = Integer.parseInt(trimmed);
            return chosen == correctIndex ? getPoints() : 0;
        } catch (NumberFormatException ex) {
            return options.indexOf(trimmed) == correctIndex ? getPoints() : 0;
        }
    }

    @Override
    public String getType() {
        return "Multiple Choice";
    }
}
