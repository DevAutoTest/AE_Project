package Danilova.utils;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtils {
    private RandomUtils() {
        // приватный конструктор, чтобы класс нельзя было инстанцировать
    }

    /**
     * Возвращает случайное целое число от min до max включительно.
     *
     * @param min нижняя граница (включительно)
     * @param max верхняя граница (включительно), должен быть >= min
     * @return случайное число в диапазоне [min, max]
     * @throws IllegalArgumentException если max < min
     */
    public static int randomIntInclusive(int min, int max) {
        if (max < min) {
            throw new IllegalArgumentException("max должен быть не меньше min");
        }
        // nextInt(bound) даёт [0, bound), поэтому прибавляем 1
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
