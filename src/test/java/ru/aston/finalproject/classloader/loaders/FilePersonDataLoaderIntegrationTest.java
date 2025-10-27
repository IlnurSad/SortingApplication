package ru.aston.finalproject.classloader.loaders;


import org.junit.jupiter.api.Test;
import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.entity.validators.PersonValidator;
import ru.aston.finalproject.interfaces.Validator;
import ru.aston.finalproject.loaders.FilePersonDataLoader;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class FilePersonDataLoaderIntegrationTest {
    private final Validator<Person> validator = new PersonValidator();
    // TODO: Дописать тест и удалить mock(Scanner.class)
    private final FilePersonDataLoader filePersonDataLoader = new FilePersonDataLoader(validator, mock(Scanner.class));

    @Test
    public void loadData() {
        List<Person> people = filePersonDataLoader.loadData();
        assertEquals(people.size(), 5);
    }
}
