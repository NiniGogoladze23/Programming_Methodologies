/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {
	public static final int NTURNS = 8;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private int counter = NTURNS;
	
	HangmanLexicon HangmanLexicon = new HangmanLexicon();
	private int wordcount = HangmanLexicon.getWordCount();
	private int randWorNum =rgen.nextInt(0,wordcount-1);
	private String randWord = HangmanLexicon.getWord(randWorNum);
	
	private HangmanCanvas canvas;

	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}
	
	public void run() {
		canvas.reset();
    	this.setSize(800, 800);
    	play();
	}
   // creates hided word with "-" signs for every character in given random word
    private String getHidedWord() {
    	int wordLength = randWord.length();
    	String hidedWord = "";
		for (int i = 0; i < wordLength; i++) {
    		hidedWord +="-";
    	}
    	return hidedWord;
    }
    //Prints welcome text and hided word before user inputs a value
    private void welcomeText(String hidedWord) {
    	println("Welcome to Hangman!");
    	println("The word now looks like this: "+hidedWord);
    	println("You have "+ counter + " guesses left");
    	canvas.displayWord(hidedWord);
    	}
    //Asks user to input character and checks that it is a letter
    private char readChar() {
    	String givenCh = readLine("Your guess: ");
    	String upperCh = givenCh.toUpperCase();
		if(givenCh.length()!=1) {
			println("You can guess only one letter");
		}else if(upperCh.charAt(0)<'A' || upperCh.charAt(0)>'Z') {
			println("Invalid character, try again.");
		}else {
			char ch = upperCh.charAt(0);
			return ch;
		}
		return 0;
    }
    //prints how many guesses has user left
    private void guessesLeft() {
    	if(counter>1) {
    		println("You have "+ counter + " guesses left");
    	}else if(counter ==1) {
    		println("You have only one guess left");
    	}else {
    		println("You're completely hung.");
    		println("The word was: "+ randWord);
    		println("You lose.");
    	}
    }
    /* Checks whether the initial word contains a user-guessed letter. 
     * If initial word contains given character it shows up in hidden word.
     * If initial word does not contain it, the number of user attempts will be reduced by one*/
    private String guessingCheck(char ch, String hidedWord) {
		if(randWord.indexOf(ch)==-1) {
			println("There are no " + ch + "'s in the word");
			println("The word now looks like this: " +hidedWord);
			counter --;
			guessesLeft();
			canvas.noteIncorrectGuess(ch);
		}else {
			String newHidedWord = "";
			for(int i=0; i<randWord.length(); i++) {
				if(ch == randWord.charAt(i)) {
					newHidedWord = newHidedWord + ch;
				}else {
					newHidedWord = newHidedWord + hidedWord.charAt(i);
				}
			}
			println("The guess is correct");
			println("The word now looks like this: " +newHidedWord);
			guessesLeft();
			canvas.displayWord(newHidedWord);
			return newHidedWord;
		}
		return hidedWord;
	}
    //playing logic (using previous methods)
    private void play() {
    	String hidedWord = getHidedWord();
    	welcomeText(hidedWord);
    	while(counter>0) {
    		char ch = readChar();
    		if(ch !=0) {
    			hidedWord = guessingCheck(ch, hidedWord);
    		}
    		if(hidedWord.indexOf("-")==-1) {
    			println("That guess is correct.");
    			println("You guessed the word: "+ hidedWord);
    			println("You win.");
    			break;
    		}
    	}
    }
}
