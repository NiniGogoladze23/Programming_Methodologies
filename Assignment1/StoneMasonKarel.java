
/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	public void run() {
		firstArch();
		while (frontIsClear()) {
			goToNextArch();
			repairArch();
			reposition();
		} // Restores other arches if any.

	}

	private void firstArch() {
		repairArch();
		reposition();
	} // It works even for a world where there is only one arch

	private void repairArch() {
		turnLeft();
		if (noBeepersPresent()) {
			putBeeper();
		} // In the absence of beeper puts it in the foundation
		while (frontIsClear()) {
			move();
			if (noBeepersPresent()) {
				putBeeper();
			}
		}
	} // Repairs whole arch.

	private void reposition() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnLeft();
	}// Goes to position, where he/she started repairing

	private void goToNextArch() {
		for (int i = 0; i < 4; i++) {
			move();
		}
	} // goes to next arch while it exists.

}
