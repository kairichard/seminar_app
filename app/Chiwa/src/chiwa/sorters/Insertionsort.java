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
public class Insertionsort extends AbstractSortingMechanics implements Sorter {

    public Insertionsort() {
        this.name = "Insertionsort";
    }
    
    public void sort() {
        ((AbstractSortingMechanics) decoratedAlgorithm).setRunning(true);
        insertionsort();
        ((AbstractSortingMechanics) decoratedAlgorithm).setRunning(false);
        System.out.println("Insertion" + Arrays.toString(problem));
    }

    public void insertionsort() {
        int i, j, t;
        for (i = 1; i < problem.length; i++) {
            j = i;
            t = problem[j];
            while (j > 0 && decoratedAlgorithm.compare(j - 1, j) == 1) {
                decoratedAlgorithm.swap(j, j - 1);
                // a[j] = a[j-1];
                j--;
            }
            decoratedAlgorithm.assign(j, t);
            // a[j] = t;
        }
    }
}