package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParallelIndexSearchTest {

    @Test
    public void whenValueNotFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int result = new ParallelIndexSearch<>(array, 11).search(array);
        assertThat(result, is(-1));
    }

    @Test
    public void whenValueIntegerIs5() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int result = new ParallelIndexSearch<>(array, 5).search(array);
        assertThat(result, is(4));
    }

    @Test
    public void whenValueIsString() {
        String[] array = {"Екатерина", "Софья", "Дарья", "Мария", "Елена"};
        int result = new ParallelIndexSearch<>(array, "Мария").search(array);
        assertThat(result, is(3));
    }
}