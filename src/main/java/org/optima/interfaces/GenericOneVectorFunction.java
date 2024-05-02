package org.optima.interfaces;

@FunctionalInterface
public interface GenericOneVectorFunction<T, R> {
    R apply(T function, R x1, double eps, int maxIterations);

}
