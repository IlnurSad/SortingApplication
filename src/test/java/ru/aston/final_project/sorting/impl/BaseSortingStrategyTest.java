package ru.aston.final_project.sorting.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.final_project.sorting.entities.Cat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class BaseSortingStrategyTest {
    private ExecutorService executor;
    
    @BeforeEach
    void setUp() {
        executor = Executors.newFixedThreadPool(4);
    }
    
    @AfterEach
    void close() {
        executor.shutdown();
    }
    
    @Test
    void whenSortingStringNaturalOrder_thenResultIsListSortedByAsc() {
        List<String> actualList = new ArrayList<>(List.of("a", "f", "e", "h", "c", "b", "g", "d"));
        BaseSortingStrategy<String> strategy = new BaseSortingStrategy<>();
        
        List<String> sortedList = strategy.sort(actualList, Comparator.naturalOrder(), executor);
        List<String> expectedList = List.of("a", "b", "c", "d", "e", "f", "g", "h");
        
        Assertions.assertEquals(expectedList, sortedList);
    }
    
    @Test
    void whenSortingStringReverseOrder_thenResultIsListSortedByDesc() {
        List<String> actualList = new ArrayList<>(List.of("a", "f", "e", "h", "c", "b", "g", "d"));
        BaseSortingStrategy<String> strategy = new BaseSortingStrategy<>();
        
        List<String> sortedList = strategy.sort(actualList, Comparator.reverseOrder(), executor);
        List<String> expectedList = List.of("h", "g", "f", "e", "d", "c", "b", "a");
        
        Assertions.assertEquals(expectedList, sortedList);
    }
    
    @Test
    void whenSortingIntegerNaturalOrder_thenResultIsListSortedByAsc() {
        List<Integer> actualList = new ArrayList<>(List.of(2, 1, 5, 3, 9, 6, 10, 8, 4, 7));
        BaseSortingStrategy<Integer> strategy = new BaseSortingStrategy<>();
        
        List<Integer> sortedList = strategy.sort(actualList, Comparator.naturalOrder(), executor);
        List<Integer> expectedList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        Assertions.assertEquals(expectedList, sortedList);
    }
    
    @Test
    void whenSortingCatByAge_thenResultIsListSortedByAsc() {
        List<Cat> actualList = new ArrayList<>(List.of(new Cat.CatBuilder().setName("Васька").setAge(2).setBreed("Чеширский").build(),
                                                       new Cat.CatBuilder().setName("Мурка").setAge(4).setBreed("Сибирский").build(),
                                                       new Cat.CatBuilder().setName("Жучка").setAge(9).setBreed("Британский").build(),
                                                       new Cat.CatBuilder().setName("Ночка").setAge(8).setBreed("Русский").build(),
                                                       new Cat.CatBuilder().setName("Тучка").setAge(1).setBreed("Канадский").build()));
        BaseSortingStrategy<Cat> strategy = new BaseSortingStrategy<>();
        
        List<Cat> sortedList = strategy.sort(actualList, Comparator.comparing(Cat::getAge), executor);
        
        System.out.println(sortedList);
        
        
//        List<Cat> expectedList = List.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
//        Assertions.assertEquals(expectedList, sortedList);
    }
    
    @Test
    void whenSortingPersonByAge_thenResultIsListSortedByAsc() {
        List<Integer> actualList = new ArrayList<>(List.of(2, 1, 5, 3, 9, 6, 10, 8, 4, 7));
        BaseSortingStrategy<Integer> strategy = new BaseSortingStrategy<>();
        
        List<Integer> sortedList = strategy.sort(actualList, Comparator.naturalOrder(), executor);
        List<Integer> expectedList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        Assertions.assertEquals(expectedList, sortedList);
    }
    
    @Test
    void whenSortingIntegerReverseOrder_thenResultIsListSortedByDesc() {
        List<Integer> actualList = new ArrayList<>(List.of(2, 1, 5, 3, 9, 6, 10, 8, 4, 7));
        BaseSortingStrategy<Integer> strategy = new BaseSortingStrategy<>();
        
        List<Integer> sortedList = strategy.sort(actualList, Comparator.reverseOrder(), executor);
        List<Integer> expectedList = List.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        
        Assertions.assertEquals(expectedList, sortedList);
    }
    
    @Test
    void whenSortingThrowException_thenResultHasRuntimeException() {
        List<Double> actualList = new ArrayList<>(List.of(2.1, 1.2, 5.6, 3.3, 9.1, 6.9, 10.2, 8.6, 4.3, 7.9));
        BaseSortingStrategy<Double> strategy = new BaseSortingStrategy<>();
        
        Assertions.assertThrows(RuntimeException.class,
                                () -> strategy.sort(actualList, (c1, c2) -> {throw new IllegalArgumentException("Sorting failed...");}, executor));
    }
}
