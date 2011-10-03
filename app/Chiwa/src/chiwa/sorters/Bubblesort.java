/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chiwa.sorters;

import fosbos.seminar.sorting.AbstractSortingMechanics;
import fosbos.seminar.sorting.Sorter;

/**
 *
 * @author kai
 */
public class Bubblesort extends AbstractSortingMechanics implements Sorter {
    public Bubblesort() {
        this.name = "Bubblesort";
    }
    /**
     * Naive Implementation von
     * http://www.java-uni.de/index.php?Seite=85
     */
    public void sort() {
        ((AbstractSortingMechanics)decoratedAlgorithm).setRunning(true);
        bubblesort();
        ((AbstractSortingMechanics)decoratedAlgorithm).setRunning(false);
   }

    private void bubblesort() {
        boolean unsorted = true;

        while (unsorted) {
            unsorted = false;   
            for (int i = 0; i < problem.length - 1; i++) {
                // -1,0,1 == lt,eq,gt
                if (decoratedAlgorithm.compare(i,i+1) > 0) {
                    decoratedAlgorithm.swap(i,i+1);
                    unsorted = true;
                }
            }
        }
    }
}
