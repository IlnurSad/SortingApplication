package ru.aston.finalproject.managers;

import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.entity.comparators.CatAgeComparator;
import ru.aston.finalproject.entity.comparators.CatBreedComparator;
import ru.aston.finalproject.entity.comparators.CatNameComparator;
import ru.aston.finalproject.entity.comparators.PersonAgeComparator;
import ru.aston.finalproject.entity.comparators.PersonNameComparator;
import ru.aston.finalproject.entity.comparators.PersonProfessionComparator;

import java.util.Comparator;
import java.util.Scanner;

public class MenuManager {
    private final Scanner scanner;

    public MenuManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public void printMainMenu() {
        System.out.println("\n=== Главное меню ===");
        System.out.println("1. Работа с котами");
        System.out.println("2. Работа с людьми");
        System.out.println("3. Выход");
    }

    public void printDataLoaderMenu() {
        System.out.println("\n=== Выбор способа загрузки данных ===");
        System.out.println("1. Из файла");
        System.out.println("2. Случайные данные");
        System.out.println("3. Вручную");
    }

    public void printSortStrategyMenu() {
        System.out.println("\n=== Выбор стратегии сортировки ===");
        System.out.println("1. Сортировка слиянием в потоках");
        System.out.println("2. Сортировка чётных чисел в потоках (доп.задание 1)");
    }

    public int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат числа. Попробуйте снова.");
            }
        }
    }

    public String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public boolean askYesNo(String question) {
        System.out.print(question + " (y/n): ");
        String answer = scanner.nextLine();
        return answer.equalsIgnoreCase("y");
    }
    
    public Comparator selectComparator(int entityType) {
        System.out.println("\n=== Выберите поле для сортировки ===");
        System.out.println("1. Natural order (как определено в классе compareTo)");
        System.out.println("2. По имени");
        System.out.println("3. По возрасту");
        System.out.println("4. По дополнительному полю");
        
        int choice = readIntInput("Ваш выбор: ");
        
        if (entityType == 1) {
            return switch (choice) {
                case 1 -> Comparator.naturalOrder();
                case 2 -> new CatNameComparator();
                case 3 -> new CatAgeComparator();
                case 4 -> new CatBreedComparator();
                default -> Comparator.comparing(Cat::getName);
            };
        } else {
            return switch (choice) {
                case 1 -> Comparator.naturalOrder();
                case 2 -> new PersonNameComparator();
                case 3 -> new PersonAgeComparator();
                case 4 -> new PersonProfessionComparator();
                default -> Comparator.comparing(Person::getName);
            };
        }
    }
}
