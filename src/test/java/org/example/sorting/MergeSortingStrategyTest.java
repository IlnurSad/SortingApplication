package org.example.sorting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MergeSortingStrategyTest {
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
        MergeSortingStrategy<String> strategy = new MergeSortingStrategy<>();
        
        List<String> sortedList = strategy.sort(actualList, Comparator.naturalOrder(), executor);
        List<String> expectedList = List.of("a", "b", "c", "d", "e", "f", "g", "h");
        
        Assertions.assertEquals(expectedList, sortedList);
    }
    
    @Test
    void whenSortingStringReverseOrder_thenResultIsListSortedByDesc() {
        List<String> actualList = new ArrayList<>(List.of("a", "f", "e", "h", "c", "b", "g", "d"));
        MergeSortingStrategy<String> strategy = new MergeSortingStrategy<>();
        
        List<String> sortedList = strategy.sort(actualList, Comparator.reverseOrder(), executor);
        List<String> expectedList = List.of("h", "g", "f", "e", "d", "c", "b", "a");
        
        Assertions.assertEquals(expectedList, sortedList);
    }
    
    @Test
    void whenSortingIntegerNaturalOrder_thenResultIsListSortedByAsc() {
        List<Integer> actualList = new ArrayList<>(List.of(2, 1, 5, 3, 9, 6, 10, 8, 4, 7));
        MergeSortingStrategy<Integer> strategy = new MergeSortingStrategy<>();
        
        List<Integer> sortedList = strategy.sort(actualList, Comparator.naturalOrder(), executor);
        List<Integer> expectedList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        Assertions.assertEquals(expectedList, sortedList);
    }
    
    @Test
    void whenSortingIntegerReverseOrder_thenResultIsListSortedByDesc() {
        List<Integer> actualList = new ArrayList<>(List.of(2, 1, 5, 3, 9, 6, 10, 8, 4, 7));
        MergeSortingStrategy<Integer> strategy = new MergeSortingStrategy<>();
        
        List<Integer> sortedList = strategy.sort(actualList, Comparator.reverseOrder(), executor);
        List<Integer> expectedList = List.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        
        Assertions.assertEquals(expectedList, sortedList);
    }
    
    @Test
    void whenSortingThrowException_thenResultHasRuntimeException() {
        List<Double> actualList = new ArrayList<>(List.of(2.1, 1.2, 5.6, 3.3, 9.1, 6.9, 10.2, 8.6, 4.3, 7.9));
        MergeSortingStrategy<Double> strategy = new MergeSortingStrategy<>();
        
        Assertions.assertThrows(RuntimeException.class,
                                () -> strategy.sort(actualList, (c1, c2) -> {throw new IllegalArgumentException("Sorting failed...");}, executor));
    }
}
