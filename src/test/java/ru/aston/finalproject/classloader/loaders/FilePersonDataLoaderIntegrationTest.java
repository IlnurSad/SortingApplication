package ru.aston.finalproject.classloader.loaders;

import org.junit.jupiter.api.Test;
import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.entity.validators.PersonValidator;
import ru.aston.finalproject.interfaces.Validator;
import ru.aston.finalproject.loaders.FilePersonDataLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilePersonDataLoaderIntegrationTest {
    private final Validator<Person> validator = new PersonValidator();
    private final FilePersonDataLoader filePersonDataLoader = new FilePersonDataLoader(validator);

    @Test
    public void loadData() {
        List<Person> people = filePersonDataLoader.loadData(5);
        assertEquals(people.size(), 5);
    }
}
