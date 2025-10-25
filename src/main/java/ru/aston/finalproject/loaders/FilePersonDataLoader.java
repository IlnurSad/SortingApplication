package ru.aston.finalproject.loaders;

import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.interfaces.DataLoader;
import ru.aston.finalproject.interfaces.Validator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FilePersonDataLoader implements DataLoader<Person> {
    private final Validator<Person> validator;
    private final Scanner scanner;

    public FilePersonDataLoader(Validator<Person> validator, Scanner scanner) {
        this.validator = validator;
        this.scanner = scanner;
    }

    @Override
    public List<Person> loadData() {
        System.out.print("Введите путь к файлу с данными людей: ");
        String filePath = scanner.nextLine().trim();

        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                System.out.println("Файл не существует: " + filePath);
                return List.of();
            }

            List<String> lines = Files.lines(path).collect(Collectors.toList());

            if (lines.isEmpty() || !containsPerson(lines.get(0))) {
                System.out.println("Файл не содержит данных людей");
                return List.of();
            }

            return lines.stream()
                    .skip(1)
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(this::parsePersonFromLine)
                    .filter(person -> person != null && validator.isValid(person))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return List.of();
        }
    }

    private boolean containsPerson(String firstLine) {
        return firstLine != null && firstLine.toLowerCase().contains("person");
    }

    private Person parsePersonFromLine(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length == 3) {
                return Person.builder()
                        .setName(parts[0].trim())
                        .setAge(Integer.parseInt(parts[1].trim()))
                        .setProfession(parts[2].trim())
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