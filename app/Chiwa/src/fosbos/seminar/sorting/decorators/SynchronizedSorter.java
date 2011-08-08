/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fosbos.seminar.sorting.decorators;

import fosbos.seminar.sorting.Sorter;
import fosbos.seminar.sorting.AbstractSortingDecorator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kai
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
    public void sort() {
        algorithm.sort();
    }

    @Override
    public void setProblem(int[] problem) {
        algorithm.setProblem(problem);
    }

    public void accquireDecoratedSelf(Sorter alog) {
        algorithm.accquireDecoratedSelf(alog);
    }
}
