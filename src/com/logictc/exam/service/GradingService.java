package com.logictc.exam.service;

import com.logictc.exam.model.Exam;
import com.logictc.exam.model.GradedAnswer;
import com.logictc.exam.model.Question;
import com.logictc.exam.model.Result;
import com.logictc.exam.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Marks a whole exam by delegating to each question's own {@code grade} method.
 * The service never checks a question's concrete type: it relies purely on
 * polymorphic dispatch, so adding a new question type requires no change here.
 */
public class GradingService {

    /**
     * @param student   the candidate
     * @param exam      the exam being marked
     * @param responses map of questionId to the raw student response
     * @return a fully populated {@link Result} with a per-question breakdown
     */
    public Result grade(Student student, Exam exam, Map<String, String> responses) {
        List<GradedAnswer> answers = new ArrayList<>();
        int score = 0;
        for (Question question : exam.getQuestions()) {
            String response = responses.get(question.getId());
            int awarded = question.grade(response); // polymorphic call
            score += awarded;
            answers.add(new GradedAnswer(question, response, awarded));
        }
        return new Result(student, exam, score, exam.getTotalPoints(), answers);
    }
}
