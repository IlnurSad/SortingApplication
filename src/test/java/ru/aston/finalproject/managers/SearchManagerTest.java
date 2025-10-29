package ru.aston.finalproject.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.entity.Person;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchManagerTest {

    private SearchManager<Cat> catSearchManager;
    private SearchManager<Person> personSearchManager;
    private List<Cat> testCats;
    private List<Person> testPersons;
    private Comparator<Cat> catNameComparator;
    private Comparator<Person> personNameComparator;

    @BeforeEach
    void setUp() {
        catSearchManager = new SearchManager<>();
        personSearchManager = new SearchManager<>();

        catNameComparator = Comparator.comparing(Cat::getName);
        personNameComparator = Comparator.comparing(Person::getName);

        testCats = Arrays.asList(
                Cat.builder().setName("Barsik").setAge(3).setBreed("Persian").build(),
                Cat.builder().setName("Murka").setAge(2).setBreed("Siamese").build(),
                Cat.builder().setName("Rex").setAge(5).setBreed("Maine Coon").build(),
                Cat.builder().setName("Murka").setAge(4).setBreed("British").build()
        );

        testPersons = Arrays.asList(
                Person.builder().setName("Alice").setAge(25).setProfession("Engineer").build(),
                Person.builder().setName("Bob").setAge(30).setProfession("Doctor").build(),
                Person.builder().setName("Charlie").setAge(35).setProfession("Teacher").build()
        );

        testCats.sort(catNameComparator);
        testPersons.sort(personNameComparator);
    }

    @Test
    void testPerformBinarySearch_Found() {
        catSearchManager.setBinarySearchStrategy(catNameComparator);
        Cat searchCat = Cat.builder().setName("Murka").setAge(2).setBreed("Siamese").build();

        int result = catSearchManager.performBinarySearch(testCats, searchCat);

        assertTrue(result >= 0, "Кот должен быть найден");
    }

    @Test
    void testPerformBinarySearch_NotFound() {
        catSearchManager.setBinarySearchStrategy(catNameComparator);
        Cat searchCat = Cat.builder().setName("Unknown").setAge(1).setBreed("Breed").build();

        int result = catSearchManager.performBinarySearch(testCats, searchCat);

        assertEquals(-1, result, "Кот не должен быть найден");
    }

    @Test
    void testPerformBinarySearch_StrategyNotSet() {
        Cat searchCat = Cat.builder().setName("Murka").setAge(2).setBreed("Siamese").build();

        assertThrows(IllegalStateException.class, () -> {
            catSearchManager.performBinarySearch(testCats, searchCat);
        });
    }

    @Test
    void testCountExactOccurrences() throws InterruptedException {
        List<Cat> catsWithDuplicates = Arrays.asList(
                Cat.builder().setName("Barsik").setAge(3).setBreed("Persian").build(),
                Cat.builder().setName("Murka").setAge(2).setBreed("Siamese").build(),
                Cat.builder().setName("Murka").setAge(2).setBreed("Siamese").build(), // Дубликат
                Cat.builder().setName("Rex").setAge(5).setBreed("Maine Coon").build()
        );

        Cat searchCat = Cat.builder().setName("Murka").setAge(2).setBreed("Siamese").build();

        int result = catSearchManager.countExactOccurrences(catsWithDuplicates, searchCat);

        assertEquals(2, result, "Должно быть найдено 2 вхождения");
    }

    @Test
    void testCountExactOccurrences_EmptyList() throws InterruptedException {
        List<Cat> emptyList = Arrays.asList();
        Cat searchCat = Cat.builder().setName("Murka").setAge(2).setBreed("Siamese").build();

        int result = catSearchManager.countExactOccurrences(emptyList, searchCat);

        assertEquals(0, result, "Для пустого списка должно возвращаться 0");
    }

    @Test
    void testFindAllOccurrences_ByName() throws InterruptedException {
        List<Integer> result = catSearchManager.findAllOccurrences(testCats,
                cat -> cat.getName().equals("Murka"));

        assertEquals(2, result.size(), "Должно быть найдено 2 кота с именем Murka");
    }

    @Test
    void testFindAllOccurrences_ByAge() throws InterruptedException {
        List<Integer> result = catSearchManager.findAllOccurrences(testCats,
                cat -> cat.getAge() == 2);

        assertEquals(1, result.size(), "Должен быть найден 1 кот возрастом 2 года");
    }

    @Test
    void testFindAllOccurrences_ByBreed() throws InterruptedException {
        List<Integer> result = catSearchManager.findAllOccurrences(testCats,
                cat -> cat.getBreed().equals("Persian"));

        assertEquals(1, result.size(), "Должен быть найден 1 кот породы Persian");
    }

    @Test
    void testFindAllOccurrences_EmptyList() throws InterruptedException {
        List<Cat> emptyList = Arrays.asList();

        List<Integer> result = catSearchManager.findAllOccurrences(emptyList,
                cat -> cat.getName().equals("Murka"));

        assertTrue(result.isEmpty(), "Для пустого списка должен возвращаться пустой список");
    }

    @Test
    void testFindAllOccurrences_NoMatches() throws InterruptedException {
        List<Integer> result = catSearchManager.findAllOccurrences(testCats,
                cat -> cat.getName().equals("NonExistent"));

        assertTrue(result.isEmpty(), "Должен возвращаться пустой список при отсутствии совпадений");
    }

    @Test
    void testPersonSearch() {
        personSearchManager.setBinarySearchStrategy(personNameComparator);
        Person searchPerson = Person.builder().setName("Bob").setAge(30).setProfession("Doctor").build();

        int result = personSearchManager.performBinarySearch(testPersons, searchPerson);

        assertTrue(result >= 0, "Человек должен быть найден");
    }

    @Test
    void testPersonFindAllOccurrences() throws InterruptedException {
        List<Integer> result = personSearchManager.findAllOccurrences(testPersons,
                person -> person.getAge() > 28);

        assertEquals(2, result.size(), "Должно быть найдено 2 человека старше 28 лет");
    }
}