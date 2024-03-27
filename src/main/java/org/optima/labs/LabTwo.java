package org.optima.labs;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.optima.collections.DoubleVector;
import org.optima.exceptions.MismatchLengthVectorsException;
import org.optima.kit.FunctionTUnary;
import org.optima.kit.NumericCommon;
import org.optima.utils.InterMethods;
import org.optima.utils.NumCharacteristics;

import java.util.function.DoubleUnaryOperator;

public class LabTwo {
    public static DoubleVector dichotomyCore(FunctionTUnary<DoubleVector> function, DoubleVector left, DoubleVector right,
                                      double eps, int maxIterations) {
        DoubleVector lhs = new DoubleVector(left);
        DoubleVector rhs = new DoubleVector(right);

        DoubleVector x_c, root = DoubleVector.direction(lhs, rhs).mul(eps);
        int iteration = 0;

        while (iteration < maxIterations && DoubleVector.sub(rhs, lhs).magnitude() > eps) {
            x_c = DoubleVector.add(rhs, lhs).mul(.5);
            if (function.apply(DoubleVector.add(x_c, root)) > function.apply(DoubleVector.sub(x_c, root)))
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

        while (iteration < maxIterations && right.subtract(left).getNorm() > eps) {
            if (function.apply(midpoint.mapAdd(eps)) > function.apply(midpoint.mapSubtract(eps))) {
                right = midpoint;
            } else {
                left = midpoint;
            }
            midpoint = left.add(right).mapMultiply(.5);
            iteration++;
        }

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

    public static DoubleVector goldenRatio(FunctionTUnary<DoubleVector> function, DoubleVector left, DoubleVector right,
                                           double eps, int maxIterations) {
        DoubleVector lhs = new DoubleVector(left);
        DoubleVector rhs = new DoubleVector(right);
        DoubleVector x_l = DoubleVector.sub(rhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs), NumericCommon.PSI));
        DoubleVector x_r = DoubleVector.add(lhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs), NumericCommon.PSI));
        double f_l = function.apply(x_l);
        double f_r = function.apply(x_r);
        int iteration = 0;
        for (; iteration != maxIterations; iteration++)
        {
            if (DoubleVector.sub(rhs, lhs).magnitude() < 2 * eps) break;
            if (f_l > f_r)
            {
                lhs = x_l;
                x_l = x_r;
                f_l = f_r;
                x_r = DoubleVector.add(lhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs), NumericCommon.PSI));
                f_r = function.apply(x_r);
            }
            else
            {
                rhs = x_r;
                x_r = x_l;
                f_r = f_l;
                x_l =  DoubleVector.sub(rhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs), NumericCommon.PSI));
                f_l = function.apply(x_l);
            }
        }
        if (NumericCommon.SHOW_ZERO_ORDER_METHODS_DEBUG_LOG)
        {
            System.out.printf("goldenRatio::function arg range    : %s\n", DoubleVector.sub(rhs, lhs).magnitude());
            System.out.printf("goldenRatio::function probes count : %s\n", 2 + iteration);
        }
        return DoubleVector.add(rhs, lhs).mul(0.5);
    }

    public static DoubleVector goldenRatio(FunctionTUnary<DoubleVector> function, DoubleVector left, DoubleVector right, double eps) {
        return goldenRatio(function, left, right, eps, NumericCommon.ITERATIONS_COUNT_HIGH);
    }

    public static DoubleVector goldenRatio(FunctionTUnary<DoubleVector> f, DoubleVector left, DoubleVector right) {
        return goldenRatio(f, left, right, NumericCommon.NUMERIC_ACCURACY_MIDDLE, NumericCommon.ITERATIONS_COUNT_HIGH);
    }

    public static DoubleVector fibonacci(FunctionTUnary<DoubleVector> function, DoubleVector left, DoubleVector right, double eps) {
        DoubleVector lhs = new DoubleVector(left);
        DoubleVector rhs = new DoubleVector(right);
        double value, fib_t, fib_1 = 1.0, fib_2 = 1.0;
        int iterations = 0;
        value = DoubleVector.sub(right, left).magnitude() / eps;
        while (fib_2 < value)
        {
            iterations++;
            fib_t = fib_1;
            fib_1 = fib_2;
            fib_2 += fib_t;
        }
        DoubleVector x_l = DoubleVector.add(lhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs), (fib_2 - fib_1) / fib_2));
        DoubleVector x_r = DoubleVector.add(lhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs),  fib_1 / fib_2));
        double f_l = function.apply(x_l);
        double f_r = function.apply(x_r);
        fib_t = fib_2 - fib_1;
        fib_2 = fib_1;
        fib_1 = fib_t;
        for (int index = iterations; index > 0; index--)
        {
            if (f_l > f_r)
            {
                lhs = x_l;
                x_l = x_r;
                f_l = f_r;
                x_r = DoubleVector.add(lhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs),  fib_1 / fib_2));
                f_r = function.apply(x_r);
            }
            else
            {
                rhs = x_r;
                x_r = x_l;
                f_r = f_l;
                x_l = DoubleVector.add(lhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs), (fib_2 - fib_1) / fib_2));
                f_l = function.apply(x_l);
            }
            fib_t = fib_2 - fib_1;
            fib_2 = fib_1;
            fib_1 = fib_t;
        }
        if (NumericCommon.SHOW_ZERO_ORDER_METHODS_DEBUG_LOG)
        {
            System.out.printf("goldenRatio::function arg range    : %s\n", DoubleVector.sub(rhs, lhs).magnitude());
            System.out.printf("goldenRatio::function probes count : %s\n", 2 + iterations);
        }
        return DoubleVector.add(rhs, lhs).mul(0.5);
    }

    public static DoubleVector fibonacci(FunctionTUnary<DoubleVector> function, DoubleVector left, DoubleVector right) {
        return fibonacci(function, left, right, NumericCommon.NUMERIC_ACCURACY_MIDDLE);
    }

    public static DoubleVector perCordDescend(FunctionTUnary<DoubleVector> function, DoubleVector xStart, double eps, int maxIterations)  {
        DoubleVector x_0 = new DoubleVector(xStart);
        DoubleVector x_1 = new DoubleVector(xStart);
        double step = 1.0;
        double x_i, y_1, y_0;
        int optCoordinatesCount = 0, coordinateId;
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            coordinateId = iteration % x_0.size();
            x_1.set(coordinateId, x_1.get(coordinateId) - eps);
            y_0 = function.apply(x_1);
            x_1.set(coordinateId, x_1.get(coordinateId) + 2 * eps);
            y_1 = function.apply(x_1);
            x_1.set(coordinateId, x_1.get(coordinateId) - eps);
            x_1.set(coordinateId, y_0 > y_1 ? x_1.get(coordinateId) + step : x_1.get(coordinateId) - step);
            x_i = x_0.get(coordinateId);
            x_1 = dichotomyCore(function, x_0, x_1, eps, maxIterations);
            x_0 = new DoubleVector(x_1);
            if (Math.abs(x_1.get(coordinateId) - x_i) < eps) {
                optCoordinatesCount++;
                if (optCoordinatesCount == x_1.size()) {
                    if (NumericCommon.SHOW_DEBUG_LOG)
                        System.out.printf("per cord descend iterations number : %s\n", iteration + 1);
                    return x_0;
                }
                continue;
            }
            optCoordinatesCount = 0;
        }
        if (NumericCommon.SHOW_DEBUG_LOG) System.out.printf("per cord descend iterations number : %s\n", maxIterations);
        return x_0;
    }

    public static DoubleVector perCordDescend(FunctionTUnary<DoubleVector> function, DoubleVector xStart, double eps)  {
        return perCordDescend(function, xStart, eps, NumericCommon.ITERATIONS_COUNT_HIGH);
    }

    public static DoubleVector perCordDescend(FunctionTUnary<DoubleVector> function, DoubleVector xStart)  {
        return perCordDescend(function, xStart, NumericCommon.NUMERIC_ACCURACY_MIDDLE, NumericCommon.ITERATIONS_COUNT_HIGH);
    }
}
