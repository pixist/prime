package com.primes;

import java.util.Arrays;

public class inputTaker {

    public static void isPrimeMenuLoop(Long... numbers) {

        for (long i : numbers) {
            System.out.println("Is " + i + " prime?");
            if (PrimeDetectorRoot.isPrime(i)) {
                System.out.println(i + " is a prime!");
            } else System.out.println(i + " is not a prime!");
            System.out.print("Checked Interval: ");
            for (long[] interval : PrimeDetectorRoot.slices) {
                System.out.print(Arrays.toString(interval));
                if (interval != PrimeDetectorRoot.slices.get(PrimeDetectorRoot.slices.size() - 1)) {
                    System.out.print(",");
                } else {
                    System.out.println("\n");
                }
            }
        }
        System.out.print("\"" + PrimeDetectorRoot.primes.size() + "\" primes are found in intervals (");
        for (long[] interval : PrimeDetectorRoot.slices) {
            System.out.print(Arrays.toString(interval));
            if (interval != PrimeDetectorRoot.slices.get(PrimeDetectorRoot.slices.size() - 1)) {
                System.out.println(", ");
            } else {
                System.out.println(")");
            }
        }
    }
}