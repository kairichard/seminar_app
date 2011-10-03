/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fosbos.seminar.sorting.decorators;

import fosbos.seminar.sorting.Sorter;
import fosbos.seminar.sorting.AbstractSortingDecorator;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dies ist eine Dekorator-Klasse die es einem Sorter erlaubt sich mit 
 * Threads zu synchronisieren, erreicht wird das in dem alle Methoden
 * überschrieben werden und die Logik an den eigentlich Sorter 
 * erst _nach_ einem <code>wait()</code> Aufruf delegiert wird.
 * @author Kai Richard König
 */
public class SynchronizedSorter extends AbstractSortingDecorator {

    public SynchronizedSorter(Sorter algorithm) {
        super(algorithm);
    }

    @Override
    public void swap(int m, int n) {
        synchronized (super.algorithm) {
            try {
                super.algorithm.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(SynchronizedSorter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        algorithm.swap(m, n);
    }

    @Override
    public int compare(int m, int n) {
        synchronized (super.algorithm) {
            try {
                super.algorithm.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(SynchronizedSorter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return algorithm.compare(m, n);
    }
    @Override
    public void assign(int m, int n) {
        synchronized (super.algorithm) {
            try {
                super.algorithm.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(SynchronizedSorter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        algorithm.assign(m, n);
    }
    
    @Override
    public int getProblemValueAt(int i) {
        synchronized (super.algorithm) {
            try {
                super.algorithm.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(SynchronizedSorter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return algorithm.getProblemValueAt(i);
    }

    @Override
    public void sort() {
        algorithm.sort();
    }

    @Override
    public void setProblem(int[] problem) {
        algorithm.setProblem(problem);
    }

    @Override
    public void setDecoratedAlgorithm(Sorter algo) {
        algorithm.setDecoratedAlgorithm(algo);
    }

    @Override
    public void highlightRange(int n, int m, Color color) {
        ((VisualFeedbackSorter)algorithm).highlightRange(n, m, color);
    }
}
