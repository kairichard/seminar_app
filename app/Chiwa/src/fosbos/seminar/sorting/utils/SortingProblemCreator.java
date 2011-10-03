package fosbos.seminar.sorting.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Diese Klasse dient dem einfachen erstellen von sogenannten Problemsets 
 * sprich eine Reihe unsortierter Integer in einem Array.
 * Dabei sind alle methoden statisch da es für mich keinen Sinn gemacht hat
 * einen solche Instanz zuerstellen und dann weiter im Spreicher zubehalten oder
 * eben bei Bedarf neu zu Instanzieren.
 * 
 * @author Kai Richard König
 */
public class SortingProblemCreator {

    /**
     * Gibt ein zufällig sortiers Integerarrays zurück mit der
     * übergebenen länge.
     * @param size Länge des zu generierenden Integerarrays
     * @return Integerarray
     */
    public static int[] random(int size) {
        List<Integer> problemSet = new ArrayList<Integer>(size);
        Random random = new Random();
        int rnd;
        for (int i = size - 1; i >= 0; i--) {
            rnd = (int) (random.nextDouble() * 10);
            while (problemSet.contains(rnd)) {
                rnd = (int) (random.nextInt(size + 1));
            }
            problemSet.add(rnd);
        }
        return buildIntArray(problemSet);
    }

    /**
     * Liefert für fünf fälle immer das selbe Problemset zurück eben 
     * für reproduzierbare Ergebnisse
     * 
     * @param Länge des zu generierenden Integerarrays
     * @return Integerarray
     */
    public static int[] predefined(int size) {
        int[] ints;
        switch (size) {
            case 10:
                ints = new int[]{0, 2, 1, 4, 5, 8, 7, 9, 3, 6};
                break;
            case 20:
                ints = new int[]{0, 12, 2, 1, 19, 14, 17, 18, 10, 4, 16, 5, 11, 8, 7, 15, 9, 13, 3, 6};
                break;
            case 30:
                ints = new int[]{20, 0, 12, 2, 1, 29, 19, 21, 14, 17, 28, 22, 27, 18, 10, 23, 4, 16, 24, 25, 5, 26, 11, 8, 7, 15, 9, 13, 3, 6};
                break;
            case 40:
                ints = new int[]{20, 0, 12, 2, 30, 1, 29, 19, 21, 38, 31, 14, 32, 17, 28, 22, 27, 18, 10, 23, 4, 39, 16, 24, 36, 25, 37, 5, 35, 26, 11, 8, 7, 15, 9, 33, 13, 3, 34, 6};
                break;
            case 50:
                ints = new int[]{20, 0, 12, 2, 30, 45, 46, 1, 40, 29, 19, 21, 38, 31, 14, 32, 47, 17, 28, 22, 27, 43, 18, 10, 23, 48, 49, 4, 39, 44, 16, 24, 36, 25, 37, 5, 35, 26, 11, 41, 8, 7, 15, 9, 33, 13, 3, 34, 6, 42};
                break;
            default:
                throw new AssertionError();
        }
        return ints;
    }

    /**
     * Liefert für fünf fälle immer das selbe "stufige" Problemset zurück eben 
     * für reproduzierbare Ergebnisse, quasi eine spezielle Form.
     * 
     * @param Länge des zu generierenden Integerarrays
     * @return Integerarray
     */
    public static int[] flat(int size) {
        int[] ints;
        switch (size) {
            case 10:
                ints = new int[]{0, 1, 1, 1, 3, 3, 3, 2, 2, 2};
                break;
            case 20:
                ints = new int[]{0, 1, 1, 1, 1, 3, 3, 3, 3, 2, 2, 2, 2, 4, 4, 4, 4, 5, 5, 5, 5};
                break;
            case 30:
                ints = new int[]{0, 1, 1, 1, 3, 3, 3, 9, 9, 9, 7, 7, 7, 10, 10, 10, 2, 2, 2, 5, 5, 5, 6, 6, 6, 4, 4, 4, 8, 8, 8};
                break;
            case 40:
                ints = new int[]{0, 1, 1, 1, 1, 3, 3, 3, 3, 9, 9, 9, 9, 7, 7, 7, 7, 10, 10, 10, 10, 2, 2, 2, 2, 5, 5, 5, 5, 6, 6, 6, 6, 4, 4, 4, 4, 8, 8, 8, 8};
                break;
            case 50:
                ints = new int[]{0, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 9, 9, 9, 9, 9, 7, 7, 7, 7, 7, 10, 10, 10, 10, 10, 2, 2, 2, 2, 2, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 4, 4, 4, 4, 4, 8, 8, 8, 8, 8};
                break;
            default:
                throw new AssertionError();
        }
        return ints;
    }

    /**
     * Liefert für fünf fälle immer das selbe falsch herrum sortierte Array, 
     * quasi eine spezielle Form.
     * 
     * @param Länge des zu generierenden Integerarrays
     * @return Integerarray
     */
    public static int[] reverse(int size) {
        int[] ints;
        switch (size) {
            case 10:
                ints = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
                break;
            case 20:
                ints = new int[]{19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
                break;
            case 30:
                ints = new int[]{29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
                break;
            case 40:
                ints = new int[]{39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
                break;
            case 50:
                ints = new int[]{49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
                break;
            default:
                throw new AssertionError();
        }
        return ints;
    }

    /**
     * Da die Normalen Java {@code int[] } irgendwie scheiße sind werden in den Methoden 
     * Listen verwendet die dann hier in {@code int[] } umgewandlet werden.
     * @param integers Die Liste mit Integers
     * @return Integerarray
     */
    static private int[] buildIntArray(List<Integer> integers) {
        int[] ints = new int[integers.size()];
        int i = 0;
        for (Integer n : integers) {
            ints[i++] = n;
        }
        return ints;
    }
}
