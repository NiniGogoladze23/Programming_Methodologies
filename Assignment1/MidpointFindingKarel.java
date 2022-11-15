
/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	public void run() {
		if (frontIsClear()) {// Works when dimension of the world is more than 1x1.
			putBeepersInEdges();
			findMidPoint();
			standInCenter();
		} else {// Works if world is dimension 1x1.
			putBeeper();
		}

	}

	private void putBeepersInEdges() {
		putBeeper();
		while (frontIsClear()) {
			move();
		}
		putBeeper();
		turnAround();
		move();
	}// Karel puts one beeper at the start of the first row and and one at the end of
		// the first row

	private void findMidPoint() {
		while (noBeepersPresent()) {
			movingEdges();
		}
		pickBeeper();// Karel takes one of the moved beepers and the remaining beeper is the center.
	}

	private void movingEdges() {
		while (noBeepersPresent()) {
			move();
		}
		pickBeeper();
		turnAround();
		move();
		putBeeper();
		move();
	} // Karel moves the left beeper to the right and the right beeper to the left.

	private void standInCenter() {
		while (frontIsClear()) {
			move();
		}
		turnAround();
		while (noBeepersPresent()) {
			move();
		}
	}// Karel stands in center on the beeper

}
