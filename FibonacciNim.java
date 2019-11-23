import java.util.Scanner;

public class FibonacciNimO {
	// Max number of heaps
	static final int MAX_HEAP = 3;
	static final int MAX_TOKEN = 9;

	public static void main(String[] args) {
		// String constants
		final String HEAP_STRING = "Heap ";
		final String TURN_PLAYER1 = "Player 1's turn.";
		final String TURN_PLAYER2 = "Player 2's turn.";
		final String LIMITS = "The  number of tokens you may take is between 1 and ";
		final String NR_TOKEN_QUESTION = "How many tokens do you want to take? ";
		final String WIN_PLAYER1 = "Player 1 wins";
		final String WIN_PLAYER2 = "Player 2 wins";
		final String HEAP_CHOICE1 = "Choose a heap (1-";
		final String HEAP_CHOICE2 = "): ";
		final String HEAP_EMPTY = " is empty, try another one";
		final String INVALID_INPUT = "Invalid input! Write a number between 1 and ";

		int heapSize = MAX_TOKEN;
		int heapNr = MAX_HEAP;

		/*
		 * We do a check to see if there are integers inputed in the command line run
		 * command and we save them as heap size and heap number
		 */
		if (args.length == 2 && isInt(args[0]) && isInt(args[1])) {
			heapSize = Integer.parseInt(args[0]);
			heapNr = Integer.parseInt(args[1]);
		}

		int[] Heap = new int[heapNr];

		/*
		 * Initialization of heaps Our array will begin at 0 because I want to not waste
		 * space
		 */
		for (int i = 0; i < heapNr; i++) {
			Heap[i] = heapSize;
		}

		boolean isEnd = false;
		boolean isFirstsTurn = true;

		Scanner in = new Scanner(System.in);

		// Maximum choice of coins taken
		int maxChoice = 0;

		// Maximum limit of coins taken in theory
		int maxLim = 2;

		do {
			// Heap insight
			for (int i = 0; i < heapNr; i++) {
				System.out.println(HEAP_STRING + (i + 1) + ' ' + Heap[i]);
			}

			// Turn declaration
			if (isFirstsTurn) {
				System.out.println(TURN_PLAYER1);
			} else {
				System.out.println(TURN_PLAYER2);
			}

			System.out.print(HEAP_CHOICE1 + heapNr + HEAP_CHOICE2);

			// Reading of information
			int heapChoice = -1;
			do {
				boolean isInRange = true;
				do {
					// Check if next input is an integer
					while (!in.hasNextInt()) {
						in.nextLine();
						System.out.println(INVALID_INPUT + heapNr);
					}
					heapChoice = in.nextInt();

					// Check if next input is an valid integer
					isInRange = (heapChoice > 0) && (heapChoice <= heapNr);
					if (!isInRange) {
						System.out.println(INVALID_INPUT + heapNr);
					}

				} while (!isInRange);

				// Check if current heap is empty
				if (Heap[heapChoice - 1] == 0) {
					System.out.println(HEAP_STRING + heapChoice + HEAP_EMPTY);
				}

			} while (Heap[heapChoice - 1] == 0);
			/*
			 * For converting our heap choice to actual position in array we decrement it by
			 * 1. The same technique we did above with Heap[heapChoice - 1] == 0.
			 */
			heapChoice--;

			int curMax = maxLim;

			// we do some analysis (check if it is possible to subtract maxLim coins)
			if (curMax > Heap[heapChoice]) {
				curMax = Heap[heapChoice];
			}

			System.out.println(LIMITS + curMax);
			System.out.print(NR_TOKEN_QUESTION);

			int curChoice = 0;

			boolean isValid = true;

			do {
				// Check if user imputs an int
				while (!in.hasNextInt()) {
					in.nextLine();
					in.nextLine();
					System.out.println(INVALID_INPUT + curMax);
				}
				curChoice = in.nextInt();
				// Check if int is valid
				isValid = (curChoice > 0) && (curChoice <= curMax);
				if (!isValid) {
					System.out.println(INVALID_INPUT + curMax);
				}
			} while (!isValid);

			Heap[heapChoice] -= curChoice;

			isEnd = true;

			// Check if all the heaps are empty
			for (int i = 0; i < heapNr; i++) {
				if (Heap[i] != 0) {
					isEnd = false;
				}
			}

			// Save the maximum choice possible
			if (curChoice > maxChoice) {
				maxChoice = curChoice;
			}

			// The change of turn and of the maximum limit
			if (!isEnd) {
				isFirstsTurn = !isFirstsTurn;
				if (maxLim < maxChoice * 2) {
					maxLim = maxChoice * 2;
				}
			}

		} while (!isEnd);

		// Win conditions
		if (isFirstsTurn) {
			System.out.println(WIN_PLAYER1);
		} else {
			System.out.println(WIN_PLAYER2);
		}
		in.close();
	}

	// Exception handling method which checks if the strings in the command line are
	// integers
	public static boolean isInt(String potentialInt) {
		try {
			Integer.parseInt(potentialInt);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}