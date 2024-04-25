package org.optima.labs;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.analysis.MultivariateVectorFunction;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.optima.kit.FunctionTUnary;

import static org.optima.utils.DetailedMethods.*;

public class Lab3 {

    public static RealVector gradientDescent(FunctionTUnary<RealVector> function, RealVector xStart, double eps, int maxIterations) {
        RealVector x = xStart.copy();

        for (int i = 0; i < maxIterations; i++) {

            RealVector gradient = calculateGradient(function, x, eps);
            // двигаемся в направлении, противоположном градиенту
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

    public static RealVector newtoneRaphson(FunctionTUnary<RealVector> function, RealVector xStart, double eps, int maxIterations) {
        RealVector x = xStart.copy();
        double stepSize = 1e-5;

        for (int iter = 0; iter < maxIterations; iter++) {
            RealVector gradient = computeGradient(function, x, stepSize);
            RealVector deltaX = gradient.mapMultiply(-1);
            x = x.add(deltaX);

            if (deltaX.getNorm() < eps) {
                break;
            }
        }

        return x;
    }







}
