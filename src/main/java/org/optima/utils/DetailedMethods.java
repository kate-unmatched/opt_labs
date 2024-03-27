package org.optima.utils;

public class DetailedMethods {

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
