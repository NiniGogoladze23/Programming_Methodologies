
/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {

	public static final double RECT_WIDTH = 180;
	public static final double RECT_HEIGHT = 60;
	public static final double HORIZONTAL_GAP = 40;
	public static final double VERTICAL_GAP = 60;

	public void run() {
		rectsWithLabels();
		rectsConnection();
	}

	private void drowRect(double startX, double startY) {
		GRect rect = new GRect(startX, startY, RECT_WIDTH, RECT_HEIGHT);
		add(rect);
	}

	private void rectsWithLabels() {
		
		//Draws mother rectangle.
		double x1 = (getWidth() - RECT_WIDTH) / 2;
		double y1 = (getHeight() - 2 * RECT_HEIGHT - VERTICAL_GAP) / 2;
		GLabel label1 = new GLabel("Program");
		double label1X = x1 + (RECT_WIDTH-label1.getWidth())/2;
		double label1Y = y1 + (RECT_HEIGHT + label1.getAscent())/2;
		drowRect(x1, y1);
		label1.setLocation(label1X,label1Y);
		add(label1);

		
		//Draws other rectangles
		double y = y1 + RECT_HEIGHT + VERTICAL_GAP;
		double bellowLineLength = 3 * RECT_WIDTH + 2 * HORIZONTAL_GAP;
		
		double x2 = (getWidth() - bellowLineLength) / 2;
		GLabel label2 = new GLabel("GraphicsProgram");
		double label2X = x2 + (RECT_WIDTH-label2.getWidth())/2;
		double label2Y = y + (RECT_HEIGHT + label2.getAscent())/2;
		drowRect(x2, y);
		label2.setLocation(label2X,label2Y);
		add(label2);
		
		
		double x3 = x2 + RECT_WIDTH + HORIZONTAL_GAP;
		GLabel label3 = new GLabel("ConsoleProgram");
		double label3X = x3 + (RECT_WIDTH-label3.getWidth())/2;
		double label3Y = y + (RECT_HEIGHT + label3.getAscent())/2;
		drowRect(x3, y);
		label3.setLocation(label3X,label3Y);
		add(label3);
		
		double x4 = x3 + RECT_WIDTH + HORIZONTAL_GAP;
		GLabel label4 = new GLabel("DialogProgram");
		double label4X = x4 + (RECT_WIDTH-label4.getWidth())/2;
		double label4Y = y + (RECT_HEIGHT + label4.getAscent())/2;
		drowRect(x4, y);
		label4.setLocation(label4X,label4Y);
		add(label4);
	}//Draws all rectangle and puts their labels in the middle of all of them.
	

	private void drawLine(double startX, double startY, double endX, double endY) {
		GLine line = new GLine(startX, startY, endX, endY);
		add(line);
	}// Draws line based given start and end points.

	private void rectsConnection() {
		double startX = getWidth() / 2;
		double startY = (getHeight() - VERTICAL_GAP) / 2;
		double endY = startY + VERTICAL_GAP;
		double x2 = startX - RECT_WIDTH - HORIZONTAL_GAP;
		double x4 = startX + RECT_WIDTH + HORIZONTAL_GAP;

		drawLine(startX, startY, x2, endY);
		drawLine(startX, startY, startX, endY);
		drawLine(startX, startY, x4, endY);

	}// connects mother rectangle to all three child rectangles.
	
}
