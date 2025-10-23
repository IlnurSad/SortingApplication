package ru.aston.finalproject.classloader.loaders;

import ru.aston.finalproject.classloader.entities.Person;
import ru.aston.finalproject.classloader.interfaces.DataLoader;
import ru.aston.finalproject.classloader.interfaces.Validator;

import java.util.Scanner;

public class ManualPersonDataLoader implements DataLoader<Person> {
    private final Scanner scanner;
    private final Validator<Person> validator;

    public ManualPersonDataLoader(Scanner scanner, Validator<Person> validator) {
        this.scanner = scanner;
        this.validator = validator;
    }

    @Override
    public Person[] loadData(int size) {
        Person[] data = new Person[size];
        System.out.println("Введите данные для " + size + " человек:");

        for (int i = 0; i < size; i++) {
            System.out.println("Человек " + (i + 1) + ":");
            data[i] = readValidPerson();
        }

        return data;
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
