package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * В классе реализована последовательная и асинхронная версия программы,
 * в которой происходит подсчёт суммы элементов строк и столбцов двумерной матрицы.
 *
 * @author yustas
 * @version 3.0
 */
public class RowColSum {
    /**
     * Сумма всех элементов матрицы по строкам и столбцам.
     *
     * @param matrix Матрица в которой находятся элементы.
     * @return Сумма всех элементов.
     */
    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int index = 0; index < matrix.length; index++) {
            Sums sums = new Sums();
            sums.setRowSum(sumRow(matrix, index));
            sums.setColSum(sumCol(matrix, index));
            result[index] = sums;
        }
        return result;
    }

    /**
     * Сумма элементов матрицы по строкам.
     *
     * @param matrix Матрица в которой находятся элементы.
     * @param index  Индекс нужной строки.
     * @return Сумма элементов по определённой строкой.
     */
    public static int sumRow(int[][] matrix, int index) {
        int result = 0;
        for (int i = 0; i < matrix.length; i++) {
            result += matrix[index][i];
        }
        return result;
    }

    /**
     * Сумма элементов матрицы по столбцам.
     *
     * @param matrix Матрица в которой находятся элементы.
     * @param index  Индекс нужного столбца.
     * @return Сумма элементов по определённому столбцу.
     */
    public static int sumCol(int[][] matrix, int index) {
        int result = 0;
        for (int i = 0; i < matrix.length; i++) {
            result += matrix[i][index];
        }
        return result;
    }

    /**
     * Асинхронный подсчёт суммы элементов всех строк и столбцов в матрице.
     *
     * @param matrix Матрица в которой находятся элементы.
     * @return Сумма элементов всех строк и столбцов.
     */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        for (int index = 0; index < matrix.length; index++) {
            result[index] = getTask(matrix, index).get();
        }
        return result;
    }

    /**
     * Асинхронный подсчёт суммы элементов строк и столбцов по определённому индексу в матрице.
     *
     * @param matrix Матрица в которой находятся элементы.
     * @param index
     * @return Сумма элементов по определённой строки и столбцу.
     */
    public static CompletableFuture<Sums> getTask(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
            sums.setRowSum(sumRow(matrix, index));
            sums.setColSum(sumCol(matrix, index));
            return sums;
        });
    }

    /**
     * Модель класса.
     */
}