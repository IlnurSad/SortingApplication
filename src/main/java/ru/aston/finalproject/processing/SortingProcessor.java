package ru.aston.finalproject.processing;

import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.interfaces.DataLoader;
import ru.aston.finalproject.managers.DataLoaderManager;
import ru.aston.finalproject.managers.MenuManager;
import ru.aston.finalproject.managers.SearchManager;
import ru.aston.finalproject.managers.SortingManager;
import ru.aston.finalproject.binarysearch.SearchUI;
import ru.aston.finalproject.appender.FileAppender;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SortingProcessor {
    private static final String OUTPUT_FILE_PATH = "src/main/resources/output.txt";
    public final Scanner scanner;
    private final MenuManager menuManager;
    private final DataLoaderManager dataLoaderManager;
    private final SearchUI searchUI;
    private final SearchManager<Cat> catSearchManager;
    private final SearchManager<Person> personSearchManager;
    private final FileAppender<Object> fileAppender;
    
    private Comparator<Object> currentCatComparator;
    private Comparator<Object> currentPersonComparator;
    
    public SortingProcessor(Scanner scanner, MenuManager menuManager, DataLoaderManager dataLoaderManager, SearchManager<Cat> catSearchManager,
                            SearchManager<Person> personSearchManager, SearchUI searchUI) {
        this.scanner = scanner;
        this.menuManager = menuManager;
        this.dataLoaderManager = dataLoaderManager;
        this.catSearchManager = catSearchManager;
        this.personSearchManager = personSearchManager;
        this.searchUI = searchUI;
        this.fileAppender = new FileAppender<>(OUTPUT_FILE_PATH);
    }
    
    @SuppressWarnings("unchecked")
    public void processSorting(int entityType) {
        menuManager.printDataLoaderMenu();
        int loaderType = menuManager.readIntInput("Выберите способ: ");
        
        if (!dataLoaderManager.isValidLoaderType(entityType, loaderType)) {
            System.out.println("Неверный выбор загрузчика данных.");
            return;
        }
        
        DataLoader<?> dataLoader = dataLoaderManager.getDataLoader(entityType, loaderType);
        List<Object> data = (List<Object>) dataLoader.loadData();
        
        if (data.isEmpty()) {
            System.out.println("Не удалось загрузить данные.");
            return;
        }
        
        System.out.println("\nЗагружено элементов: " + data.size());
        System.out.println("Исходные данные:");
        printList(data);
        
        menuManager.printSortStrategyMenu();
        int strategyType = menuManager.readIntInput("Выберите стратегию: ");
        
        if (!isValidStrategyType(strategyType)) {
            System.out.println("Неверный выбор стратегии сортировки.");
            return;
        }
        
        Comparator<Object> comparator = menuManager.selectComparator(entityType);
        
        if (entityType == 1) {
            currentCatComparator = comparator;
        } else {
            currentPersonComparator = comparator;
        }
        
        try {
            System.out.println("\nСортировка...");
            SortingManager sortingManager = new SortingManager(strategyType);
            data = sortingManager.sort(data, comparator);
            System.out.println("Сортировка завершена!");
            
            System.out.println("\nОтсортированные данные:");
            printList(data);
            
            saveSortedData(data);
            
            performSearchWithType(data, entityType);
            
        } catch (Exception e) {
            System.out.println("Ошибка при сортировке: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void performSearchWithType(List<Object> sortedData, int entityType) {
        if (entityType == 1) {
            List<Cat> catList = (List<Cat>) (List<?>) sortedData;
            if (currentCatComparator != null) {
                catSearchManager.setBinarySearchStrategy(currentCatComparator);
            }
            searchUI.performCatSearch(catSearchManager, catList);
        } else {
            List<Person> personList = (List<Person>) (List<?>) sortedData;
            if (currentPersonComparator != null) {
                personSearchManager.setBinarySearchStrategy(currentPersonComparator);
            }
            searchUI.performPersonSearch(personSearchManager, personList);
        }
    }
    
    private void printList(List<Object> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }
    
    public boolean isValidStrategyType(int strategyType) {
        return strategyType >= 1 && strategyType <= 2;
    }
    
    private void saveSortedData(List<Object> sortedData) {
        fileAppender.appendData(sortedData, Object::toString);
        System.out.println("Отсортированные данные успешно записаны в файл.");
    }
}