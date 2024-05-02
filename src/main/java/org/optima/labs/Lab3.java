package org.optima.labs;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.optima.kit.FunctionTUnary;
import org.optima.utils.InterMethods;
import org.optima.utils.NumCharacteristics;

import static org.optima.utils.DetailedMethods.*;

public class Lab3 {

    public static RealVector gradientDescent(FunctionTUnary<RealVector> function, RealVector xStart, double eps, int maxIterations) {
        RealVector tempX = xStart.copy();
        RealVector tempNew = xStart.copy();

        for (int i = 0; i < maxIterations; i++) {

            RealVector gradient = calculateGradient(function, tempX, eps);
            // двигаемся в направлении, противоположном градиенту
            tempNew = tempX.subtract(gradient.mapMultiply(eps));

            if (tempNew.getDistance(tempX) < 2 * eps) {
                break;
            }
            tempX = tempNew;
        }
        System.out.println("Minimum found at: " + tempNew);
        System.out.println("Function value at minimum: " + function.apply(tempNew));
        return tempNew;
    }

    public static RealVector gradientDescent(FunctionTUnary<RealVector> function, RealVector xStart,
                                            double eps, NumCharacteristics levelIter) {
        return InterMethods.customParam2(function, xStart, eps, levelIter, Lab3::gradientDescent);
    }

    public static RealVector gradientDescent(FunctionTUnary<RealVector> function, RealVector xStart,
                                            NumCharacteristics levelEps, NumCharacteristics levelIter) {
        return InterMethods.customParam2(function, xStart, levelEps, levelIter, Lab3::gradientDescent);
    }

    public static RealVector conjGradientDescend(FunctionTUnary<RealVector> function, RealVector xStart, double eps, int maxIterations) {
        RealVector xPrev = xStart.copy();
        RealVector xNext, directionNew;
        RealVector direction = calculateGradient(function, xStart, eps).mapMultiply(-1.);

        double omega;
        int iteration = 0;
        while (iteration < maxIterations) {
            xNext = Lab2.goldenRatioVector(function, xPrev, xPrev.add(direction), eps, maxIterations);
            if (xNext.getDistance(xPrev) < 2 * eps) break;
            directionNew = calculateGradient(function, xNext, eps);
            omega = Math.pow((directionNew).getNorm(), 2) / Math.pow((direction).getNorm(), 2);
            direction.mapMultiply(omega).subtract(directionNew);
            xPrev = xNext;
            iteration++;
        }

        System.out.println("Minimum found at: " + xPrev);
        System.out.println("Function value at minimum: " + function.apply(xPrev));
        return xPrev;
    }

    public static RealVector conjGradientDescend(FunctionTUnary<RealVector> function, RealVector xStart,
                                             double eps, NumCharacteristics levelIter) {
        return InterMethods.customParam2(function, xStart, eps, levelIter, Lab3::conjGradientDescend);
    }

    public static RealVector conjGradientDescend(FunctionTUnary<RealVector> function, RealVector xStart,
                                             NumCharacteristics levelEps, NumCharacteristics levelIter) {
        return InterMethods.customParam2(function, xStart, levelEps, levelIter, Lab3::conjGradientDescend);
    }

    public static RealVector newtoneRaphson(FunctionTUnary<RealVector> function, RealVector xStart, double eps, int maxIterations) {
        RealVector x = xStart.copy();
        RealMatrix hessian;
        RealVector gradient;
        int iter = 0;

        while (iter < maxIterations) {
            gradient = calculateGradient(function, x, eps);
            hessian = calculateHessian(function, x, eps);
            RealVector direction = new LUDecomposition(hessian).getSolver().solve(gradient.mapMultiply(-1));

            if (direction.getDistance(x) < 2 * eps) {
                break;
            }

            x = x.add(direction);
            iter++;
        }
        System.out.println("Minimum found at: " + x);
        System.out.println("Function value at minimum: " + function.apply(x));
        return x;
    }

    public static RealVector newtoneRaphson(FunctionTUnary<RealVector> function, RealVector xStart,
                                                 double eps, NumCharacteristics levelIter) {
        return InterMethods.customParam2(function, xStart, eps, levelIter, Lab3::newtoneRaphson);
    }

    public static RealVector newtoneRaphson(FunctionTUnary<RealVector> function, RealVector xStart,
                                                 NumCharacteristics levelEps, NumCharacteristics levelIter) {
        return InterMethods.customParam2(function, xStart, levelEps, levelIter, Lab3::newtoneRaphson);
    }

    public static RealVector penaltyFunctions(FunctionTUnary<RealVector> function, FunctionTUnary<RealVector> constraintFunction, RealVector xStart, double eps, int maxIterations) {
        RealVector x = xStart.copy();
        int iter = 0;

        double penalty = 1e6;

        while (iter < maxIterations) {

            RealVector gradient = calculateGradient(function, x, eps);
            RealMatrix hessian = calculateHessian(function, x, eps);

            RealVector constraintGradient = calculateGradient(constraintFunction, x, eps);

            RealVector penaltyGradient = gradient.add(constraintGradient.mapMultiply(penalty));

            RealVector direction = new LUDecomposition(hessian).getSolver().solve(penaltyGradient.mapMultiply(-1));

            if (direction.getDistance(x) < 2 * eps) {
                break;
            }

            x = x.add(direction);
            iter++;
        }

        System.out.println("Minimum found at: " + x);
        System.out.println("Function value at minimum: " + function.apply(x));
        return x;
    }

    public static RealVector penaltyFunctions(FunctionTUnary<RealVector> function1, FunctionTUnary<RealVector> function2,
                                              RealVector xStart, double eps, NumCharacteristics levelIter) {
        return InterMethods.customParam3(function1, function2, xStart, eps, levelIter, Lab3::penaltyFunctions);
    }
    public static RealVector penaltyFunctions(FunctionTUnary<RealVector> function1, FunctionTUnary<RealVector> function2,
                                              RealVector xStart, NumCharacteristics levelEps, NumCharacteristics levelIter) {
        return InterMethods.customParam3(function1, function2, xStart, levelEps, levelIter, Lab3::penaltyFunctions);
    }

}
