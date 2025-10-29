package ru.aston.finalproject.loaders;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.entity.validators.PersonValidator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

class ManualPersonDataLoaderTest {
    private final PersonValidator validator =  new PersonValidator();;

    @Test
    void whenLoadDataWithExistingPersons_thenReturnListOfPersons() {
        String input = "2\nИван\n25\nИнженер\nМария\n30\nВрач\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ManualPersonDataLoader loader = new ManualPersonDataLoader(new Scanner(in), validator);
        List<Person> persons = loader.loadData();

        assertNotNull(persons);
        assertEquals(2, persons.size());

        Person firstPerson = persons.getFirst();
        assertEquals("Иван", firstPerson.getName());
        assertEquals(25, firstPerson.getAge());
        assertEquals("Инженер", firstPerson.getProfession());

        Person secondPerson = persons.get(1);
        assertEquals("Мария", secondPerson.getName());
        assertEquals(30, secondPerson.getAge());
        assertEquals("Врач", secondPerson.getProfession());
    }

    @Test
    void whenLoadDataWithInvalidInput_thenReturnEmptyList() {
        String input = "1\n\n25\nИнженер\nАлексей\n35\nПрограммист\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ManualPersonDataLoader loader = new ManualPersonDataLoader(new Scanner(in), validator);

        List<Person> persons = loader.loadData();

        assertNotNull(persons);
        assertEquals(1, persons.size());
        assertEquals("Алексей", persons.getFirst().getName());
    }
}