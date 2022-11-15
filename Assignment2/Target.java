
/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {
	private static double cmToPx = 72 / 2.54;
	private static double radius1 = 2.54;
	private static double radius2 = 1.65;
	private static double radius3 = 0.76;

	public void run() {
		drowTarget();
	}

	private void drawcircle(double radius, Color color) {
		double startX = getWidth() / 2 - radius;
		double startY = getHeight() / 2 - radius;
		GOval circle = new GOval(startX, startY, 2*radius, 2*radius);
		circle.setFilled(true);
		circle.setColor(color);
		add(circle);
	}//Draws filled circle with given radius and colour.

	private void drowTarget() {
		double r1 = radius1 * cmToPx;
		drawcircle(r1, Color.RED);
		double r2 = radius2 * cmToPx;
		drawcircle(r2, Color.WHITE);
		double r3 = radius3 * cmToPx;
		drawcircle(r3, Color.RED);
	}// Draws target AKA three circle.
}
