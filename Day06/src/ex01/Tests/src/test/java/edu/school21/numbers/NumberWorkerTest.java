package edu.school21.numbers;

import edu.school21.numbers.exceptions.IllegalNumberException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 11, 13, 17, 19, 23, 29, 811, 997, Integer.MAX_VALUE})
    void isPrimeTrue(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 12, 14, 15, 16, 18, 20, 22, 24, 26, 28, 812, 998, Integer.MAX_VALUE - 1})
    void isPrimeFalse(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -1, -2, -3, -4, -5, -6, -7, -8, -9, Integer.MIN_VALUE})
    void isPrimeIllegalNumberException(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", delimiter = ',')
    void digitsSum(int number, int sum) {
        NumberWorker numberWorker = new NumberWorker();
        assertEquals(sum, numberWorker.digitsSum(number));
    }

}
