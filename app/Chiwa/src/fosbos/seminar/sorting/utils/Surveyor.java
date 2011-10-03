/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fosbos.seminar.sorting.utils;

import java.util.Arrays;

/**
 *
 * @author kai
 */
public class Surveyor {    
        static public double levelOfSortedness(int[] items) {
        // Find the sorted positions
        int n = items.length;
        int[] sorted = new int[n];
        boolean[] used = new boolean[n];
        int[] sortedItems = items.clone();
        Arrays.sort(sortedItems);
        for (int i = 0; i < n; i++) {
            int key = items[i];
            int pos = Arrays.binarySearch(sortedItems, key);
            while (pos > 0 && sortedItems[pos - 1] == key) {
                pos--;
            }
            while (pos + 1 < n && used[pos] && sortedItems[pos + 1] == key) {
                pos++;
            }
            used[pos] = true;
            sorted[i] = pos;
        }

        // Sum up the distances
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += Math.abs(sorted[i] - i);
        }

        // Calculate the maximum
        int maximum;
        if (n % 2 == 0) { // Even
            maximum = n * n / 2;
        } else { // Odd
            maximum = (n * n - 1) / 2;
        }

        // Return the ratio
        double r = (double) sum / maximum;
        return (r - 1.0) * - 1.0 ;
    }    
}
