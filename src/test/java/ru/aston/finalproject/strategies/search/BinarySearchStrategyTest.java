package ru.aston.finalproject.strategies.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.finalproject.entity.Cat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchStrategyTest {

    private BinarySearchStrategy<Cat> binarySearchStrategy;
    private List<Cat> sortedCats;
    private Comparator<Cat> nameComparator;

    @BeforeEach
    void setUp() {
        nameComparator = Comparator.comparing(Cat::getName)
                .thenComparing(Cat::getAge)
                .thenComparing(Cat::getBreed);

        binarySearchStrategy = new BinarySearchStrategy<>(nameComparator);

        sortedCats = Arrays.asList(
                Cat.builder().setName("Barsik").setAge(3).setBreed("Persian").build(),
                Cat.builder().setName("Murka").setAge(2).setBreed("Siamese").build(),
                Cat.builder().setName("Murka").setAge(4).setBreed("British").build(),
                Cat.builder().setName("Rex").setAge(5).setBreed("Maine Coon").build()
        );

        sortedCats.sort(nameComparator);
    }

    @Test
    void testSearch_Found() {
        Cat searchCat = Cat.builder().setName("Murka").setAge(2).setBreed("Siamese").build();

        int result = binarySearchStrategy.search(sortedCats, searchCat);

        assertEquals(1, result);
    }

    @Test
    void testSearch_NotFound() {
        Cat searchCat = Cat.builder().setName("Unknown").setAge(1).setBreed("Breed").build();

        int result = binarySearchStrategy.search(sortedCats, searchCat);

        assertEquals(-1, result);
    }

    @Test
    void testSearch_EmptyList() {
        List<Cat> emptyList = Arrays.asList();
        Cat searchCat = Cat.builder().setName("Barsik").setAge(3).setBreed("Persian").build();

        int result = binarySearchStrategy.search(emptyList, searchCat);

        assertEquals(-1, result);
    }

    @Test
    void testSearch_DuplicateElements() {
        List<Cat> catsWithDuplicates = Arrays.asList(
                Cat.builder().setName("Barsik").setAge(3).setBreed("Persian").build(),
                Cat.builder().setName("Murka").setAge(2).setBreed("Siamese").build(),
                Cat.builder().setName("Murka").setAge(2).setBreed("Siamese").build(),
                Cat.builder().setName("Rex").setAge(5).setBreed("Maine Coon").build()
        );
        catsWithDuplicates.sort(nameComparator);

        Cat searchCat = Cat.builder().setName("Murka").setAge(2).setBreed("Siamese").build();

        int result = binarySearchStrategy.search(catsWithDuplicates, searchCat);

        assertTrue(result >= 1 && result <= 2);
    }
}