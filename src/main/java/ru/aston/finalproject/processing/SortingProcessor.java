package ru.aston.finalproject.processing;

import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.interfaces.DataLoader;
import ru.aston.finalproject.managers.DataLoaderManager;
import ru.aston.finalproject.managers.MenuManager;
import ru.aston.finalproject.managers.SearchManager;
import ru.aston.finalproject.managers.SortingManager;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SortingProcessor {
    private final MenuManager menuManager;
    private final DataLoaderManager dataLoaderManager;
    private final SearchManager searchManager;
    private final ExecutorService executor;
    
    public SortingProcessor(MenuManager menuManager, DataLoaderManager dataLoaderManager, SearchManager searchManager) {
        this.menuManager = menuManager;
        this.dataLoaderManager = dataLoaderManager;
        this.searchManager = searchManager;
        this.executor = Executors.newFixedThreadPool(4);
    }
    
    @SuppressWarnings("unchecked")
    public void processSorting(int entityType, String entityName) {
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
        
        try {
            System.out.println("\nСортировка...");
            SortingManager sortingManager = new SortingManager(strategyType);
            data = sortingManager.sort(data, comparator, executor);
            System.out.println("Сортировка завершена!");
            
            System.out.println("\nОтсортированные данные:");
            printList(data);
            
            performSearchWithType(data, entityType);
            
        } catch (Exception e) {
            System.out.println("Ошибка при сортировке: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void performSearchWithType(List<Object> sortedData, int entityType) {
        if (entityType == 1) {
            List<Cat> catList = (List<Cat>) (List<?>) sortedData;
            searchManager.performCatSearch(catList);
        } else {
            List<Person> personList = (List<Person>) (List<?>) sortedData;
            searchManager.performPersonSearch(personList);
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
}
