package ru.aston.finalproject.binarysearch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.managers.SearchManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class SearchUITest {

    private SearchUI searchUI;
    private ByteArrayOutputStream outputStream;
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    private final List<Cat> testCats = Arrays.asList(
            Cat.builder().setName("Barsik").setAge(3).setBreed("Persian").build(),
            Cat.builder().setName("Murka").setAge(2).setBreed("Siamese").build(),
            Cat.builder().setName("Rex").setAge(5).setBreed("Maine Coon").build()
    );

    private final List<Person> testPersons = Arrays.asList(
            Person.builder().setName("Alice").setAge(25).setProfession("Engineer").build(),
            Person.builder().setName("Bob").setAge(30).setProfession("Doctor").build(),
            Person.builder().setName("Charlie").setAge(35).setProfession("Teacher").build()
    );

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        setInput("");
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private void setInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        searchUI = new SearchUI(new Scanner(System.in));
    }

    private String getOutput() {
        return outputStream.toString();
    }

    private static class TestCatSearchManager extends SearchManager<Cat> {
        private final Integer binarySearchResult;
        private final Integer countExactOccurrencesResult;
        private final List<Integer> findAllOccurrencesResult;
        private final RuntimeException exceptionToThrow;

        TestCatSearchManager(Integer binarySearchResult, Integer countExactOccurrencesResult,
                             List<Integer> findAllOccurrencesResult, RuntimeException exceptionToThrow) {
            this.binarySearchResult = binarySearchResult;
            this.countExactOccurrencesResult = countExactOccurrencesResult;
            this.findAllOccurrencesResult = findAllOccurrencesResult;
            this.exceptionToThrow = exceptionToThrow;
        }

        @Override
        public int performBinarySearch(List<? extends Cat> list, Cat key) {
            if (exceptionToThrow != null) {
                throw exceptionToThrow;
            }
            return binarySearchResult != null ? binarySearchResult : -1;
        }

        @Override
        public int countExactOccurrences(List<? extends Cat> list, Cat key) {
            if (exceptionToThrow != null) {
                throw exceptionToThrow;
            }
            return countExactOccurrencesResult != null ? countExactOccurrencesResult : 0;
        }

        @Override
        public List<Integer> findAllOccurrences(List<? extends Cat> list, Predicate<Cat> predicate) {
            if (exceptionToThrow != null) {
                throw exceptionToThrow;
            }
            return findAllOccurrencesResult != null ? findAllOccurrencesResult : Collections.emptyList();
        }

    }

    private static class TestPersonSearchManager extends SearchManager<Person> {
        private final Integer binarySearchResult;
        private final Integer countExactOccurrencesResult;
        private final List<Integer> findAllOccurrencesResult;
        private final RuntimeException exceptionToThrow;

        TestPersonSearchManager(Integer binarySearchResult, Integer countExactOccurrencesResult,
                                List<Integer> findAllOccurrencesResult, RuntimeException exceptionToThrow) {
            this.binarySearchResult = binarySearchResult;
            this.countExactOccurrencesResult = countExactOccurrencesResult;
            this.findAllOccurrencesResult = findAllOccurrencesResult;
            this.exceptionToThrow = exceptionToThrow;
        }

        @Override
        public int performBinarySearch(List<? extends Person> list, Person key) {
            if (exceptionToThrow != null) {
                throw exceptionToThrow;
            }
            return binarySearchResult != null ? binarySearchResult : -1;
        }

        @Override
        public int countExactOccurrences(List<? extends Person> list, Person key) {
            if (exceptionToThrow != null) {
                throw exceptionToThrow;
            }
            return countExactOccurrencesResult != null ? countExactOccurrencesResult : 0;
        }

        @Override
        public List<Integer> findAllOccurrences(List<? extends Person> list, Predicate<Person> predicate) {
            if (exceptionToThrow != null) {
                throw exceptionToThrow;
            }
            return findAllOccurrencesResult != null ? findAllOccurrencesResult : Collections.emptyList();
        }

        @Override
        public void setBinarySearchStrategy(java.util.Comparator<? super Person> comparator) {
            // No-op for tests
        }
    }

    private void assertOutputContains(String expected) {
        String output = getOutput();
        assertTrue(output.contains(expected),
                "Expected output to contain: '" + expected + "' but was: '" + output + "'");
    }

    private void assertOutputDoesNotContain(String unexpected) {
        String output = getOutput();
        assertFalse(output.contains(unexpected),
                "Expected output not to contain: '" + unexpected + "' but was: '" + output + "'");
    }

    @Test
    void performCatSearch_WhenUserDeclines_ShouldNotSearch() {
        setInput("n\n");
        TestCatSearchManager catManager = new TestCatSearchManager(null, null, null, null);

        searchUI.performCatSearch(catManager, testCats);

        assertOutputDoesNotContain("=== Поиск кота ===");
    }

    @Test
    void performCatSearch_WithEmptyList_ShouldShowErrorMessage() {
        setInput("y\n");
        TestCatSearchManager catManager = new TestCatSearchManager(null, null, null, null);

        searchUI.performCatSearch(catManager, Collections.emptyList());

        assertOutputContains("Сначала загрузите данные котов.");
    }

    @Test
    void performPersonSearch_WhenUserDeclines_ShouldNotSearch() {
        setInput("n\n");
        TestPersonSearchManager personManager = new TestPersonSearchManager(null, null, null, null);

        searchUI.performPersonSearch(personManager, testPersons);

        assertOutputDoesNotContain("=== Поиск человека ===");
    }

    @Test
    void performPersonSearch_WithEmptyList_ShouldShowErrorMessage() {
        setInput("y\n");
        TestPersonSearchManager personManager = new TestPersonSearchManager(null, null, null, null);

        searchUI.performPersonSearch(personManager, Collections.emptyList());

        assertOutputContains("Сначала загрузите данные людей.");
    }

    @Test
    void displaySearchResults_WithEmptyPositions_ShouldShowNoResultsMessage() throws Exception {
        List<Integer> emptyPositions = Collections.emptyList();

        invokeDisplaySearchResults(testCats, emptyPositions, "Коты");

        assertOutputContains("Совпадений не найдено");
    }

    @Test
    void displaySearchResults_WithSinglePosition_ShouldShowSingleResult() throws Exception {
        List<Integer> positions = Arrays.asList(1);

        invokeDisplaySearchResults(testCats, positions, "Коты");

        assertOutputContains("Позиции в списке: [2]");
        assertOutputContains("На позиции 2:");
    }

    @Test
    void displaySearchResults_WithMultiplePositions_ShouldShowAllResults() throws Exception {
        List<Integer> positions = Arrays.asList(0, 2);

        invokeDisplaySearchResults(testCats, positions, "Коты");

        assertOutputContains("Позиции в списке: [1, 3]");
    }

    @Test
    void displaySearchResults_WithPersonObjects_ShouldShowCorrectResults() throws Exception {
        List<Integer> positions = Arrays.asList(0, 2);

        invokeDisplaySearchResults(testPersons, positions, "Люди");

        assertOutputContains("Позиции в списке: [1, 3]");
    }

    @Test
    void performExactSearchWithThreads_WithFoundResults_ShouldDisplayResults() throws Exception {
        Cat searchCat = Cat.builder().setName("Barsik").setAge(3).setBreed("Persian").build();
        TestCatSearchManager catManager = new TestCatSearchManager(0, 2, null, null);

        invokePerformExactSearchWithThreads(catManager, testCats, searchCat);

        assertOutputContains("Найдено точных совпадений: 2");
        assertOutputContains("точных совпадений");
    }

    @Test
    void performExactSearchWithThreads_WithNoResults_ShouldDisplayNotFoundMessage() throws Exception {
        Cat searchCat = Cat.builder().setName("Unknown").setAge(1).setBreed("Breed").build();
        TestCatSearchManager catManager = new TestCatSearchManager(-1, 0, null, null);

        invokePerformExactSearchWithThreads(catManager, testCats, searchCat);

        assertOutputContains("Точных совпадений не найдено");
    }

    @Test
    void performExactSearchWithThreads_WithException_ShouldDisplayErrorMessage() throws Exception {
        Cat searchCat = Cat.builder().setName("Barsik").setAge(3).setBreed("Persian").build();
        TestCatSearchManager catManager = new TestCatSearchManager(null, null, null,
                new RuntimeException("Ошибка поиска"));

        invokePerformExactSearchWithThreads(catManager, testCats, searchCat);

        assertOutputContains("Ошибка при выполнении поиска");
        assertOutputContains("Ошибка поиска");
    }

    @Test
    void performPersonExactSearchWithThreads_WithFoundResults_ShouldDisplayResults() throws Exception {
        Person searchPerson = Person.builder().setName("Alice").setAge(25).setProfession("Engineer").build();
        TestPersonSearchManager personManager = new TestPersonSearchManager(0, 1, null, null);

        invokePerformPersonExactSearchWithThreads(personManager, testPersons, searchPerson);

        assertOutputContains("Найдено точных совпадений: 1");
        assertOutputContains("точных совпадений");
    }

    @Test
    void performPersonExactSearchWithThreads_WithNoResults_ShouldDisplayNotFoundMessage() throws Exception {
        Person searchPerson = Person.builder().setName("Unknown").setAge(1).setProfession("Job").build();
        TestPersonSearchManager personManager = new TestPersonSearchManager(-1, 0, null, null);

        invokePerformPersonExactSearchWithThreads(personManager, testPersons, searchPerson);

        assertOutputContains("Точных совпадений не найдено");
    }

    @Test
    void performPersonExactSearchWithThreads_WithException_ShouldDisplayErrorMessage() throws Exception {
        Person searchPerson = Person.builder().setName("Alice").setAge(25).setProfession("Engineer").build();
        TestPersonSearchManager personManager = new TestPersonSearchManager(null, null, null,
                new RuntimeException("Ошибка поиска"));

        invokePerformPersonExactSearchWithThreads(personManager, testPersons, searchPerson);

        assertOutputContains("Ошибка при выполнении поиска");
        assertOutputContains("Ошибка поиска");
    }

    private <T> void invokeDisplaySearchResults(List<T> list, List<Integer> positions, String entityType) throws Exception {
        SearchUI tempSearchUI = new SearchUI(new Scanner(new ByteArrayInputStream("".getBytes())));
        Method method = SearchUI.class.getDeclaredMethod("displaySearchResults", List.class, List.class, String.class);
        method.setAccessible(true);
        method.invoke(tempSearchUI, list, positions, entityType);
    }

    private void invokePerformExactSearchWithThreads(SearchManager<Cat> manager, List<Cat> cats, Cat searchCat) throws Exception {
        SearchUI tempSearchUI = new SearchUI(new Scanner(new ByteArrayInputStream("".getBytes())));
        Method method = SearchUI.class.getDeclaredMethod("performExactSearchWithThreads", SearchManager.class, List.class, Cat.class);
        method.setAccessible(true);
        method.invoke(tempSearchUI, manager, cats, searchCat);
    }

    private void invokePerformPersonExactSearchWithThreads(SearchManager<Person> manager, List<Person> persons, Person searchPerson) throws Exception {
        SearchUI tempSearchUI = new SearchUI(new Scanner(new ByteArrayInputStream("".getBytes())));
        Method method = SearchUI.class.getDeclaredMethod("performPersonExactSearchWithThreads", SearchManager.class, List.class, Person.class);
        method.setAccessible(true);
        method.invoke(tempSearchUI, manager, persons, searchPerson);
    }
}