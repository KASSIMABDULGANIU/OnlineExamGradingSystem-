package com.logictc.exam.service;

import com.logictc.exam.exception.InvalidInputException;
import com.logictc.exam.model.Student;

/**
 * Handles sign-in and validation for both students and the teacher/admin.
 * All validation failures are reported through {@link InvalidInputException}.
 */
public class AuthService {

    private static final String TEACHER_PIN = "1234";

    /**
     * Validate the student sign-in form and build a {@link Student}.
     *
     * @throws InvalidInputException if any field is missing or malformed
     */
    public Student loginStudent(String id, String name, String program)
            throws InvalidInputException {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidInputException("Student ID is required.");
        }
        if (!id.trim().matches("[A-Za-z0-9]+")) {
            throw new InvalidInputException("Student ID may contain letters and numbers only.");
        }
        if (name == null || name.trim().length() < 2) {
            throw new InvalidInputException("Please enter your full name (at least 2 characters).");
        }
        String safeProgram = (program == null) ? "" : program.trim();
        return new Student(id.trim(), name.trim(), safeProgram);
    }

    /**
     * Verify the teacher PIN.
     *
     * @throws InvalidInputException if the PIN is empty or incorrect
     */
    public void verifyTeacher(String pin) throws InvalidInputException {
        if (pin == null || pin.trim().isEmpty()) {
            throw new InvalidInputException("Please enter the teacher PIN.");
        }
        if (!pin.trim().equals(TEACHER_PIN)) {
            throw new InvalidInputException("Incorrect teacher PIN.");
        }
    }
}
