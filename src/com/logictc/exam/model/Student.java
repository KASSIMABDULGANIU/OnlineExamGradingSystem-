package com.logictc.exam.model;

/**
 * Represents a student sitting an exam. Immutable value object with all fields
 * private (encapsulation), exposed through getters only.
 */
public class Student {

    private final String id;
    private final String name;
    private final String program;

    public Student(String id, String name, String program) {
        this.id = id;
        this.name = name;
        this.program = program;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProgram() {
        return program;
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
