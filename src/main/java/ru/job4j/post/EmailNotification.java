package ru.job4j.post;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * В классе происходит отправка письма пользователю.
 *
 * @author yustas
 * @version 1.0
 */
public class EmailNotification {
    ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Создаётся задача на отправку письма по шаблону.
     *
     * @param user Пользователю, которому необходимо отправить письмо.
     */
    public void emailTo(User user) {
        pool.submit(
                () -> send(" Notification " + user.getUsername() + " to email " + user.getEmail(),
                        "Add a new event to " + user.getUsername(),
                        user.getEmail()));
    }

    /**
     * Закрывается задача.
     */
    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }
}