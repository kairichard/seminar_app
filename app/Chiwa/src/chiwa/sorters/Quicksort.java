/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chiwa.sorters;

import fosbos.seminar.sorting.AbstractSortingMechanics;
import fosbos.seminar.sorting.Sorter;
import fosbos.seminar.sorting.decorators.SynchronizedSorter;
import fosbos.seminar.sorting.decorators.VisualFeedbackSorter;
import java.awt.Color;

public class Quicksort extends AbstractSortingMechanics implements Sorter {

    public Quicksort() {
        this.name = "Quicksort";
    }

    //
    // sortiere Feld a mit Quicksort
    //
    @Override
    public void sort() {
        ((AbstractSortingMechanics) decoratedAlgorithm).setRunning(true);

        // sortiere gesamtes Feld
        quicksort(0, problem.length - 1);

        ((AbstractSortingMechanics) decoratedAlgorithm).setRunning(false);
        // System.out.println("Quicksort"+Arrays.toString(problem));

    }

    private void quicksort(int lo, int hi) {
        ((SynchronizedSorter)decoratedAlgorithm).highlightRange(lo, hi, VisualFeedbackSorter.colorHighlight);

        int i = lo, j = hi;

        // Vergleichs­element x
        int x = decoratedAlgorithm.getProblemValueAt((lo + hi) / 2);
        ((SynchronizedSorter)decoratedAlgorithm).highlightRange(lo, hi, VisualFeedbackSorter.colorHighlight);

        //  Aufteilung
        while (i <= j) {
            while (decoratedAlgorithm.getProblemValueAt(i) < x) {
                i++;
            }
            while (decoratedAlgorithm.getProblemValueAt(j) > x) {
                j--;
            }
            if (i <= j) {
                decoratedAlgorithm.swap(i, j);
                i++;
                j--;
            }
        }

        // Rekursion
        if (lo < j) {
            quicksort(lo, j);
        }
        if (i < hi) {
            quicksort(i, hi);
        }
    }
}
