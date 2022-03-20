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
    private final int SIZE;

    public SimpleBlockingQueue(int SIZE) {
        this.SIZE = SIZE;
    }

    /**
     * В очередь вставляются значения пока она не будет заполнена.
     *
     * @param value вставка значения в очередь.
     */
    public void offer(T value) throws InterruptedException {
        synchronized (queue) {
            while (queue.size() == SIZE) {
                queue.wait();
            }
            queue.add(value);
            queue.notifyAll();
        }
    }

    /**
     * Из очереди берутся значения пока она не окажется пустой.
     */
    public T poll() throws InterruptedException {
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
            }
            queue.notifyAll();
        }
        return queue.remove();
    }

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(10);
        Thread fist = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        sbq.offer(5);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        sbq.poll();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
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