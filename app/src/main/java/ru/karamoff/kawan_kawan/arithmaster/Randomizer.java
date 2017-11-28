package ru.karamoff.kawan_kawan.arithmaster;

import java.util.Random;

class Randomizer {

    private static Random random = new Random();


    static void setSmallNumber(int setS[]) {
        setS[1] = random.nextInt(20) + 1;
        if (setS[1] < 6) {
            setS[2] = random.nextInt(20) + 1;
        } else {
            setS[2] = random.nextInt(6) + 1;
        }
        setS[3] = Solver.solve(setS[1], setS[2], setS[0]);
    }

    static int[] generate() {
            int[] set = new int[4]; // 0 - операция, 1-2 - цифры, 3 - результат

        set[0] = random.nextInt(4);

        if(set[0] == 2) { // более маленькие числа для умножения
            setSmallNumber(set);
            return set;
        } else {
            if(set[0] == 3) { // топ развод проверки деления нацело
                setSmallNumber(set);
                set[3] = set[1];
                set[1] = set[3] * set[2];
                return set;
            }

        }

        set[1] = random.nextInt(50) + 1;
        set[2] = random.nextInt(50) + 1;
        set[3] = Solver.solve(set[1], set[2], set[0]);

        return set;
    }
}
