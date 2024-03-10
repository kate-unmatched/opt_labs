package org.optima.lab1;

import org.optima.utils.DefaultNum;
import org.optima.utils.NumCharacteristics;
import org.optima.utils.UsualMethods;

import java.util.function.DoubleUnaryOperator;

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

            double fRootLeft = function.applyAsDouble(root-eps);
            double fRootRight = function.applyAsDouble(root+eps);

            if (fRootRight > fRootLeft) {
                x2 = root;
            } else {
                x1 = root;
            }
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


    public static void main(String[] args) {
        System.out.println(dichotomy(x -> x * x - 4, 1, 3, 0.0001, 100));
    }
}
