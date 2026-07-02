package com.logictc.exam.exception;

/**
 * Thrown when user-supplied input fails validation (empty fields, bad format,
 * wrong PIN, etc.). A checked exception so callers are forced to handle it,
 * which drives the exception-handling requirement of the project.
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String message) {
        super(message);
    }
}
