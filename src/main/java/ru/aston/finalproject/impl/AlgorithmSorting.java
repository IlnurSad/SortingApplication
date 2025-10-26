package ru.aston.finalproject.impl;

import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.entity.Person;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;

public enum AlgorithmSorting {
    BASE_SORT("Базовая сортировка") {
        @Override
        public <T> void sort(List<T> list, Comparator<T> comparator, ExecutorService executor) {
            System.out.println("Выполняется базовая сортировка в потоках...");
            BaseSortingStrategy<T> strategy = new BaseSortingStrategy<>();
            List<T> sorted = strategy.sort(list, comparator, executor);
            System.out.println("Результат: " + sorted);
        }
    }, EVEN_SORT("Сортировка четных чисел") {
        @Override
        public <T> void sort(List<T> list, Comparator<T> comparator, ExecutorService executor) {
            System.out.println("Выполняется сортировка чётных чисел в потоках...");
            EvenNumberSortingStrategy<T> strategy = new EvenNumberSortingStrategy<>(new BaseSortingStrategy<>(), value -> {
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
            });
            List<T> sorted = strategy.sort(list, comparator, executor);
            System.out.println("Результат: " + sorted);
        }
    };
    
    private final String description;
    
    AlgorithmSorting(String description) {
        this.description = description;
    }
    
    public String getDescription() {return description;}
    
    public abstract <T> void sort(List<T> list, Comparator<T> comparator, ExecutorService executor);
}


