package org.optima.kit;

@FunctionalInterface
public interface FunctionTUnary<T> {
    double apply(T arg);
}
