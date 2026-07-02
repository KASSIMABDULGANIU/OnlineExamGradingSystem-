package com.logictc.exam.model;

import java.util.List;

/**
 * A specialised {@link MultipleChoiceQuestion} restricted to True / False.
 *
 * <p>Reuses the parent's grading logic entirely (inheritance) and only changes
 * the displayed type label, demonstrating a deeper inheritance chain:
 * {@code Question -> MultipleChoiceQuestion -> TrueFalseQuestion}.</p>
 */
public class TrueFalseQuestion extends MultipleChoiceQuestion {

    public TrueFalseQuestion(String id, String text, int points, boolean correctAnswer) {
        // Index 0 == "True", index 1 == "False".
        super(id, text, points, List.of("True", "False"), correctAnswer ? 0 : 1);
    }

    @Override
    public String getType() {
        return "True / False";
    }
}
