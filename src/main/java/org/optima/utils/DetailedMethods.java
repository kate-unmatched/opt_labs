package org.optima.utils;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.optima.kit.FunctionTUnary;

public class DetailedMethods {

    public static double[] closestFibonacciPair(double value) {

        if (value < 2) return new double[]{0, 1};
        int fib_num_1 = 0;
        int fib_num_2 = 1;
        int temp;
        while (fib_num_1 < value) {
            temp = fib_num_1;
            fib_num_1 = fib_num_2;
            fib_num_2 += temp;
        }
        return new double[]{fib_num_1, fib_num_2};
    }

    public static RealVector calculateGradient(FunctionTUnary<RealVector> function, RealVector x, double eps) {
        double[] gradient = new double[x.getDimension()];

        for (int i = 0; i < x.getDimension(); i++) {

            RealVector xPlusEps = x.copy();
            xPlusEps.setEntry(i, x.getEntry(i) + eps);
            double functionValueAtXPlusEps = function.apply(xPlusEps);

            RealVector xMinusEps = x.copy();
            xMinusEps.setEntry(i, x.getEntry(i) - eps);
            double functionValueAtXMinusEps = function.apply(xMinusEps);

            gradient[i] = (functionValueAtXPlusEps - functionValueAtXMinusEps) / (2 * eps);
        }
        return new ArrayRealVector(gradient);
    }

    public static double goldenSectionSearch(FunctionTUnary<RealVector> function, RealVector x, RealVector direction, double eps) {
        double a = 0;
        double b = 1;
        double phi = (1 + Math.sqrt(5)) / 2 - 1;
        double x1 = b - phi * (b - a);
        double x2 = a + phi * (b - a);
        double f1 = function.apply(x.add(direction.mapMultiply(x1)));
        double f2 = function.apply(x.add(direction.mapMultiply(x2)));
        for (int i = 0; i < 100; i++) {
            if (f1 > f2) {
                a = x1;
                x1 = x2;
                f1 = f2;
                x2 = a + phi * (b - a);
                f2 = function.apply(x.add(direction.mapMultiply(x2)));
            } else {
                b = x2;
                x2 = x1;
                f2 = f1;
                x1 = b - phi * (b - a);
                f1 = function.apply(x.add(direction.mapMultiply(x1)));
            }
            if (Math.abs(b - a) < eps) {
                break;
            }
        }
        return (a + b) / 2;
    }

    public static RealVector computeGradient(FunctionTUnary<RealVector> function, RealVector x, double stepSize) {
        int dimensions = x.getDimension();
        RealVector gradient = new ArrayRealVector(dimensions);

        for (int i = 0; i < dimensions; i++) {
            RealVector xPlusEps = x.copy();
            xPlusEps.addToEntry(i, stepSize);
            double fxPlusEps = function.apply(xPlusEps);
            double fx = function.apply(x);
            double derivative = (fxPlusEps - fx) / stepSize; // Численное дифференцирование
            gradient.setEntry(i, derivative);
        }

        return gradient;
    }


}
