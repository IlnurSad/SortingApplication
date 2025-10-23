package ru.aston.finalproject.classloader.entities.validators;

import ru.aston.finalproject.classloader.entities.Person;
import ru.aston.finalproject.classloader.interfaces.Validator;

public class PersonValidator implements Validator<Person> {
    @Override
    public boolean isValid(Person person) {
        return person != null &&
                person.getName() != null &&
                !person.getName().trim().isEmpty() &&
                person.getAge() >= 0 &&
                person.getAge() <= 150 &&
                person.getProfession() != null &&
                !person.getProfession().trim().isEmpty();
    }
}
