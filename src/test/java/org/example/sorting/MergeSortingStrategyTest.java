package org.example.sorting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void sort() {
        List<String> actualList = List.of("a", "f", "e", "h", "c", "b", "g", "d");
        new MergeSortingStrategy().sort(actualList, Comparator.naturalOrder(), executor);
        List<String> expectedList = List.of("a", "b", "c", "d", "e", "f", "g", "h");
        Assertions.assertEquals(expectedList, actualList);
    }
}
