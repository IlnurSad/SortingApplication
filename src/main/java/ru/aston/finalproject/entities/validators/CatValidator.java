package ru.aston.finalproject.entities.validators;

import ru.aston.finalproject.entities.Cat;
import ru.aston.finalproject.interfaces.Validator;

public class CatValidator implements Validator<Cat> {
    @Override
    public boolean isValid(Cat cat) {
        return cat != null &&
                cat.getName() != null &&
                !cat.getName().trim().isEmpty() &&
                cat.getAge() >= 0 &&
                cat.getAge() <= 30 &&
                cat.getBreed() != null &&
                !cat.getBreed().trim().isEmpty();
    }
}
