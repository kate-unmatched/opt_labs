package org.optima.interfaces;

@FunctionalInterface
public interface GenericTwoFunctions<T, R> {
    R apply(T function1, T function2, R x1, double eps, int maxIterations);

}
