package ru.aston.finalproject.loaders;

import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.interfaces.DataLoader;
import ru.aston.finalproject.interfaces.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManualCatDataLoader implements DataLoader<Cat> {
    private final Scanner scanner;
    private final Validator<Cat> validator;

    public ManualCatDataLoader(Scanner scanner, Validator<Cat> validator) {
        this.scanner = scanner;
        this.validator = validator;
    }

    @Override
    public List<Cat> loadData() {
        int size = getSizeInput();
        List<Cat> data = new ArrayList<>();
        System.out.println("Введите данные для " + size + " котов:");

        for (int i = 0; i < size; i++) {
            System.out.println("Кот " + (i + 1) + ":");
            data.add(readValidCat());
        }

        return data;
    }

    private int getSizeInput() {
        while (true) {
            try {
                System.out.print("Введите количество котов: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат числа. Попробуйте снова.");
            }
        }
    }

    private Cat readValidCat() {
        while (true) {
            try {
                System.out.print("Имя: ");
                String name = scanner.nextLine();

                System.out.print("Возраст: ");
                int age = Integer.parseInt(scanner.nextLine());

                System.out.print("Порода: ");
                String breed = scanner.nextLine();

                Cat cat = Cat.builder()
                        .setName(name)
                        .setAge(age)
                        .setBreed(breed)
                        .build();

                if (validator.isValid(cat)) {
                    return cat;
                } else {
                    System.out.println("Невалидные данные. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }
}