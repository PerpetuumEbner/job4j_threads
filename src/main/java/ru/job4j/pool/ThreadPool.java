package ru.job4j.pool;

import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Создаются пул потоков по количеству ядер в системе. В каждый поток добавляется блокирующая очередь.
 * Если в очереди закончились элементы, то поток приостанавливается.
 * Когда приходит новая задача, потоки продолжают работу.
 *
 * @author yustas
 * @version 1.0
 */
public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int index = 0; index < size; index++) {
            Thread thread = new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.poll();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            );
            threads.add(thread);
            thread.start();
        }
    }

    /**
     * Добавляет задачи в блокирующую очередь.
     *
     * @param job Задачи.
     * @throws InterruptedException
     */
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /**
     * Завершение всех запущенных задач.
     */
    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}