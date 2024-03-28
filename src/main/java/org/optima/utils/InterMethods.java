package org.optima.utils;

public class InterMethods {

    public static <U, R> R customParam(U function, R x1, R x2, double eps,
                                     NumCharacteristics levelIter, GenericIterFunction<U,R> algo){

        int maxIterations = 0;
        switch (levelIter){
            case LOW -> maxIterations = DefaultNum.MAX_ITERATIONS_LOW;
            case MIDDLE -> maxIterations = DefaultNum.MAX_ITERATIONS_MIDDLE;
            case HIGH -> maxIterations = DefaultNum.MAX_ITERATIONS_HIGH;
        }

        return algo.apply(function, x1, x2, eps, maxIterations);
    }

    public static <U, R> R customParam(U function, R x1, R x2,
                                     NumCharacteristics levelEps, NumCharacteristics levelIter, GenericIterFunction<U,R> algo){
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

    public static <U, R> R customParam(U function, R x1, R x2, NumCharacteristics levelEps, GenericEpsFunction<U,R> algo){

        double eps = 0;
        switch (levelEps){
            case LOW -> eps = DefaultNum.EPSILON_LOW;
            case MIDDLE -> eps = DefaultNum.EPSILON_MIDDLE;
            case HIGH -> eps = DefaultNum.EPSILON_HIGH;
        }

        return algo.apply(function, x1, x2, eps);
    }

    public static <U, R> R customParam2(U function, R xStart, double eps,
                                       NumCharacteristics levelIter, GenericOneVectorFunction<U,R> algo){

        int maxIterations = 0;
        switch (levelIter){
            case LOW -> maxIterations = DefaultNum.MAX_ITERATIONS_LOW;
            case MIDDLE -> maxIterations = DefaultNum.MAX_ITERATIONS_MIDDLE;
            case HIGH -> maxIterations = DefaultNum.MAX_ITERATIONS_HIGH;
        }

        return algo.apply(function, xStart, eps, maxIterations);
    }

    public static <U, R> R customParam2(U function, R xStart, NumCharacteristics levelEps,
                                       NumCharacteristics levelIter, GenericOneVectorFunction<U,R> algo){
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

        return algo.apply(function, xStart, eps, maxIterations);
    }



}
