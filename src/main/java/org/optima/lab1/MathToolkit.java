package org.optima.lab1;

import org.optima.utils.NumCharacteristics;

import static java.lang.Math.*;

import java.util.function.DoubleUnaryOperator;

import static org.optima.utils.DefaultNum.REVERSE_GRP;

public class MathToolkit {
    public static double dichotomy(DoubleUnaryOperator function, double x1, double x2, double eps, int maxIterations) {

        if (x1 > x2) {
            double tmp = x1;
            x1 = x2;
            x2 = tmp;
        }

        double root = .0;
        int iteration = 0;

        while (iteration < maxIterations && x2 - x1 > eps) {

            root = (x1 + x2) * .5;

            double fRootLeft = function.applyAsDouble(root - eps);
            double fRootRight = function.applyAsDouble(root + eps);

            if (fRootRight > fRootLeft)
                x2 = root;
            else
                x1 = root;

            iteration++;
        }
        return root;
    }

    public static double dichotomy(DoubleUnaryOperator function, double x1, double x2,
                                   double eps, NumCharacteristics levelIter) {
        return UsualMethods.customParam(function, x1, x2, eps, levelIter, MathToolkit::dichotomy);
    }

    public static double dichotomy(DoubleUnaryOperator function, double x1, double x2,
                                   NumCharacteristics levelEps, NumCharacteristics levelIter) {
        return UsualMethods.customParam(function, x1, x2, levelEps, levelIter, MathToolkit::dichotomy);
    }

    public static double goldenRatioMinimum(DoubleUnaryOperator function, double x1, double x2, double eps, int maxIterations) {

        if (x1 > x2) {
            double tmp = x1;
            x1 = x2;
            x2 = tmp;
        }

        int iteration = 0;

        while (iteration < maxIterations && abs(x2 - x1) > eps){

            double leftBoundary = x2 - (x2 - x1) * REVERSE_GRP;
            double rightBoundary = x1 + (x2 - x1) * REVERSE_GRP;

            double fRootLeft = function.applyAsDouble(leftBoundary);
            double fRootRight = function.applyAsDouble(rightBoundary);

            if (fRootLeft >= fRootRight)
                x1 = leftBoundary;
            else
                x2 = rightBoundary;

            iteration++;
        }

        return (x1 + x2) * .5;
    }

    public static double goldenRatioMinimum(DoubleUnaryOperator function, double x1, double x2,
                                   double eps, NumCharacteristics levelIter) {
        return UsualMethods.customParam(function, x1, x2, eps, levelIter, MathToolkit::goldenRatioMinimum);
    }

    public static double goldenRatioMinimum(DoubleUnaryOperator function, double x1, double x2,
                                   NumCharacteristics levelEps, NumCharacteristics levelIter) {
        return UsualMethods.customParam(function, x1, x2, levelEps, levelIter, MathToolkit::goldenRatioMinimum);
    }

    public static double goldenRatioMaximum(DoubleUnaryOperator function, double x1, double x2, double eps, int maxIterations) {

        if (x1 > x2) {
            double tmp = x1;
            x1 = x2;
            x2 = tmp;
        }

        int iteration = 0;

        while (iteration < maxIterations && abs(x2 - x1) > eps){

            double leftBoundary = x2 - (x2 - x1) * REVERSE_GRP;
            double rightBoundary = x1 + (x2 - x1) * REVERSE_GRP;

            double fRootLeft = function.applyAsDouble(leftBoundary);
            double fRootRight = function.applyAsDouble(rightBoundary);

            if (fRootLeft <= fRootRight)
                x1 = leftBoundary;
            else
                x2 = rightBoundary;

            iteration++;
        }

        return (x1 + x2) * .5;
    }

    public static double goldenRatioMaximum(DoubleUnaryOperator function, double x1, double x2,
                                            double eps, NumCharacteristics levelIter) {
        return UsualMethods.customParam(function, x1, x2, eps, levelIter, MathToolkit::goldenRatioMaximum);
    }

    public static double goldenRatioMaximum(DoubleUnaryOperator function, double x1, double x2,
                                            NumCharacteristics levelEps, NumCharacteristics levelIter) {
        return UsualMethods.customParam(function, x1, x2, levelEps, levelIter, MathToolkit::goldenRatioMaximum);
    }

    public static double fibonacci(DoubleUnaryOperator function, double x1, double x2, double eps) {

        if (x1 > x2) {
            double tmp = x1;
            x1 = x2;
            x2 = tmp;
        }

        double a = x1, b = x2, delta, temp;
        double[] fib_pair = UsualMethods.closestFibonacciPair((b - a) / eps);
        double fib_num_1 = fib_pair[0], fib_num_2 = fib_pair[1];

        while (fib_num_1 != fib_num_2 && x2 - x1 > eps) {
            delta = (b - a);
            temp = fib_num_2 - fib_num_1;
            x1 = a + delta * (temp / fib_num_2);
            x2 = a + delta * (fib_num_1 / fib_num_2);
            fib_num_2 = fib_num_1;
            fib_num_1 = temp;
            if (function.applyAsDouble(x1) < function.applyAsDouble(x2)) {
                b = x2;
                continue;
            }
            a = x1;
        }

        return (x2 + x1) * .5;
    }

    public static double fibonacci(DoubleUnaryOperator function, double x1, double x2, NumCharacteristics levelEps) {
        return UsualMethods.customParam(function, x1, x2, levelEps);
    }
}
