package ru.aston.finalproject.classloader.entities.validators;

import ru.aston.finalproject.classloader.entities.Cat;
import ru.aston.finalproject.classloader.interfaces.Validator;

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
