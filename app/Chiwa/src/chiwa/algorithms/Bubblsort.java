/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chiwa.algorithms;

import chiwa.Algorithm;
import chiwa.Algorithmics;

/**
 *
 * @author kai
 */
public class Bubblsort extends Algorithm implements Algorithmics {
    /**
     * Naive Implementation von
     * http://www.java-uni.de/index.php?Seite=85
     */
    public void sort() {
        boolean unsorted = true;
        int temp;

        while (unsorted) {
            unsorted = false;
            for (int i = 0; i < problemSet.length - 1; i++) {
//                compare(i,i+1);
                if (this.problemSet[i] > problemSet[i + 1]) {
//                    swap(i,i+1)
                    temp = problemSet[i];
                    problemSet[i] = problemSet[i + 1];
                    problemSet[i + 1] = temp;
                    
                    unsorted = true;
                }
            }
        }
    }
}
