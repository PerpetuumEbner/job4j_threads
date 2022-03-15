package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

/**
 * Класс считывает данные из источника.
 *
 * @author ystas
 * @version 1.0
 */
public final class ParseFile implements Content {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    /**
     * @return Возвращает строку с записанным текстом.
     */
    public synchronized String getContent() throws IOException {
        return content(data -> data < 0x80);
    }

    /**
     * @return Возвращает строку с записанным текстом.
     */
    public synchronized String getContentWithoutUnicode() throws IOException {
        return content(data -> true);
    }

    /**
     * Метод считывает данных из файла с помощью буферизации.
     *
     * @param filter Проверяет соблюдение некоторого условия.
     * @return Возвращает строку с записанным текстом.
     */
    @Override
    public synchronized String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        int data;
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            while ((data = bis.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}