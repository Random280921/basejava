package ru.topjava.webapp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Класс  ДЗ 12 - задачи по стримам
 *
 * @author KAIvanov
 * created by 04.01.2022 16:59
 * @version 1.0
 */
public class MainStream {
    public static void main(String[] args) {
        int[] arr = {2, 3, 6, 1, 4, 1, 6, 8, 2, 3};
        System.out.println(minValue(arr));

        List<Integer> beginning = Arrays.asList(1, 6, 4, 7, 8, 5, 9);
        System.out.println("Сумма элементов: " + beginning.stream().reduce(0, Integer::sum));
        List<Integer> ending = oddOrEven(beginning);
        for (Integer integer : ending) System.out.println(integer);
    }

    private static int minValue(int[] values) {
        AtomicInteger cnt = new AtomicInteger(-1);
        return Arrays.stream(values)
                .boxed()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .mapToDouble(Integer::doubleValue)
                .map(x -> x * (Math.pow(10, cnt.incrementAndGet())))
                .mapToInt(x -> (int) x)
                .sum();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        AtomicReference<Integer> Sum = new AtomicReference<>(0);
        return integers
                .stream()
                .peek(x -> Sum.updateAndGet(v -> v + x))
                .collect(Collectors
                        .collectingAndThen(
                                Collectors.toList(),
                                c -> c.stream()
                                        .filter(x -> (Sum.get() % 2 == 0 && x % 2 == 1) || (Sum.get() % 2 == 1 && x % 2 == 0))
                                        .collect(Collectors.toList())
                        )
                );
    }
}
