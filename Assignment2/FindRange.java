
/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	private static final int SENTINEL = 0;

	public void run() {
		println("This program finds the largest and smallest numbers");
		int x = requireVal();
		findRange(x); // calls findRange method with required parameters
	}

	private int requireVal() {
		int val = readInt("?");
		return val;
		// requireVal method asks user to input numbers and returns them.

	}

	private void findRange(int val) {
		int min = val;
		int max = val;
		// (if first value is SENTINEL) when programme has no values, asks for them.
		if (val == SENTINEL)
			println("Please, enter values");
		else {
			// when programme has values. (first is not equal SENTINEL).
			while (true) {

				if (val > max && val != SENTINEL) {
					max = val;

				}
				if (val < min && val != SENTINEL) {
					min = val;
				}
				// when value equals sentinel programme stops.
				val = readInt("?");
				if (val == SENTINEL)
					break;
			}
			println("largest:" + max);
			println("smallest:" + min);
			// prints the maximum and minimum from given numbers.

		}
	}
}

