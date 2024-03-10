package org.optima.utils;

import org.optima.lab1.GenericFunction;

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
}
