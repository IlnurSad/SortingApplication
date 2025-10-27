package ru.aston.finalproject.strategies.sorting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.finalproject.entity.Person;
import ru.aston.utils.TestData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EvenNumberSortingStrategyTest {
    private ExecutorService executor;
    
    @BeforeEach
    void setUp() {
        executor = Executors.newFixedThreadPool(4);
    }
    
    @AfterEach
    void close() {
        executor.shutdown();
    }
    
    @Test
    void whenSortingIntegerNaturalOrder_thenResultIsListSortedEvenNumberByAsc() {
        List<Integer> actualList = new ArrayList<>(List.of(10, 1, 5, 12, 3, 9, 8, 6, 5, 0, 4, 7));
        EvenNumberSortingStrategy<Integer> strategy = new EvenNumberSortingStrategy<>(new BaseSortingStrategy<>(), i -> i);
        
        List<Integer> sortedList = strategy.sort(actualList, Comparator.naturalOrder(), executor);
        List<Integer> expectedList = List.of(0, 1, 5, 4, 3, 9, 6, 8, 5, 10, 12, 7);
        
        assertEquals(expectedList, sortedList);
    }
    
    @Test
    void whenSortingPersonAgeByEvenNaturalOrder_thenResultIsListSortedEvenNumberByAsc() {
        List<Person> actualList = new ArrayList<>(TestData.createPersons());
        EvenNumberSortingStrategy<Person> strategy = new EvenNumberSortingStrategy<>(new BaseSortingStrategy<>(), Person::getAge);
        List<Person> sortedList = strategy.sort(actualList, Comparator.comparing(Person::getAge), executor);
        String expectedString = "[Person{name='Сергей', age=29, profession='Инженер'}, " + "Person{name='Екатерина', age=27, profession='Повар'}, "
                + "Person{name='Татьяна', age=18, profession='Артист'}, " + "Person{name='Влад', age=22, profession='Менеджер'}, "
                + "Person{name='Владимир', age=36, profession='Стоматолог'}, " + "Person{name='Ольга', age=65, profession='Флорист'}, "
                + "Person{name='Григория', age=36, profession='Администратор'}, " + "Person{name='Владимир', age=46, profession='Программист'}, "
                + "Person{name='Ольга', age=31, profession='Девопс'}, " + "Person{name='Борис', age=54, profession='Космонавт'}, "
                + "Person{name='Фёдор', age=29, profession='Хирург'}]";
        
        assertEquals(expectedString, sortedList.toString());
    }
}
