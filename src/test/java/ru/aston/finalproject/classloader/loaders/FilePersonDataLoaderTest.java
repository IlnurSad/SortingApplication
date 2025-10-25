package ru.aston.finalproject.classloader.loaders;

import org.junit.Test;
import ru.aston.finalproject.entities.Person;
import ru.aston.finalproject.interfaces.Validator;
import ru.aston.finalproject.loaders.FilePersonDataLoader;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilePersonDataLoaderTest {
    private final Validator<Person> validator = mock();
    private final FilePersonDataLoader filePersonDataLoader = new FilePersonDataLoader(validator);

    @Test
    public void whenValidationReturnsTrue_thenReturnsPeopleList() {
        when(validator.isValid(any(Person.class))).thenReturn(true);
        List<Person> people = filePersonDataLoader.loadData(5);
        assertEquals(people.size(), 5);
    }

    @Test
    public void whenValidationThrowsException_thenReturnsEmptyList() {
        when(validator.isValid(any(Person.class))).thenThrow(new RuntimeException());
        List<Person> people = filePersonDataLoader.loadData(5);
        assertEquals(people.size(), 0);
    }
    @Test
    public void whenValidationReturnsFalse_thenReturnsEmptyList() {
        when(validator.isValid(any(Person.class))).thenReturn(false);
        List<Person> people = filePersonDataLoader.loadData(5);
        assertEquals(people.size(), 0);
    }
}