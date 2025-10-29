package ru.aston.finalproject.appender;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

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

    public <U> void writeSearchResults(List<U> list, List<Integer> positions, DataToStringConverter<U> converter) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write("\n=== Результаты поиска ===\n");

            if (positions.isEmpty()) {
                writer.write("Совпадений не найдено\n");
            } else {
                List<Integer> userPositions = new ArrayList<>();
                for (int index : positions) {
                    userPositions.add(index + 1);
                }

                writer.write("Позиции в списке: " + userPositions + "\n");

                for (int index : positions) {
                    String result = "На позиции " + (index + 1) + ": " + converter.convert(list.get(index));
                    writer.write(result + "\n");
                }
            }
            writer.write("=======================\n");
        } catch (IOException e) {
            System.err.println("Ошибка при записи результатов поиска в файл: " + e.getMessage());
        }
    }

    @FunctionalInterface
    public interface DataToStringConverter<T> {
        String convert(T item);
    }
}