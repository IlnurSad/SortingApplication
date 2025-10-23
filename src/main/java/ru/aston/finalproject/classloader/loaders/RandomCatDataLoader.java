package ru.aston.finalproject.classloader.loaders;

import ru.aston.finalproject.classloader.entities.Cat;
import ru.aston.finalproject.classloader.interfaces.DataLoader;
import ru.aston.finalproject.classloader.interfaces.Validator;

import java.util.Random;

public class RandomCatDataLoader implements DataLoader<Cat> {
    private final Validator<Cat> validator;
    private final Random random;

    public RandomCatDataLoader(Validator<Cat> validator) {
        this.validator = validator;
        this.random = new Random();
    }

    @Override
    public Cat[] loadData(int size) {
        Cat[] data = new Cat[size];
        String[] names = {"Мурзик", "Барсик", "Васька", "Рыжик", "Снежок", "Пушистик", "Гарфилд", "Том"};
        String[] breeds = {"Сиамская", "Персидская", "Мейн-кун", "Сфинкс", "Британская", "Сибирская", "Бенгальская", "Дворняжка"};

        for (int i = 0; i < size; i++) {
            String name = names[random.nextInt(names.length)] + (i + 1);
            int age = random.nextInt(15) + 1;
            String breed = breeds[random.nextInt(breeds.length)];

            data[i] = Cat.builder()
                    .setName(name)
                    .setAge(age)
                    .setBreed(breed)
                    .build();
        }

        return data;
    }
}
