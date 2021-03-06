package ru.job4j.pool;

/**
 * Модель класса.
 */
public class Sums {
    private int rowSum;
    private int colSum;

    public int getRowSum() {
        return rowSum;
    }

    public void setRowSum(int rowSum) {
        this.rowSum = rowSum;
    }

    public int getColSum() {
        return colSum;
    }

    public void setColSum(int colSum) {
        this.colSum = colSum;
    }

    @Override
    public String toString() {
        return "Sums{" +
                "rowSum=" + rowSum +
                ", colSum=" + colSum +
                '}';
    }
}