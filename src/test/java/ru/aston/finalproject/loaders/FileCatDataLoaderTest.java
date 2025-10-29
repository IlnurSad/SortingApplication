package ru.aston.finalproject.loaders;

import org.junit.jupiter.api.Test;
import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.entity.validators.CatValidator;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class FileCatDataLoaderTest {
    private final CatValidator validator = new CatValidator();

    @Test
    void whenLoadDataWithExistingCatsResourceFile_thenReturnListOfCats() {
        Path resourcePath = Path.of("src/test/resources/Cats_test");
        String input = resourcePath + "\n";
        FileCatDataLoader loader = new FileCatDataLoader(validator,
                new Scanner(new java.io.ByteArrayInputStream(input.getBytes())));

        List<Cat> cats = loader.loadData();

        assertNotNull(cats);
        assertEquals(3, cats.size());

        Cat firstCat = cats.getFirst();
        assertEquals("Мурзик", firstCat.getName());
        assertEquals(3, firstCat.getAge());
        assertEquals("Сиамская", firstCat.getBreed());

        Cat secondCat = cats.get(1);
        assertEquals("Барсик", secondCat.getName());
        assertEquals(5, secondCat.getAge());
        assertEquals("Персидская", secondCat.getBreed());

        Cat thirdCat = cats.get(2);
        assertEquals("Васька", thirdCat.getName());
        assertEquals(2, thirdCat.getAge());
        assertEquals("Дворняжка", thirdCat.getBreed());
    }

    @Test
    void whenLoadDataWithInvalidFileFormat_thenReturnEmptyList() {
        Path testFile = Path.of("src/test/resources/InvalidCat_test");

        String input = testFile + "\n";
        FileCatDataLoader loader = new FileCatDataLoader(validator,
                new Scanner(new java.io.ByteArrayInputStream(input.getBytes())));

        List<Cat> Cats = loader.loadData();

        assertTrue(Cats.isEmpty());
    }

    @Test
    void whenLoadDataWithNonExistentFile_thenReturnEmptyList() {
        String input = "/nonexistent/file.txt\n";
        FileCatDataLoader loader = new FileCatDataLoader(validator,
                new Scanner(new java.io.ByteArrayInputStream(input.getBytes())));

        List<Cat> Cats = loader.loadData();

        assertTrue(Cats.isEmpty());
    }
}