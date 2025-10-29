package ru.aston.finalproject.entity.validators;

import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.interfaces.Validator;

public class CatValidator implements Validator<Cat> {
    @Override
    public boolean isValid(Cat cat) {
        return cat != null &&
                cat.getName() != null &&
                !cat.getName().trim().isEmpty() &&
                cat.getAge() >= 1 &&
                cat.getAge() <= 30 &&
                cat.getBreed() != null &&
                !cat.getBreed().trim().isEmpty();
    }
}