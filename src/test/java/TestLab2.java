import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Assert;
import org.junit.Test;
import org.optima.exceptions.MismatchLengthVectorsException;
import org.optima.kit.FunctionTUnary;
import org.optima.labs.LabTwo;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.stream.IntStream;

import static org.optima.utils.NumCharacteristics.MIDDLE;
import static org.optima.utils.NumCharacteristics.LOW;

public class TestLab2 {

    @Test
    public void checkExceptionDichotomy(){
        FunctionTUnary<RealVector> function = v -> {
            double sum = 0.0;
            for (int i = 0; i < v.getDimension(); i++) {
                sum += Math.pow(v.getEntry(i), 2);
            }
            return sum;
        };

        RealVector left = new ArrayRealVector(new double[]{-10, -10});
        RealVector right = new ArrayRealVector(new double[]{10, 10, 3});

        Assert.assertThrows(MismatchLengthVectorsException.class,
                () -> LabTwo.dichotomyVector(function, left, right, 1E-2, 100));
    }
    @Test
    public void checkEfficiencyDichotomyVector(){
        FunctionTUnary<RealVector> function = v -> {
            double sum = 0.0;
            for (int i = 0; i < v.getDimension(); i++) {
                sum += Math.pow(v.getEntry(i), 2);
            }
            return sum;
        };

        RealVector left = new ArrayRealVector(new double[]{-10, -10});
        RealVector right = new ArrayRealVector(new double[]{10, 10});

        RealVector correct1 = new ArrayRealVector(new double[]{0.0000023842, 0.0000023842});
        RealVector correct2 = new ArrayRealVector(new double[]{0.0000023842, 0.0000023842});
        RealVector correct3 = new ArrayRealVector(new double[]{0.000000298, 0.000000298});

        long startTime = System.currentTimeMillis();
        RealVector misa = LabTwo.dichotomyVector(function, left, right, 1E-5, 100);
        long endTime = System.currentTimeMillis();
        System.out.println("Working time of the Dichotomy > " + new DecimalFormat("#0.0000")
                .format((endTime - startTime)/1000.));

        RealVector kira = LabTwo.dichotomyVector(function, left, right, 1E-5, MIDDLE);
        RealVector ryuk = LabTwo.dichotomyVector(function, left, right, MIDDLE, LOW);

        IntStream.range(0, correct1.getDimension()).forEach(i ->
                Assert.assertEquals(misa.getEntry(i), correct1.getEntry(i), 1E-7));
        IntStream.range(0, correct2.getDimension()).forEach(i ->
                Assert.assertEquals(kira.getEntry(i), correct2.getEntry(i), 1E-7));
        IntStream.range(0, correct3.getDimension()).forEach(i ->
                Assert.assertEquals(ryuk.getEntry(i), correct3.getEntry(i), 1E-7));
    }

    @Test
    public void checkEfficiencyGoldenRatioVector(){
        FunctionTUnary<RealVector> function = v -> {
            double sum = 0.0;
            for (int i = 0; i < v.getDimension(); i++) {
                sum += Math.pow(v.getEntry(i), 2);
            }
            return sum;
        };

        RealVector left = new ArrayRealVector(new double[]{-10, -10});
        RealVector right = new ArrayRealVector(new double[]{10, 10});

        long startTime = System.currentTimeMillis();
        RealVector result1 = LabTwo.goldenRatioVector(function, left, right, 1E-5, 100);
        long endTime = System.currentTimeMillis();
        System.out.println("Working time of the GoldenRatio > " + new DecimalFormat("#0.0000")
                .format((endTime - startTime)/1000.));

        RealVector result2 = LabTwo.goldenRatioVector(function, left, right, 1E-5, MIDDLE);
        RealVector result3 = LabTwo.goldenRatioVector(function, left, right, MIDDLE, LOW);

        RealVector correct1 = new ArrayRealVector(new double[]{0.0000002053, 0.0000002053});
        RealVector correct2 = new ArrayRealVector(new double[]{0.0000023842, 0.0000023842});
        RealVector correct3 = new ArrayRealVector(new double[]{0.000000298, 0.000000298});

        IntStream.range(0, correct1.getDimension()).forEach(i ->
                Assert.assertEquals(result1.getEntry(i), correct1.getEntry(i), 1E-5));
        IntStream.range(0, correct2.getDimension()).forEach(i ->
                Assert.assertEquals(result2.getEntry(i), correct2.getEntry(i), 1E-5));
        IntStream.range(0, correct3.getDimension()).forEach(i ->
                Assert.assertEquals(result3.getEntry(i), correct3.getEntry(i), 1E-5));
    }
    @Test
    public void checkEfficiencyFibonacciVector(){
        FunctionTUnary<RealVector> function = v -> {
            double sum = 0.0;
            for (int i = 0; i < v.getDimension(); i++) {
                sum += Math.pow(v.getEntry(i), 2);
            }
            return sum;
        };

        RealVector left = new ArrayRealVector(new double[]{-10, -10});
        RealVector right = new ArrayRealVector(new double[]{10, 10});

        long startTime = System.currentTimeMillis();
        RealVector result1 = LabTwo.fibonacciVector(function, left, right, 1E-5);
        long endTime = System.currentTimeMillis();
        System.out.println("Working time of the Fibonacci > " + new DecimalFormat("#0.000")
                .format((endTime - startTime)/1000.));

        RealVector result2 = LabTwo.fibonacciVector(function, left, right, MIDDLE);

        RealVector correct1 = new ArrayRealVector(new double[]{0.0000001735, 0.0000001735});
        RealVector correct2 = new ArrayRealVector(new double[]{0.0000023842, 0.0000023842});

        IntStream.range(0, correct1.getDimension()).forEach(i ->
                Assert.assertEquals(result1.getEntry(i), correct1.getEntry(i), 1E-5));
        IntStream.range(0, correct2.getDimension()).forEach(i ->
                Assert.assertEquals(result2.getEntry(i), correct2.getEntry(i), 1E-5));
    }

    @Test
    public void checkEfficiencyPerCordDescend(){
        FunctionTUnary<RealVector> function = v -> {
            double sum = 0.0;
            for (int i = 0; i < v.getDimension(); i++) {
                sum += Math.pow(i + 1 - v.getEntry(i), 2);
            }
            return sum;
        };

        RealVector xStart = new ArrayRealVector(new double[]{10, 10});

        long startTime = System.currentTimeMillis();
        RealVector result1 = LabTwo.perCordDescend(function, xStart,1E-5, 100);
        long endTime = System.currentTimeMillis();
        System.out.println("Working time of the PerCordDescend > " + new DecimalFormat("#0.0000")
                .format((endTime - startTime)/1000.));

        RealVector result2 = LabTwo.perCordDescend(function, xStart,1E-5, MIDDLE);
        RealVector result3 = LabTwo.perCordDescend(function, xStart,MIDDLE, LOW);

        RealVector correct1 = new ArrayRealVector(new double[]{1.0, 2.0});
        RealVector correct2 = new ArrayRealVector(new double[]{1.0, 2.0});
        RealVector correct3 = new ArrayRealVector(new double[]{1.0, 2.0});

        IntStream.range(0, correct1.getDimension()).forEach(i ->
                Assert.assertEquals(result1.getEntry(i), correct1.getEntry(i), 1E-5));
        IntStream.range(0, correct2.getDimension()).forEach(i ->
                Assert.assertEquals(result2.getEntry(i), correct2.getEntry(i), 1E-5));
        IntStream.range(0, correct3.getDimension()).forEach(i ->
                Assert.assertEquals(result3.getEntry(i), correct3.getEntry(i), 1E-5));
    }

    @Test
    public void checkPerCordDescend16(){
        FunctionTUnary<RealVector> function = v -> {
            double sum = 0.0;
            for (int i = 0; i < v.getDimension(); i++) {
                sum += Math.pow(v.getEntry(i), 2)-2;
            }
            return sum;
        };
        Random random = new Random();
        double[] values = new double[32];

        for (int i = 0; i < values.length; i++) {
            values[i] = -50 + (100 * random.nextDouble());
        }

        RealVector xStart = new ArrayRealVector(values);

        RealVector result = LabTwo.perCordDescend(function, xStart,1E-5, 100);
        System.out.println("dc");
    }
}
