import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.*;
import java.util.Scanner;
import java.util.Random;
import java.lang.Math;
	
public class Card implements Serializable {

	public static final int CLUBS 		= 1;
	public static final int DIAMONDS	= 2;
	public static final int HEARTS 		= 3;
	public static final int SPADES 		= 4;

	private int suit;
	private int rank;

	//1 = clubs, 2 = diamonds 3 = hearts 4 = spades
	public Card( int suit, int rank) {

		this.suit = suit;
		this.rank = rank;
	}

	public Card(String cardName) {
		if (cardName.length() > 3 && cardName.length() < 2) {
			System.out.println("Please type in a proper card. The code will bug.");
		}

		String suit = new String( cardName.substring(1) );
		String rank = new String( cardName.substring(0,1) );
		boolean isNumber1 = false;
		boolean isNumber2 = false;

		if ( cardName.length() == 2) {
			try {
				Integer.parseInt( rank );
				isNumber1 = true;
			}
			catch ( Exception e ) {
			}
			try {
				Integer.parseInt( suit );
				isNumber2 = true;
			}
			catch ( Exception e ) {
			}

			if ( isNumber2 ) {
				System.out.println("Please type in a proper card. The code will bug.");
			}

		}
		else {
			if ( Integer.parseInt(cardName.substring(0,2)) != 10 ) {
				System.out.println("Please type in a proper card. The code will bug.");
			}
			else {
				this.rank = 9;
			}
			suit = new String( cardName.substring(2) );
		}

		if ( suit.equalsIgnoreCase("c") ) {
			this.suit = 1;
		}
		else if ( suit.equalsIgnoreCase("d") ) {
			this.suit = 2;
		}
		else if ( suit.equalsIgnoreCase("h") ) {
			this.suit = 3;
		}
		else if ( suit.equalsIgnoreCase("s") ) {
			this.suit = 4;
		}
		else {
			System.out.println("Please type in a proper card. The code will bug.");
		}

		if (isNumber1) {
			this.rank = Integer.parseInt(rank) - 1;
		}
		else {
			if (rank.equalsIgnoreCase("A")) {
				this.rank = 13;
			}
			else if (rank.equalsIgnoreCase("K")) {
				this.rank = 12;
			}
			else if (rank.equalsIgnoreCase("Q")) {
				this.rank = 11;
			}
			else if (rank.equalsIgnoreCase("J")) {
				this.rank = 10;
			}
			else if (rank.equalsIgnoreCase("T")) {
				this.rank = 9;
			}
			else {
				System.out.println("Please type in a proper card. The code will bug.");
			}
		}

	}

	public String toStringFullDeck() {
		String suitSring = null;
		String totalRep = null;
		if (getSuit() == 1) {
			suitSring = "Clubs";
		}
		else if (getSuit() == 2) {
			suitSring = "Diamonds";
		}
		else if (getSuit() == 3) {
			suitSring = "Hearts";
		}
		else {
			suitSring = "Spades";
		}

		if (getRank() == 13) {
			totalRep = "Ace of " + suitSring;
			return totalRep;
		}
		else if (getRank() == 12) {
			totalRep = "King of " + suitSring;
			return totalRep;
		}
		else if (getRank() == 11) {
			totalRep = "Queen of " + suitSring;
			return totalRep;
		}
		else if (getRank() == 10) {
			totalRep = "Jack of " + suitSring;
			return totalRep;
		}
		else if (getRank() == 9) {
			totalRep = "Ten of " + suitSring;
			return totalRep;
		}
		else if (getRank() == 8) {
			totalRep = "Nine of " + suitSring;
			return totalRep;
		}
		else if (getRank() == 7) {
			totalRep = "Eight of " + suitSring;
			return totalRep;
		}
		else if (getRank() == 6) {
			totalRep = "Seven of " + suitSring;
			return totalRep;
		}
		else if (getRank() == 5) {
			totalRep = "Six of " + suitSring;
			return totalRep;
		}
		else if (getRank() == 4) {
			totalRep = "Five of " + suitSring;
			return totalRep;
		}
		else if (getRank() == 3) {
			totalRep = "Four of " + suitSring;
			return totalRep;
		}
		else if (getRank() == 2) {
			totalRep = "Three of " + suitSring;
			return totalRep;
		}
		else {
			totalRep = "Two of " + suitSring;
			return totalRep;
		}

	}

	public String suitConverter() {
		if (getSuit() == 1) {
			return "c";
		}
		else if (getSuit() == 2) {
			return "d";
		}
		else if (getSuit() == 3) {
			return "h";
		}
		else {
			return "s";
		}
	}

	public String singleCharFormat()  {
		if (getRank() == 9) {
			return "T";
		}
		else if (getRank() == 10) {
			return "J";
		}
		else if (getRank() == 11) {
			return "Q";
		}
		else if (getRank() == 12) {
			return "K";
		}
		else if (getRank() == 13) {
			return "A";
		}
		else {
			return "" + (getRank() + 1);
		}
	}

	public void convertCardToShortDeck() {
		this.rank = this.rank - 4;
	}

	public int getSuit() {
		return this.suit;
	}

	public int getRank() {
		return this.rank;
	}

	public int compareRank(Card card) {
		return this.getRank() - card.getRank();
	}

	public int compareSuit(Card card) {
		return this.getSuit() - card.getSuit();
	}

	public void changeSuit(int suitToChangeTo) {
		this.suit = suitToChangeTo;
	} 

	public void changeRank(int rankToChangeTo) {
		this.rank = rankToChangeTo;
	}

	public boolean isEqual(Card card) {
		return ( compareRank(card) == 0 && compareSuit(card) == 0);
	}

}

