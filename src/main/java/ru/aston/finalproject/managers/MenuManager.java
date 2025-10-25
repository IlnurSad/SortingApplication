package ru.aston.finalproject.managers;

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

    public void printSortStrategyMenu(int entityType) {
        System.out.println("\n=== Выбор стратегии сортировки ===");
        System.out.println("1. Пузырьковая сортировка (по имени)");
        System.out.println("2. Быстрая сортировка (по имени)");

        if (entityType == 1) {
            System.out.println("3. Сортировка по возрасту");
            System.out.println("4. Сортировка по породе");
        } else {
            System.out.println("3. Сортировка по возрасту");
            System.out.println("4. Сортировка по профессии");
        }
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
}