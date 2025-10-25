package ru.aston.finalproject.classloader.loaders;

import org.junit.Test;
import ru.aston.finalproject.entities.Person;
import ru.aston.finalproject.entities.validators.PersonValidator;
import ru.aston.finalproject.interfaces.Validator;
import ru.aston.finalproject.loaders.FilePersonDataLoader;

import java.util.List;

import static org.junit.Assert.*;

public class FilePersonDataLoaderIntegrationTest {
    private final Validator<Person> validator = new PersonValidator();
    private final FilePersonDataLoader filePersonDataLoader = new FilePersonDataLoader(validator);

    @Test
    public void loadData() {
        List<Person> people = filePersonDataLoader.loadData(5);
        assertEquals(people.size(), 5);
    }
}