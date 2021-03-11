package com.primes;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner inputs = new Scanner(System.in);
        ArrayList<Long> numbers = new ArrayList<>();

        System.out.print("Do you want to be informed about the calculations?(true or false) ");
        boolean info = inputs.nextBoolean();

        System.out.print("Do you want to load from the previous save(if there is any)?(true or false) ");
        boolean load = inputs.nextBoolean();

        System.out.print("\nWhich number do you want to evaluate?(number) ");
        long number;

        while ((number = inputs.nextLong()) != 0) {
            numbers.add(number);
            System.out.print("Which number do you want to evaluate next?(number) (0 to exit) ");
        }
        Long[] numbersArray = numbers.toArray(new Long[0/*numbers.size()*/]);

        long start = System.nanoTime();

        PrimeDetectorRoot.prompt = info;
        if (load) dataModifier.load();
        inputTaker.isPrimeMenuLoop(numbersArray);

        long end = System.nanoTime();
        double endTime = (end - start);
        System.out.println("\nTime elapsed for the calculation = " + endTime/1000000000 + " seconds.");

        System.out.print("\nDo you want to create a save file(overwrites if already exists)?(true or false) ");
        boolean save = inputs.nextBoolean();

        if (!load && save) {
            System.out.println("This is going to erase your existing save, do you still want to create a save file?");
            save = inputs.nextBoolean();
        }
        if (save) dataModifier.save();
    }
}