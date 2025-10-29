package ru.aston.finalproject.loaders;

import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.interfaces.DataLoader;
import ru.aston.finalproject.interfaces.Validator;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ManualPersonDataLoader implements DataLoader<Person> {
    private final Scanner scanner;
    private final Validator<Person> validator;

    public ManualPersonDataLoader(Scanner scanner, Validator<Person> validator) {
        this.scanner = scanner;
        this.validator = validator;
    }

    @Override
    public List<Person> loadData() {
        int size = getSizeInput();
        System.out.println("Введите данные для " + size + " человек:");

        return IntStream.range(0, size)
                .mapToObj(i -> {
                    System.out.println("Человек " + (i + 1) + ":");
                    return readValidPerson();
                })
                .collect(Collectors.toList());
    }

    private int getSizeInput() {
        while (true) {
            try {
                System.out.print("Введите количество людей: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат числа. Попробуйте снова.");
            }
        }
    }

    private Person readValidPerson() {
        while (true) {
            try {
                System.out.print("Имя: ");
                String name = scanner.nextLine();

                System.out.print("Возраст: ");
                int age = Integer.parseInt(scanner.nextLine());

                System.out.print("Профессия: ");
                String profession = scanner.nextLine();

                Person person = Person.builder()
                        .setName(name)
                        .setAge(age)
                        .setProfession(profession)
                        .build();

                if (validator.isValid(person)) {
                    return person;
                } else {
                    System.out.println("Невалидные данные. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }
}