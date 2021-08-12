package softwire.sudoku;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sudoku {

	public static void main(String[] args) {
		long timeBefore = System.nanoTime();
		int[][] initialBoard = {
				// ORIGINAL
				//                {0, 0, 0, 0, 0, 2, 1, 0, 0},
				//                {0, 0, 4, 0, 0, 8, 7, 0, 0},
				//                {0, 2, 0, 3, 0, 0, 9, 0, 0},
				//                {6, 0, 2, 0, 0, 3, 0, 4, 0},
				//                {0, 0, 0, 0, 0, 0, 0, 0, 0},
				//                {0, 5, 0, 6, 0, 0, 3, 0, 1},
				//                {0, 0, 3, 0, 0, 5, 0, 8, 0},
				//                {0, 0, 8, 2, 0, 0, 5, 0, 0},
				//                {0, 0, 9, 7, 0, 0, 0, 0, 0}
				// HARD
				//        		{0, 0, 0, 0, 0, 0, 3, 1, 2},
				//                {3, 0, 0, 0, 2, 6, 9, 0, 0},
				//                {0, 0, 0, 0, 0, 0, 0, 0, 4},
				//                {0, 9, 6, 0, 0, 4, 7, 0, 0},
				//                {0, 8, 1, 7, 0, 0, 0, 3, 0},
				//                {7, 0, 3, 0, 1, 0, 5, 2, 0},
				//                {0, 0, 8, 9, 0, 0, 0, 0, 7},
				//                {0, 3, 0, 0, 7, 0, 0, 9, 0},
				//                {0, 1, 0, 0, 0, 0, 0, 0, 0}
				// EXPERT
				{0, 0, 2, 0, 0, 5, 0, 0, 0},
				{0, 0, 1, 6, 0, 2, 0, 0, 0},
				{9, 3, 8, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 4, 7, 0, 6, 0},
				{0, 7, 0, 0, 0, 0, 2, 0, 0},
				{3, 0, 0, 0, 0, 0, 0, 1, 0},
				{0, 0, 0, 9, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 2, 0, 9, 0, 8},
				{0, 1, 0, 8, 0, 0, 0, 0, 3}
				// ZEROS
				//        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
				//        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
				//        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
				//        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
				//        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
				//        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
				//        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
				//        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
				//        		{0, 0, 0, 0, 0, 0, 0, 0, 0}
		};

		// Initialising
		System.out.println("Solving board:");

		// Set up the stack
		Deque<int[][]> stack = new ArrayDeque<>();
		stack.push(initialBoard);
		int count = 0;
		int[][] tempBoard, board;
		while (!stack.isEmpty()) {
			count++;
			board = stack.pop();
			System.out.println("next board");
			printBoard(board);

			List<Slot> slots = getEmptySlots(board);

			if (slots.size() == 0) {
				System.out.println("Solved!");
				break;
			}

			// Do the first move that we know is correct
			// (assuming that the state of 'board' is correct).
			tempBoard = doPerfectMove(slots, board);
			if (tempBoard != null) {
				stack.push(tempBoard);
			}

			// If we couldn't find a move that we know to be correct,
			// add all possible moves to the stack.
			if (stack.isEmpty()) {
				for (Slot slot : slots) {
					for (int guess = 1; guess <= 9; guess++) {
						if (isValidInSlot(guess, slot, board)) {
							stack.push(updateBoard(guess, slot, board));
						}
					}
				}
			}
		}
		System.out.println(count + " moves");
		long timeTaken = System.nanoTime() - timeBefore;
		System.out.println("Time taken " + timeTaken/1000 + "ms");
	}

	private static int[][] doPerfectMove(List<Slot> slots, int[][] board) {
		for (Slot slot : slots) {
			for (int guess = 1; guess <= 9; guess++) {
				if (onlyValidGuessInSlot(guess, slot, board)) {
					return updateBoard(guess, slot, board);
				}
			}
		}
		return null;
	}

	private static List<Slot> getEmptySlots(int[][] board) {
		List<Slot> validSlots = new ArrayList<>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) {
					validSlots.add(new Slot(i, j));
				}
			}
		}
		return validSlots;
	}

	private static boolean isValidInSlot(int guess, Slot slot, int[][] board) {
		return isValidInRow(slot.row, guess, board) &&
				isValidInCol(slot.col, guess, board) &&
				isValidInSquare(slot, guess, board);
	}

	private static boolean otherGuessesInvalidInSlot(int guess, Slot slot, int[][] board) {
		for (int newGuess = 1; newGuess <= 9; newGuess++) {
			if (newGuess == guess) {
				continue;
			}
			if (isValidInSlot(newGuess, slot, board)) {
				return false;
			}
		}
		return true;
	}

	private static boolean onlyValidSlotInRow(int guess, Slot slot, int[][] board) {
		for (int i = 0; i < board.length; i++) {
			if (i == slot.row) {
				continue;
			}
			if (board[i][slot.col] == 0 && isValidInSlot(guess, new Slot(i, slot.col), board)) {
				return false;
			}
		}
		return true;
	}

	private static boolean onlyValidSlotInCol(int guess, Slot slot, int[][] board) {
		for (int j = 0; j < board[slot.row].length; j++) {
			if (j == slot.col) {
				continue;
			}
			if (board[slot.row][j] == 0 && isValidInSlot(guess, new Slot(slot.row, j), board)) {
				return false;
			}
		}
		return true;
	}

	private static boolean onlyValidSlotInSquare(int guess, Slot slot, int[][] board) {
		int rowMod = (slot.row / 3) * 3;
		int colMod = (slot.col / 3) * 3;
		for (int i = rowMod; i <= rowMod + 2; i++) {
			for (int j = colMod; j <= colMod + 2; j++) {
				if (i == slot.row && j == slot.col) {
					continue;
				}
				if (board[i][j] == 0 && isValidInSlot(guess, new Slot(i, j), board)) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean onlyValidGuessInSlot(int guess, Slot slot, int[][] board) {
		if (!isValidInSlot(guess, slot, board)) {
			return false;
		}
		return otherGuessesInvalidInSlot(guess, slot, board) ||
				onlyValidSlotInRow(guess, slot, board) ||
				onlyValidSlotInCol(guess, slot, board) ||
				onlyValidSlotInSquare(guess, slot, board);
	}

	private static boolean isValidInRow(int row, int guess, int[][] board) {
		for (int col : board[row]) {
			if (col == guess) {
				return false;
			}
		}
		return true;
	}

	private static boolean isValidInCol(int col, int guess, int[][] board) {
		for (int[] row : board) {
			if (row[col] == guess) {
				return false;
			}
		}
		return true;
	}

	private static Integer[] getNumsInSquare(Slot slot, int[][] board) {
		Set<Integer> nums = new HashSet<>();
		int rowMod = (slot.row / 3) * 3;
		int colMod = (slot.col / 3) * 3;
		for (int i = rowMod; i <= rowMod + 2; i++) {
			for (int j = colMod; j <= colMod + 2; j++) {
				nums.add(board[i][j]);
			}
		}
		return nums.toArray(new Integer[]{});
	}

	private static boolean isValidInSquare(Slot slot, int guess, int[][] board) {
		Integer[] nums = getNumsInSquare(slot, board);
		for (int num : nums) {
			if (num == guess) {
				return false;
			}
		}
		return true;
	}

	private static int[][] updateBoard(int guess, Slot slot, int[][] board) {
		int[][] newBoard = new int[board.length][board[0].length];
		for (int i = 0; i < newBoard.length; i++) {
			for (int j = 0; j < newBoard[i].length; j++) {
				newBoard[i][j] = board[i][j];
			}
		}
		newBoard[slot.row][slot.col] = guess;
		return newBoard;
	}

	private static void printBoard(int[][] board) {
		int colInd = 0;
		for (int[] row : board) {
			for (int cell : row) {
				System.out.print(cell == 0 ? "  " : cell + " ");
				System.out.print(colInd % 3 == 2 ? "|" : "");
				colInd++;
			}
			System.out.println();
			colInd = 0;
		}
	}

	private static class Slot {
		int row;
		int col;

		Slot(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
}
