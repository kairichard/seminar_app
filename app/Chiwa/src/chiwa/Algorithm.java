/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chiwa;

/**
 *
 * @author kai
 */
public class Algorithm {
    
    protected String name;
    protected int[] problemSet;

    /**
     * Getter für den Namen eines Algorythmus z.b. "BubbleSort"
     * Der name sollte in einer instance Varibale gespeichert 
     * 
     * @return Name des Algorythmus 
     */
    public String getName() {
        return name;
    }

    /**
     * Get the value of problemSet
     *
     * @return the value of problemSet
     */
    public int[] getProblemSet() {
        return problemSet;
    }

    /**
     * Set the value of problemSet
     *
     * @param problemSet new value of problemSet
     */
    public void setProblemSet(int[] problemSet) {
        this.problemSet = problemSet;
    }

    /**
     * Get the value of problemSet at specified index
     *
     * @param index
     * @return the value of problemSet at specified index
     */
    public int getProblemSet(int index) {
        return this.problemSet[index];
    }

    /**
     * Set the value of problemSet at specified index.
     *
     * @param index
     * @param newProblemSet new value of problemSet at specified index
     */
    public void setProblemSet(int index, int newProblemSet) {
        this.problemSet[index] = newProblemSet;
    }
    
//    public Visualizer visualizeWith(Visualizer visualizer){
//        this.visualizer = visualizer;
//        return visualizer;
//    };
    
    


    
}
