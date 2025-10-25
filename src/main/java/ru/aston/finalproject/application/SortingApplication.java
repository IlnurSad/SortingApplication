package ru.aston.finalproject.application;

import ru.aston.finalproject.managers.DataLoaderManager;
import ru.aston.finalproject.managers.MenuManager;
import ru.aston.finalproject.managers.SearchManager;
import ru.aston.finalproject.managers.SortStrategyManager;
import ru.aston.finalproject.processing.SortingProcessor;

import java.util.Scanner;

public class SortingApplication {
    private final Scanner scanner;
    private final MenuManager menuManager;
    private final DataLoaderManager dataLoaderManager;
    private final SortStrategyManager strategyManager;
    private final SearchManager searchManager;
    private final SortingProcessor sortingProcessor;

    public SortingApplication() {
        this.scanner = new Scanner(System.in);
        this.menuManager = new MenuManager(scanner);
        this.dataLoaderManager = new DataLoaderManager(scanner);
        this.strategyManager = new SortStrategyManager();
        this.searchManager = new SearchManager(scanner);
        this.sortingProcessor = new SortingProcessor(menuManager, dataLoaderManager, strategyManager, searchManager);
    }

    public void run() {
        System.out.println("=== Универсальное приложение для сортировки ===");

        while (true) {
            menuManager.printMainMenu();
            int choice = menuManager.readIntInput("Выберите опцию: ");

            switch (choice) {
                case 1:
                    processCatSorting();
                    break;
                case 2:
                    processPersonSorting();
                    break;
                case 3:
                    shutdown();
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void processCatSorting() {
        System.out.println("\n=== Работа с котами ===");
        sortingProcessor.processSorting(1, "котов");
    }

    private void processPersonSorting() {
        System.out.println("\n=== Работа с людьми ===");
        sortingProcessor.processSorting(2, "человек");
    }

    private void shutdown() {
        System.out.println("Выход из программы...");
        sortingProcessor.shutdown();
        scanner.close();
    }

    public static void main(String[] args) {
        SortingApplication app = new SortingApplication();
        app.run();
    }
}