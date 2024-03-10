package org.optima.lab1;

import java.util.function.DoubleUnaryOperator;

@FunctionalInterface
public interface GenericFunction {
    double apply(DoubleUnaryOperator function, double x1, double x2, double eps, int maxIterations);
}
