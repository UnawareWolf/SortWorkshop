package bigOWorkshop;

import java.util.Arrays;

public class MergeSort {
	
	private int comparisons;
	private final int[] input;
	private int[] interim;
	private int[] output;
	private long timeTaken;
	private final boolean v2;

	public MergeSort(int[] input, boolean v2) {
		this.input = input;
		this.interim = new int[input.length];
		this.v2 = v2;
	}
	
	public void sort() {
		comparisons = 0;
		long startTime = System.nanoTime();
		if (v2) {
			output = input.clone();
			mergeSort(output, 0, input.length - 1);
		}
//		else {
//			output = input.clone();
//			mergeSort(output, 0, input.length - 1);
//		}
		else output = mergeSort(input);
		
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
	
//	private void mergeSort(int[] inputList, int startIndex, int endIndex) {
//		if (inputList[startIndex] == inputList[endIndex]) {
//			return;
//		}
//		int halfIndex = startIndex + (endIndex - startIndex) / 2;
//		mergeSort(inputList, startIndex, halfIndex);
//		mergeSort(inputList, halfIndex + 1, endIndex);
//
//		int leftIndex = startIndex;
//		int rightIndex = halfIndex + 1;
//		int newIndex = 0;
//		
//		int[] interimIntArray = new int[1 + endIndex - startIndex];
//		while (leftIndex <= halfIndex || rightIndex <= endIndex) {
////			int temp = inputList[newIndex + startIndex];
//			if (rightIndex > endIndex ||
//					(leftIndex <= halfIndex && inputList[leftIndex] < inputList[rightIndex])) {
////				inputList[newIndex + startIndex] = inputList[leftIndex];
////				inputList[leftIndex] = temp;
//				interimIntArray[newIndex] = inputList[leftIndex];
//				leftIndex++;
//			}
//			else {
//				interimIntArray[newIndex] = inputList[rightIndex];
////				inputList[newIndex + startIndex] = inputList[rightIndex];
////				inputList[rightIndex] = temp;
//				rightIndex++;
//			}
//			comparisons++;
//			newIndex++;
//		}
//		System.arraycopy(interimIntArray, 0, inputList, startIndex, interimIntArray.length);
//	}

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
