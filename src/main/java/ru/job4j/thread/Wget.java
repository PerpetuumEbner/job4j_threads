package ru.job4j.thread;

import java.io.*;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String path;

    public Wget(String url, int speed, String path) {
        this.url = url;
        this.speed = speed;
        this.path = path;
    }


    @Override
    public void run() {
        System.out.println("Speed: " + speed + " B/s.");
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                System.out.println("");
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long time = System.currentTimeMillis() - start;
                if (time < speed) {
                    Thread.sleep(speed - time);
                }
                start = System.currentTimeMillis();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("Complete");
    }

    public static void checkArgs(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new InterruptedException("Ошибка инициализации аргументов.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        checkArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String path = args[2];
        Thread wget = new Thread(new Wget(url, speed, path));
        wget.start();
        wget.join();
    }
}