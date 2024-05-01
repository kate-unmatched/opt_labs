import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Assert;
import org.junit.Test;
import org.optima.kit.FunctionTUnary;
import org.optima.labs.Lab3;

import java.util.stream.IntStream;


public class TestLab3 {
    @Test
    public void checkGradientDescent(){
        FunctionTUnary<RealVector> function = v -> {
            double sum = 0;
            for (int i = 0; i < v.getDimension(); i++) {
                sum += v.getEntry(i) * v.getEntry(i);
            }
            return sum;
        };

        RealVector xStart = new ArrayRealVector(new double[]{1.0, -3.0, -6.0, 15.0});

        RealVector minimum = Lab3.gradientDescent(function, xStart, 1e-3, 10000);
    }

    @Test
    public void checkCongGradientDescent(){
        FunctionTUnary<RealVector> function = v -> {
            double x1 = v.getEntry(0);
            double x2 = v.getEntry(1);
            return 100 * Math.pow(x2 - x1 * x1, 2) + Math.pow(1 - x1, 2);
        };

        RealVector xStart = new ArrayRealVector(new double[]{1.0, -1.0});
        RealVector correct = new ArrayRealVector(new double[]{-0.23, -0.38});

        RealVector minimum = Lab3.conjGradientDescend(function, xStart, 1e-6, 1000);

        IntStream.range(0, correct.getDimension()).forEach(i ->
                Assert.assertEquals(minimum.getEntry(i), correct.getEntry(i), 1E-2));
    }
    @Test
    public void checkNewtoneRaphson() {
        FunctionTUnary<RealVector> function = v -> {
            double sum = 0;
            for (int i = 0; i < v.getDimension(); i++) {
                sum += Math.exp(v.getEntry(i)) - Math.pow(v.getEntry(i), 2);
            }
            return sum;
        };

        RealVector xStart = new ArrayRealVector(new double[]{0.1, -0.1, 0.1});

        RealVector minimum = Lab3.newtoneRaphson(function, xStart, 1e-6, 1000);
    }

    @Test
    public void checkPenaltyFunctions(){
        FunctionTUnary<RealVector> rosenbrockFunction = v -> 100 * Math.pow(v.getEntry(1) - v.getEntry(0)
                * v.getEntry(0), 2) + Math.pow(1 - v.getEntry(0), 2);
        RealVector xStart = new ArrayRealVector(new double[]{1.0, -1.0});

        FunctionTUnary<RealVector> constraintFunction = v -> v.getEntry(0) * v.getEntry(0)
                + v.getEntry(1) * v.getEntry(1) - 1.0;

        RealVector minimum = Lab3.penaltyFunctions(rosenbrockFunction, constraintFunction,
                xStart, 1e-6, 1000);

    }

}
