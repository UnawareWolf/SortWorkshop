package bigOWorkshop;

import java.util.Arrays;

public class MergeSort {
	
	private int comparisons;
	private int[] input;
	private int[] output;
	private long timeTaken;

	public MergeSort(int[] input) {
		this.input = input;
	}
	
	public void sort() {
		comparisons = 0;
		long startTime = System.nanoTime();
		output = mergeSort(input);
		timeTaken = System.nanoTime() - startTime;
		System.out.println("Merge sort... \ncomparisons: " + comparisons + "\ntime taken: " + timeTaken +
				"\noutputs: " + Arrays.toString(output) + "\n");
	}
	
	public int[] getOutput() {
		return output;
	}
	
	private int[] mergeSort(int[] inputList) {
		if (inputList.length == 1) {
			return inputList;
		}
		int halfIndex = inputList.length / 2;
		int[] left = new int[halfIndex];
		int[] right = new int[inputList.length - halfIndex];
		for (int index = 0; index < inputList.length; index++) {
			if (index < halfIndex) {
				left[index] = inputList[index];
			}
			else {
				right[index - halfIndex] = inputList[index];
			}
		}
		left = mergeSort(left);
		right = mergeSort(right);
		
		int leftIndex = 0;
		int rightIndex = 0;
		int newIndex = 0;
		
		int[] sortedList = new int[inputList.length];
		while (leftIndex < left.length || rightIndex < right.length) {
			if (rightIndex >= right.length ||
					(leftIndex < left.length && left[leftIndex] < right[rightIndex])) {
				sortedList[newIndex] = left[leftIndex];
				leftIndex++;
			}
			else {
				sortedList[newIndex] = right[rightIndex];
				rightIndex++;
			}
			comparisons++;
			newIndex++;
		}
		
		return sortedList;
	}
	
//	private int[] mergeSort(int[] inputList, int startIndex, int endIndex) {
//		if (inputList.length == 1) {
//			return inputList;
//		}
//		int halfIndex = inputList.length / 2;
//		int[] left = new int[halfIndex];
//		int[] right = new int[inputList.length - halfIndex];
//		for (int index = 0; index < inputList.length; index++) {
//			if (index < halfIndex) {
//				left[index] = inputList[index];
//			}
//			else {
//				right[index - halfIndex] = inputList[index];
//			}
//		}
//		left = mergeSort(left, startIndex, );
//		right = mergeSort(right);
//		
//		int leftIndex = 0;
//		int rightIndex = 0;
//		int newIndex = 0;
//		
//		int[] sortedList = new int[inputList.length];
//		while (leftIndex < left.length || rightIndex < right.length) {
//			if (rightIndex >= right.length ||
//					(leftIndex < left.length && left[leftIndex] < right[rightIndex])) {
//				sortedList[newIndex] = left[leftIndex];
//				leftIndex++;
//			}
//			else {
//				sortedList[newIndex] = right[rightIndex];
//				rightIndex++;
//			}
//			comparisons++;
//			newIndex++;
//		}
//		
//		return sortedList;
//	}
}
