package bigOWorkshop;

import java.util.Arrays;

public class MergeSort {
	
	private int comparisons;
	private final int[] input;
	private int[] interim;
	private int[] output;
	private long timeTaken;

	public MergeSort(int[] input) {
		this.input = input;
		this.interim = new int[input.length];
	}
	
	public void sort() {
		comparisons = 0;
		long startTime = System.nanoTime();
		output = input.clone();
		mergeSort(output, 0, input.length - 1);
		
		timeTaken = System.nanoTime() - startTime;
		System.out.println("Merge sort... \ncomparisons: " + comparisons + "\ntime taken: " + timeTaken +
				"\noutputs: " + Arrays.toString(output) + "\n");
	}
	
	public int[] getOutput() {
		return output;
	}
	
	private void mergeSort(int[] inputList, int startIndex, int endIndex) {
		if (inputList[startIndex] == inputList[endIndex]) {
			return;
		}
		int halfIndex = startIndex + (endIndex - startIndex) / 2;
		mergeSort(inputList, startIndex, halfIndex);
		mergeSort(inputList, halfIndex + 1, endIndex);

		int leftIndex = startIndex;
		int rightIndex = halfIndex + 1;
		int newIndex = 0;

		while (leftIndex <= halfIndex || rightIndex <= endIndex) {
			if (rightIndex > endIndex ||
					(leftIndex <= halfIndex && inputList[leftIndex] < inputList[rightIndex])) {
				interim[newIndex] = inputList[leftIndex];
				leftIndex++;
			}
			else {
				interim[newIndex] = inputList[rightIndex];
				rightIndex++;
			}
			comparisons++;
			newIndex++;
		}
		System.arraycopy(interim, 0, inputList, startIndex, 1 + endIndex - startIndex);
	}
}
