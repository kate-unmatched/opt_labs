import org.junit.Assert;
import org.junit.Test;
import org.optima.lab1.MathToolkit;
import org.optima.utils.NumCharacteristics;

import static java.lang.Math.*;
import static org.optima.utils.NumCharacteristics.LOW;
import static org.optima.utils.NumCharacteristics.MIDDLE;
import static org.optima.utils.NumCharacteristics.HIGH;

public class TestLab1 {

    @Test
    public void checkEfficiencyDichotomy(){
        Assert.assertEquals(MathToolkit.dichotomy(x -> x * x - 4, 1, 3, 0.0001, 100),
                1.00006103515625, 1.E-7);
        Assert.assertEquals(MathToolkit.dichotomy(x -> pow(x,3) - pow(x,2) + x - 1, 2, -2, 0.0001, 1000),
                -1.99993896484375, 1.E-7);
        Assert.assertEquals(MathToolkit.dichotomy(x -> sqrt(x) - 2 * cos(x), 0, 10, 0.0001, 100),
                6.1824798583984375, 1.E-7);
    }

    @Test
    public void checkDefaultParametersDichotomy(){
        Assert.assertEquals(MathToolkit.dichotomy(x -> exp(x) - 2*x, 1, 0, 0.000001, HIGH),
                0.6931467056274414, 1.E-7);
        Assert.assertEquals(MathToolkit.dichotomy(Math::sin, 3, 4, MIDDLE, MIDDLE),
                3.9999990463256836, 1.E-7);
        Assert.assertEquals(MathToolkit.dichotomy(x -> log(x)-1, 4, 2, HIGH, LOW),
                2.0000000009313226, 1.E-7);
    }


}
