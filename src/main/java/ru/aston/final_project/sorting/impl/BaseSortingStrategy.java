package ru.aston.final_project.sorting.impl;

import ru.aston.final_project.sorting.service.SortingStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class BaseSortingStrategy<T> implements SortingStrategy<T> {
    @Override
    public List<T> sort(List<T> list, Comparator<T> comparator, ExecutorService executor) {
        if (list == null || list.isEmpty() || list.size() == 1) {
            return list;
        }
        
        int halfListCount = list.size() / 2;
        List<T> leftPart = new ArrayList<>(list.subList(0, halfListCount));
        List<T> rightPart = new ArrayList<>(list.subList(halfListCount, list.size()));
        
        try {
            Future<List<T>> leftFuture = executor.submit(() -> sort(leftPart, comparator, executor));
            List<T> sortedRightPart = sort(rightPart, comparator, executor);
            List<T> sortedLeftPart = leftFuture.get();
            return merge(sortedLeftPart, sortedRightPart, comparator);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
