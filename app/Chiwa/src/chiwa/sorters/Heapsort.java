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
public class Heapsort extends AbstractSortingMechanics implements Sorter {

    public Heapsort() {
        this.name = "Heapsort";
    }

    /**
     * Standard heapsort.
     * @param a an array of Comparable items.
     */
    @Override
    public void sort() {
        ((AbstractSortingMechanics) decoratedAlgorithm).setRunning(true);

        int max = problem.length - 1;

        // Den Heap aufbauen.
        // Nur die untere Haelfte des Feldes muss 
        // betrachtet werden, da die obere Haelfte 
        // nur aus Blaettern besteht. 
        // ( Blaetter sind schon ein Heap. )

        for (int i = (max + 1) / 2 - 1; i >= 0; i--) {
            makeheap(problem, i, max);
        }

        // Heap abbauen
        for (int i = max; i > 0; i--) {
            // Tausche Wurzel (null'tes Element)
            //  mit dem i. Element.
            // Die Wurzel des Heap ist immer
            // das groesste Element.
            decoratedAlgorithm.swap(i,0);
            // Lasse das Wurzelelement in den Heap 
            // einsinken, bis ein gueltiger Heap entsteht.
            makeheap(problem, 0, i - 1);
        }


        ((AbstractSortingMechanics) decoratedAlgorithm).setRunning(false);
    }

    public void makeheap(int a[], int root, int max) {
        int sub;
        do {
            // berechne Index des Sohnes
            sub = 2 * (root + 1) - 1;
            if (sub <= max) {
                // bestimme den groesseren Sohn
                if (sub < max && decoratedAlgorithm.compare(sub, sub + 1) < 0 ) {
                    sub++;
                }

                if (decoratedAlgorithm.compare(sub, root) > 0) {
                    // vertausche Sohn und Vater
                    decoratedAlgorithm.swap(sub, root);
                    // der Sohn wird der neue Vater...
                    root = sub;
                }
            }
            // und makeheap wird iterativ auf 
            // den Sohn/Vater angewandt.
        } while (root == sub);
    }
}
