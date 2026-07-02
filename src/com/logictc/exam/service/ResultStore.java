package com.logictc.exam.service;

import com.logictc.exam.model.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps every submitted {@link Result} for the current session and derives
 * simple class statistics for the teacher dashboard.
 */
public class ResultStore {

    private final List<Result> results = new ArrayList<>();

    public void add(Result result) {
        results.add(result);
    }

    public List<Result> getAll() {
        return List.copyOf(results);
    }

    public int getCount() {
        return results.size();
    }

    public double averagePercentage() {
        if (results.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Result result : results) {
            sum += result.getPercentage();
        }
        return sum / results.size();
    }

    public double highestPercentage() {
        double highest = 0.0;
        for (Result result : results) {
            highest = Math.max(highest, result.getPercentage());
        }
        return highest;
    }
}
