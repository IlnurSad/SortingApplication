package ru.aston.finalproject.strategies.sorting;

import ru.aston.finalproject.interfaces.SortingStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class BaseSortingStrategy<T> implements SortingStrategy<T> {
    @Override
    public List<T> sort(List<T> list, Comparator<T> comparator, ForkJoinPool executor) {
        if (list == null || list.size() <= 1) {
            return list;
        }
        return executor.invoke(new MergeSortTask<>(list, comparator));
    }
    
    private static class MergeSortTask<T> extends RecursiveTask<List<T>> {
        private final List<T> list;
        private final Comparator<T> comparator;
        
        MergeSortTask(List<T> list, Comparator<T> comparator) {
            this.list = list;
            this.comparator = comparator;
        }
        
        @Override
        protected List<T> compute() {
            if (list.size() <= 1) {
                return list;
            }
            
            int halfListCount = list.size() / 2;
            List<T> leftPart = new ArrayList<>(list.subList(0, halfListCount));
            List<T> rightPart = new ArrayList<>(list.subList(halfListCount, list.size()));
            
            MergeSortTask<T> leftTask = new MergeSortTask<>(leftPart, comparator);
            MergeSortTask<T> rightTask = new MergeSortTask<>(rightPart, comparator);
            
            leftTask.fork();
            
            List<T> rightResult = rightTask.compute();
            List<T> leftResult = leftTask.join();
            
            return merge(leftResult, rightResult, comparator);
        }
        
        private List<T> merge(List<T> leftPart, List<T> rightPart, Comparator<T> comparator) {
            List<T> mergedList = new ArrayList<>();
            int leftIndex = 0;
            int rightIndex = 0;
            
            while (leftIndex < leftPart.size() && rightIndex < rightPart.size()) {
                T leftElement = leftPart.get(leftIndex);
                T rightElement = rightPart.get(rightIndex);
                
                if (comparator.compare(leftElement, rightElement) <= 0) {
                    mergedList.add(leftElement);
                    leftIndex++;
                } else {
                    mergedList.add(rightElement);
                    rightIndex++;
                }
            }
            
            while (leftIndex < leftPart.size()) {
                T leftElement = leftPart.get(leftIndex++);
                mergedList.add(leftElement);
            }
            
            while (rightIndex < rightPart.size()) {
                T rightElement = rightPart.get(rightIndex++);
                mergedList.add(rightElement);
            }
            
            return mergedList;
        }
    }
}
