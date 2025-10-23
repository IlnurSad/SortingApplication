package ru.aston.finalproject.classloader.interfaces;

public interface Validator<T> {
    boolean isValid(T value);
}
