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
            case LOW -> eps = DefaultNum.MAX_ITERATIONS_LOW;
            case MIDDLE -> eps = DefaultNum.MAX_ITERATIONS_MIDDLE;
            case HIGH -> eps = DefaultNum.MAX_ITERATIONS_HIGH;
        }

        return MathToolkit.fibonacci(function, x1, x2, eps);
    }

    public static int[] closestFibonacciPairWithIndex(double value) {
        int prev = 0;
        int curr = 1;
        int index = 1;

        while (curr < value) {
            int next = prev + curr;
            prev = curr;
            curr = next;
            index++;
        }

        return new int[]{prev, curr, index - 2};
    }
}
