package org.optima.labs;

import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.FastMath;
import org.optima.exceptions.MismatchLengthVectorsException;
import org.optima.kit.FunctionTUnary;
import org.optima.utils.InterMethods;
import org.optima.utils.NumCharacteristics;

import static org.optima.utils.DefaultNum.REVERSE_GRP;

public class Lab2 {

    public static RealVector dichotomyVector(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                             double eps, int maxIterations) {
        if (left.getDimension() != right.getDimension())
            throw new MismatchLengthVectorsException(left.getDimension(), right.getDimension());

        int iteration = 0;
        RealVector midpoint = left.add(right).mapMultiply(.5);
        RealVector direction = right.subtract(left).unitVector().mapMultiplyToSelf(eps);
        while (iteration < maxIterations && left.getDistance(right) > 2 * eps) {
            if (function.apply(midpoint.subtract(direction)) < function.apply(midpoint.add(direction))) {
                right = midpoint;
            } else {
                left = midpoint;
            }
            midpoint = left.add(right).mapMultiply(.5);
            iteration++;
        }

        System.out.printf("Dichotomy number calling function : %s\n", iteration * 2);
        System.out.printf("Dichotomy argument range          : %s\n", left.getDistance(right));
        System.out.printf("Dichotomy root > %s \n", midpoint.toString());
        return midpoint;
    }

    public static RealVector dichotomyVector(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                             double eps, NumCharacteristics levelIter) {
        return InterMethods.customParam(function, left, right, eps, levelIter, Lab2::dichotomyVector);
    }

    public static RealVector dichotomyVector(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                             NumCharacteristics levelEps, NumCharacteristics levelIter) {
        return InterMethods.customParam(function, left, right, levelEps, levelIter, Lab2::dichotomyVector);
    }

    public static RealVector goldenRatioVector(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                               double eps, int maxIterations) {
        if (left.getDimension() != right.getDimension())
            throw new MismatchLengthVectorsException(left.getDimension(), right.getDimension());

        int iteration = 0;

        RealVector leftBoundary = right.subtract(right.subtract(left).mapMultiply(REVERSE_GRP));
        RealVector rightBoundary = left.add(right.subtract(left).mapMultiply(REVERSE_GRP));
        double fRootLeft = function.apply(leftBoundary);
        double fRootRight = function.apply(rightBoundary);

        while (iteration++ < maxIterations && left.getDistance(right) > 2 * eps) {
            if (fRootLeft > fRootRight) {
                left = leftBoundary;
                leftBoundary = rightBoundary;
                fRootLeft = fRootRight;
                rightBoundary = left.add(right.subtract(left).mapMultiply(REVERSE_GRP));
                fRootRight = function.apply(rightBoundary);
            } else {
                right = rightBoundary;
                rightBoundary = leftBoundary;
                fRootRight = fRootLeft;
                leftBoundary = right.subtract(right.subtract(left).mapMultiply(REVERSE_GRP));
                fRootLeft = function.apply(leftBoundary);
            }
        }

        RealVector root = left.add(right).mapMultiply(0.5);
        System.out.printf("GoldenRatio number calling function : %s\n", iteration + 2);
        System.out.printf("GoldenRatio argument range          : %s\n", left.getDistance(right));
        System.out.printf("GoldenRatio root > %s \n", root.toString());
        return root;
    }

    public static RealVector goldenRatioVector(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                               double eps, NumCharacteristics levelIter) {
        return InterMethods.customParam(function, left, right, eps, levelIter, Lab2::goldenRatioVector);
    }

    public static RealVector goldenRatioVector(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                               NumCharacteristics levelEps, NumCharacteristics levelIter) {
        return InterMethods.customParam(function, left, right, levelEps, levelIter, Lab2::goldenRatioVector);
    }

    public static RealVector fibonacciVector(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                             double eps) {
        if (left.getDimension() != right.getDimension())
            throw new MismatchLengthVectorsException(left.getDimension(), right.getDimension());

        RealVector lhs = left.copy();
        RealVector rhs = right.copy();
        double fib_num_1 = 1.0, fib_num_2 = 1.0, ft, delta = (rhs.subtract(lhs)).getNorm() / eps;
        int iterations = 0;
        while (fib_num_2 < delta) {
            ft = fib_num_1;
            fib_num_1 = fib_num_2;
            fib_num_2 += ft;
            iterations++;
        }
        RealVector v_left = lhs.add(rhs.subtract(lhs).mapMultiply((fib_num_2 - fib_num_1) / fib_num_2));
        RealVector v_right = lhs.add(rhs.subtract(lhs).mapMultiply(fib_num_1 / fib_num_2));
        double f_val_left = function.apply(v_left);
        double f_val_right = function.apply(v_right);
        ft = fib_num_2 - fib_num_1;
        fib_num_2 = fib_num_1;
        fib_num_1 = ft;

        int iter = 0;
        while (iter++ < iterations) {
            if (f_val_left > f_val_right) {
                lhs = v_left;
                v_left = v_right;
                f_val_left = f_val_right;
                v_right = lhs.add(rhs.subtract(lhs).mapMultiply(fib_num_1 / fib_num_2));
                f_val_right = function.apply(v_right);
            } else {
                rhs = v_right;
                v_right = v_left;
                f_val_right = f_val_left;
                v_left = lhs.add(rhs.subtract(lhs).mapMultiply((fib_num_2 - fib_num_1) / fib_num_2));
                f_val_left = function.apply(v_left);
            }
            ft = fib_num_2 - fib_num_1;
            fib_num_2 = fib_num_1;
            fib_num_1 = ft;
            System.out.printf("Fibonacci argument current range          : %s\n", lhs.getDistance(rhs));
        }
        RealVector root = lhs.add(rhs).mapMultiply(0.5);
        System.out.printf("Fibonacci number calling function : %s\n", iterations + 2);
        System.out.printf("Fibonacci argument range          : %s\n", lhs.getDistance(rhs));
        System.out.printf("Fibonacci root > %s\n", root.toString());
        return root;
    }

    public static RealVector fibonacciVector(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                             NumCharacteristics levelEps) {
        return InterMethods.customParam(function, left, right, levelEps, Lab2::fibonacciVector);
    }

    public static RealVector perCordDescend(FunctionTUnary<RealVector> function, RealVector xStart, double eps, int maxIterations) {
        RealVector x_0 = xStart.copy();
        RealVector x_1 = xStart.copy();
        double step = 1.0;
        double x_i, y_1, y_0;
        int optCoordinatesCount = 0, coordinateId;
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            coordinateId = iteration % x_0.getDimension();
            x_1.setEntry(coordinateId, x_1.getEntry(coordinateId) - eps);
            y_0 = function.apply(x_1);
            x_1.setEntry(coordinateId, x_1.getEntry(coordinateId) + 2 * eps);
            y_1 = function.apply(x_1);
            x_1.setEntry(coordinateId, y_0 > y_1 ? x_1.getEntry(coordinateId) + step : x_1.getEntry(coordinateId) - step);
            x_i = x_0.getEntry(coordinateId);
            x_1 = fibonacciVector(function, x_0, x_1, eps);
            x_0 = x_1.copy();
            if (FastMath.abs(x_1.getEntry(coordinateId) - x_i) < 2 * eps) {
                optCoordinatesCount++;
                if (optCoordinatesCount == x_1.getDimension()) {
                    System.out.printf("Per cord root > %s\n", x_0.toString());
                    return x_0;
                }
                continue;
            }
            optCoordinatesCount = 0;
        }
        System.out.printf("Per cord root > %s\n", x_0.toString());
        return x_0;
    }

    public static RealVector perCordDescend(FunctionTUnary<RealVector> function, RealVector xStart,
                                            double eps, NumCharacteristics levelIter) {
        return InterMethods.customParam2(function, xStart, eps, levelIter, Lab2::perCordDescend);
    }

    public static RealVector perCordDescend(FunctionTUnary<RealVector> function, RealVector xStart,
                                            NumCharacteristics levelEps, NumCharacteristics levelIter) {
        return InterMethods.customParam2(function, xStart, levelEps, levelIter, Lab2::perCordDescend);
    }
}
