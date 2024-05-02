package org.optima.interfaces;

@FunctionalInterface
public interface GenericIterFunction<T, R> {
    R apply(T function, R x1, R x2, double eps, int maxIterations);

}
