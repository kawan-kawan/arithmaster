package ru.karamoff.kawan_kawan.arithmaster;

import java.util.Random;

class Randomizer {

    private static Random random = new Random();

    static int[] generate() {
        int[] set = new int[4]; // 0 - операция, 1-2 - цифры, 3 - результат

        set[0] = random.nextInt(3); // TODO: прверка на деление нацело


        set[1] = random.nextInt(50) + 1;
        set[2] = random.nextInt(50) + 1; // TODO: сделать более маленькие числа для умножения

        set[3] = Solver.solve(set[1], set[2], set[0]);

        return set;
    }
}
