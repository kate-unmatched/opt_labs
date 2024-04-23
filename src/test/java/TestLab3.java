import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;
import org.optima.kit.FunctionTUnary;

import static org.optima.labs.Lab3.gradientDescent;

public class TestLab3 {
    @Test
    public void checkGradientDescent(){
        // Пример использования
        FunctionTUnary<RealVector> function = new FunctionTUnary<RealVector>() {
            @Override
            public double apply(RealVector x) {
                // Функция, которую мы оптимизируем (можно заменить на свою)
                return Math.pow(x.getEntry(0) - 3, 2) + Math.pow(x.getEntry(1) - 4, 2);
            }
        };

        // Начальное приближение
        RealVector xStart = new ArrayRealVector(new double[]{0, 0});

        // Точность и максимальное число итераций
        double eps = 1e-6;
        int maxIterations = 1000;

        // Выполнение градиентного спуска
        RealVector result = gradientDescent(function, xStart, eps, maxIterations);

        // Вывод результата
        System.out.println("Результат градиентного спуска:");
        for (int i = 0; i < result.getDimension(); i++) {
            System.out.println("x" + i + " = " + result.getEntry(i));
        }
        System.out.println("Значение функции = " + function.apply(result));
    }
}
