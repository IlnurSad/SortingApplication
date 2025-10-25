package ru.aston.finalproject.loaders;

import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.interfaces.DataLoader;
import ru.aston.finalproject.interfaces.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomCatDataLoader implements DataLoader<Cat> {
    private final Validator<Cat> validator;
    private final Random random;

    public RandomCatDataLoader(Validator<Cat> validator) {
        this.validator = validator;
        this.random = new Random();
    }

    @Override
    public List<Cat> loadData() {
        int size = getRandomSize();
        List<Cat> data = new ArrayList<>();
        String[] names = {"Мурзик", "Барсик", "Васька", "Рыжик", "Снежок", "Пушистик", "Гарфилд", "Том"};
        String[] breeds = {"Сиамская", "Персидская", "Мейн-кун", "Сфинкс", "Британская", "Сибирская", "Бенгальская", "Дворняжка"};

        System.out.println("Генерируем " + size + " случайных котов...");

        for (int i = 0; i < size; i++) {
            String name = names[random.nextInt(names.length)];
            int age = random.nextInt(15) + 1;
            String breed = breeds[random.nextInt(breeds.length)];

            Cat cat = Cat.builder()
                    .setName(name)
                    .setAge(age)
                    .setBreed(breed)
                    .build();

            data.add(cat);
        }

        return data;
    }

    private int getRandomSize() {
        return 5 + random.nextInt(10);
    }
}