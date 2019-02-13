package de.henningwobken.test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings("Duplicates") // Damit mehrere Solution-Klassen nebeneinander stehen können
public class Solution {

    // Complete the shortPalindrome function below.
    static Map<Character, int[]> indizes = new HashMap<>();
    static BigInteger result = BigInteger.ZERO;
    static BigInteger modulo = BigInteger.valueOf((long) Math.pow(10, 9) + 7);
    static long moduloLong = (long) Math.pow(10, 9) + 7;
    static int threadsFinished = 0;
    static final Object lock = new Object();
    static final Object lock2 = new Object();

    // Complete the shortPalindrome function below.
    static int shortPalindrome(String s) {
        if (s.length() < 4) {
            return 0;
        }
//        final long time = System.currentTimeMillis();
        final char[] letters = s.toCharArray();
        buildIndizesMap(letters);
//        System.out.println("Time for map and possibilities: " + (System.currentTimeMillis() - time));

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
//                    System.out.println("Starting " + a + ":" + b);
//                    long timeThread = System.currentTimeMillis();
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
//                    System.out.println("Checks bei " + a + ":" + b + " done nach " + (System.currentTimeMillis() - timeThread));
//                    timeThread = System.currentTimeMillis();

                    // Array mergen

                    boolean stop = false;
                    int aIndex = 0;
                    int bIndex = 0;
                    int commonIndex = 0;
                    while (aIndizes[aIndex] > bIndizes[bIndex]) {
                        bIndex++;
                    }
                    final boolean[] sequence = new boolean[aIndizes.length + bIndizes.length - bIndex];
                    int stopIndex = sequence.length;
                    while (!stop) {

                        if (aIndizes[aIndex] < bIndizes[bIndex]) {
                            sequence[commonIndex] = true;
                            commonIndex++;
                            aIndex++;
                            if (aIndex == aIndizes.length) {
                                stop = true;
                                stopIndex = commonIndex;
                            }
                        } else {
                            sequence[commonIndex] = false;
                            commonIndex++;
                            bIndex++;
                            if (bIndex == bIndizes.length) {
                                for (; commonIndex < sequence.length; commonIndex++) {
                                    sequence[commonIndex] = true;
                                }
                                stop = true;
                            }
                        }
                    }

                    // Array enthält jetzt true für a und false für b,
                    // z.B. [true, false, false, true, false] bei der Zeichenkette "abbab"

//                    System.out.println("Array merge bei " + a + ":" + b + " done nach " + (System.currentTimeMillis() - timeThread));
//                    timeThread = System.currentTimeMillis();

                    // Lookup-Table aufbauen

                    boolean[] subSequence = {true, false, false, true}; // abba
                    long[][] possibilities = new long[sequence.length + 1][subSequence.length + 1];
                    for (int i = 0; i <= subSequence.length; i++) { // In der ersten Zeile alles auf 0 setzen
                        possibilities[0][i] = 0;
                    }
                    for (int i = 0; i <= sequence.length; i++) { // In der ersten Spalte alles auf 1 setzen
                        possibilities[i][0] = 1;
                    }

                    // Ergebnis des oberen Codes für aabbaa in der unteren Tabelle.
                    // Nicht gefüllte Zellen wurden noch nicht berührt.
                    // Jede Zelle gibt an, wie oft der String aus dem Spaltentitel im String aus dem Zeilentitel vorkommt
                    // Die Booleans in den Titelzellen werden aus Performancegründen verwendet.
                    // Für die Berechnung wird nur der Anfangsbuchstabe benötigt (true = a, false = b)
                    // Die dazugehörigen "Strings" der Titelzellen können wie folgt ermittelt werden:
                    // Der Index jeder Spalte/Zeile bezeichnet die Start bzw. Offsetposition in der
                    // - Sequence --> Zeilenindex (y-Achse) (Input-String, z.B. aabbaa): (1) bedeutet a, (2) bedeutet aa usw.
                    // - Subsequence --> Spaltenindex (x-Achse) (Palindrome, z.B. abba): (1) bedeutet a, (2) bedeutet ab usw.
                    // Index 0 repräsentiert jeweils einen leeren String
                    /*
                      | x | 1 | 0 | 0 | 1
                    ------------------------
                    x | 1 | 0 | 0 | 0 | 0
                    1 | 1 |
                    1 | 1 |
                    0 | 1 |
                    0 | 1 |
                    1 | 1 |
                    1 | 1 |
                     */
                    // Die erste Zeile wird mit 0 gefüllt, weil in einem leeren String kein nicht-leerer String vorkommen kann
                    // Die erste Spalte wird mit 1 gefüllt, weil ein leerer String in der Subsequence bedeutet, dass ein Match gefunden wurde

                    // Der Algorithmus kann gut verstanden werden, wenn man einmal verkehrt herum auf die Aufgabe blickt:
                    // Es sollen die Möglichkeiten gefunden werden, die Subsequence in der Sequence zu bilden.
                    // Man nehme also die Subsequence und vergleiche sie mit der Sequence. Beispiel mit Sequence aabbaa und Subsequence abba:
                    // Anfangsbuchstaben bei aabbaa und abba vergleichen --> stimmt überein
                    // Das bedeutet, dass nach der Zeichenkombination bba im übrigen String abbaa gesucht werden muss, um einen Match zu finden
                    // Gleichzeitig kann im übrigen String abba auch noch nach der vollständigen Subsequence gesucht werden, also abba.
                    // Das bedeutet, dass das Problem (abba in aabbaa) jetzt aufgeteilt wurde in die Subprobleme (bba in abbaa) und (abba in abbaa)
                    // Jedes angefangene Problem wird weiter durchgegangen und aufgeteilt, bis entweder die Sequence oder die Subsequence leer ist
                    // Spinnt man dieses Vorgehen weiter, so werden bestimmte Probleme, z.B. (ba in baa) häufiger vorkommen und daher auch doppelt berechnet.
                    // Um dieses zu verhindern, werden alle Probleme vorher berechnet. Die Subprobleme werden aufeinander aufbauend gelöst,
                    // bis man wieder beim Ursprungsproblem angekommen ist.

                    for (int i = 1; i <= stopIndex; i++) { // Startindex = 1, da erste Zeile + Spalte bereits belegt, s.o.
                        for (int j = 1; j <= subSequence.length; j++) {
                            // In einer kleineren Sequence kommt die aktuell betrachtete Subsequence mindestens so häufig
                            // vor wie bei der vorherigen sequence
                            possibilities[i][j] = possibilities[i - 1][j];
                            if (sequence[i - 1] == subSequence[j - 1]) { // Buchstaben stimmen überein
                                possibilities[i][j] += possibilities[i - 1][j - 1];
//                                possibilities[i][j] %= moduloLong;
                            }
                        }
                    }

//                    System.out.println("Lookup-Table bei " + a + ":" + b + " done nach " + (System.currentTimeMillis() - timeThread));
                    longCount += possibilities[stopIndex][subSequence.length];
                    longCount = longCount % moduloLong;
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
        BigInteger bigCount = BigInteger.valueOf(count);
        return bigCount
                .multiply(BigInteger.valueOf(count - 1))
                .multiply(BigInteger.valueOf(count - 2))
                .multiply(BigInteger.valueOf(count - 3))
                .divide(BigInteger.valueOf(24));
    }


    static void buildIndizesMap(char[] letters) {
        int[][] arr = new int[26][letters.length];
        int[] letterIndizes = new int[26];
        for (int i = 0; i < letters.length; i++) {
            int letterNr = letters[i] - 'a';
            arr[letterNr][letterIndizes[letterNr]] = i;
            letterIndizes[letterNr]++;
        }
        for (char i = 'a'; i <= 'z'; i++) {
            int index = i - 'a';
            int length = letterIndizes[index];
            int[] newArr = new int[length];
            System.arraycopy(arr[index], 0, newArr, 0, length);
            indizes.put(i, newArr);
        }
    }


    public static void main(String[] args) throws IOException {
        final Scanner scanner = new Scanner(new File("/home/henning/dev/short-palindrome/src/main/resources/test6"));
        String s = scanner.nextLine();
        scanner.close();

        final long time = System.currentTimeMillis();

        int result = shortPalindrome(s);

        System.out.println("Fertig nach " + (System.currentTimeMillis() - time));
        System.out.println(result);

    }
}
