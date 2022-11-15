
/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {

	public void run() {
		turnLeft();
		while (frontIsClear()) {
			fillTwoRows();
		} // Works perfectly for even numbers of rows
		checkLastRow();// Correction in the case of an odd number of rows

	}

	private void fillTwoRows() {
		turnRight();
		fillOddRow();
		reposition();
		goToNextRow();
		fillEvenRow();
		reposition();
		goToNextRow();
		turnLeft();
	}// Karel fills two rows together.

	// As mentioned, Karel fills two rows together. It means that if we have odd
	// numbers of rows,
	// the last row would be unfilled and we have to fill it.
	private void checkLastRow() {
		turnRight();
		if (frontIsClear()) {
			move();
			// If beeper presents, it means that we have an even number of rows and all of
			// them are filled.
			if (beepersPresent()) {
				turnAround();
				move();
				turnRight();
			} else { // If beeper does not present, we are at odd row which is empty
				turnAround();
				move();
				turnAround();
				fillOddRow();
				reposition();
				turnLeft();
			}
		}

	}

	private void fillOddRow() {
		putBeeper();
		while (frontIsClear()) {
			move();
			if (frontIsClear()) {
				move();
				putBeeper();
			}
		}
	}// Filling odd rows

	private void fillEvenRow() {
		if (frontIsClear()) {
			move();
			fillOddRow();
		}
	}// Filling even rows

	private void reposition() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnAround();
	}// Goes to the beginning of the row (to the left).

	private void goToNextRow() {
		turnLeft();
		if (frontIsClear()) {
			move();
		}
		turnRight();
	}// Goes up to the next row, if exists.

}
