/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		println("Enter values to compute Pythagorean theorem.");
		int a = readInt("a:"); //asks user to input a
		int b = readInt("b:"); //asks user to input b
		double c = Pythagorean(a, b);
		println("c = "+c);//prints result.
	}
	
	private double Pythagorean(int a, int b) {
		double c = Math.sqrt(a*a + b*b);
		return c;
	}// calculates and returns value of c
}
