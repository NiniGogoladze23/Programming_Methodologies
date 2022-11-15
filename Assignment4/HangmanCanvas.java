
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

	int scaffold_upward_shift = (getHeight() - SCAFFOLD_HEIGHT) / 3;
	int upper_space = (getHeight() - UPPER_ARM_LENGTH) / 2 - scaffold_upward_shift;
	private String incorrectLetters = "";

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		buildScaffold();
	}

// Builds the initial drawing, without man.
	private void buildScaffold() {
		double scafoldX = getWidth() / 2 - BEAM_LENGTH;
		double scafoldY1 = upper_space;
		double scafoldY2 = scafoldY1 + SCAFFOLD_HEIGHT;

		GLine scaffold = new GLine(scafoldX, scafoldY1, scafoldX, scafoldY2);
		add(scaffold);

		double beamX2 = getWidth() / 2;
		GLine beam = new GLine(scafoldX, scafoldY1, beamX2, scafoldY1);
		add(beam);

		double ropeY2 = scafoldY1 + ROPE_LENGTH;
		GLine rope = new GLine(beamX2, scafoldY1, beamX2, ropeY2);
		add(rope);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		/* You fill this in */
		double wordX = getWidth() / 5;
		double wordY = SCAFFOLD_HEIGHT + 2 * upper_space;

		GLabel hidedWord = new GLabel(word, wordX, wordY);
		hidedWord.setFont("Halvetica-25");
		if (getElementAt(wordX, wordY) != null) {
			remove(getElementAt(wordX, wordY));
		}
		add(hidedWord);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user. Calling
	 * this method causes the next body part to appear on the scaffold and adds the
	 * letter to the list of incorrect guesses that appears at the bottom of the
	 * window.
	 */
	public void noteIncorrectGuess(char letter) {
		double lettersX = getWidth() / 5;
		double lettersY = SCAFFOLD_HEIGHT + 3 * upper_space;

		incorrectLetters = incorrectLetters + letter;
		GLabel incorrectGuesses = new GLabel(incorrectLetters, lettersX, lettersY);
		if (getElementAt(lettersX, lettersY) != null) {
			remove(getElementAt(lettersX, lettersY));
		}
		add(incorrectGuesses);

		if (incorrectLetters.length() == 1) {
			drawHead();
		}
		if (incorrectLetters.length() == 2) {
			drawBody();
		}
		if (incorrectLetters.length() == 3) {
			drawLeftArm();
		}
		if (incorrectLetters.length() == 4) {
			drawRightArm();
		}
		if (incorrectLetters.length() == 5) {
			drawLeftLeg();
		}
		if (incorrectLetters.length() == 6) {
			drawRightLeg();
		}
		if (incorrectLetters.length() == 7) {
			drawLeftFoot();
		}
		if (incorrectLetters.length() == 8) {
			drawRightFoot();
		}
	}

	// Draws head after first incorrect letter.
	private void drawHead() {
		double headX = getWidth() / 2 - HEAD_RADIUS;
		double headY = upper_space + ROPE_LENGTH;
		GOval head = new GOval(headX, headY, 2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		add(head);
	}

	// Draws body after second incorrect letter.
	private void drawBody() {
		double bodyX = getWidth() / 2;
		double bodyY1 = upper_space + ROPE_LENGTH + 2 * HEAD_RADIUS;
		double bodyY2 = bodyY1 + BODY_LENGTH;
		GLine body = new GLine(bodyX, bodyY1, bodyX, bodyY2);
		add(body);
	}

	// Draws left arm after third incorrect letter.
	private void drawLeftArm() {
		double armX1 = getWidth() / 2;
		double armX2 = armX1 - UPPER_ARM_LENGTH;
		double armY = upper_space + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
		GLine leftArm = new GLine(armX1, armY, armX2, armY);
		add(leftArm);
		GLine leftHand = new GLine(armX2, armY, armX2, armY + LOWER_ARM_LENGTH);
		add(leftHand);
	}

	// Draws right arm after fourth incorrect letter.
	private void drawRightArm() {
		double armX1 = getWidth() / 2;
		double armX2 = armX1 + UPPER_ARM_LENGTH;
		double armY = upper_space + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
		GLine rightArm = new GLine(armX1, armY, armX2, armY);
		add(rightArm);
		GLine rightHand = new GLine(armX2, armY, armX2, armY + LOWER_ARM_LENGTH);
		add(rightHand);
	}

	// Draws left leg after fifth incorrect letter.
	private void drawLeftLeg() {
		double hipX1 = getWidth() / 2;
		double hipX2 = hipX1 - HIP_WIDTH;
		double hipY = upper_space + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH;
		GLine leftHip = new GLine(hipX1, hipY, hipX2, hipY);
		add(leftHip);
		GLine leftLeg = new GLine(hipX2, hipY, hipX2, hipY + LEG_LENGTH);
		add(leftLeg);
	}

	// Draws right leg after sixth incorrect letter.
	private void drawRightLeg() {
		double hipX1 = getWidth() / 2;
		double hipX2 = hipX1 + HIP_WIDTH;
		double hipY = upper_space + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH;
		GLine rightHip = new GLine(hipX1, hipY, hipX2, hipY);
		add(rightHip);
		GLine rightLeg = new GLine(hipX2, hipY, hipX2, hipY + LEG_LENGTH);
		add(rightLeg);
	}

	// Draws left foot after seventh incorrect letter.
	private void drawLeftFoot() {
		double footX1 = getWidth() / 2 - HIP_WIDTH;
		double footX2 = footX1 - FOOT_LENGTH;
		double footY = upper_space + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH;
		GLine leftFoot = new GLine(footX1, footY, footX2, footY);
		add(leftFoot);
	}

	// Draws right foot after eighth incorrect letter.
	private void drawRightFoot() {
		double footX1 = getWidth() / 2 + HIP_WIDTH;
		double footX2 = footX1 + FOOT_LENGTH;
		double footY = upper_space + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH;
		GLine rightFoot = new GLine(footX1, footY, footX2, footY);
		add(rightFoot);
	}

}
