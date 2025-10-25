package ru.aston.finalproject.entities.validators;

import ru.aston.finalproject.entities.Person;
import ru.aston.finalproject.interfaces.Validator;

public class PersonValidator implements Validator<Person> {
    @Override
    public boolean isValid(Person person) {
        return person != null &&
                person.getName() != null &&
                !person.getName().trim().isEmpty() &&
                person.getAge() >= 0 &&
                person.getAge() <= 130 &&
                person.getProfession() != null &&
                !person.getProfession().trim().isEmpty();
    }
}
