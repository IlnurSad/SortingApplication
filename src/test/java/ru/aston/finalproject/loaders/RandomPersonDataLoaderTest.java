package ru.aston.finalproject.loaders;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.aston.finalproject.entity.Person;

import java.util.List;

class RandomPersonDataLoaderTest {
    private final RandomPersonDataLoader dataLoader = new RandomPersonDataLoader();
    private final List<Person> persons = dataLoader.loadData();

    @Test
    void whenLoadDataWithGeneratedPersons_thenReturnListOfPersons() {
        assertNotNull(persons);
        assertFalse(persons.isEmpty());
        assertTrue(persons.size() >= 5 && persons.size() <= 25);

        for (Person person : persons) {
            assertNotNull(person.getName());
            assertTrue(person.getAge() >= 18 && person.getAge() <= 80);
            assertNotNull(person.getProfession());
        }
    }
}