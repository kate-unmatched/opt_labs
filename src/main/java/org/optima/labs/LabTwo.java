package org.optima.labs;

import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.FastMath;
import org.optima.collections.DoubleVector;
import org.optima.exceptions.MismatchLengthVectorsException;
import org.optima.kit.FunctionTUnary;
import org.optima.kit.NumericCommon;
import org.optima.utils.InterMethods;
import org.optima.utils.NumCharacteristics;

import static org.optima.utils.DefaultNum.REVERSE_GRP;
import static org.optima.utils.DetailedMethods.closestFibonacciPair;

public class LabTwo {
    public static DoubleVector dichotomyCore(FunctionTUnary<DoubleVector> function, DoubleVector left, DoubleVector right,
                                             double eps, int maxIterations) {
        DoubleVector lhs = new DoubleVector(left);
        DoubleVector rhs = new DoubleVector(right);

        DoubleVector x_c, root = DoubleVector.direction(lhs, rhs).mul(eps);
        int iteration = 0;

        while (iteration < maxIterations && DoubleVector.sub(rhs, lhs).magnitude() > eps) {
            x_c = DoubleVector.add(rhs, lhs).mul(.5);
            if (function.apply(DoubleVector.add(x_c, root)) < function.apply(DoubleVector.sub(x_c, root)))
                rhs = x_c;
            else
                lhs = x_c;

            iteration++;
        }

        return DoubleVector.add(rhs, lhs).mul(0.5);
    }

    public static RealVector dichotomyMy(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                         double eps, int maxIterations) {
        if (left.getDimension() != right.getDimension())
            throw new MismatchLengthVectorsException(left.getDimension(), right.getDimension());

        int iteration = 0;
        RealVector midpoint = left.add(right).mapMultiply(.5);

        while (iteration < maxIterations && right.subtract(left).getNorm() > 2 * eps) {
            if (function.apply(midpoint.mapAdd(eps)) > function.apply(midpoint.mapSubtract(eps))) {
                right = midpoint;
            } else {
                left = midpoint;
            }
            midpoint = left.add(right).mapMultiply(.5);
            iteration++;
        }

        System.out.printf("Dichotomy number calling function: %s\n", (iteration + 1) * 2);
        return midpoint;
    }

    public static RealVector dichotomyMy(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                         double eps, NumCharacteristics levelIter) {
        return InterMethods.customParam(function, left, right, eps, levelIter, LabTwo::dichotomyMy);
    }

    public static RealVector dichotomyMy(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                         NumCharacteristics levelEps, NumCharacteristics levelIter) {
        return InterMethods.customParam(function, left, right, levelEps, levelIter, LabTwo::dichotomyMy);
    }

    public static RealVector goldenRatio(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
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

        System.out.printf("GoldenRatio number calling function : %s\n", iteration + 2);
        System.out.printf("GoldenRatio argument range          : %s\n", left.getDistance(right));
        return left.add(right).mapMultiply(0.5);
    }

    public static RealVector goldenRatio(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                         double eps, NumCharacteristics levelIter) {
        return InterMethods.customParam(function, left, right, eps, levelIter, LabTwo::goldenRatio);
    }

    public static RealVector goldenRatio(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                         NumCharacteristics levelEps, NumCharacteristics levelIter) {
        return InterMethods.customParam(function, left, right, levelEps, levelIter, LabTwo::goldenRatio);
    }

    public static RealVector fibonacci(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                       double eps) {
        if (left.getDimension() != right.getDimension())
            throw new MismatchLengthVectorsException(left.getDimension(), right.getDimension());

        RealVector a = left.copy();
        RealVector b = right.copy();
        double delta;
        double[] fib_pair = closestFibonacciPair((b.subtract(a)).getNorm() / eps);
        double fib_num_1 = fib_pair[0];
        double fib_num_2 = fib_pair[1];

        while (fib_num_1 != fib_num_2 && (b.subtract(a)).getNorm() > eps) {
            delta = (b.subtract(a)).getNorm();
            double temp = fib_num_2 - fib_num_1;
            RealVector x1 = a.add(b.subtract(a).mapMultiply(temp / fib_num_2));
            RealVector x2 = a.add(b.subtract(a).mapMultiply(fib_num_1 / fib_num_2));
            fib_num_2 = fib_num_1;
            fib_num_1 = temp;
            if (function.apply(x1) < function.apply(x2)) {
                b = x2;
            } else {
                a = x1;
            }
        }

        return a.add(b).mapMultiply(0.5);
    }

    public static RealVector fibonacci(FunctionTUnary<RealVector> function, RealVector left, RealVector right,
                                       NumCharacteristics levelEps) {
        return InterMethods.customParam(function, left, right, levelEps, LabTwo::fibonacci);
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
            x_1 = dichotomyMy(function, x_0, x_1, eps, maxIterations);
            x_0 = x_1.copy();
            if (FastMath.abs(x_1.getEntry(coordinateId) - x_i) < eps) {
                optCoordinatesCount++;
                if (optCoordinatesCount == x_1.getDimension()) {
                    if (NumericCommon.SHOW_DEBUG_LOG) {
                        System.out.printf("per cord descend iterations number : %s\n", iteration + 1);
                    }
                    return x_0;
                }
                continue;
            }
            optCoordinatesCount = 0;
        }
        return x_0;
    }

    public static RealVector perCordDescend(FunctionTUnary<RealVector> function, RealVector xStart,
                                            double eps, NumCharacteristics levelIter) {
        return InterMethods.customParam2(function, xStart, eps, levelIter, LabTwo::perCordDescend);
    }

    public static RealVector perCordDescend(FunctionTUnary<RealVector> function, RealVector xStart,
                                            NumCharacteristics levelEps, NumCharacteristics levelIter) {
        return InterMethods.customParam2(function, xStart, levelEps, levelIter, LabTwo::perCordDescend);
    }
}
