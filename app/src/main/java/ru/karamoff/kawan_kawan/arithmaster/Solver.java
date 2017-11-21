package ru.karamoff.kawan_kawan.arithmaster;

/**
 * Created by nick on 14.11.17.
 */

public class Solver {
    static int solveForClassic(int a, int b, int operation) {
        switch (operation) {
            case 0:
                return a + b;
            case 1:
                return a - b;
            case 2:
                return a * b;
            case 3:
                return a / b;
            default:
                return a + b;
        }
    }
}
