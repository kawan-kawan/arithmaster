package ru.karamoff.kawan_kawan.arithmaster;

class Solver {
    static int solve(int number1, int number2, int operation) {
        switch (operation) {
            case 0:
                return number1 + number2;
            case 1:
                return number1 - number2;
            case 2:
                return number1 * number2;
            case 3:
                return number1 / number2;
            default:
                return number1 + number2;
        }
    }
}
