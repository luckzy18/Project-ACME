package org.example.utils.generator;

import java.util.Random;

public class GenerateNumberToEightDigits {

    private static final Random RANDOM = new Random();

    public static String generate() {
        int num = RANDOM.nextInt(100_000_000);
        return String.format("%08d",num);
    }
}
