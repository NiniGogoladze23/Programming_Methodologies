
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = 0;
		while (nPlayers <= 0 || nPlayers > MAX_PLAYERS) {
			nPlayers = dialog.readInt("Enter number of players");
		}
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		/* You fill this in */

		boolean[][] categoriesChosen = new boolean[nPlayers + 1][N_CATEGORIES + 1];
		int[] diceArray = new int[N_DICE];
		int[][] scoresArray = new int[nPlayers + 1][2];
		fillInitially(scoresArray, categoriesChosen);
		for (int i = 0; i < N_SCORING_CATEGORIES; i++) {
			simulateOneDice(scoresArray, diceArray, categoriesChosen);
		}
		winner(scoresArray);

	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

	// Fill arrays initially
	private void fillInitially(int[][] scoresArray, boolean[][] categoriesChosen) {
		for (int j = 0; j < nPlayers; j++) {
			for (int i = 0; i < scoresArray[j + 1].length; i++) {
				scoresArray[j + 1][i] = 0;
			}
			for (int i = 0; i < categoriesChosen[j + 1].length; i++) {
				categoriesChosen[j + 1][i] = false;
			}
			categoriesChosen[j + 1][UPPER_BONUS] = categoriesChosen[j
					+ 1][LOWER_SCORE] = categoriesChosen[j + 1][TOTAL] = categoriesChosen[j + 1][UPPER_SCORE] = true;
		}
	}

	// All 3 rolls for each player
	private void playerRolling(int[] diceArray, int playerIndex) {
		boolean isFirstRoll = true;
		for (int j = 0; j < 3; j++) {
			rollDice(diceArray, isFirstRoll, playerNames[playerIndex - 1], playerIndex);
			isFirstRoll = false;
		}
	}

	// One roll
	private void rollDice(int[] diceArray, boolean isFirstRoll, String player, int playerIndex) {
		messageBeforeRoll(isFirstRoll, player, playerIndex);
		for (int i = 0; i < N_DICE; i++) {
			if (isFirstRoll || display.isDieSelected(i)) {
				diceArray[i] = rgen.nextInt(1, 6);
			}
		}
		display.displayDice(diceArray);
	}

	// Messages before roll
	private void messageBeforeRoll(boolean isFirstRoll, String player, int playerIndex) {
		if (isFirstRoll) {
			display.printMessage(player + "'s Turn! Click  \"Roll Dice\" Button To Roll The Dice.");
			display.waitForPlayerToClickRoll(playerIndex);
		} else {
			display.printMessage("Select The Dice You Wish To Re-Roll And Click  \"Roll again\".");
			display.waitForPlayerToSelectDice();
		}

	}

	// Simulates One Dice From All Players
	private void simulateOneDice(int[][] scoresArray, int[] diceArray, boolean[][] categoriesChosen) {
		for (int i = 0; i < nPlayers; i++) {
			playerRolling(diceArray, i + 1);
			chooseCategoryAndDisplay(scoresArray, categoriesChosen, diceArray, i + 1);
		}
	}

	// Player chooses category and display is updated
	private void chooseCategoryAndDisplay(int[][] scoresArray, boolean[][] categoriesChosen, int[] diceArray,
			int playerIndex) {
		int categoryIndex = chooseCategory(categoriesChosen, playerIndex);
		updateDisplay(scoresArray, playerIndex, diceArray, categoryIndex);
	}

	// Updates display
	private void updateDisplay(int[][] scoresArray, int playerIndex, int[] diceArray, int categoryIndex) {
		int scoresGained = calculateScore(diceArray, categoryIndex);
		display.updateScorecard(categoryIndex, playerIndex, scoresGained);
		if (categoryIndex < 7) {
			scoresArray[playerIndex][0] += scoresGained;
		} else {
			scoresArray[playerIndex][1] += scoresGained;
		}
		int totalScore = scoresArray[playerIndex][0] + scoresArray[playerIndex][1];
		display.updateScorecard(TOTAL, playerIndex, totalScore);
	}

	// Asks player to choose category
	private int chooseCategory(boolean[][] categoriesChosen, int playerIndex) {
		boolean isFirstTry = true;
		while (true) {
			if (isFirstTry) {
				display.printMessage("Select a category for this roll");
				isFirstTry = false;
			} else {
				display.printMessage("You can't choose that category. select another category for this roll");
			}

			int categoryIndex = display.waitForPlayerToSelectCategory();

			if (!categoriesChosen[playerIndex][categoryIndex]) {
				categoriesChosen[playerIndex][categoryIndex] = true;
				return categoryIndex;
			}
		}
	}

	// Checks three of a kind or four of a kind and returns score
	private int threeOrFourOfAKindScore(int[] diceArray, int numOfAKind) {
		int score = 0;
		Map<Integer, Integer> valuesWithCounts = new HashMap<Integer, Integer>();
		for (int i = 0; i < diceArray.length; i++) {
			int diceValue = diceArray[i];
			if (valuesWithCounts.containsKey(diceValue)) {
				int valueCount = valuesWithCounts.get(diceValue);
				valuesWithCounts.put(diceValue, valueCount + 1);
			} else {
				valuesWithCounts.put(diceValue, 1);
			}
		}

		for (int key : valuesWithCounts.keySet()) {
			if (valuesWithCounts.get(key) >= numOfAKind) {
				for (int i = 0; i < diceArray.length; i++) {
					score += diceArray[i];
				}
				break;
			}
		}

		return score;
	}

	// Checks full house and returns score
	private int fullHouseCheckAndScore(int[] diceArray) {
		int score = 0;
		Map<Integer, Integer> valuesWithCounts = new HashMap<Integer, Integer>();
		for (int i = 0; i < diceArray.length; i++) {
			int diceValue = diceArray[i];
			if (valuesWithCounts.containsKey(diceValue)) {
				int valueCount = valuesWithCounts.get(diceValue);
				valuesWithCounts.put(diceValue, valueCount + 1);
			} else {
				valuesWithCounts.put(diceValue, 1);
			}
		}

		boolean canBeFullHouse = false;
		for (int key : valuesWithCounts.keySet()) {
			if (valuesWithCounts.get(key) == 2 || valuesWithCounts.get(key) == 3) {
				canBeFullHouse = true;
			}
			break;
		}

		if (valuesWithCounts.size() == 2 && canBeFullHouse) {
			score = 25;
		}

		return score;
	}

	// Checks straight and gives relevant score
	private int largeOrSmallStraightScore(int[] diceArray, int sequenceLength) {
		int score = 0;
		int[] valuesCounts = new int[6];
		for (int i = 0; i < valuesCounts.length; i++) {
			valuesCounts[i] = 0;
		}
		for (int i = 0; i < diceArray.length; i++) {
			valuesCounts[diceArray[i] - 1] += 1;
		}

		int numOfDifferentDices = countDifferentDices(valuesCounts);

		if (numOfDifferentDices >= sequenceLength && isStraight(valuesCounts, sequenceLength)) {
			score = 10 * (sequenceLength - 1);
		}

		return score;
	}

	// Counts different dices in array
	private int countDifferentDices(int[] valuesCounts) {
		int count = 0;
		for (int i = 0; i < valuesCounts.length; i++) {
			if (valuesCounts[i] != 0) {
				count++;
			}
		}
		return count;
	}

	// Checks if combination of dices is straight(large or small)
	private boolean isStraight(int[] valuesCounts, int sequenceLength) {
		for (int i = 0; i <= valuesCounts.length - (sequenceLength - 1); i++) {
			boolean passed = isSequence(valuesCounts, i, sequenceLength);
			if (passed) {
				return true;
			}
		}
		return false;
	}

	// Checks if there is sequence Of given length
	private boolean isSequence(int[] valuesCounts, int i, int sequenceLength) {
		for (int j = i; j < i + sequenceLength; j++) {
			if (valuesCounts[j] == 0) {
				return false;
			}
		}
		return true;
	}

	// Checks Yahtzee and returns score
	private int yahtzeeCheckAndScore(int[] diceArray) {
		int score = 0;
		Map<Integer, Integer> valuesWithCounts = new HashMap<Integer, Integer>();
		for (int i = 0; i < diceArray.length; i++) {
			int diceValue = diceArray[i];
			if (valuesWithCounts.containsKey(diceValue)) {
				int valueCount = valuesWithCounts.get(diceValue);
				valuesWithCounts.put(diceValue, valueCount + 1);
			} else {
				valuesWithCounts.put(diceValue, 1);
			}
		}

		if (valuesWithCounts.size() == 1) {
			score = 50;
		}

		return score;
	}

	// Calculates Score
	private int calculateScore(int[] diceArray, int categoryIndex) {
		int score = 0;

		if (categoryIndex > 0 && categoryIndex < 7) {
			for (int i = 0; i < diceArray.length; i++) {
				if (diceArray[i] == categoryIndex) {
					score += categoryIndex;
				}
			}
		}

		if (categoryIndex == THREE_OF_A_KIND) {
			score = threeOrFourOfAKindScore(diceArray, 3);
		}

		if (categoryIndex == FOUR_OF_A_KIND) {
			score = threeOrFourOfAKindScore(diceArray, 4);
		}

		if (categoryIndex == FULL_HOUSE) {
			score = fullHouseCheckAndScore(diceArray);
		}

		if (categoryIndex == SMALL_STRAIGHT) {
			score = largeOrSmallStraightScore(diceArray, 4);
		}

		if (categoryIndex == LARGE_STRAIGHT) {
			score = largeOrSmallStraightScore(diceArray, 5);
		}

		if (categoryIndex == YAHTZEE) {
			score = yahtzeeCheckAndScore(diceArray);
		}

		if (categoryIndex == CHANCE) {
			for (int i = 0; i < diceArray.length; i++) {
				score += diceArray[i];
			}
		}

		return score;
	}

	// Announce winners
	private void winner(int[][] scoresArray) {
		int max = 0;
		int winnerIndex = -1;
		ArrayList<Integer> scores = new ArrayList<Integer>();
		for (int i = 0; i < nPlayers; i++) {
			int upperScore = scoresArray[i + 1][0];
			int lowerScore = scoresArray[i + 1][1];
			int upperBonus = 0;
			if (upperScore >= 63) {
				upperBonus = 35;
			}
			int total = upperScore + upperBonus + lowerScore;
			display.updateScorecard(UPPER_SCORE, i + 1, upperScore);
			display.updateScorecard(UPPER_BONUS, i + 1, upperBonus);
			display.updateScorecard(LOWER_SCORE, i + 1, lowerScore);
			display.updateScorecard(TOTAL, i + 1, total);
			if (max < total) {
				max = total;
				winnerIndex = i + 1;
			}
			scores.add(total);
		}
		boolean oneWinner = countWinners(max, scores);
		if (oneWinner) {
			display.printMessage(playerNames[winnerIndex - 1] + " won the game with score " + max);
		} else {
			display.printMessage("Game has more than one winner with score " + max);
		}
	}

	// Counts how many players won the game
	private boolean countWinners(int max, ArrayList<Integer> scores) {
		int count = 0;
		for (int i = 0; i < scores.size(); i++) {
			if (scores.get(i) == max) {
				count++;
			}
		}
		return count == 1;
	}
}
