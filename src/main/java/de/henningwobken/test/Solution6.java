package de.henningwobken.test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings("Duplicates") // Damit mehrere Solution-Klassen nebeneinander stehen können
public class Solution6 {

    // Complete the shortPalindrome function below.
    static Map<Character, int[]> indizes = new HashMap<>();
    //    static BigInteger[] resultArr = new BigInteger[26];
    static BigInteger result = BigInteger.ZERO;
    static BigInteger modulo = BigInteger.valueOf((long) Math.pow(10, 9) + 7);
    static long moduloLong = (long) Math.pow(10, 9) + 7;
    static int threadsFinished = 0;
    static final Object lock = new Object();
    static final Object lock2 = new Object();
    static long[] possibilitiesTwo;

    // Complete the shortPalindrome function below.
    static int shortPalindrome(String s) {
        if (s.length() < 4) {
            return 0;
        }
        final long time = System.currentTimeMillis();
        final char[] letters = s.toCharArray();
        int max = buildIndizesMap(letters);
        buildPosibillitiesForTwoArray(max);
        System.out.println("Time for map and possibilities: " + (System.currentTimeMillis() - time));

        for (char a2 = 'a'; a2 <= 'z'; a2++) {
            final char a = a2;
            new Thread(() -> {

                BigInteger count = BigInteger.ZERO;
                long longCount = 0;
                final int[] aIndizes = indizes.get(a);

                if (aIndizes.length < 2) {
                    synchronized (lock) {
                        threadsFinished++;
                    }
                    if (threadsFinished == 26) {
                        synchronized (lock2) {
                            lock2.notify();
                        }
                    }

                    return;
                }
                for (char b = 'a'; b <= 'z'; b++) {
                    System.out.println("Starting " + a + ":" + b);
                    long timeThread = System.currentTimeMillis();
                    if (a == b) {
                        if (aIndizes.length < 4) {
                            continue;
                        }
                        count = count.add(possibilitiesFour(aIndizes.length));//.mod(modulo);
                        continue;
                    }
                    final int[] bIndizes = indizes.get(b);
                    if (bIndizes.length < 2) {
                        continue;
                    }
                    // Disjunkte Mengen ausschließen
                    if (aIndizes[0] > bIndizes[bIndizes.length - 1] || aIndizes[aIndizes.length - 1] < bIndizes[0]) {
                        continue;
                    }
                    System.out.println("Checks bei " + a + ":" + b + " done nach " + (System.currentTimeMillis() - timeThread));
                    timeThread = System.currentTimeMillis();

                    // Array mergen
                    final int[] numbers = new int[aIndizes.length + bIndizes.length];
                    boolean stop = false;
                    int aIndex = 0;
                    int bIndex = 0;
                    int commonIndex = 0;
                    boolean aTurn = true;
                    while (aIndizes[aIndex] > bIndizes[bIndex]) {
                        bIndex++;
                    }
                    final int bStartIndex = bIndex;
                    while (!stop) {

                        if (aIndizes[aIndex] < bIndizes[bIndex]) {
                            if (aTurn) {
                                numbers[commonIndex]++;
                            } else {
                                commonIndex++;
                                numbers[commonIndex] = 1;
                                aTurn = true;
                            }
                            aIndex++;
                            if (aIndex == aIndizes.length) {
                                stop = true;
                            }
                        } else {
                            if (aTurn) {
                                commonIndex++;
                                numbers[commonIndex] = 1;
                                aTurn = false;
                            } else {
                                numbers[commonIndex]++;
                            }
                            bIndex++;
                            if (bIndex == bIndizes.length) {
                                commonIndex++;
                                numbers[commonIndex] = aIndizes.length - aIndex;
                                stop = true;
                            }
                        }
                    }

                    System.out.println("Array merge bei " + a + ":" + b + " done nach " + (System.currentTimeMillis() - timeThread));
                    timeThread = System.currentTimeMillis();

//                    int[] newNumbers = new int[commonIndex];
////                    int oldNumbersIndex = 0;
//                    int newNumbersIndex = 0;
//                    newNumbers[0] = numbers[0];
//                    for (int oldIndex = 2; oldIndex <= commonIndex; oldIndex += 2) {
//                        if (numbers[oldIndex - 1] == 1) {
//                            newNumbers[newNumbersIndex] += numbers[oldIndex];
//                        }
//                    }
//                    while (oldNumbersIndex < commonIndex) {
//                        newNumbers[newNumbersIndex] = numbers[oldNumbersIndex];
//
//                        if (numbers[oldNumbersIndex + 1] == 1) {
//
//                        } else {
//                            newNumbersIndex++;
//                        }
//                        oldNumbersIndex+=2;
//                    }
//                    newNumbers[newNumbersIndex] = numbers[commonIndex];

                    int[] calculations = new int[bIndizes.length - bStartIndex + 1];


                    // Möglichkeiten berechnen
                    for (int a1 = 0; a1 <= commonIndex; a1 += 2) {
                        int bCount = 0;
                        final int a1Count = numbers[a1];
                        for (int a4 = a1 + 2; a4 <= commonIndex; a4 += 2) {
                            final int a4Count = numbers[a4];
                            bCount += numbers[a4 - 1];
                            calculations[bCount] += a1Count * a4Count;
                        }
                    }

                    System.out.println("Berechnung (1/2) bei " + a + ":" + b + " done nach " + (System.currentTimeMillis() - timeThread));
                    timeThread = System.currentTimeMillis();

                    for (int i = 2; i < calculations.length; i++) {
                        final int factor = calculations[i]; //* calculationsB[i];
                        if (factor == 0) {
                            continue;
                        }
                        longCount += factor * possibilitiesTwo[i];
                        if (longCount > moduloLong) {
                            longCount %= moduloLong;
                        }
                    }

                    System.out.println("Berechnung (2/2) bei " + a + ":" + b + " done nach " + (System.currentTimeMillis() - timeThread));
                }
                synchronized (lock) {
                    threadsFinished++;
                    result = result.add(count).add(BigInteger.valueOf(longCount));
                }
                if (threadsFinished == 26) {
                    synchronized (lock2) {
                        lock2.notify();
                    }
                }
            }).start();
        }

        try {
            synchronized (lock2) {
                lock2.wait(60000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result.mod(modulo).intValue();
    }

    static BigInteger possibilitiesFour(long count) {
//        if (true) {//(count > 110000) {
        BigInteger bigCount = BigInteger.valueOf(count);
        System.out.println("BIIIG Count 4");
        return bigCount
                .multiply(BigInteger.valueOf(count - 1))
                .multiply(BigInteger.valueOf(count - 2))
                .multiply(BigInteger.valueOf(count - 3))
                .divide(BigInteger.valueOf(24));
//        } else {
//            return BigInteger.valueOf((count / 24) * (count - 1) * (count - 2) * (count - 3));
//        }
    }

    static long possibilitiesTwo(long count) {
        return count * (count - 1) / 2;
    }

    static void buildPosibillitiesForTwoArray(int count) {
        possibilitiesTwo = new long[count + 1];
        for (int i = 0; i <= count; i++) {
            possibilitiesTwo[i] = possibilitiesTwo(i);
        }
    }

    static int buildIndizesMap(char[] letters) {
        int[][] arr = new int[26][letters.length];
        int[] letterIndizes = new int[26];
        for (int i = 0; i < letters.length; i++) {
            int letterNr = letters[i] - 'a';
            arr[letterNr][letterIndizes[letterNr]] = i;
            letterIndizes[letterNr]++;
        }
        int max = 0;
        for (char i = 'a'; i <= 'z'; i++) {
            int index = i - 'a';
            int length = letterIndizes[index];
            int[] newArr = new int[length];
            System.arraycopy(arr[index], 0, newArr, 0, length);
            indizes.put(i, newArr);
            if (length > max) {
                max = length;
            }
        }
        return max;
    }


    public static void main(String[] args) throws IOException {
        final Scanner scanner = new Scanner(new File("/home/henning/dev/short-palindrome/src/main/resources/test2"));
        String s = scanner.nextLine();
        scanner.close();

        final long time = System.currentTimeMillis();

        int result = shortPalindrome(s);

        System.out.println("Fertig nach " + (System.currentTimeMillis() - time));
        System.out.println(result);

    }
}
