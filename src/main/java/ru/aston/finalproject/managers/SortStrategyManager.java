package ru.aston.finalproject.managers;

import ru.aston.finalproject.entity.comparators.CatAgeComparator;
import ru.aston.finalproject.entity.comparators.CatBreedComparator;
import ru.aston.finalproject.entity.comparators.PersonAgeComparator;
import ru.aston.finalproject.entity.comparators.PersonProfessionComparator;
import ru.aston.finalproject.interfaces.SortStrategy;
import ru.aston.finalproject.strategies.sorting.BubbleSortStrategy;
import ru.aston.finalproject.strategies.sorting.QuickSortStrategy;

import java.util.Comparator;


public class SortStrategyManager {

    @SuppressWarnings("unchecked")
    public SortStrategy<Object> getSortStrategy(int entityType, int strategyType) {
        switch (strategyType) {
            case 1:
                return new BubbleSortStrategy();
            case 2:
                return new QuickSortStrategy();
            case 3:
                if (entityType == 1) {
                    return createComparatorSortStrategy(new CatAgeComparator());
                } else {
                    return createComparatorSortStrategy(new PersonAgeComparator());
                }
            case 4:
                if (entityType == 1) {
                    return createComparatorSortStrategy(new CatBreedComparator());
                } else {
                    return createComparatorSortStrategy(new PersonProfessionComparator());
                }
            default:
                return null;
        }
    }

    public boolean isValidStrategyType(int strategyType) {
        return strategyType >= 1 && strategyType <= 4;
    }

    private SortStrategy<Object> createComparatorSortStrategy(Comparator<?> comparator) {
        return new SortStrategy<Object>() {
            @SuppressWarnings("unchecked")
            private final Comparator<Object> objectComparator = (Comparator<Object>) comparator;

            @Override
            public void sort(java.util.List<Object> list) {
                if (list == null || list.size() <= 1) return;

                for (int i = 0; i < list.size() - 1; i++) {
                    for (int j = 0; j < list.size() - i - 1; j++) {
                        if (objectComparator.compare(list.get(j), list.get(j + 1)) > 0) {
                            Object temp = list.get(j);
                            list.set(j, list.get(j + 1));
                            list.set(j + 1, temp);
                        }
                    }
                }
            }
        };
    }
}