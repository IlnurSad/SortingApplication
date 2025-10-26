package ru.aston.finalproject.strategies.sorting;

import ru.aston.finalproject.interfaces.SortingStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.ToIntFunction;

public class EvenNumberSortingStrategy<T> implements SortingStrategy<T> {
    private final SortingStrategy<T> sorter;
    private final ToIntFunction<T> intFunction;
    
    public EvenNumberSortingStrategy(SortingStrategy<T> sorter, ToIntFunction<T> intFunction) {
        this.sorter = sorter;
        this.intFunction = intFunction;
    }
    
    @Override
    public List<T> sort(List<T> list, Comparator<T> comparator, ExecutorService executor) {
        if (list == null || list.isEmpty() || list.size() == 1) {
            return list;
        }
        
        List<T> evenElements = list.stream().filter(element -> intFunction.applyAsInt(element) % 2 == 0).toList();
        List<T> sortedEven = sorter.sort(new ArrayList<>(evenElements), comparator, executor);
        
        List<T> result = new ArrayList<>();
        int evenIndex = 0;
        for (T element : list) {
            if (intFunction.applyAsInt(element) % 2 == 0) {
                result.add(sortedEven.get(evenIndex++));
            } else {
                result.add(element);
            }
        }
        return result;
    }
}
