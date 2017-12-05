package ru.karamoff.kawan_kawan.arithmaster;

import java.util.Random;

class Randomizer {

    private static final Random RANDOM = new Random();

    private Randomizer() {}

    private static void makeSmallSet(int[] smallSet) {
        smallSet[1] = RANDOM.nextInt(20) + 1;
        if (smallSet[1] < 6) {
            smallSet[2] = RANDOM.nextInt(20) + 1;
        } else {
            smallSet[2] = RANDOM.nextInt(6) + 1;
        }
        smallSet[3] = Solver.solve(smallSet[1], smallSet[2], smallSet[0]);
    }

    static int[] generate() {
        int[] set = new int[4]; // 0 - операция, 1-2 - цифры, 3 - результат

        set[0] = RANDOM.nextInt(4);

        if (set[0] == 2) { // более маленькие числа для умножения
            makeSmallSet(set);
            return set;
        } else {
            if (set[0] == 3) { // топ развод проверки деления нацело
                makeSmallSet(set);
                set[3] = set[1];
                set[1] = set[3] * set[2];
                return set;
            }

        }

        set[1] = RANDOM.nextInt(50) + 1;
        set[2] = RANDOM.nextInt(50) + 1;
        set[3] = Solver.solve(set[1], set[2], set[0]);

        return set;
    }
}
