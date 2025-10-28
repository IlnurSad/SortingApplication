package ru.aston.finalproject.loaders;

import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.interfaces.DataLoader;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomCatDataLoader implements DataLoader<Cat> {
    private final Random random;

    public RandomCatDataLoader() {
        this.random = new Random();
    }

    @Override
    public List<Cat> loadData() {
        int size = getRandomSize();
        String[] names = {"Мурзик", "Барсик", "Васька", "Рыжик", "Снежок", "Пушистик", "Гарфилд", "Том"};
        String[] breeds = {"Смакская", "Персидская", "Мейн-кун", "Сфинкс", "Британская", "Сибирская", "Бенгальская", "Дворняжка"};

        System.out.println("Генерируем " + size + " случайных котов...");

        return IntStream.range(0, size)
                .mapToObj(i -> {
                    String name = names[random.nextInt(names.length)];
                    int age = random.nextInt(29) + 1;
                    String breed = breeds[random.nextInt(breeds.length)];
                    return Cat.builder()
                            .setName(name)
                            .setAge(age)
                            .setBreed(breed)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private int getRandomSize() {
        return 5 + random.nextInt(20);
    }
}