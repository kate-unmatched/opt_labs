package org.optima.utils;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.optima.kit.FunctionTUnary;

import static org.optima.utils.DefaultNum.GOLDEN_RATIO_PROPORTION;

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
        RealVector gradient = new ArrayRealVector(x.getDimension());

        for (int i = 0; i < x.getDimension(); i++) {

            RealVector xPlusEps = x.copy();
            xPlusEps.setEntry(i, x.getEntry(i) + eps);
            double functionValueAtXPlusEps = function.apply(xPlusEps);

            RealVector xMinusEps = x.copy();
            xMinusEps.setEntry(i, x.getEntry(i) - eps);
            double functionValueAtXMinusEps = function.apply(xMinusEps);

            gradient.setEntry(i, (functionValueAtXPlusEps - functionValueAtXMinusEps) / (2 * eps));
        }
        return gradient;
    }

    public static RealMatrix calculateHessian(FunctionTUnary<RealVector> function, RealVector x, double eps) {
        int n = x.getDimension();
        RealMatrix hessian = new Array2DRowRealMatrix(n, n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                RealVector xPlusEpsI = x.copy();
                xPlusEpsI.setEntry(i, x.getEntry(i) + eps);
                RealVector xPlusEpsJ = x.copy();
                xPlusEpsJ.setEntry(j, x.getEntry(j) + eps);
                RealVector xMinusEpsI = x.copy();
                xMinusEpsI.setEntry(i, x.getEntry(i) - eps);
                RealVector xMinusEpsJ = x.copy();
                xMinusEpsJ.setEntry(j, x.getEntry(j) - eps);

                double f00 = function.apply(x);
                double f10 = function.apply(xPlusEpsI);
                double f01 = function.apply(xPlusEpsJ);
                double f11 = function.apply(xPlusEpsI.add(xPlusEpsJ).mapMultiply(0.5));
                double f20 = function.apply(xMinusEpsI);
                double f02 = function.apply(xMinusEpsJ);

                double hessianEntry = (f11 - f10 - f01 + f00) / (eps * eps);
                hessian.setEntry(i, j, hessianEntry);
            }
        }

        return hessian;
    }
}
