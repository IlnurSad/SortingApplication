package ru.aston.finalproject.managers;

import ru.aston.finalproject.entities.Cat;
import ru.aston.finalproject.entities.Person;
import ru.aston.finalproject.interfaces.SearchStrategy;
import ru.aston.finalproject.strategies.search.BinarySearchStrategy;

import java.util.List;
import java.util.Scanner;

public class SearchManager {
    private final Scanner scanner;

    public SearchManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public void performCatSearch(List<Cat> sortedData) {
        performSearch(sortedData, 1);
    }

    public void performPersonSearch(List<Person> sortedData) {
        performSearch(sortedData, 2);
    }

    private <T extends Comparable<T>> void performSearch(List<T> sortedData, int entityType) {
        System.out.print("\nХотите выполнить поиск? (y/n): ");
        String answer = scanner.nextLine();

        if (!answer.equalsIgnoreCase("y")) {
            return;
        }

        System.out.println("Введите данные для поиска:");
        try {
            @SuppressWarnings("unchecked")
            T searchKey = (T) createSearchKey(entityType);

            SearchStrategy<T> searchStrategy = new BinarySearchStrategy<>();
            int index = searchStrategy.search(sortedData, searchKey);

            if (index != -1) {
                System.out.println("Элемент найден на позиции: " + index);
                System.out.println("Найденный элемент: " + sortedData.get(index));
            } else {
                System.out.println("Элемент не найден.");
            }

        } catch (Exception e) {
            System.out.println("Ошибка ввода данных для поиска: " + e.getMessage());
        }
    }

    private Object createSearchKey(int entityType) {
        if (entityType == 1) {
            return createCatSearchKey();
        } else {
            return createPersonSearchKey();
        }
    }

    private Cat createCatSearchKey() {
        System.out.print("Имя: ");
        String name = scanner.nextLine();

        System.out.print("Возраст: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("Порода: ");
        String breed = scanner.nextLine();

        return Cat.builder()
                .setName(name)
                .setAge(age)
                .setBreed(breed)
                .build();
    }

    private Person createPersonSearchKey() {
        System.out.print("Имя: ");
        String name = scanner.nextLine();

        System.out.print("Возраст: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("Профессия: ");
        String profession = scanner.nextLine();

        return Person.builder()
                .setName(name)
                .setAge(age)
                .setProfession(profession)
                .build();
    }
}