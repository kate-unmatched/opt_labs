import org.junit.Assert;
import org.junit.Test;
import org.optima.labs.LabOne;

import static java.lang.Math.*;
import static org.optima.utils.NumCharacteristics.LOW;
import static org.optima.utils.NumCharacteristics.MIDDLE;
import static org.optima.utils.NumCharacteristics.HIGH;

public class TestLab1 {

    @Test
    public void checkEfficiencyDichotomy(){
        Assert.assertEquals(LabOne.dichotomy(x -> x * x - 4, 1, 3, 0.0001, 100),
                1.00006103515625, 1E-7);
        Assert.assertEquals(LabOne.dichotomy(x -> pow(x,3) - pow(x,2) + x - 1, 2, -2, 0.0001, 1000),
                -1.99993896484375, 1E-7);
        Assert.assertEquals(LabOne.dichotomy(x -> sqrt(x) - 2 * cos(x), 0, 10, 0.0001, 100),
                6.1824798583984375, 1E-7);
    }

    @Test
    public void checkDefaultParametersDichotomy(){
        Assert.assertEquals(LabOne.dichotomy(x -> exp(x) - 2*x, 1, 0, 0.000001, HIGH),
                0.6931467056274414, 1E-7);
        Assert.assertEquals(LabOne.dichotomy(Math::sin, 3, 4, MIDDLE, MIDDLE),
                3.9999990463256836, 1E-7);
        Assert.assertEquals(LabOne.dichotomy(x -> log(x)-1, 4, 2, HIGH, LOW),
                2.0000000009313226, 1E-7);
    }

    @Test
    public void checkEfficiencyGoldenRatioMinimum(){
        Assert.assertEquals(LabOne.goldenRatioMinimum(x -> pow(x,3) - 2 * pow(x,2) - 5 * x + 6, 0, 4, 0.0001, 100),
                2.1196552871094, 1E-4);
        Assert.assertEquals(LabOne.goldenRatioMinimum(x -> sin(x) + x, 0, 3, 0.000001, MIDDLE),
                4.9891915465022E-7, 1E-4);
        Assert.assertEquals(LabOne.goldenRatioMinimum(x -> 1 / x - x, 0.1, 1.5, HIGH, LOW),
                1.5000000000000, 1E-4);
    }

    @Test
    public void checkEfficiencyGoldenRatioMaximum(){
        Assert.assertEquals(LabOne.goldenRatioMaximum(x -> pow(x,3) - 2 * pow(x,2) - 5 * x + 6,
                        0, 4, 0.0001, 100), 3.1240319208736E-5, 1.E-6);
        Assert.assertEquals(LabOne.goldenRatioMaximum(x -> sin(x) + x, 0, 3, 0.000001, MIDDLE),
                2.9999995010808, 1E-6);
        Assert.assertEquals(LabOne.goldenRatioMaximum(x -> 1 / x - x, 0.1, 1.5, HIGH, LOW),
                0.1000000000000, 1E-6);
    }

    @Test
    public void checkEfficiencyFibonacci(){
        Assert.assertEquals(LabOne.fibonacci(x -> x * x - 4 * x + 4, 1, 3, 0.001),
                2.001195886151638, 1E-7);
        Assert.assertEquals(LabOne.fibonacci(x -> 1 / x - x, 1, 6, LOW),
                5.998172848529143, 1E-7);
        Assert.assertEquals(LabOne.fibonacci(x -> x * cos(x), 1, 10, MIDDLE),
                3.425618531967631, 1E-7);

    }


}
