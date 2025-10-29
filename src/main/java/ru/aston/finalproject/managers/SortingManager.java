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
import java.util.concurrent.ForkJoinPool;

public class SortingManager {
    private static final int NUM_THREADS = 4;
    private final Map<Integer, SortingStrategy<Object>> strategyMap = new HashMap<>();
    private final int selectedStrategy;
    private final ForkJoinPool executor;
    
    public SortingManager(int selectedStrategy) {
        this.selectedStrategy = selectedStrategy;
        this.executor = new ForkJoinPool(NUM_THREADS);
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