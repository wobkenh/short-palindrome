package de.henningwobken.test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution2 {

    // Complete the shortPalindrome function below.
//    static Map<Character, int[]> indizes = new HashMap<>();
//    static BigInteger modulo = BigInteger.valueOf((long) Math.pow(10, 9) + 7);
//
//    // Complete the shortPalindrome function below.
//    static int shortPalindrome(String s) {
//        if (s.length() < 4) {
//            return 0;
//        }
//        final char[] letters = s.toCharArray();
//        BigInteger count = BigInteger.ZERO;
//
//        int i1Pairs = 1;
//        int i4Pairs = 1;
//
//        for (char a = 'a'; a <= 'z'; a++) {
//            final int[] aIndizes = getIndizes(letters, a);
//            if (aIndizes.length < 2) {
//                continue;
//            }
//            for (char b = 'a'; b <= 'z'; b++) {
//                if (a == b) {
//                    if (aIndizes.length < 4) {
//                        continue;
//                    }
//                    count = count.add(possibilitiesFour(BigInteger.valueOf(aIndizes.length))).mod(modulo);
//                    continue;
//                }
//                final int[] bIndizes = getIndizes(letters, b);
//                if (bIndizes.length < 2) {
//                    continue;
//                }
//                // Disjunkte Mengen ausschließen
//                if (aIndizes[0] > bIndizes[bIndizes.length - 1] || aIndizes[aIndizes.length - 1] < bIndizes[0]) {
//                    continue;
//                }
//                int i1;
//                int i4;
//                for (i1 = 0; i1 < aIndizes.length - 1; i1++) {
//                    if (i1 < aIndizes.length - 2 && aIndizes[i1] + 1 == aIndizes[i1 + 1]) {
//                        // Wenn der nächste Eintrag der gleiche Buchstabe ist, diesen überspringen
//                        i1Pairs++;
//                        continue;
//                    }
//                    // Anfang = aIndizes[i1]
//                    // Ende = aIndizes[i4]
//                    // Suche alle Einträge in bIndizes dazwischen
//                    int start = 0;
//                    for (; start < bIndizes.length; start++) {
//                        if (aIndizes[i1] < bIndizes[start]) {
//                            break;
//                        }
//                    }
//                    int lastEnd = 0;
//                    for (i4 = i1 + 1; i4 < aIndizes.length; i4++) {
//                        if (i4 < aIndizes.length - 1 && aIndizes[i4] + 1 == aIndizes[i4 + 1]) {
//                            // Wenn der nächste Eintrag der gleiche Buchstabe ist, diesen überspringen
//                            i4Pairs++;
//                            continue;
//                        }
//
//                        int end = 0;
//                        if (lastEnd == 0) {
//                            end = bIndizes.length - 1;
//                            for (; end >= 0; end--) {
//                                if (aIndizes[i4] > bIndizes[end]) {
//                                    break;
//                                }
//                            }
//                        } else {
//                            end = lastEnd;
//                            for (; end < bIndizes.length; end++) {
//                                if (end + 1 == bIndizes.length || bIndizes[end + 1] > aIndizes[i4]) {
//                                    break;
//                                }
//                            }
//                        }
//
//                        if (start >= end) {
//                            i4Pairs = 1;
//                            continue;
//                        }
//                        lastEnd = end;
//                        count = count
//                                .add(possibilitiesTwo(BigInteger.valueOf(end - start + 1)).multiply(BigInteger.valueOf(i4Pairs * i1Pairs)))
//                                .mod(modulo);
//                        i4Pairs = 1;
//                    }
//                    i1Pairs = 1;
//                }
//            }
//        }
//
//        return count.intValue();
//    }
//
//    static BigInteger possibilitiesFour(BigInteger count) {
//        return count
//                .multiply(count.subtract(BigInteger.ONE))
//                .multiply(count.subtract(BigInteger.valueOf(2)))
//                .multiply(count.subtract(BigInteger.valueOf(3)))
//                .divide(BigInteger.valueOf(24));
//    }
//
//    static BigInteger possibilitiesTwo(BigInteger count) {
//        return count
//                .multiply(count.subtract(BigInteger.ONE))
//                .divide(BigInteger.valueOf(2));
//    }
//
//    static int[] getIndizes(char[] letters, char letter) {
//        final int[] prepArr = indizes.get(letter);
//        if (prepArr != null) {
//            return prepArr;
//        }
//        int[] arr = new int[letters.length];
//        int i = 0;
//        for (int j = 0; j < letters.length; j++) {
//            if (letters[j] == letter) {
//                arr[i] = j;
//                i++;
//            }
//        }
//        int[] newArr = new int[i];
//        System.arraycopy(arr, 0, newArr, 0, i);
//        indizes.put(letter, newArr);
//        return newArr;
//    }
//
//
//    public static void main(String[] args) throws IOException {
//        final Scanner scanner = new Scanner(new File("/home/henning/dev/test/resources/test2"));
//        String s = scanner.nextLine();
//
//        int result = shortPalindrome(s);
//
//        System.out.println(result);
//
//        scanner.close();
//    }
}
