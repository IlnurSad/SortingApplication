package ru.aston.utils;

import ru.aston.finalproject.entity.Person;

import java.util.List;

public final class TestData {
    
    private TestData() {}
    
    public static List<Person> createPersons() {
        return List.of(Person.builder().setName("Сергей").setAge(29).setProfession("Инженер").build(),
                       Person.builder().setName("Екатерина").setAge(27).setProfession("Повар").build(),
                       Person.builder().setName("Владимир").setAge(36).setProfession("Стоматолог").build(),
                       Person.builder().setName("Татьяна").setAge(18).setProfession("Артист").build(),
                       Person.builder().setName("Борис").setAge(54).setProfession("Космонавт").build(),
                       Person.builder().setName("Ольга").setAge(65).setProfession("Флорист").build(),
                       Person.builder().setName("Влад").setAge(22).setProfession("Менеджер").build(),
                       Person.builder().setName("Владимир").setAge(46).setProfession("Программист").build(),
                       Person.builder().setName("Ольга").setAge(31).setProfession("Девопс").build(),
                       Person.builder().setName("Григория").setAge(36).setProfession("Администратор").build(),
                       Person.builder().setName("Фёдор").setAge(29).setProfession("Хирург").build());
    }
}
