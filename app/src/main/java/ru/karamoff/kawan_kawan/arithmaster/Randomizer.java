package ru.karamoff.kawan_kawan.arithmaster;

/**
 * Created by nick on 14.11.17.
 */
import java.util.Random;

public class Randomizer {
    private static int operation;
    private static int number;
    static Random random = new Random();

    static int generateOperation() {
        operation = random.nextInt(3); //без деления
        return operation;
    }

    static int generateNumber() {
        number = random.nextInt(50) + 1;
        return number;
    }
}
