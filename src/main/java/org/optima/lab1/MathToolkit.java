package org.optima.lab1;

import org.optima.utils.NumCharacteristics;

import static java.lang.Math.*;

import java.util.function.DoubleUnaryOperator;

import static org.optima.lab1.UsualMethods.closestFibonacciPairWithIndex;
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
        int n = 0; // Номер элемента в последовательности Фибоначчи
        double fibN = 1, fibNPlus1 = 1, fibNPlus2 = 2;

        // Определение n, при котором F(n+2) будет достаточно большим, чтобы удовлетворить условие точности
        while ((x2 - x1) / eps < fibNPlus2) {
            fibN = fibNPlus1;
            fibNPlus1 = fibNPlus2;
            fibNPlus2 = fibN + fibNPlus1;
            n++;
        }
        int[] pare = closestFibonacciPairWithIndex((x2 - x1) / eps);
        double x1New = x1 + fibN / fibNPlus2 * (x2 - x1);
        double x2New = x1 + fibNPlus1 / fibNPlus2 * (x2 - x1);
        double f1 = function.applyAsDouble(x1New);
        double f2 = function.applyAsDouble(x2New);

        for (int i = 0; i < n - 1; i++) {
            if (f1 < f2) {
                x2 = x2New;
                x2New = x1New;
                f2 = f1;
                x1New = x1 + fibNPlus2 - fibNPlus1 / fibNPlus2 * (x2 - x1);
                f1 = function.applyAsDouble(x1New);
            } else {
                x1 = x1New;
                x1New = x2New;
                f1 = f2;
                x2New = x1 + fibNPlus1 / fibNPlus2 * (x2 - x1);
                f2 = function.applyAsDouble(x2New);
            }

            fibNPlus2 = fibNPlus1;
            fibNPlus1 = fibN;
            fibN = fibNPlus2 - fibNPlus1;
        }

        // Последний шаг: выбор меньшего значения
        if (f1 < f2) {
            x2 = x1New;
        } else {
            x1 = x2New;
        }

        return (x1 + x2) / 2;
    }

    public static double fibonacci(DoubleUnaryOperator function, double x1, double x2, NumCharacteristics levelEps) {
        return UsualMethods.customParam(function, x1, x2, levelEps);
    }

    public static void main(String[] args) {
        System.out.println(fibonacci(x -> x * x - 4 * x + 4, 0, 3, 0.001));
    }
}
