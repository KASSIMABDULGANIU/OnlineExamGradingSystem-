package com.logictc.exam.model;

/**
 * Abstraction: any object that can score a student's response and expose a
 * maximum attainable mark. Implemented by every question type so the grading
 * engine can treat all questions uniformly (polymorphism).
 */
public interface Gradable {

    /**
     * Grade a raw student response and return the marks awarded.
     *
     * @param response the answer submitted by the student
     * @return marks awarded, never negative and never above {@link #getMaxPoints()}
     */
    int grade(String response);

    /** @return the maximum marks this item is worth. */
    int getMaxPoints();
}
