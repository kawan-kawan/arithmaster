package ru.karamoff.kawan_kawan.arithmaster;

import java.util.Random;

class Randomizer {

    private final static Random RANDOM = new Random();


    private static int[] setSmallNumber(int setS[]) {
        setS[1] = RANDOM.nextInt(20) + 1;
        if (setS[1] < 6) {
            setS[2] = RANDOM.nextInt(20) + 1;
        } else {
            setS[2] = RANDOM.nextInt(6) + 1;
        }
        setS[3] = Solver.solve(setS[1], setS[2], setS[0]);
        return setS;
    }

    static int[] generate() {
        int[] set = new int[4]; // 0 - операция, 1-2 - цифры, 3 - результат

        set[0] = RANDOM.nextInt(4);

        if (set[0] == 2) { // более маленькие числа для умножения
            set = setSmallNumber(set);
            return set;
        } else {
            if (set[0] == 3) { // топ развод проверки деления нацело
                set = setSmallNumber(set);
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
