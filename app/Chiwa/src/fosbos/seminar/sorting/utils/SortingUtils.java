package fosbos.seminar.sorting.utils;

/**
 * Kleinster gemeinsamer Nenner zwischen den {@link AbstractSortingMechanics} und dem
 * {@link RunnableSortingCollectionDelegator}
 * @author kai
 */
public interface SortingUtils {
    /**
     * Schreibt ein Interger Array in eine Member variable zur spätern 
     * bearbeitung.
     * @param problem Ein beliebiges Integer Array 
     */
    public void setProblem(int[] problem);
}
