/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chiwa.sorters;

import fosbos.seminar.sorting.AbstractSortingMechanics;
import fosbos.seminar.sorting.Sorter;
import java.util.Arrays;

/**
 *
 * @author kai
 */
public class Mergesort extends AbstractSortingMechanics implements Sorter {
        public Mergesort() {
            this.name = "Mergesort";
        }

	public void sort() {
                ((AbstractSortingMechanics)decoratedAlgorithm).setRunning(true);
		mergesort(0, problem.length - 1);
                ((AbstractSortingMechanics)decoratedAlgorithm).setRunning(false);
                System.out.println("Mergesort"+Arrays.toString(problem));
	}

	public void mergesort(int low, int high) {
		// Check if low is smaller then high, if not then the array is sorted
		if (low < high) {
			// Get the index of the element which is in the middle
			int middle = (low + high) / 2;
			// Sort the left side of the array
			mergesort(low, middle);
			// Sort the right side of the array
			mergesort(middle + 1, high);
			// Combine them both
			merge(low, middle, high);
		}
	}

	public void merge(int low, int middle, int high) {

		// Helperarray
		int[] helper = new int[problem.length];

		// Copy both parts into the helper array
		for (int i = low; i <= high; i++) {
			helper[i] = problem[i];
		}

		int i = low;
		int j = middle + 1;
		int k = low;
		// Copy the smallest values from either the left or the right side back
		// to the original array
		while (i <= middle && j <= high) {
			if (helper[i] <= helper[j]) {
                                decoratedAlgorithm.assign(k, helper[i]);
				// problem[k] = helper[i];
				i++;
			} else {
                                decoratedAlgorithm.assign(k, helper[j]);
				// problem[k] = helper[j];
				j++;
			}
			k++;
		}
		// Copy the rest of the left side of the array into the target array
		while (i <= middle) {
                        decoratedAlgorithm.assign(k, helper[i]);
			// problem[k] = helper[i];
			k++;
			i++;
		}
		helper = null;

	}

}