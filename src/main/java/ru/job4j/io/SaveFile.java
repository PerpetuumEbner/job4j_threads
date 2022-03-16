package ru.job4j.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Класс считывает данные из источника.
 *
 * @author ystas
 * @version 1.0
 */
public final class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    /**
     * Метод записывает данные в файл.
     *
     * @param content Строка из которой считываются данные с помощью буфера.
     */
    public synchronized void saveContent(String content) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int index = 0; index < content.length(); index += 1) {
                bos.write(content.charAt(index));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}