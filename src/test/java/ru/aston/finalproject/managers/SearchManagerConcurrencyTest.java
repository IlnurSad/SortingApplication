package ru.aston.finalproject.managers;

import org.junit.jupiter.api.Test;
import ru.aston.finalproject.entity.Cat;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchManagerConcurrencyTest {

    @Test
    void testCountExactOccurrences_LargeList() throws InterruptedException {
        SearchManager<Cat> manager = new SearchManager<>();

        List<Cat> largeList = new ArrayList<>();
        Cat targetCat = Cat.builder().setName("Target").setAge(5).setBreed("Test").build();

        for (int i = 0; i < 1000; i++) {
            if (i % 10 == 0) {
                largeList.add(targetCat);
            } else {
                largeList.add(Cat.builder().setName("Cat" + i).setAge(i % 10).setBreed("Breed" + i).build());
            }
        }

        int result = manager.countExactOccurrences(largeList, targetCat);

        assertEquals(100, result, "Должно быть найдено 100 вхождений");
    }

    @Test
    void testFindAllOccurrences_LargeList() throws InterruptedException {
        SearchManager<Cat> manager = new SearchManager<>();

        List<Cat> largeList = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            if (i % 2 == 0) {
                largeList.add(Cat.builder().setName("Common").setAge(i % 5).setBreed("Breed").build());
            } else {
                largeList.add(Cat.builder().setName("Unique" + i).setAge(i % 5).setBreed("Breed").build());
            }
        }

        List<Integer> result = manager.findAllOccurrences(largeList,
                cat -> cat.getName().equals("Common"));

        assertEquals(250, result.size(), "Должно быть найдено 250 вхождений");
    }
}