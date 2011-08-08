package fosbos.seminar.sorting;

import fosbos.seminar.sorting.utils.SortingUtils;


/*
 * Interface für alle Algorithmen
 * Vornehmlich als Marker-Interface verwenden
 * 
 * @author Kai Richard König
 */
public interface Sorter extends SortingUtils {
    /**
     * Hier wird die eigentliche Sorttierlogik implementiert
     */
    public void sort();
    public void swap(int n, int m);    
    public int compare(int n, int m);
    public void assign(int i, int value);
    public void accquireDecoratedSelf(Sorter heapsort);
    
}
