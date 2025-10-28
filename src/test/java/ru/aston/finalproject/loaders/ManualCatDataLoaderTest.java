package ru.aston.finalproject.loaders;

import org.junit.jupiter.api.Test;
import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.entity.validators.CatValidator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ManualCatDataLoaderTest {
    private final CatValidator validator = new CatValidator();

    @Test
    void whenLoadDataWithExistingCats_thenReturnListOfCats() {
        String INPUT = "2\nМурзик\n3\nСиамская\nБарсик\n5\nПерсидская\n";
        InputStream in = new ByteArrayInputStream(INPUT.getBytes());
        ManualCatDataLoader dataLoader= new ManualCatDataLoader(new Scanner(in), validator);

        List<Cat> cats = dataLoader.loadData();

        assertNotNull(cats);
        assertEquals(2, cats.size());

        Cat firstCat = cats.getFirst();
        assertEquals("Мурзик", firstCat.getName());
        assertEquals(3, firstCat.getAge());
        assertEquals("Сиамская", firstCat.getBreed());

        Cat secondCat = cats.get(1);
        assertEquals("Барсик", secondCat.getName());
        assertEquals(5, secondCat.getAge());
        assertEquals("Персидская", secondCat.getBreed());
    }

    @Test
    void whenLoadDataWithInvalidInput_thenReturnEmptyList() {
        String input = "1\n\n2\nДворняжка\nВаська\n2\nДворняжка\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ManualCatDataLoader loader = new ManualCatDataLoader(new Scanner(in), validator);

        List<Cat> cats = loader.loadData();

        assertNotNull(cats);
        assertEquals(1, cats.size());
        assertEquals("Васька", cats.getFirst().getName());
    }
}