
/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		int n = readInt("Enter a number: ");
		int counter = 0;
		counter = doHailstone(n, counter);
		println("The process took " + counter + " to reach 1");
	}

	private int forEvenNumber(int n) {
		int n1 = n;
		n = n1 / 2;
		println(n1 + " is even so i take half: " + n);
		return n;
	}//If given number is even, takes half and returns

	private int forOddNumber(int n) {
		int n1= n;
		n=3*n1+1;
		println(n1+ "is odd, so i make 3n + 1: "+n);
		return n;
	}//If given number is odd, multiplies by 3 and adds 1.
	
	private int doHailstone(int n, int counter) {
		while (n != 1) {
			if (n % 2 == 0) {
				n = forEvenNumber(n);
				counter += 1;
			} else {
				n = forOddNumber(n);
				counter += 1;
			}
		}
		return counter;
	}// counts steps to reach 1.
}
