/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;
	
	public void run() {
		buildPyramid();
	}
	
	private void oneRow(int startX, int startY, int bricksNumber) {
		for(int i=0; i<bricksNumber;i++) {
			GRect brick = new GRect(startX+i*BRICK_WIDTH,startY,BRICK_WIDTH,BRICK_HEIGHT);
			add(brick);
		}
	}//Builds one row based number of bricks.
	
	private void buildPyramid() {
		for(int j = 1; j<=BRICKS_IN_BASE; j++) {
			int bricksNumber = BRICKS_IN_BASE-(j-1);
			int startX = (getWidth()-bricksNumber*BRICK_WIDTH)/2;
			int startY = getHeight()-j*BRICK_HEIGHT;
			oneRow(startX, startY, bricksNumber);
		}
	}//Builds all row based oneRow method
}

