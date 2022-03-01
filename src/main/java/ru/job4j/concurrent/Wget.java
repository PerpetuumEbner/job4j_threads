package ru.job4j.concurrent;

/**
 * В классе инкриминируется переменная в потоке, каждый раз приостанавливается.
 *
 * @author yustas
 * @version 1.0
 */

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        System.out.println("Loaded.");
                        for (int index = 0; index <= 100; index++) {
                            Thread.sleep(1000);
                            System.out.print("\rLoading : " + index + "%");
                        }
                        System.out.println("\nComplete.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
    }
}
