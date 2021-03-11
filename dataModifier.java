package com.primes;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class dataModifier {

    public static void save() throws IOException {
        Writer primesWr = new FileWriter("primes.txt", false);
        Writer intervalWr = new FileWriter("interval.txt", false);

        for (long prime : PrimeDetectorRoot.primes) {
            primesWr.write(prime + "\t");
        }
        primesWr.close();

        for (long[] slice : PrimeDetectorRoot.slices) {
            for (long i : slice) {
                intervalWr.write(i + "\t");
            }
            intervalWr.write("\n");
        }
        intervalWr.close();
    }
    public static void load() throws  IOException {
        PrimeDetectorRoot.primes.remove(2L);
        BufferedReader primesIn = new BufferedReader(new FileReader(new File("primes.txt")));
        while (primesIn.ready()) {
            /*
            String line = primesIn.readLine();
            int pos = 2, end;
            while ((end = line.indexOf('\t', pos)) >= 0) {
                PrimeDetectorRoot.primes.add(Long.parseLong(line.substring(pos, end)));
                pos = end + 1;
             */

            StringTokenizer primeToken = new StringTokenizer(primesIn.readLine(), "\t");
            while (primeToken.hasMoreTokens()) {
                PrimeDetectorRoot.primes.add(Long.parseLong(primeToken.nextToken()));
            }


        }
        primesIn.close();

        BufferedReader intervalIn = new BufferedReader(new FileReader(new File("interval.txt")));
        PrimeDetectorRoot.slices.remove(new long[]{1, 2});
        while (intervalIn.ready()) {
            String[] intervalToken = intervalIn.readLine().split("\t");
            PrimeDetectorRoot.sliceComputer(new long[]{Long.parseLong(intervalToken[0]),
                    Long.parseLong(intervalToken[1])});
        }
        intervalIn.close();

        if (PrimeDetectorRoot.prompt) {
            String endPrompt = "\"" + PrimeDetectorRoot.primes.size() + "\" primes are loaded in interval(s): ";
            for (long[] interval : PrimeDetectorRoot.slices) {
                endPrompt = endPrompt.concat(Arrays.toString(interval));
                if (interval != PrimeDetectorRoot.slices.get(PrimeDetectorRoot.slices.size() - 1)) {
                    endPrompt = endPrompt.concat(", ");
                } else {
                    endPrompt = endPrompt.concat(")");
                }
            }
            endPrompt = endPrompt.concat(" from the save file!");
            String stars = new String(new char[endPrompt.length()]).replace("\0", "*");
            System.out.print(stars + "\n" + endPrompt + "\n" + stars + "\n\n");
        }
    }
}
