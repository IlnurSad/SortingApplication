package ru.aston.finalproject.loaders;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.entity.validators.PersonValidator;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

class FilePersonDataLoaderTest {
    private final PersonValidator validator = new PersonValidator();


    @Test
    void whenLoadDataWithExistingPersonsResourceFile_thenReturnListOfPersons() {
        Path resourcePath = Path.of("src/test/resources/Persons_test");
        String input = resourcePath + "\n";
        FilePersonDataLoader loader = new FilePersonDataLoader(validator,
                new Scanner(new java.io.ByteArrayInputStream(input.getBytes())));

        List<Person> persons = loader.loadData();

        assertNotNull(persons);
        assertEquals(5, persons.size());

        Person firstPerson = persons.getFirst();
        assertEquals("Иван", firstPerson.getName());
        assertEquals(25, firstPerson.getAge());
        assertEquals("Инженер", firstPerson.getProfession());

        Person secondPerson = persons.get(1);
        assertEquals("Мария", secondPerson.getName());
        assertEquals(30, secondPerson.getAge());
        assertEquals("Врач", secondPerson.getProfession());

        Person thirdPerson = persons.get(2);
        assertEquals("Алексей", thirdPerson.getName());
        assertEquals(35, thirdPerson.getAge());
        assertEquals("Программист", thirdPerson.getProfession());

        Person fourthPerson = persons.get(3);
        assertEquals("Елена", fourthPerson.getName());
        assertEquals(28, fourthPerson.getAge());
        assertEquals("Учитель", fourthPerson.getProfession());

        Person fifthPerson = persons.get(4);
        assertEquals("Дмитрий", fifthPerson.getName());
        assertEquals(40, fifthPerson.getAge());
        assertEquals("Менеджер", fifthPerson.getProfession());
    }

    @Test
    void whenLoadDataWithInvalidFileFormat_thenReturnEmptyList() {
        Path testFile = Path.of("src/test/resources/InvalidPersons_test");

        String input = testFile + "\n";
        FilePersonDataLoader loader = new FilePersonDataLoader(validator,
                new Scanner(new java.io.ByteArrayInputStream(input.getBytes())));

        List<Person> persons = loader.loadData();

        assertTrue(persons.isEmpty());
    }

    @Test
    void whenLoadDataWithNonExistentFile_thenReturnEmptyList() {
        String input = "/nonexistent/file.txt\n";
        FilePersonDataLoader loader = new FilePersonDataLoader(validator,
                new Scanner(new java.io.ByteArrayInputStream(input.getBytes())));

        List<Person> persons = loader.loadData();

        assertTrue(persons.isEmpty());
    }
}