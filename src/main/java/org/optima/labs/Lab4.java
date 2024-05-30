package org.optima.labs;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Lab4 {

    public static double[] bOObsMethod(RealMatrix constraints, RealVector objective, boolean isMinimization) {
        int numConstraints = constraints.getRowDimension();
        int numVariables = constraints.getColumnDimension() - 1;

        // Преобразование задачи минимизации в задачу максимизации
        if (isMinimization) {
            objective = objective.mapMultiply(-1);
        }

        // Создание таблицы симплекс-метода
        RealMatrix tableau = new Array2DRowRealMatrix(numConstraints + 1, numVariables + 1);
        tableau.setSubMatrix(constraints.getData(), 0, 0);
        tableau.setRowVector(numConstraints, objective);

        // Инициализация массива базисных переменных
        String[] basisVariables = new String[numConstraints];
        for (int i = 0; i < numConstraints; i++) {
            basisVariables[i] = "x" + (numVariables - numConstraints + i + 1);
        }

        while (true) {
            // Поиск ведущего столбца
            int pivotCol = getPivotColumn(tableau);
            if (pivotCol == -1) break;

            // Поиск ведущей строки
            int pivotRow = getPivotRow(tableau, pivotCol);
            if (pivotRow == -1) break;

            // Обновление базисной переменной
            basisVariables[pivotRow] = "x" + (pivotCol + 1);

            // Поворотная операция
            pivotOperation(tableau, pivotRow, pivotCol);

            // Отладочная информация для отслеживания состояния таблицы
            printTableau(tableau, basisVariables);
        }

        // Оптимальное значение целевой функции
        double optimalValue = tableau.getEntry(numConstraints, numVariables);
        if (isMinimization) {
            optimalValue = -optimalValue; // Вернуть значение с правильным знаком для минимизации
        }
        System.out.println("Optimal Value: " + optimalValue);

        // Оптимальные значения переменных
        double[] solution = new double[numVariables];
        for (int i = 0; i < numVariables; i++) {
            boolean isBasic = true;
            double value = 0;
            for (int j = 0; j < numConstraints; j++) {
                if (tableau.getEntry(j, i) == 1 && value == 0) {
                    value = tableau.getEntry(j, numVariables);
                } else if (tableau.getEntry(j, i) != 0) {
                    isBasic = false;
                    break;
                }
            }
            solution[i] = isBasic ? value : 0;
        }
        return solution;
    }

    private static int getPivotColumn(RealMatrix tableau) {
        int lastRow = tableau.getRowDimension() - 1;
        int numCols = tableau.getColumnDimension() - 1;
        for (int j = 0; j < numCols; j++) {
            if (tableau.getEntry(lastRow, j) < 0) {
                return j;
            }
        }
        return -1;
    }

    private static int getPivotRow(RealMatrix tableau, int pivotCol) {
        int numRows = tableau.getRowDimension() - 1;
        int numCols = tableau.getColumnDimension() - 1;
        double minRatio = Double.POSITIVE_INFINITY;
        int pivotRow = -1;
        for (int i = 0; i < numRows; i++) {
            double entry = tableau.getEntry(i, pivotCol);
            if (entry > 0) {
                double ratio = tableau.getEntry(i, numCols) / entry;
                if (ratio < minRatio) {
                    minRatio = ratio;
                    pivotRow = i;
                }
            }
        }
        return pivotRow;
    }

    private static void pivotOperation(RealMatrix tableau, int pivotRow, int pivotCol) {
        int numRows = tableau.getRowDimension();
        int numCols = tableau.getColumnDimension();
        double pivotElement = tableau.getEntry(pivotRow, pivotCol);

        for (int j = 0; j < numCols; j++) {
            tableau.setEntry(pivotRow, j, tableau.getEntry(pivotRow, j) / pivotElement);
        }

        for (int i = 0; i < numRows; i++) {
            if (i != pivotRow) {
                double factor = tableau.getEntry(i, pivotCol);
                for (int j = 0; j < numCols; j++) {
                    tableau.setEntry(i, j, tableau.getEntry(i, j) - factor * tableau.getEntry(pivotRow, j));
                }
            }
        }
    }

    private static void printTableau(RealMatrix tableau, String[] basisVariables) {
        int numRows = tableau.getRowDimension();
        int numCols = tableau.getColumnDimension();
        System.out.print("    ");

        for (int j = 0; j < numCols; j++) {
            if (j == numCols - 1) {
                System.out.print("b");
                break;
            }
            System.out.printf("X%s         ", j);
        }
        System.out.println();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.printf("%10.4f ", tableau.getEntry(i, j));
            }
            if (i < basisVariables.length) {
                System.out.printf("| %s", basisVariables[i]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
