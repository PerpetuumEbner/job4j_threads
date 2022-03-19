package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Реализация bounded blocking queue.
 *
 * @param <T>
 * @author yustas
 * @version 1.0
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int SIZE = 10;

    /**
     * В очередь вставляются значения пока она не будет заполнена.
     *
     * @param value вставка значения в очередь.
     */
    public void offer(T value) {
        synchronized (queue) {
            while (queue.size() == SIZE) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            queue.add(value);
            queue.notifyAll();
        }

    }

    /**
     * Из очереди берутся значения пока она не окажется пустой.
     */
    public void poll() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                queue.remove();
                queue.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>();
        Thread fist = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                    sbq.offer(5);
                }
        );
        Thread second = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                    sbq.poll();
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