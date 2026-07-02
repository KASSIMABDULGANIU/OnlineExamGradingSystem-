package com.logictc.exam.model;

import java.util.List;
import java.util.Locale;

/**
 * A free-text question that is auto-marked by keyword coverage: the more of the
 * expected keywords appear in the answer, the higher the proportional mark.
 *
 * <p>Overrides {@link #grade}, {@link #getType} and {@link #isAutoMarked}
 * with behaviour completely different from the multiple-choice family, which is
 * the essence of <b>polymorphism</b>.</p>
 */
public class TheoryQuestion extends Question {

    private final List<String> keywords;
    private final String modelAnswer;

    public TheoryQuestion(String id, String text, int points,
                          List<String> keywords, String modelAnswer) {
        super(id, text, points);
        this.keywords = keywords == null ? List.of() : List.copyOf(keywords);
        this.modelAnswer = modelAnswer == null ? "" : modelAnswer;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public String getModelAnswer() {
        return modelAnswer;
    }

    @Override
    public int grade(String response) {
        if (response == null || response.isBlank() || keywords.isEmpty()) {
            return 0;
        }
        String lower = response.toLowerCase(Locale.ROOT);
        int matched = 0;
        for (String keyword : keywords) {
            if (lower.contains(keyword.toLowerCase(Locale.ROOT))) {
                matched++;
            }
        }
        return (int) Math.round((matched / (double) keywords.size()) * getPoints());
    }

    @Override
    public boolean isAutoMarked() {
        return !keywords.isEmpty();
    }

    @Override
    public String getType() {
        return "Theory";
    }
}
