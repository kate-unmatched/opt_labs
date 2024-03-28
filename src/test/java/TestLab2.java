import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Assert;
import org.junit.Test;
import org.optima.collections.DoubleVector;
import org.optima.exceptions.MismatchLengthVectorsException;
import org.optima.kit.FunctionTUnary;
import org.optima.kit.NumericUtils;
import org.optima.labs.LabTwo;
import org.optima.utils.NumCharacteristics;

import java.text.DecimalFormat;
import java.util.stream.IntStream;

import static org.optima.utils.NumCharacteristics.MIDDLE;
import static org.optima.utils.NumCharacteristics.HIGH;
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
                () -> LabTwo.dichotomyMy(function, left, right, 1E-2, 100));
    }
    @Test
    public void checkEfficiencyDichotomyCore(){
        FunctionTUnary<DoubleVector> function = v -> {
            double sum = 0.0;
            for (int i = 0; i < v.size(); i++) {
                sum += Math.pow(2 - v.get(i), 2);
            }
            return sum;
        };

        DoubleVector x_0 = new DoubleVector(-10., -10.);
        DoubleVector x_1 = new DoubleVector(10., 10.);
        DoubleVector core = LabTwo.dichotomyCore(function, x_1, x_0, 1E-2, 100);

        RealVector correct = new ArrayRealVector(new double[]{2, 2});

        IntStream.range(0, x_0.size()).forEach(i ->
                Assert.assertEquals(core.get(i), correct.getEntry(i), 1E-3));
    }
    @Test
    public void checkEfficiencyDichotomyMy(){
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
        RealVector misa = LabTwo.dichotomyMy(function, left, right, 1E-5, 100);
        long endTime = System.currentTimeMillis();
        System.out.println(new DecimalFormat("#0.0000").format((endTime - startTime)/1000.));

        RealVector kira = LabTwo.dichotomyMy(function, left, right, 1E-5, MIDDLE);
        RealVector ryuk = LabTwo.dichotomyMy(function, left, right, MIDDLE, LOW);

        IntStream.range(0, correct1.getDimension()).forEach(i ->
                Assert.assertEquals(misa.getEntry(i), correct1.getEntry(i), 1E-7));
        IntStream.range(0, correct2.getDimension()).forEach(i ->
                Assert.assertEquals(kira.getEntry(i), correct2.getEntry(i), 1E-7));
        IntStream.range(0, correct3.getDimension()).forEach(i ->
                Assert.assertEquals(ryuk.getEntry(i), correct3.getEntry(i), 1E-7));
    }

    @Test
    public void checkEfficiencyGoldenRatioMy(){
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
        RealVector result1 = LabTwo.goldenRatio(function, left, right, 1E-5, 100);
        long endTime = System.currentTimeMillis();
        System.out.println(new DecimalFormat("#0.0000").format((endTime - startTime)/1000.));

        RealVector result2 = LabTwo.goldenRatio(function, left, right, 1E-5, MIDDLE);
        RealVector result3 = LabTwo.goldenRatio(function, left, right, MIDDLE, LOW);

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
    public void checkEfficiencyFibonacci(){
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
        RealVector result1 = LabTwo.fibonacci(function, left, right, 1E-5);
        long endTime = System.currentTimeMillis();
        System.out.println(new DecimalFormat("#0.000").format((endTime - startTime)/1000.));

        RealVector result2 = LabTwo.fibonacci(function, left, right, MIDDLE);

        RealVector correct1 = new ArrayRealVector(new double[]{0.0000001735, 0.0000001735});
        RealVector correct2 = new ArrayRealVector(new double[]{0.0000023842, 0.0000023842});

        IntStream.range(0, correct1.getDimension()).forEach(i ->
                Assert.assertEquals(result1.getEntry(i), correct1.getEntry(i), 1E-5));
        IntStream.range(0, correct2.getDimension()).forEach(i ->
                Assert.assertEquals(result2.getEntry(i), correct2.getEntry(i), 1E-5));
    }
}
