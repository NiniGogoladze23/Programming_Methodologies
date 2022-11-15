
/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import acm.util.*;
import acmx.export.java.util.ArrayList;

public class HangmanLexicon {
	// transforming text file to array list
	public ArrayList readText() {
		ArrayList lexicon = new ArrayList();
		BufferedReader rd = null;
		try {
			rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
		} catch (FileNotFoundException ex) {
		}
		try {
			while (true) {
				String st = rd.readLine();
				if (st == null) {
					break;
				}
				lexicon.add(st);
			}
			rd.close();
		} catch (IOException e) {
		}
		return lexicon;
	}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		ArrayList lexicon = readText();
		return lexicon.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		ArrayList lexicon = readText();
		return (String) lexicon.get(index);
	}

	/** initial test words */
//	public String getWord(int index) {
//		switch (index) {
//			case 0: return "BUOY";
//			case 1: return "COMPUTER";
//			case 2: return "CONNOISSEUR";
//			case 3: return "DEHYDRATE";
//			case 4: return "FUZZY";
//			case 5: return "HUBBUB";
//			case 6: return "KEYHOLE";
//			case 7: return "QUAGMIRE";
//			case 8: return "SLITHER";
//			case 9: return "ZIRCON";
//			default: throw new ErrorException("getWord: Illegal index");
//		}
//	};

}
