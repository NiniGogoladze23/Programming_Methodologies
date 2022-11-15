
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 1;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 1;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;
	/** pause in milliseconds */
	private static final int DELAY = 10;

	/** GObects we need in game */
	private GRect Rect;
	private GRect Paddle;
	private GOval Ball;

	/** need for velocities */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private double vx; // horizontal velocity of ball
	private double vy; // vertical velocity of ball

	/** counters to check if we win on not */
	int bricksCounter = NBRICKS_PER_ROW * NBRICK_ROWS;
	int turnsCounter = NTURNS;

	/** Initialisation */
	public void init() {
		buildBricks();
		buildPaddle();
	}

	/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		while (turnsCounter > 0 && bricksCounter > 0) {
			addBall();
//			waitForClick();
			initialVelocity();
			ballMoving();
		}
		winOrLoose();
	}

	// Builds all rows
	private void buildBricks() {
		for (int row = 0; row < NBRICK_ROWS; row++) {
			oneRowOfBricks(row);
		}
	}

	// Builds one row of bricks
	private void oneRowOfBricks(int row) {
		int wallSepparation = (WIDTH - NBRICKS_PER_ROW * BRICK_WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / 2;
		for (int i = 0; i < NBRICKS_PER_ROW; i++) {
			Rect = new GRect(wallSepparation + i * (BRICK_WIDTH + BRICK_SEP),
					BRICK_Y_OFFSET + row * (BRICK_HEIGHT + BRICK_SEP), BRICK_WIDTH, BRICK_HEIGHT);
			Rect.setFilled(true);
			if (row < 2) {
				Rect.setColor(Color.RED);
			} else if (row < 4) {
				Rect.setColor(Color.ORANGE);
			} else if (row < 6) {
				Rect.setColor(Color.YELLOW);
			} else if (row < 8) {
				Rect.setColor(Color.GREEN);
			} else {
				Rect.setColor(Color.CYAN);
			}
			add(Rect);
		}
	}

	// Builds paddle with MouseListeners
	private void buildPaddle() {
		Paddle = new GRect((WIDTH - PADDLE_WIDTH) / 2, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT, PADDLE_WIDTH,
				PADDLE_HEIGHT);
		Paddle.setFilled(true);
		add(Paddle);
		addMouseListeners();
	}

	// Paddle movement logic (according to mouse movement)
	public void mouseMoved(MouseEvent e) {
		int paddleLocationX;
		if (e.getX() <= PADDLE_WIDTH / 2) {
			paddleLocationX = 0;
		} else if (e.getX() >= WIDTH - PADDLE_WIDTH / 2) {
			paddleLocationX = WIDTH - PADDLE_WIDTH;
		} else {
			paddleLocationX = e.getX() - PADDLE_WIDTH / 2;
		}

		Paddle.setLocation(paddleLocationX, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
	}

	// Adds ball in the centre
	private void addBall() {
		int ballX = WIDTH / 2 - BALL_RADIUS;
		int ballY = HEIGHT / 2 - BALL_RADIUS;
		Ball = new GOval(ballX, ballY, BALL_RADIUS, BALL_RADIUS);
		Ball.setFilled(true);
		add(Ball);
	}

	// Gives initial velocity of ball
	private void initialVelocity() {
		vy = 3.0;
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		}
	}

	// Determines the movement of the ball across the window
	private void ballMoving() {
		while (Ball.getY() < HEIGHT - 2 * BALL_RADIUS) {
			if (Ball.getY() <= 0) {
				vy = -vy;
			}
			if (Ball.getX() <= 0 || Ball.getX() >= WIDTH - 2 * BALL_RADIUS) {
				vx = -vx;
			}
			Ball.move(vx, vy);
			pause(DELAY);
			ballColliding();

			if (bricksCounter == 0) {
				break;
			}
		}
		// Collision with the bottom wall
		if (Ball.getY() >= HEIGHT - 2 * BALL_RADIUS) {
			turnsCounter--;
			remove(Ball);
		}
	}

	// Getting colliding objects (Bricks and paddle)
	private GObject getCollidingObject() {
		if (getElementAt(Ball.getX() + BALL_RADIUS, Ball.getY()) != null
				&& getElementAt(Ball.getX() + BALL_RADIUS, Ball.getY()) != Ball) {
			vy = -vy;
			return (getElementAt(Ball.getX() + BALL_RADIUS, Ball.getY() - 1));
		} else if (getElementAt(Ball.getX() + BALL_RADIUS, Ball.getY() + 2 * BALL_RADIUS) != null
				&& getElementAt(Ball.getX() + BALL_RADIUS, Ball.getY()) != Ball) {
			vy = -vy;
			return (getElementAt(Ball.getX() + BALL_RADIUS, Ball.getY() + 2 * BALL_RADIUS));
		} else if (getElementAt(Ball.getX(), Ball.getY() + BALL_RADIUS) != null
				&& getElementAt(Ball.getX() + BALL_RADIUS, Ball.getY()) != Ball) {
			vx = -vx;
			return (getElementAt(Ball.getX(), Ball.getY() + BALL_RADIUS));
		} else if (getElementAt(Ball.getX() + 2 * BALL_RADIUS, Ball.getY() + BALL_RADIUS) != null
				&& getElementAt(Ball.getX() + BALL_RADIUS, Ball.getY()) != Ball) {
			vx = -vx;
			return (getElementAt(Ball.getX() + 2 * BALL_RADIUS, Ball.getY() + BALL_RADIUS));
		} else
			return null;

	}

	// If there is a collision with a brick, it will disappear and consequently we
	// will be left with one less brick
	private void ballColliding() {
		GObject collider = getCollidingObject();
		if (collider != null && collider != Paddle) {
			remove(collider);
			bricksCounter--;
		}
		// if ball collides to sides of the paddle
		if (collider == Paddle) {
			if (Ball.getY() < HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT / 2) {
				Ball.setLocation(Ball.getX(), Paddle.getY() - 2 * BALL_RADIUS);
			} else {
				vy = Math.abs(vy);
				vx = -Math.abs(vx);
			}
		}
	}

	// returns winning or loosing title
	private void winOrLoose() {
		if (bricksCounter == 0) {
			reportYouWin();
		}
		if (turnsCounter == 0) {
			reportGameOver();
		}
	}

	// if ball fell three times
	private void reportGameOver() {
		GLabel gameOver = new GLabel("Game Over");
		gameOver.setFont("sansSerif-36");
		gameOver.setLocation(WIDTH / 2 - gameOver.getWidth() / 2, HEIGHT / 2 - gameOver.getAscent() / 2);
		gameOver.setColor(Color.RED);
		add(gameOver);
	}

	// if all rectangles are removed
	private void reportYouWin() {
		removeAll();
		GLabel youWin = new GLabel("You Win");
		youWin.setFont("sansSerif-36");
		youWin.setLocation(WIDTH / 2 - youWin.getWidth() / 2, HEIGHT / 2 - youWin.getAscent() / 2);
		youWin.setColor(Color.GREEN);
		add(youWin);
	}

}
