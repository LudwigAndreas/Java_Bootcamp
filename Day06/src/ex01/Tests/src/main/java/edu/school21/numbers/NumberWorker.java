package edu.school21.numbers;

import edu.school21.numbers.exceptions.IllegalNumberException;

public class NumberWorker {
    public boolean isPrime(int number) throws IllegalNumberException {
          if (number <= 1)
                throw new IllegalNumberException("Number must be greater than 1");
            for (int i = 2; i < number; i++)
                if (number % i == 0)
                    return false;
            return true;
    }

    public int digitsSum(int number) {
          int sum = 0;
            while (number != 0) {
                sum += (number % 10);
                number /= 10;
            }
            return sum;
    }


}
