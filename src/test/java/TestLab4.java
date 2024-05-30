import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;

import static org.optima.labs.Lab4.bOObsMethod;

public class TestLab4 {

    @Test
    public void checkBOObsMethod1(){
        // Пример задачи: максимизация Z = 3x + 5y
        // при ограничениях:
        // 2x + 8y <= 13
        // 5x - 4y >= 1
        // x + y = 7
        // 4x + 2y <= 10
        // 3x - y >= 2
        // x - 2y = 4

        // Матрица ограничений
        RealMatrix provMatr = new Array2DRowRealMatrix(
                new double[][]{
                        {2, 8, 1, 0, 0, 0, 13},  // 2x + 8y + x3 = 13
                        {5, -4, 0, 1, 0, 0, -1}, // 5x - 4y + x4 = -1
                        {1, 1, 0, 0, 1, 0, 7},   // x + y + x5 = 7
                        {4, 2, 0, 0, 0, 1, 10}   // 4x + 2y + x6 = 10
                });

        // Вектор коэффициентов целевой функции (с учетом дополнительных переменных)
        RealVector provVecC = new ArrayRealVector(new double[]{-3, -5, 0, 0, 0, 0, 0});

        // Запуск симплекс-метода
        double[] solution = bOObsMethod(provMatr, provVecC);

        // Вывод результатов
        System.out.println("Оптимальное решение:");
        for (double v : solution) {
            System.out.printf("%.4f ", v);
        }
    }

    @Test
    public void checkBOObsMethod2() {
        // Пример задачи: максимизация Z = 2x + 3y + 4z
        // при ограничениях:
        // x + y + z <= 4
        // 2x + y + 3z <= 6
        // x + 2y + z <= 5

        // Матрица ограничений
        RealMatrix provMatr = new Array2DRowRealMatrix(
                new double[][]{
                        {1, 1, 1, 1, 0, 0, 4},  // x + y + z + x4 = 4
                        {2, 1, 3, 0, 1, 0, 6},  // 2x + y + 3z + x5 = 6
                        {1, 2, 1, 0, 0, 1, 5}   // x + 2y + z + x6 = 5
                });

        // Вектор коэффициентов целевой функции (с учетом дополнительных переменных)
        RealVector provVecC = new ArrayRealVector(new double[]{-2, -3, -4, 0, 0, 0, 0});

        // Запуск симплекс-метода
        double[] solution = bOObsMethod(provMatr, provVecC);

        // Вывод результатов
        System.out.println("Оптимальное решение (Пример 3):");
        for (double v : solution) {
            System.out.printf("%.4f ", v);
        }
        System.out.println();
    }
    @Test
    public void checkBOObsMethod3() {
        // Пример задачи: минимизация Z = 2x + 3y
        // при ограничениях:
        // x + 2y >= 8
        // 4x + y >= 12
        // x >= 0
        // y >= 0

        // Матрица ограничений
        RealMatrix provMatr = new Array2DRowRealMatrix(
                new double[][]{
                        {-1, -2, 1, 0, -8}, // -x - 2y + x3 = -8
                        {-4, -1, 0, 1, -12} // -4x - y + x4 = -12
                });

        // Вектор коэффициентов целевой функции (с учетом дополнительных переменных)
        RealVector provVecC = new ArrayRealVector(new double[]{-2, -3, 0, 0, 0});

        // Запуск симплекс-метода
        double[] solution = bOObsMethod(provMatr, provVecC);

        // Вывод результатов
        System.out.println("Оптимальное решение:");
        for (double v : solution) {
            System.out.printf("%.4f ", v);
        }
        System.out.println();
    }

}
