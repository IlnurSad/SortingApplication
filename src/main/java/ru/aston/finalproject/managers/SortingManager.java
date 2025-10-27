package ru.aston.finalproject.managers;

import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.interfaces.SortingStrategy;
import ru.aston.finalproject.strategies.sorting.BaseSortingStrategy;
import ru.aston.finalproject.strategies.sorting.EvenNumberSortingStrategy;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SortingManager {
    private final Map<Integer, SortingStrategy<Object>> strategyMap = new HashMap<>();
    private final int selectedStrategy;
    private final ExecutorService executor;
    
    public SortingManager(int selectedStrategy) {
        this.selectedStrategy = selectedStrategy;
        this.executor = Executors.newFixedThreadPool(4);
        registerStrategies();
    }
    
    private void registerStrategies() {
        strategyMap.put(1, new BaseSortingStrategy<>());
        strategyMap.put(2, new EvenNumberSortingStrategy<>(new BaseSortingStrategy<>(), value -> {
            if (value instanceof Integer integer) {
                return integer;
            }
            if (value instanceof Person person) {
                return person.getAge();
            }
            if (value instanceof Cat cat) {
                return cat.getAge();
            }
            throw new IllegalArgumentException("Неизвестный тип для сортировки: " + value.getClass());
        }));
    }
    
    @SuppressWarnings("unchecked")
    public <T> List<T> sort(List<T> list, Comparator<T> comparator) {
        SortingStrategy<T> strategy = (SortingStrategy<T>) strategyMap.get(selectedStrategy);
        if (strategy == null) {
            throw new IllegalArgumentException("Неизвестный тип сортировки: " + selectedStrategy);
        }
        try {
            return strategy.sort(list, comparator, executor);
        } finally {
            executor.shutdown();
        }
    }
}


