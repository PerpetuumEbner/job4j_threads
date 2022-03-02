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
            e.printStackTrace();
        }
        progress.interrupt();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.print("\r load: " + " \\ ");
                Thread.sleep(500);
                System.out.print("\r load: " + " | ");
                Thread.sleep(500);
                System.out.print("\r load: " + " / ");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("\n Поток прерван.");
            }
        }
    }
}