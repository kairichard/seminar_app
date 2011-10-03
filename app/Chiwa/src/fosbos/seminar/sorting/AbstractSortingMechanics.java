package fosbos.seminar.sorting;

/**
 * Abstakte "Mutterklasse" alle Sorter, hier wurden nur die notwendige Sortierlogik
 * aus dem {@link Sorter} Interface implementiert. Zusätzlich aber gibt es 
 * noch Hilfslogik die dazu dienen soll die Klasse schlüssiger und benutzbarer zumachen.
 */
public abstract class AbstractSortingMechanics implements Sorter {
    /** Beinhaltet das zusortierende integer Array */
    public int[] problem;
    
    /** Name des Sorter z.b. "Bubblesort" */
    public String name;
    
    /** Für eine spätere Auswertung werden hier die anzahl durchgeführter Operationen gespeichert. */
    public int numberOfOperations;

    /** Zeigt an ob der Sorter "arbeitet". */
    protected boolean running = false;
    
    /** 
     * Alle tatsächlichen Algorithmen delegieren 
     * {@link #assign(int, int) }, {@link #compare(int, int) } und {@link #swap(int, int) }  
     * an die Instanz in dieser Variable.
     * Das kann entweder der Algorithmus selbst sein oder eine 
     * dekorierte Version des Algorithmus wie in dieser Applikation.
     */
    protected Sorter decoratedAlgorithm;
    
    /**
     * Stellt ein ob der Sorter "arbeitet" oder nicht.
     * @param running true || false
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    /**
     * Prüft ob ein Sorter "arbeitet".
     * @return 
     */
    public boolean isRunning() {
        return running;
    }
    
    /**
     * Gibt die Anzahl dürchgeführter Operationen zurück.
     * @return 
     */
    public int getNumberOfOperations() {
        return numberOfOperations;
    }

    /**
     * Setzt die Anzahl dürchgeführter Operationen zurück auf 0.
     * @return 
     */
    public void resetNumberOfOperations() {
        this.numberOfOperations = 0;
    }
    /**
     * Siehe {@link Sorter#swap(int, int) }
     */
    public void swap(int m, int n) {
        numberOfOperations++;
        int temp = problem[m];
        problem[m] = problem[n];
        problem[n] = temp;        
    }

    /**
     * Siehe {@link Sorter#compare(int, int) }
     */
    public int compare(int m, int n) {
        numberOfOperations++;
        if (problem[m] < problem[n]) {
            return -1;
        }
        if (problem[m] == problem[n]) {
            return 0;
        }
        if (problem[m] > problem[n]) {
            return 1;
        }
        throw new IllegalArgumentException("Arguments aren't compareable");
    }
    
    /**
     * Siehe {@link Sorter#assign(int, int) }
     */
    public void assign(int i, int value) {
        numberOfOperations++;
        problem[i] = value;
    }
    
    /**
     * Siehe {@link Sorter#setProblem(int[]) }
     */
    public void setProblem(int[] problem) {
        resetNumberOfOperations();
        this.problem = problem;
    }
        
    /**
     * Siehe {@link Sorter#setDecoratedAlgorithm(fosbos.seminar.sorting.Sorter)  }
     */
    public void setDecoratedAlgorithm(Sorter algorithm) {
        this.decoratedAlgorithm = algorithm;
    }
    
    public int[] getProblem(){
        return this.problem;
    }
    public int getProblemValueAt(int i){
        numberOfOperations++;
        return this.problem[i];
    }
    /**
     * Pseudo Callback - kann in der implementierenden Klasse verwendet werden 
     * um zusätzlich Code/Logik auszuführen wenn eine Sorter mit dem sortieren
     * fertig ist.
     */
    public void finish(){
        return;
    }
    /**
     * Siehe {@link Sorter#sort()  }
     */
    public abstract void sort();

}
