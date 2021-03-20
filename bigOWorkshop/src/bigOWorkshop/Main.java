package bigOWorkshop;

import java.util.Arrays;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		
		int[] intArray = getRandomIntArray(10000, 300);
		selectionSort(intArray.clone());
		insertionSort(intArray.clone());
		MergeSort mergeSort;
		System.out.println("trues");
		for (int i = 0; i < 8; i++) {
			mergeSort = new MergeSort(intArray.clone(), true);
			mergeSort.sort();
		}
		System.out.println("falses");
		for (int i = 0; i < 8; i++) {
			mergeSort = new MergeSort(intArray.clone(), false);
			mergeSort.sort();
		}
	}
	
	public static int[] getRandomIntArray(int maxSize, int maxValue) {
		Random rand = new Random();
		int[] intArray = new int[rand.nextInt(maxSize)];
		for (int i = 0; i < intArray.length; i ++) {
			intArray[i] = rand.nextInt(maxValue);
		}
		return intArray;
	}

	public static int[] selectionSort(int[] inputList) {
		long timeBefore = System.nanoTime();
		int[] outputList = inputList.clone();
		int swaps = 0;
		int comparisons = 0;
		for (int i = 0; i < inputList.length; i++) {
			int smallestIndex = i;
			for (int j = i; j < outputList.length; j++) {
				comparisons++;
				if (outputList[j] < outputList[smallestIndex]) {
					smallestIndex = j;
				}
			}
			
			int oldItem = outputList[i];
			outputList[i] = outputList[smallestIndex];
			outputList[smallestIndex] = oldItem;
			swaps++;
		}
		long timeTaken = System.nanoTime() - timeBefore;
		System.out.println("Selection sort... \nswaps: " + swaps + "\ncomparisons: " + comparisons +
				"\ntime taken: " + timeTaken + "\noutputs: " + Arrays.toString(outputList) + "\n");
		
		return outputList;
	}
	
	public static int[] insertionSort(int[] inputList) {
		long timeBefore = System.nanoTime();
		int[] outputList = new int[inputList.length];
		int swaps = 0;
		int comparisons = 0;
		for (int index = 0; index < inputList.length; index++) {
			outputList[index] = inputList[index];
			for (int i = index - 1; i >= 0; i--) {
				comparisons++;
				if (inputList[index] < outputList[i]) {
					outputList[i + 1] = outputList[i];
					outputList[i] = inputList[index];
					swaps++;
				}
				else break;
			}
		}
		long timeTaken = System.nanoTime() - timeBefore;
		System.out.println("Insertion sort... \nswaps: " + swaps + "\ncomparisons: " + comparisons +
				"\ntime taken: " + timeTaken + "\noutputs: " + Arrays.toString(outputList) + "\n");
		return outputList;
	}
	

	
}
