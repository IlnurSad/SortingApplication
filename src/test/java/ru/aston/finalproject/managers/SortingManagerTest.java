package ru.aston.finalproject.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.entity.comparators.PersonAgeComparator;
import ru.aston.finalproject.entity.comparators.PersonProfessionComparator;
import ru.aston.utils.TestData;

import java.util.ArrayList;
import java.util.List;

class SortingManagerTest {
    
    @Test
    void whenCallBaseSortingStrategyByProfession_thenReturnListIsSortedByProfessionAsc() {
        SortingManager manager = new SortingManager(1);
        List<Person> list = TestData.createPersons();
        List<Person> sortedList = manager.sort(list, new PersonProfessionComparator());
        
        String expectSortedList =
                "[Person{name='Григория', age=36, profession='Администратор'}, " + "Person{name='Татьяна', age=18, profession='Артист'}, "
                        + "Person{name='Ольга', age=31, profession='Девопс'}, " + "Person{name='Сергей', age=29, profession='Инженер'}, "
                        + "Person{name='Борис', age=54, profession='Космонавт'}, " + "Person{name='Влад', age=22, profession='Менеджер'}, "
                        + "Person{name='Екатерина', age=27, profession='Повар'}, " + "Person{name='Владимир', age=46, profession='Программист'},"
                        + " Person{name='Владимир', age=36, profession='Стоматолог'}, " + "Person{name='Ольга', age=65, profession='Флорист'}, "
                        + "Person{name='Фёдор', age=29, profession='Хирург'}]";
        
        Assertions.assertEquals(expectSortedList, sortedList.toString());
    }
    
    @Test
    void whenCallEvenNumberSortingStrategy_thenReturnEvenNumbersByAsc() {
        SortingManager manager = new SortingManager(2);
        List<Person> list = TestData.createPersons();
        List<Person> sortedList = manager.sort(list, new PersonAgeComparator());
        
        String expectSortedList = "[Person{name='Сергей', age=29, profession='Инженер'}, " + "Person{name='Екатерина', age=27, profession='Повар'}, "
                + "Person{name='Татьяна', age=18, profession='Артист'}, " + "Person{name='Влад', age=22, profession='Менеджер'}, "
                + "Person{name='Владимир', age=36, profession='Стоматолог'}, " + "Person{name='Ольга', age=65, profession='Флорист'}, "
                + "Person{name='Григория', age=36, profession='Администратор'}, " + "Person{name='Владимир', age=46, profession='Программист'}, "
                + "Person{name='Ольга', age=31, profession='Девопс'}, " + "Person{name='Борис', age=54, profession='Космонавт'}, "
                + "Person{name='Фёдор', age=29, profession='Хирург'}]";
        
        Assertions.assertEquals(expectSortedList, sortedList.toString());
    }
    
    @Test
    void whenCallUnknownStrategy_thenThrowsException() {
        int strategyNumber = 5;
        SortingManager manager = new SortingManager(strategyNumber);
        
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class,
                                                      () -> manager.sort(new ArrayList<>(), new PersonProfessionComparator()));
        
        Assertions.assertEquals("Неизвестный тип сортировки: " + strategyNumber, exception.getMessage());
    }
}
