package ru.aston.finalproject.entity.comparators;

import ru.aston.finalproject.entity.Person;

import java.util.Comparator;

public class PersonProfessionComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return Comparator.comparing(Person::getProfession).thenComparing(Person::getName).thenComparing(Person::getAge).compare(p1, p2);
    }
}