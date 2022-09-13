import java.util.Scanner;

/**
 * CS312 Assignment 10.
 *
 * On my honor, Tri Nguyen, this programming assignment is my own work and I
 * have not shared my solution with any other student in the class.
 *
 * email address: tritn928@gmail.com 
 * UTEID: ttn2797 
 * Section 5 digit ID: 52195
 * Grader name: Omeed Tehrani 
 * Number of slip days used on this assignment: 1
 *
 * Program that allows two people to play Connect Four.
 */

public class ConnectFour {

	// CS312 Students, add your constants here.
	public static final int MAX_TURNS = 42;
	public static final int MAX_ROWS = 6;
	public static final int MAX_COLUMNS = 7;
	public static final int OUT_OF_BOUNDS_COLUMN = -1;
	// Integers corresponding to a direction
	// i.e. row + south would move south one row
	public static final int SOUTH = 1;
	public static final int WEST = -1;
	public static final int EAST = 1;

	public static void main(String[] args) {
		intro();

		// Complete this method.
		// Make and use one Scanner connected to System.in.
		Scanner keyboard = new Scanner(System.in);
		int[][] board = new int[MAX_ROWS][MAX_COLUMNS];
		gameLoop(board, keyboard);
		keyboard.close();

	}

	// CS312 Students, add your methods here.

	public static void gameLoop(int[][] board, Scanner keyboard) {
		String playerOneName = getName(keyboard, 1);
		System.out.println();
		String playerTwoName = getName(keyboard, 2);
		System.out.println();
		int turnCounter = 0;
		int checkerColumn = -1;
		boolean redTurn = true;
		boolean gameFinished = false;
		do {

			System.out.println("Current Board");
			printBoard(board);

			// Determines where the player will place a checker and update the board
			// accordingly
			if (redTurn) {
				System.out.println(playerOneName + " it is your turn.");
				System.out.println("Your pieces are the r's.");
				checkerColumn = getColumn(keyboard, playerOneName, board);
				placeChecker(board, checkerColumn, redTurn);
			} else {
				System.out.println(playerTwoName + " it is your turn.");
				System.out.println("Your pieces are the b's.");
				checkerColumn = getColumn(keyboard, playerTwoName, board);
				placeChecker(board, checkerColumn, redTurn);
			}

			turnCounter++;

			// Check if someone won
			if (checkWinner(board, redTurn)) {
				if (redTurn)
					System.out.println(playerOneName + " wins!!");
				else
					System.out.println(playerTwoName + " wins!!");

				System.out.println("\nFinal Board");
				printBoard(board);
				gameFinished = true;
			}

			// Check if there is no more space
			if (turnCounter == MAX_TURNS) {
				System.out.println("The game is a draw.");
				System.out.println();
				System.out.println("Final Board");
				printBoard(board);
				gameFinished = true;
			}

			// Switch turns
			redTurn = !redTurn;

		} while (!gameFinished);
	}

	// Gets the player's name
	public static String getName(Scanner keyboard, int playerNumber) {
		System.out.print("Player " + playerNumber + " enter your name: ");
		return keyboard.next();
	}

	// Prints the 2d matrix into a visible board where 'r' represents red
	// 'b' represents black and '.' represents an empty space
	public static void printBoard(int[][] board) {
		System.out.println("1 2 3 4 5 6 7  column numbers");

		// Loop through each element, printing an 'r' if it is a 1
		// a 'b' if it is 2 or '.' if it is empty.
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				if (board[row][col] == 1)
					System.out.print("r");
				else if (board[row][col] == 2)
					System.out.print("b");
				else
					System.out.print(".");
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
	}

	// Determines if there is a winner for the game
	public static boolean checkWinner(int[][] board, boolean redTurn) {

		// Identifies who's turn it is and what number corresponds to the player
		int playerNumber = 0;
		if (redTurn)
			playerNumber = 1;
		else
			playerNumber = 2;

		// Loops through the matrix searching for instances of the player number
		// Calls checkDirections() to see if the piece makes a four in a row
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				if (board[row][col] == playerNumber && checkDirections(board, row, col))
					return true;
			}
		}
		return false;
	}

	// Checks the different directions that could possibly lead to a win for a given
	// piece
	public static boolean checkDirections(int[][] board, int checkerRow, int checkerColumn) {
		// If any direction gives a 4 in a row, return true
		if (checkDirection(board, checkerRow, checkerColumn, 0, SOUTH)
				|| checkDirection(board, checkerRow, checkerColumn, WEST, SOUTH)
				|| checkDirection(board, checkerRow, checkerColumn, EAST, SOUTH)
				|| checkDirection(board, checkerRow, checkerColumn, EAST, 0))
			return true;

		return false;
	}

	// Gets the player's input for which column to drop a checker
	// Checks for a valid integer in a column that is not full
	public static int getColumn(Scanner keyboard, String playerName, int[][] board) {
		boolean validColumn = false;
		int checkerColumn = -1;

		while (!validColumn) {
			validColumn = true;
			System.out.print(playerName + ", enter the column to drop your checker: ");
			checkerColumn = getInt(keyboard,
					playerName + ", enter the column to drop your checker: ") - 1;
			System.out.println();

			// Error check for columns out of bounds.
			if (checkerColumn <= OUT_OF_BOUNDS_COLUMN || checkerColumn >= MAX_COLUMNS) {
				System.out.println((checkerColumn + 1) + " is not a valid column.");
				validColumn = false;
			}
			
			// Error check for full column
			int topElement = board[0][checkerColumn];
			if (topElement != 0) {
				System.out.println(
						(checkerColumn + 1) + " is not a legal column. That column is full");
				validColumn = false;
			}
		}

		return checkerColumn;
	}

	// Places a checker of the player's color at the specified column
	public static void placeChecker(int[][] board, int checkerColumn, boolean redTurn) {

		// Loops from the bottom row up, checking for an empty space
		for (int row = board.length - 1; row >= 0; row--) {
			if (board[row][checkerColumn] == 0) {
				if (redTurn)
					board[row][checkerColumn] = 1;
				else
					board[row][checkerColumn] = 2;
				break;
			}
		}
	}

	// Gets the row of the last checker placed
	public static int getCheckerRow(int[][] board, int checkerColumn) {
		int checkerRow = -1;

		// Loops from the top row to the bottom checking for a player placed checker
		for (int row = 0; row <= board.length - 1; row++) {
			if (board[row][checkerColumn] == 1 || board[row][checkerColumn] == 2) {
				checkerRow = row;
				break;
			}
		}
		return checkerRow;
	}

	// Checks the pieces in a specified direction. Directions are given by final global variables.
	public static boolean checkDirection(int[][] board, int checkerRow, int checkerColumn,
			int horDirection, int verDirection) {
		int inARow = 1;

		while (inARow != 4) {
			//Checks if the next piece would be out of bounds, return false if it is
			if ((checkerRow + verDirection) == MAX_ROWS)
				return false;
			if ((checkerColumn + horDirection) == MAX_COLUMNS
					|| (checkerColumn + horDirection) == OUT_OF_BOUNDS_COLUMN)
				return false;
			
			// Get the current element and the next element
			// If they match, increase inARow and move onto the next piece
			int currentElement = board[checkerRow][checkerColumn];
			int nextElement = board[checkerRow + verDirection][checkerColumn + horDirection];
			if (currentElement == nextElement) {
				inARow++;
				checkerRow += verDirection;
				checkerColumn += horDirection;
			} else
				return false;
	
			if (inARow == 4)
				return true;
		}

		return true;
	}

	// Show the introduction.
	public static void intro() {
		System.out.println("This program allows two people to play the");
		System.out.println("game of Connect four. Each player takes turns");
		System.out.println("dropping a checker in one of the open columns");
		System.out.println("on the board. The columns are numbered 1 to 7.");
		System.out.println("The first player to get four checkers in a row");
		System.out.println("horizontally, vertically, or diagonally wins");
		System.out.println("the game. If no player gets fours in a row and");
		System.out.println("and all spots are taken the game is a draw.");
		System.out.println("Player one's checkers will appear as r's and");
		System.out.println("player two's checkers will appear as b's.");
		System.out.println("Open spaces on the board will appear as .'s.\n");
	}

	// Prompt the user for an int. The String prompt will
	// be printed out. key must be connected to System.in.
	public static int getInt(Scanner key, String prompt) {
		while (!key.hasNextInt()) {
			String notAnInt = key.nextLine();
			System.out.println("\n" + notAnInt + " is not an integer.");
			System.out.print(prompt);
		}
		int result = key.nextInt();
		key.nextLine();
		return result;
	}
}
