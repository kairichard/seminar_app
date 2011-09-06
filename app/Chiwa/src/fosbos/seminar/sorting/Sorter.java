package fosbos.seminar.sorting;

import fosbos.seminar.sorting.utils.SortingUtils;


/*
 * Interface für alle Algorithmen
 * Wird nur als Marker-Interface benutzt damit alle 
 * Algorithmen die selbe Basis haben und sich gleich 
 * ansprechen lassen
 * 
 * @author Kai Richard König
 */
public interface Sorter extends SortingUtils {
    public void sort();
    public void swap(int n, int m);    
    public void assign(int i, int value);
    public void setDecoratedAlgorithem(Sorter sorter);
    public int  compare(int n, int m);
}
