package ru.aston.finalproject.appender;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class FileAppender<T> {

    private final String filePath;

    public FileAppender(String filePath) {
        this.filePath = filePath;
    }

    public void appendData(List<T> data, DataToStringConverter<T> converter) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            for (T item : data) {
                writer.write(converter.convert(item) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи данных в файл: " + e.getMessage());
        }
    }

    public void appendValue(T value, DataToStringConverter<T> converter) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(converter.convert(value) + "\n");
        } catch (IOException e) {
            System.err.println("Ошибка при записи значения в файл: " + e.getMessage());
        }
    }

    @FunctionalInterface
    public interface DataToStringConverter<T> {
        String convert(T item);
    }
}
