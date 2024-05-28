package org.optima.exceptions;

public class NoSolutionsException extends RuntimeException {
    public NoSolutionsException() {
        super("The problem has no feasible solution");
    }
}
