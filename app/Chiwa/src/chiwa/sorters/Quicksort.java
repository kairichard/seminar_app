/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chiwa.sorters;

import fosbos.seminar.sorting.AbstractSortingMechanics;
import fosbos.seminar.sorting.Sorter;
import java.util.Arrays;

/**
 *
 * @author kai
 */
public class Quicksort extends AbstractSortingMechanics implements Sorter {

    public Quicksort() {
        this.name = "Quicksort";
    }

    //
    // quicksort sortiert ein Teilfeld rekursiv.
    //
    public void quicksort(int field[], int left, int right) {
        int l = left;
        int r = right;
        int median = (l + r) / 2;
        do {
            // Suche von links ein Element,
            // das nicht kleiner als der Median ist.
            while (decoratedAlgorithm.compare(l, median) < 0) {
                l++;
            }

            // Suche von rechts ein Element,
            // das nicht groesser als der Median ist.
            while (decoratedAlgorithm.compare(r, median) > 0) {
                r--;
            }

            if (l <= r) {
                if (l < r) {
                    // tausche Elemente, wenn l!=r
                    decoratedAlgorithm.swap(l, r);
//                    int tmp = field[l];
//                    field[l] = field[r];
//                    field[r] = tmp;
                }
                l++;
                r--;
            }

            // Wenn nicht mehr l<=r gilt, dann ist die 
            // Aufteilung in zwei Teilfelder beendet,
            // da alle Elemente im linken Teilfeld
            // kleiner oder gleich  
            // und alle Elemente im rechten Teilfeld 
            // groesser oder gleich dem Median sind.
        } while (l <= r);

        // Die beiden Teilfelder muessen noch sortiert 
        // werden
        if (left < r) {
            quicksort(field, left, r);
        }
        if (l < right) {
            quicksort(field, l, right);
        }
    }

    //
    // sortiere Feld a mit Quicksort
    //
    @Override
    public void sort() {
        ((AbstractSortingMechanics) decoratedAlgorithm).setRunning(true);

        // sortiere gesamtes Feld
        quicksort(problem, 0, problem.length - 1);
        
        ((AbstractSortingMechanics) decoratedAlgorithm).setRunning(false);
        System.out.println("Quicksort"+Arrays.toString(problem));

    }
}
