package ru.aston.finalproject.processing;

import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.interfaces.DataLoader;
import ru.aston.finalproject.interfaces.SortStrategy;
import ru.aston.finalproject.managers.*;
import ru.aston.finalproject.utils.FileAppender;

import java.util.List;
import java.util.concurrent.Future;

public class SortingProcessor {
    private final MenuManager menuManager;
    private final DataLoaderManager dataLoaderManager;
    private final SortStrategyManager strategyManager;
    private final SearchManager searchManager;
    private final SortingManager<Object> sortingManager;
    private final FileAppender<Object> fileAppender;

    public SortingProcessor(MenuManager menuManager,
                            DataLoaderManager dataLoaderManager,
                            SortStrategyManager strategyManager,
                            SearchManager searchManager) {
        this.menuManager = menuManager;
        this.dataLoaderManager = dataLoaderManager;
        this.strategyManager = strategyManager;
        this.searchManager = searchManager;
        this.sortingManager = new SortingManager<>(2);
        this.fileAppender = new FileAppender<>("output.txt");
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

        menuManager.printSortStrategyMenu(entityType);
        int strategyType = menuManager.readIntInput("Выберите стратегию: ");

        if (!strategyManager.isValidStrategyType(strategyType)) {
            System.out.println("Неверный выбор стратегии сортировки.");
            return;
        }

        SortStrategy<Object> sortStrategy = strategyManager.getSortStrategy(entityType, strategyType);

        try {
            System.out.println("\nСортировка...");
            Future<Void> future = sortingManager.sortAsync(data, sortStrategy);
            future.get();
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
            searchManager.performCatSearch(catList);
        } else {
            List<Person> personList = (List<Person>) (List<?>) sortedData;
            searchManager.performPersonSearch(personList);
        }
    }

    public void shutdown() {
        sortingManager.shutdown();
    }

    private void printList(List<Object> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    private void saveSortedData(List<Object> sortedData) {
        fileAppender.appendData(sortedData, Object::toString);
        System.out.println("Отсортированные данные успешно записаны в файл.");
    }
}