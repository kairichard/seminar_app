package fosbos.seminar.sorting;

/**
 * Abstrakte Klasse mit allen Methoden die mann zum sortieren
 * eines Integer Arrays braucht. Außerdem gibts es zusätzlich noch
 * ein Indikator das anzeigt ob noch sortiert wird.
 */
public abstract class AbstractSortingMechanics implements Sorter {
    public int[] problem;
    public String name;
    
    protected boolean running = false;
    protected Sorter decoratedAlgorithm;


    public void setRunning(boolean running) {
        this.running = running;
    }

    public int[] getProblem() {
        return problem;
    }    

    public boolean isRunning() {
        return running;
    }
    
    @Override
    public void swap(int m, int n) {
        int temp = problem[m];
        problem[m] = problem[n];
        problem[n] = temp;
        
    }

    public int compare(int m, int n) {
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
    
    public void assign(int i, int value) {
        problem[i] = value;
    }
    
    public void setProblem(int[] problem) {
        this.problem = problem;
    }
        
    public void setDecoratedAlgorithem(Sorter algorithm) {
        this.decoratedAlgorithm = algorithm;
    }
    
    public abstract void sort();

}
