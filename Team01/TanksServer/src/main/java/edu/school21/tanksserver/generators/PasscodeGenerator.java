package edu.school21.tanksserver.generators;

import java.util.HashSet;
import java.util.Set;

public class PasscodeGenerator {
    static private Set<Integer> generatedValues = new HashSet<>();

    static public int nextInt() {
        int maxValue = 10000; // Set your desired maximum value

        while (true) {
            int value = (int) (Math.random() * maxValue); // Generate a random value

            if (!generatedValues.contains(value)) {
                generatedValues.add(value);
                return value;
            }
        }
    }
}
