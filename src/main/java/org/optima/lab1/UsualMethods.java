package org.optima.lab1;

import org.optima.lab1.GenericFunction;
import org.optima.lab1.MathToolkit;
import org.optima.utils.DefaultNum;
import org.optima.utils.NumCharacteristics;

import java.util.function.DoubleUnaryOperator;

public class UsualMethods {

    public static double customParam(DoubleUnaryOperator function, double x1, double x2, double eps,
                                     NumCharacteristics levelIter, GenericFunction algo){

        int maxIterations = 0;
        switch (levelIter){
            case LOW -> maxIterations = DefaultNum.MAX_ITERATIONS_LOW;
            case MIDDLE -> maxIterations = DefaultNum.MAX_ITERATIONS_MIDDLE;
            case HIGH -> maxIterations = DefaultNum.MAX_ITERATIONS_HIGH;
        }

        return algo.apply(function, x1, x2, eps, maxIterations);
    }

    public static double customParam(DoubleUnaryOperator function, double x1, double x2,
                                     NumCharacteristics levelEps, NumCharacteristics levelIter, GenericFunction algo){
        double eps = .0;
        switch (levelEps){
            case LOW -> eps = DefaultNum.EPSILON_LOW;
            case MIDDLE -> eps = DefaultNum.EPSILON_MIDDLE;
            case HIGH -> eps = DefaultNum.EPSILON_HIGH;
        }

        int maxIterations = 0;
        switch (levelIter){
            case LOW -> maxIterations = DefaultNum.MAX_ITERATIONS_LOW;
            case MIDDLE -> maxIterations = DefaultNum.MAX_ITERATIONS_MIDDLE;
            case HIGH -> maxIterations = DefaultNum.MAX_ITERATIONS_HIGH;
        }

        return algo.apply(function, x1, x2, eps, maxIterations);
    }

    public static double customParam(DoubleUnaryOperator function, double x1, double x2, NumCharacteristics levelEps){

        double eps = 0;
        switch (levelEps){
            case LOW -> eps = DefaultNum.EPSILON_LOW;
            case MIDDLE -> eps = DefaultNum.EPSILON_MIDDLE;
            case HIGH -> eps = DefaultNum.EPSILON_HIGH;
        }

        return MathToolkit.fibonacci(function, x1, x2, eps);
    }

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
}
