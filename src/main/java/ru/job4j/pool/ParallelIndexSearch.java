package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int left;
    private final int right;
    private final T value;
    private static final int THRESHOLD = 10;

    public ParallelIndexSearch(T[] array, int left, int right, T value) {
        this.array = array;
        this.left = left;
        this.right = right;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        if (array.length <= THRESHOLD) {
            for (int index = 0; index < array.length; index++) {
                if (array[index] == value) {
                    return index;
                }
            }
        }
        int mid = (left + right) / 2;
        var leftSearch = new ParallelIndexSearch<>(array, left, mid, value);
        var rightSearch = new ParallelIndexSearch<>(array, mid + 1, right, value);
        leftSearch.fork();
        rightSearch.fork();
        return null;
    }

    public Integer search(T[] array) {
        return new ForkJoinPool().invoke(new ParallelIndexSearch<>(array, 0, array.length - 1, value));
    }
}