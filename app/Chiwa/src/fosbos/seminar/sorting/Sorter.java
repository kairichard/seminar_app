package fosbos.seminar.sorting;

import fosbos.seminar.sorting.utils.SortingUtils;


/**
 * Interface für alle Algorithmen.
 * Wird nur als Marker-Interface benutzt damit alle 
 * Algorithmen die selbe Basis haben und sich gleich 
 * ansprechen lassen.
 * @author Kai Richard König
 */
public interface Sorter extends SortingUtils {
    /**
     * Schreibt an der Stelle <code>i</code> einen neuen Wert <code>value</code>.
     * @param i Index des zuschreibenden Feldes
     * @param value Wert der geschrieben werden soll
     */
    public void assign(int i, int value);    
    
    /**
     * Vergleicht den wert an der Stelle {@code n} mit dem Wert
     * an der Stelle {@code m}.
     * @param n Index linker Operand 
     * @param m Index rechter Operand
     * @return -1 für kleiner als, 0 für gleich, 1 für größer als
     */
    public int compare(int n, int m);

    /**
     * Hier verbiergt sich meiner Meinung nach die einzige schwäche
     * der vorhanden "dekoriere" - da nur #sort aufgerufen wird, kann z.B. der
     * Heapsort-Algorithmus bzw der SynchronizedSorter nicht mehr in seiner 
     * #sort Methode
     * auf die dekorierten Methoden zugreifen.
     * 
     * Deshalb wird jeden Sorter noch mal sein dekoriertes selbst 
     * übergeben.
     * @param sorter Eine Klasse die dieses Interface implementiert.
     */
    public void setDecoratedAlgorithm(Sorter sorter);
    
    /**
     * Die eigentlich Sortiermethode, hängt von der jeweiligen Implementierenden
     * Klasse ab.
     */
    public void sort();

    /**
     * Tauscht die Werte an den Stellen {@code n} und {@code n} mit sich selber
     * @param n Index des ersten Wertes
     * @param m Index des zweiten Wertes
     */
    public void swap(int n, int m);
    
    /**
     * Get the {@code i}th member of the problem.
     * @param i
     * @return 
     */
    public int getProblemValueAt(int i);

}
