package de.henningwobken.test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution5 {

    // Complete the shortPalindrome function below.
//    static Map<Character, int[]> indizes = new HashMap<>();
//    //    static BigInteger[] resultArr = new BigInteger[26];
//    static BigInteger result = BigInteger.ZERO;
//    static BigInteger modulo = BigInteger.valueOf((long) Math.pow(10, 9) + 7);
//    static long moduloLong = (long) Math.pow(10, 9) + 7;
//    static int threadsFinished = 0;
//    static final Object lock = new Object();
//    static final Object lock2 = new Object();
//    static long[] possibilitiesTwo;
//
//    // Complete the shortPalindrome function below.
//    static int shortPalindrome(String s) {
//        if (s.length() < 4) {
//            return 0;
//        }
//        final long time = System.currentTimeMillis();
//        final char[] letters = s.toCharArray();
//        int max = buildIndizesMap(letters);
//        buildPosibillitiesForTwoArray(max);
//        System.out.println("Time for map and possibilities: " + (System.currentTimeMillis() - time));
//
//        for (char a2 = 'a'; a2 <= 'z'; a2++) {
//            final char a = a2;
//            new Thread(() -> {
//
//                BigInteger count = BigInteger.ZERO;
//                long longCount = 0;
//                int i1Pairs = 1;
//                int i4Pairs = 1;
//                final int[] aIndizes = indizes.get(a);
//
//                if (aIndizes.length < 2) {
//                    synchronized (lock) {
//                        threadsFinished++;
//                    }
//                    if (threadsFinished == 26) {
//                        synchronized (lock2) {
//                            lock2.notify();
//                        }
//                    }
//
//                    return;
//                }
//                for (char b = 'a'; b <= 'z'; b++) {
//                    System.out.println(a + ":" + b + " - count - " + count.toString());
//                    System.out.println(a + ":" + b + " - long count - " + longCount);
//                    if (a == b) {
//                        if (aIndizes.length < 4) {
//                            continue;
//                        }
//                        count = count.add(possibilitiesFour(aIndizes.length));//.mod(modulo);
//                        continue;
//                    }
//                    final int[] bIndizes = indizes.get(b);
//                    if (bIndizes.length < 2) {
//                        continue;
//                    }
//                    // Disjunkte Mengen ausschließen
//                    if (aIndizes[0] > bIndizes[bIndizes.length - 1] || aIndizes[aIndizes.length - 1] < bIndizes[0]) {
//                        continue;
//                    }
//                    int i1;
//                    int i4;
//                    int lastStart = 0;
//                    for (i1 = 0; i1 < aIndizes.length - 1; i1++) {
//                        final int a1 = aIndizes[i1];
//                        final int a1p1 = aIndizes[i1 + 1];
//                        if (i1 < aIndizes.length - 2 && (a1p1 < lastStart || a1 + 1 == a1p1)) {
//                            // Wenn der nächste Eintrag der gleiche Buchstabe ist, diesen überspringen
//                            // Wenn dar nächste Eintrag noch vor dem ersten B vom letzen mal liegt
//                            i1Pairs++;
//                            continue;
//                        }
//                        // Anfang = aIndizes[i1]
//                        // Ende = aIndizes[i4]
//                        // Suche alle Einträge in bIndizes dazwischen
//                        int start = lastStart;
//                        for (; start < bIndizes.length; start++) {
//                            if (a1 < bIndizes[start]) {
//                                break;
//                            }
//                        }
//                        lastStart = start;
//                        int lastEnd = 0;
//                        for (i4 = i1 + 1; i4 < aIndizes.length; i4++) {
//                            final int a4 = aIndizes[i4];
//                            if (i4 < aIndizes.length - 1 && (lastEnd == (bIndizes.length - 1) ||
//                                    (lastEnd > 0 && aIndizes[i4 + 1] < bIndizes[lastEnd + 1]) || a4 + 1 == aIndizes[i4 + 1])) {
//                                // Wenn der nächste Eintrag der gleiche Buchstabe ist, diesen überspringen
//                                i4Pairs++;
//                                continue;
//                            }
//
//                            int end = lastEnd;
//                            for (; end < bIndizes.length; end++) {
//                                if (end + 1 == bIndizes.length || bIndizes[end + 1] > a4) {
//                                    break;
//                                }
//                            }
////                            }
//
//                            if (start >= end) {
//                                i4Pairs = 1;
//                                continue;
//                            }
//                            lastEnd = end;
////                            longCount += (i4Pairs * i1Pairs) * possibilitiesTwo[(end - start + 1)];
////                            if (longCount > moduloLong) {
////                                longCount %= moduloLong;
////                            }
//                            i4Pairs = 1;
//                        }
//                        i1Pairs = 1;
//                    }
//                }
//                synchronized (lock) {
//                    threadsFinished++;
//                    result = result.add(count).add(BigInteger.valueOf(longCount));
//                }
//                if (threadsFinished == 26) {
//                    synchronized (lock2) {
//                        lock2.notify();
//                    }
//                }
//            }).start();
//        }
//
//        try {
//            synchronized (lock2) {
//                lock2.wait(60000);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return result.mod(modulo).intValue();
//    }
//
//    static BigInteger possibilitiesFour(long count) {
////        if (true) {//(count > 110000) {
//        BigInteger bigCount = BigInteger.valueOf(count);
//        System.out.println("BIIIG Count 4");
//        return bigCount
//                .multiply(BigInteger.valueOf(count - 1))
//                .multiply(BigInteger.valueOf(count - 2))
//                .multiply(BigInteger.valueOf(count - 3))
//                .divide(BigInteger.valueOf(24));
////        } else {
////            return BigInteger.valueOf((count / 24) * (count - 1) * (count - 2) * (count - 3));
////        }
//    }
//
//    static long possibilitiesTwo(long count) {
//        return count * (count - 1) / 2;
//    }
//
//    static void buildPosibillitiesForTwoArray(int count) {
//        possibilitiesTwo = new long[count + 1];
//        for (int i = 0; i <= count; i++) {
//            possibilitiesTwo[i] = possibilitiesTwo(i);
//        }
//    }
//
//    static int buildIndizesMap(char[] letters) {
//        int[][] arr = new int[26][letters.length];
//        int[] letterIndizes = new int[26];
//        for (int i = 0; i < letters.length; i++) {
//            int letterNr = letters[i] - 'a';
//            arr[letterNr][letterIndizes[letterNr]] = i;
//            letterIndizes[letterNr]++;
//        }
//        int max = 0;
//        for (char i = 'a'; i <= 'z'; i++) {
//            int index = i - 'a';
//            int length = letterIndizes[index];
//            int[] newArr = new int[length];
//            System.arraycopy(arr[index], 0, newArr, 0, length);
//            indizes.put(i, newArr);
//            if (length > max) {
//                max = length;
//            }
//        }
//        return max;
//    }
//
//
//    public static void main(String[] args) throws IOException {
//        final Scanner scanner = new Scanner(new File("/home/henning/dev/test/src/main/resources/test2"));
//        String s = scanner.nextLine();
//        scanner.close();
//
//        final long time = System.currentTimeMillis();
//
//        int result = shortPalindrome(s);
//
//        System.out.println("Fertig nach " + (System.currentTimeMillis() - time));
//        System.out.println(result);
//
//    }
}
