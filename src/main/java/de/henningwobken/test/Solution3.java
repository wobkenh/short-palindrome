package de.henningwobken.test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Solution3 {

    // Complete the shortPalindrome function below.
    static int shortPalindrome(String s) {
        final char[] letters = s.toCharArray();
        final int[][][] matrix = new int[26][26][]; // Beinhaltet die Arrays mit den Buchstabencounts
        final int[][] indexMatrix = new int[26][26]; // Bezeichnet die nächste Position im Array
        for (int i = 0; i < letters.length; i++) {
            int indexA = letters[i] - 'a';
            for (int indexB = 0; indexB < 26; indexB++) {
                // Wenn leer, bei eigenen Arrays des Chars den Anfang setzen
                if (matrix[indexA][indexB] == null) {
                    initCombination(matrix, indexMatrix, indexA, indexB);
                }

                // Ungerade Längen, selber erster
                // Gerade Länge, selber zweiter
                // => Increment
                // Gerade Länge, selber erster
                // Ungerade Länge, selber zweiter
                // => Neu
                /*
                        Ungerade | Gerade
                   ------------------------
                   1.     INC    |   NEW
                   2.     NEW    |   INC
                 */

                // 1.
                if (indexMatrix[indexA][indexB] % 2 == 0) { // Gerade
                    newPosition(matrix, indexMatrix, indexA, indexB);
                } else { // Ungerade
                    increment(matrix, indexMatrix, indexA, indexB);
                }

                // 2.
                if (matrix[indexB][indexA] != null && indexA != indexB) {
                    if (indexMatrix[indexB][indexA] % 2 == 0) { // Gerade
                        increment(matrix, indexMatrix, indexB, indexA);
                    } else { // Ungerade
                        newPosition(matrix, indexMatrix, indexB, indexA);
                    }
                }
            }
        }

        BigInteger result = BigInteger.ZERO;
        for (int indexA = 0; indexA < 26; indexA++) {
            for (int indexB = 0; indexB < 26; indexB++) {
                if (matrix[indexA][indexB] == null) {
                    continue;
                }
                if (indexA == indexB) {
                    result = result.add(possibilitiesFour(BigInteger.valueOf(matrix[indexA][indexB][0])));
                    continue;
                }
                for (int i = 2; i < matrix[indexA][indexB].length; i += 2) {
                    if (matrix[indexA][indexB][i] == 0) {
                        break;
                    }
                    int start = i - 2;
                    int bCount = 0;
                    while (start >= 0) {
                        int factor = matrix[indexA][indexB][start] * matrix[indexA][indexB][i];
                        int middleCount = matrix[indexA][indexB][i - 1] + bCount;
                        if (middleCount >= 2) {
                            result = result.add(possibilitiesTwo(BigInteger.valueOf(middleCount)).multiply(BigInteger.valueOf(factor)));
                        }
                        bCount = middleCount;
                        start -= 2;
                    }
                }
            }
        }
        return result.mod(BigInteger.valueOf((long) Math.pow(10, 9) + 7)).intValue();
    }

    static BigInteger possibilitiesTwo(BigInteger count) {
        return count
                .multiply(count.subtract(BigInteger.ONE))
                .divide(BigInteger.valueOf(2));
    }

    static BigInteger possibilitiesFour(BigInteger count) {
        return count
                .multiply(count.subtract(BigInteger.ONE))
                .multiply(count.subtract(BigInteger.valueOf(2)))
                .multiply(count.subtract(BigInteger.valueOf(3)))
                .divide(BigInteger.valueOf(24));
    }

    public static void initCombination(int[][][] charMatrix, int[][] indexMatrix, int indexA, int indexB) {
        charMatrix[indexA][indexB] = new int[10];
//        charMatrix[indexA][indexB][0] = 1;
//        indexMatrix[indexA][indexB] = 1;
    }

    public static void newPosition(int[][][] charMatrix, int[][] indexMatrix, int indexA, int indexB) {
        int indexInArray = indexMatrix[indexA][indexB];
        if (indexInArray == charMatrix[indexA][indexB].length) {
            int[] oldArrReference = charMatrix[indexA][indexB];
            charMatrix[indexA][indexB] = new int[oldArrReference.length * 2];
            System.arraycopy(oldArrReference, 0, charMatrix[indexA][indexB], 0, oldArrReference.length);
        }
        charMatrix[indexA][indexB][indexInArray] = 1;
        indexMatrix[indexA][indexB]++;
    }

    public static void increment(int[][][] charMatrix, int[][] indexMatrix, int indexA, int indexB) {
        int indexInArray = indexMatrix[indexA][indexB];
        charMatrix[indexA][indexB][indexInArray - 1]++;
    }


    public static void main(String[] args) throws IOException {
        final Scanner scanner = new Scanner(new File("/home/henning/dev/test/resources/test"));
        String s = scanner.nextLine();

        final long time = System.currentTimeMillis();

        int result = shortPalindrome(s);

        System.out.println("Fertig nach " + (System.currentTimeMillis() - time));

        System.out.println(result);

        scanner.close();
    }
}
