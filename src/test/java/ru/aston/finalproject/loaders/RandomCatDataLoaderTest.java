package ru.aston.finalproject.loaders;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.aston.finalproject.entity.Cat;

import java.util.List;

class RandomCatDataLoaderTest {
    private final RandomCatDataLoader dataLoader = new RandomCatDataLoader();
    private final List<Cat> cats = dataLoader.loadData();

    @Test
    void whenLoadDataWithGeneratedCats_thenReturnListOfCats() {
        assertNotNull(cats);
        assertFalse(cats.isEmpty());
        assertTrue(cats.size() >= 5 && cats.size() <= 25);

        for (Cat cat : cats) {
            assertNotNull(cat.getName());
            assertTrue(cat.getAge() > 0 && cat.getAge() <= 30);
            assertNotNull(cat.getBreed());
        }
    }
}