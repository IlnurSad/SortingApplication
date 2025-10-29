package ru.aston.finalproject.interfaces;

public interface Validator<T> {
    boolean isValid(T value);
}