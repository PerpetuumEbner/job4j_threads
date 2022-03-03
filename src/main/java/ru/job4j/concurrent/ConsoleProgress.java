package ru.job4j.concurrent;

/**
 * В классе эмулируется загрузка с помощью символов в потоке.
 *
 * @author yustas
 * @version 1.0
 */

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        progress.interrupt();
    }

    @Override
    public void run() {
        String[] symbols = {"\\", "|", "/"};

        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (String symbol : symbols) {
                    System.out.print("\r load: " + symbol);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("\n Поток прерван.");
            }
        }
    }
}