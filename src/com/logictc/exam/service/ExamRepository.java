package com.logictc.exam.service;

import com.logictc.exam.model.Exam;
import com.logictc.exam.model.MultipleChoiceQuestion;
import com.logictc.exam.model.Question;
import com.logictc.exam.model.TheoryQuestion;
import com.logictc.exam.model.TrueFalseQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory store of the exams available in the system. Seeds a small computing
 * curriculum so the application is fully functional without a database.
 */
public class ExamRepository {

    private final List<Exam> exams = new ArrayList<>();

    public ExamRepository() {
        seed();
    }

    private void seed() {
        // ---- Exam 1: Introduction to Computing ----
        List<Question> intro = new ArrayList<>();
        intro.add(new MultipleChoiceQuestion("C1",
                "Which of these is an input device?", 5,
                List.of("Monitor", "Keyboard", "Printer", "Speaker"), 1));
        intro.add(new MultipleChoiceQuestion("C2",
                "What does CPU stand for?", 5,
                List.of("Central Print Unit", "Computer Personal Unit",
                        "Central Processing Unit", "Control Program Utility"), 2));
        intro.add(new TrueFalseQuestion("C3",
                "RAM is a form of volatile memory.", 5, true));
        intro.add(new TheoryQuestion("C4",
                "Explain the difference between hardware and software.", 10,
                List.of("hardware", "physical", "software", "program"),
                "Hardware is the physical components of a computer while software "
                        + "is the set of programs or instructions that run on it."));
        exams.add(new Exam("EXM-101", "Introduction to Computing", "Computing", 20, intro));

        // ---- Exam 2: Computer Networks ----
        List<Question> networks = new ArrayList<>();
        networks.add(new MultipleChoiceQuestion("N1",
                "Which device connects two different networks together?", 5,
                List.of("Switch", "Router", "Hub", "Repeater"), 1));
        networks.add(new TrueFalseQuestion("N2",
                "HTTP is the protocol used to transfer web pages.", 5, true));
        networks.add(new MultipleChoiceQuestion("N3",
                "Which of these is a valid IPv4 address?", 5,
                List.of("192.168.1.1", "256.300.1.1", "abcd.1.2.3", "192-168-1-1"), 0));
        networks.add(new TheoryQuestion("N4",
                "State two advantages of using a computer network.", 10,
                List.of("share", "resource", "communication", "file"),
                "Networks allow resource and file sharing and make communication "
                        + "between users faster and easier."));
        exams.add(new Exam("EXM-102", "Computer Networks", "Computing", 15, networks));
    }

    public List<Exam> getExams() {
        return List.copyOf(exams);
    }

    public Exam findByCode(String code) {
        for (Exam exam : exams) {
            if (exam.getCode().equals(code)) {
                return exam;
            }
        }
        return null;
    }
}
