package ru.aston.finalproject.entity.comparators;

import ru.aston.finalproject.entity.Person;

import java.util.Comparator;

public class PersonNameComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return Comparator.comparing(Person::getName).thenComparing(Person::getAge).thenComparing(Person::getProfession).compare(p1, p2);
    }
}
