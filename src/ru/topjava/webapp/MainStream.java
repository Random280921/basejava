package ru.topjava.webapp;

import java.util.Arrays;
import java.util.List;
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
        int[] arr = {2, 3, 6, 6, 4, 6, 6, 8, 2, 3};
        System.out.println(minValue(arr));

        List<Integer> beginning = Arrays.asList(1, 6, 4, 7, 8, 5, 9);
        System.out.println("Сумма элементов: " + beginning.stream().reduce(0, Integer::sum));
        List<Integer> ending = oddOrEven(beginning);
        for (Integer integer : ending) System.out.println(integer);
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (abs, x) -> abs * 10 + x);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(0, Integer::sum);
        return integers
                .stream()
                .filter(x -> (sum % 2 != x % 2))
                .collect(Collectors.toList());
    }
}
