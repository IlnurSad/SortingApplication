package ru.aston.finalproject.classloader;

import ru.aston.finalproject.classloader.entities.validators.CatValidator;
import ru.aston.finalproject.classloader.entities.validators.PersonValidator;
import ru.aston.finalproject.classloader.interfaces.DataLoader;
import ru.aston.finalproject.classloader.interfaces.SortStrategy;
import ru.aston.finalproject.classloader.loaders.*;

import java.util.*;
import java.util.concurrent.Future;

public class SortingApplication {
    private final Scanner scanner;
    private Map<Integer, Map<Integer, DataLoader<?>>> dataLoadersMap;

    public SortingApplication() {
        this.scanner = new Scanner(System.in);
        this.dataLoadersMap = initializeDataLoadersMap();
    }

    private Map<Integer, Map<Integer, DataLoader<?>>> initializeDataLoadersMap() {
        Map<Integer, Map<Integer, DataLoader<?>>> loadersMap = new HashMap<>();

        Map<Integer, DataLoader<?>> catLoaders = new HashMap<>();
        catLoaders.put(1, new FileCatDataLoader(new CatValidator()));
        catLoaders.put(2, new RandomCatDataLoader(new CatValidator()));
        catLoaders.put(3, new ManualCatDataLoader(scanner, new CatValidator()));
        loadersMap.put(1, catLoaders);

        Map<Integer, DataLoader<?>> personLoaders = new HashMap<>();
        personLoaders.put(1, new FilePersonDataLoader(new PersonValidator()));
        personLoaders.put(2, new RandomPersonDataLoader(new PersonValidator()));
        personLoaders.put(3, new ManualPersonDataLoader(scanner, new PersonValidator()));
        loadersMap.put(2, personLoaders);

        return loadersMap;
    }

    public void run() {
        System.out.println("=== Универсальное приложение для сортировки ===");

        while (true) {
            printMainMenu();
            int choice = readIntInput("Выберите опцию: ");

            switch (choice) {
                case 1:
                    processCatSorting();
                    break;
                case 2:
                    processPersonSorting();
                    break;
                case 3:
                    System.out.println("Выход из программы...");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n=== Главное меню ===");
        System.out.println("1. Работа с котами");
        System.out.println("2. Работа с людьми");
        System.out.println("3. Выход");
    }

    private void processCatSorting() {
        System.out.println("\n=== Работа с котами ===");
        processSorting(1, "котов");
    }

    private void processPersonSorting() {
        System.out.println("\n=== Работа с людьми ===");
        processSorting(2, "человек");
    }

    private void processSorting(int entityType, String entityName) {
        DataLoader<?> dataLoader = selectDataLoader(entityType);
        if (dataLoader == null) return;

        int size = readIntInput("Введите количество " + entityName + ": ");
        if (size <= 0) {
            System.out.println("Неверное количество.");
            return;
        }

        Object[] data = dataLoader.loadData(size);
        if (data.length == 0) {
            System.out.println("Не удалось загрузить данные.");
            return;
        }

        System.out.println("\nИсходные данные:");
        printArray(data);
    }

    private DataLoader<?> selectDataLoader(int entityType) {
        System.out.println("\n=== Выбор способа загрузки данных ===");
        System.out.println("1. Из файла");
        System.out.println("2. Случайные данные");
        System.out.println("3. Вручную");

        int choice = readIntInput("Выберите способ: ");
        Map<Integer, DataLoader<?>> loaders = dataLoadersMap.get(entityType);

        if (loaders == null) {
            System.out.println("Неверный тип сущности.");
            return null;
        }

        DataLoader<?> loader = loaders.get(choice);
        if (loader == null) {
            System.out.println("Неверный выбор.");
        }

        return loader;
    }

    private void printArray(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println((i + 1) + ". " + array[i]);
        }
    }

    private int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат числа. Попробуйте снова.");
            }
        }
    }

    public static void main(String[] args) {
        SortingApplication app = new SortingApplication();
        app.run();
    }
}
