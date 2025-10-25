package ru.aston.finalproject.loaders;

import ru.aston.finalproject.entities.Cat;
import ru.aston.finalproject.interfaces.DataLoader;
import ru.aston.finalproject.interfaces.Validator;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileCatDataLoader implements DataLoader<Cat> {
    private final Validator<Cat> validator;
    private final Scanner scanner;

    public FileCatDataLoader(Validator<Cat> validator, Scanner scanner) {
        this.validator = validator;
        this.scanner = scanner;
    }

    @Override
    public List<Cat> loadData() {
        System.out.print("Введите путь к файлу с данными котов: ");
        String filePath = scanner.nextLine().trim();

        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                System.out.println("Файл не существует: " + filePath);
                return List.of();
            }

            List<String> lines = Files.lines(path).collect(Collectors.toList());

            if (lines.isEmpty() || !containsCat(lines.get(0))) {
                System.out.println("Файл не содержит данных котов");
                return List.of();
            }

            return lines.stream()
                    .skip(1)
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(this::parseCatFromLine)
                    .filter(cat -> cat != null && validator.isValid(cat))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return List.of();
        }
    }

    private boolean containsCat(String firstLine) {
        return firstLine != null && firstLine.toLowerCase().contains("cat");
    }

    private Cat parseCatFromLine(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length == 3) {
                return Cat.builder()
                        .setName(parts[0].trim())
                        .setAge(Integer.parseInt(parts[1].trim()))
                        .setBreed(parts[2].trim())
                        .build();
            } else {
                System.out.println("Неверный формат строки (ожидается 3 поля): " + line);
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка парсинга возраста в строке: " + line);
            return null;
        } catch (Exception e) {
            System.out.println("Ошибка парсинга строки: " + line);
            return null;
        }
    }
}