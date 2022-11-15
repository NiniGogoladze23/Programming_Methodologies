
/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {

	public void run() {
		goToNewspaper();
		pickNewspaper();
		reposition();
	}

	private void goToNewspaper() {
		while (frontIsClear()) {
			move();
		} // SuperKarel goes to the wall, where the door is located .
		turnRight();
		move();
		turnLeft();
		move();
	} // SuperKarel goes out of his/her house and steps forward to the newspaper.

	private void pickNewspaper() {
		pickBeeper(); // Just picks beeper(in our case: "Newspaper").
	}

	private void reposition() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnRight();
		move();
		turnRight();
	}// Goes back and stands in the starting position.

}
