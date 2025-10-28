package ru.aston.finalproject;

import ru.aston.finalproject.binarysearch.SearchUI;
import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.managers.DataLoaderManager;
import ru.aston.finalproject.managers.MenuManager;
import ru.aston.finalproject.managers.SearchManager;
import ru.aston.finalproject.processing.SortingProcessor;

import java.util.Scanner;

public class SortingApplication {
    private final Scanner scanner;
    private final MenuManager menuManager;
    private final DataLoaderManager dataLoaderManager;
    private final SearchManager<Cat> catSearchManager;
    private final SearchManager<Person> personSearchManager;
    private final SearchUI searchUI;
    private final SortingProcessor sortingProcessor;

    public SortingApplication() {
        this.scanner = new Scanner(System.in);
        this.menuManager = new MenuManager(scanner);
        this.dataLoaderManager = new DataLoaderManager(scanner);
        this.catSearchManager = new SearchManager<>();
        this.personSearchManager = new SearchManager<>();
        this.searchUI = new SearchUI(scanner);
        this.sortingProcessor = new SortingProcessor(
                scanner,
                menuManager,
                dataLoaderManager,
                catSearchManager,
                personSearchManager,
                searchUI
        );
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
        sortingProcessor.processSorting(1);
    }

    private void processPersonSorting() {
        System.out.println("\n=== Работа с людьми ===");
        sortingProcessor.processSorting(2);
    }

    private void shutdown() {
        System.out.println("Выход из программы...");
        scanner.close();
    }

    public static void main(String[] args) {
        SortingApplication app = new SortingApplication();
        app.run();
    }
}