package ru.aston.finalproject.classloader.loaders;

import ru.aston.finalproject.classloader.entities.Cat;
import ru.aston.finalproject.classloader.interfaces.DataLoader;
import ru.aston.finalproject.classloader.interfaces.Validator;

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
    public List<Cat> loadData(int size) {
        List<Cat> data = new ArrayList<>();
        System.out.println("Введите данные для " + size + " котов:");

        for (int i = 0; i < size; i++) {
            System.out.println("Кот " + (i + 1) + ":");
            data.add(readValidCat());
        }

        return data;
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
