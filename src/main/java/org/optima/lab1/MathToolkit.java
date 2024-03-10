package org.optima.lab1;

import org.optima.utils.NumCharacteristics;
import org.optima.utils.UsualMethods;
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

    public static void main(String[] args) {
        System.out.println(goldenRatioMinimum(x -> x * x - 4, 1, 3, 0.0001, 100));
    }
}
