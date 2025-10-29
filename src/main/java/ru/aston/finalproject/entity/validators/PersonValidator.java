package ru.aston.finalproject.entity.validators;

import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.interfaces.Validator;

public class PersonValidator implements Validator<Person> {
    @Override
    public boolean isValid(Person person) {
        return person != null &&
                person.getName() != null &&
                !person.getName().trim().isEmpty() &&
                person.getAge() >= 18 &&
                person.getAge() <= 80 &&
                person.getProfession() != null &&
                !person.getProfession().trim().isEmpty();
    }
}
