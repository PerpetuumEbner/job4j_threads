package ru.job4j.wait;

public class CountBarrier {
    private final Object monitor = this;
    private final int total;
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(5);
        Thread fist = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                    countBarrier.count();
                }
        );

        Thread second = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                    countBarrier.await();
                }
        );

        fist.start();
        second.start();

        try {
            fist.join();
            second.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}