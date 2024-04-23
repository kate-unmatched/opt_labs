package org.optima.labs;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.analysis.MultivariateVectorFunction;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.SimpleValueChecker;
import org.apache.commons.math3.optim.nonlinear.scalar.gradient.NonLinearConjugateGradientOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.PowellOptimizer;
import org.optima.kit.FunctionTUnary;
import org.optima.utils.GenericOneVectorFunction;
import org.optima.utils.NumCharacteristics;

import static org.optima.utils.DetailedMethods.calculateGradient;
import static org.optima.utils.DetailedMethods.goldenSectionSearch;

public class Lab3 {

    public static RealVector gradientDescent(FunctionTUnary<RealVector> function, RealVector xStart, double eps, int maxIterations) {
        RealVector x = xStart.copy();

        for (int i = 0; i < maxIterations; i++) {

            RealVector gradient = calculateGradient(function, x, eps);

            x = x.subtract(gradient.mapMultiply(eps));

            if (gradient.getNorm() < eps) {
                break;
            }
        }

        return x;
    }

    public static RealVector conjGradientDescend(FunctionTUnary<RealVector> function, RealVector xStart, double eps, int maxIterations) {
        RealVector xPrev = xStart;
        RealVector gradientPrev = calculateGradient(function, xStart, eps);
        RealVector direction = gradientPrev.mapMultiply(-1);

        for (int i = 0; i < maxIterations; i++) {
            double alpha = goldenSectionSearch(function, xPrev, direction, eps);

            RealVector xNext = xPrev.add(direction.mapMultiply(alpha));

            if (xNext.subtract(xPrev).getNorm() < eps) {
                break;
            }

            RealVector gradientNext = calculateGradient(function, xNext, eps);

            double beta = gradientNext.dotProduct(gradientNext) / gradientPrev.dotProduct(gradientPrev);

            direction = gradientNext.mapMultiply(-1).add(direction.mapMultiply(beta));

            xPrev = xNext;
            gradientPrev = gradientNext;
        }

        return xPrev;
    }







}
