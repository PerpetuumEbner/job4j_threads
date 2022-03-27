package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


/**
 * В классе происходит поиск индекса значения в массиве по принципу work-stealing с помошью ForkJoinPool.
 *
 * @param <T>
 * @author yustas
 * @version 1.0
 */
public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private int left;
    private int right;
    private final T value;
    private static final int THRESHOLD = 10;

    public ParallelIndexSearch(T[] array, int left, int right, T value) {
        this.array = array;
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public ParallelIndexSearch(T[] array, T value) {
        this.array = array;
        this.value = value;
    }

    /**
     * Линейный поиск индекса.
     *
     * @return Индекс значения в массиве.
     */
    private Integer linearSearch() {
        for (int index = left; index <= right; index++) {
            if (array[index].equals(value)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Метод выбирает способ поиска массива. Если длинна массива меньше чем значение THRESHOLD,
     * то поиск происходит линейно. Если нет, то массив рекурсивно делится на две части,
     * на основе которых создаётся своя задача и отправляется на выполнение путём вызова метода fork().
     * Когда деление завершено, метод  join() запускает каждую задачу на выполнение
     * и возвращает максимальное число из двух найденных при поиске.
     *
     * @return Максимальное число.
     */
    @Override
    protected Integer compute() {
        if (array.length <= THRESHOLD) {
            return linearSearch();
        }
        int mid = (left + right) / 2;
        var leftSearch = new ParallelIndexSearch<>(array, left, mid, value);
        var rightSearch = new ParallelIndexSearch<>(array, mid + 1, right, value);
        leftSearch.fork();
        rightSearch.fork();
        return Math.max(leftSearch.join(), rightSearch.join());
    }

    /**
     * Выполняется задача по поиску индекса в массиве.
     *
     * @param array Массив в котором происходит поиск индекса элемента.
     * @return Индекс элемента.
     */
    public Integer search(T[] array) {
        return new ForkJoinPool().invoke(new ParallelIndexSearch<>(array, 0, array.length - 1, value));
    }
}