package org.optima.exceptions;

public class MismatchLengthVectorsException extends RuntimeException {
    public MismatchLengthVectorsException(int len1, int len2) {
        super(String.format("The length of 1 vector (length - %s) does not match 2 vector (length - %s)",
                len1, len2));
    }
}
