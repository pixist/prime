package com.primes;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.Comparator;
//slices.sort(Comparator.comparingInt(o -> o[0]));

public class PrimeDetectorRoot {

    public static ArrayList<Long> primes = new ArrayList<>();
    public static ArrayList<long[]> slices = new ArrayList<>();
    public static boolean prompt = false;

    static {
        System.out.println("\n*******************************\nInitializing primes and slices!" +
                "\n*******************************\n");
        primes.add((long)2);
        slices.add(new long[]{1, 2});
    }

    public static boolean isPrime(long x) {

        if (x < 2) {
            if (prompt) System.out.println(x + " is not eligible for being a prime number.");
            return false;
        } else if (x == 2) {
            if (prompt) System.out.println((x + " is the smallest prime number."));
            return true;
        } else if (x % 2 == 0) {
            if (prompt) System.out.println(x + " is an even number and is not 2.");
            return false;
        }

        if (primes.contains(x)) {
            if (prompt) System.out.println(x + " is a prime in primes list.");
            return true;
        }

        long xSqrt = (long)Math.sqrt(x);

        for (long i : primes) {
            if (xSqrt >= i && x % i == 0) {
                if (prompt) System.out.println(x + " can be divided by the prime " + i + " in primes list.");
                return false;
            }
        }

        long upperBound = -1;
        long lowerBound;
        int primesSizeCopy;
        ArrayList<long[]> slicesCopy = (ArrayList<long[]>) slices.clone();

        for (int j = 0; upperBound != xSqrt && j < slicesCopy.size(); j++) {

            if (j + 1 == slicesCopy.size() || slicesCopy.get(j + 1)[0] > xSqrt) {
                upperBound = xSqrt;
            } else {
                upperBound = slicesCopy.get(j + 1)[0] - 1;
            }
            lowerBound = slicesCopy.get(j)[1] + 1;

            if (prompt) System.out.println("Checking interval: [" + lowerBound + ", " + upperBound + "]");

            for (long i = lowerBound; i <= upperBound; i++) {

                if (prompt) System.out.println("Checking " + i);

                primesSizeCopy = primes.size();

                if (PrimeDetectorRoot.isPrime(i)) {
                    if (x % i == 0) {
                        if (prompt) System.out.println("Number is not prime, checking is stopped at " + i +
                                "! Adding interval: [" + (lowerBound + 1) + ", " + i + "]");
                        PrimeDetectorRoot.sliceComputer(new long[]{lowerBound, i});
                        return false;
                    }
                    if (primesSizeCopy != primes.size()) {
                        if (prompt) System.out.println("New prime " + i + " found during the prime check for " + x +
                                "! Interval [" + (lowerBound + 1) + ", " + i +
                                "] is being added to the checked intervals");
                        PrimeDetectorRoot.sliceComputer(new long[]{lowerBound, i});
                        lowerBound = i + 1;
                    }
                }
            }

            if (upperBound >= lowerBound) {
                if (prompt) System.out.println("Interval [" + lowerBound + ", " + upperBound +
                        "] is checked, adding to the list!");
                PrimeDetectorRoot.sliceComputer(new long[]{lowerBound, upperBound});
            }
        }
        primes.add(x);
        if (prompt) System.out.println(x +
                " cannot be divided by the primes in the checked intervals! New prime is found.");
        PrimeDetectorRoot.sliceComputer(new long[]{x, x});
        Collections.sort(primes);

        return true;
    }

    public static void sliceComputer(long[] slice) {
        boolean change = false;
        long[] oldSlice;
        ArrayList<Integer> indexes = new ArrayList<>();
        int index = 0;
        int size = slices.size();

        for (int i = 0;  i < size; i++) {
            if (!change && index + 1 >= i) index = i;
            if (index != i) slice = slices.get(i);
            oldSlice = slices.get(index);
            change = false;

            if (oldSlice[0] >= slice[0] && oldSlice[0] <= slice[1]) {
                oldSlice[0] = slice[0];
                change = true;
            }
            if (oldSlice[1] <= slice[1] && slice[0] <= oldSlice[1]) {
                oldSlice[1] = slice[1];
                change = true;
            }
            if (oldSlice[0] <= slice[0] && slice[1] <= oldSlice[1]) {
                change = true;
            }
            if (oldSlice[1] + 1 == slice[0]) {
                oldSlice[1] = slice[1];
                change = true;
            } else if (oldSlice[0] - 1 == slice[1]) {
                oldSlice[0] = slice[0];
                change = true;
            }
            if (change && index != i) indexes.add(i);
        }
        if (!change && index == size - 1 && slice[0] <= slice[1]) slices.add(slice);

        Collections.reverse(indexes);
        for (int j : indexes) slices.remove(j);
    }
}