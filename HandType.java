
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
	
public class HandType implements Serializable {

	private String nameOfHand;
	private Card[] hand;

	public HandType(Card[] fiveCardHand, String typeOfHand ) {

		this.hand = fiveCardHand;
		this.nameOfHand = typeOfHand;

	}

	public Card[] getHand() {
		return hand;
	}

	public String getNameOfHand() {
		return nameOfHand;
	}

	public void changeNameOfHand(String nameToChange) {
		this.nameOfHand = nameToChange;
	}

	public void changeHand(Card[] handToChange) {
		this.hand = handToChange;
	}

	public String toStringHand() {
		return hand[0].singleCharFormat() + hand[0].suitConverter() + hand[1].singleCharFormat() + hand[1].suitConverter() +
			   hand[2].singleCharFormat() + hand[2].suitConverter() + hand[3].singleCharFormat() + hand[3].suitConverter() +
			   hand[4].singleCharFormat() + hand[4].suitConverter();
	}

	//precondition is must be same length
	//return 1 for firstHand victorious, return 2 for secondHand. 0 = tie.
	public int compareHandsFullDeck(HandType otherHand, int highestCardInDeck) {
		int handOneTypeValue = fullDeckTypeValue(this);
		int handTwoTypeValue = fullDeckTypeValue(otherHand);
		if (handOneTypeValue > handTwoTypeValue) {
			return 1;
		}
		else if (handOneTypeValue < handTwoTypeValue) {
			return 2;
		}
		else {
			//now we know we are dealing with the same hand.
			if (handOneTypeValue == 0) {
				return compareHighCard(this, otherHand);
			}
			else if (handOneTypeValue == 1) {
				return compareOnePair(this, otherHand);
			}
			else if (handOneTypeValue == 2) {
				return compareTwoPair(this, otherHand);
			}
			else if (handOneTypeValue == 3) {
				return compareThreeOfAKind(this, otherHand);
			}
			else if (handOneTypeValue == 4) {
				return compareStraight(this, otherHand, highestCardInDeck);
			}
			else if (handOneTypeValue == 5) {
				return compareFlush(this, otherHand);
			}
			else if (handOneTypeValue == 6) {
				return compareFullHouse(this, otherHand);
			}
			else if (handOneTypeValue == 7) {
				return compareFourOfAKind(this, otherHand);
			}
			else {
				return compareStraightFlush(this, otherHand, highestCardInDeck);
			}
		}
	}

	//precondition is must be same length
	//return 1 for firstHand victorious, return 2 for secondHand. 0 = tie.
	public int compareHandsShortDeckOne(HandType otherHand, int highestCardInDeck) {
		int handOneTypeValue = fullDeckTypeValue(this);
		int handTwoTypeValue = fullDeckTypeValue(otherHand);
		if (handOneTypeValue > handTwoTypeValue) {
			return 1;
		}
		else if (handOneTypeValue < handTwoTypeValue) {
			return 2;
		}
		else {
			//now we know we are dealing with the same hand.
			if (handOneTypeValue == 0) {
				return compareHighCard(this, otherHand);
			}
			else if (handOneTypeValue == 1) {
				return compareOnePair(this, otherHand);
			}
			else if (handOneTypeValue == 2) {
				return compareTwoPair(this, otherHand);
			}
			else if (handOneTypeValue == 3) {
				return compareThreeOfAKind(this, otherHand);
			}
			else if (handOneTypeValue == 4) {
				return compareStraight(this, otherHand, highestCardInDeck);
			}
			else if (handOneTypeValue == 5) {
				return compareFullHouse(this, otherHand);
			}
			else if (handOneTypeValue == 6) {
				return compareFlush(this, otherHand);
			}
			else if (handOneTypeValue == 7) {
				return compareFourOfAKind(this, otherHand);
			}
			else {
				return compareStraightFlush(this, otherHand, highestCardInDeck);
			}
		}
	}

	//precondition is must be same length
	//return 1 for firstHand victorious, return 2 for secondHand. 0 = tie.
	public int compareHandsShortDeckTwo(HandType otherHand, int highestCardInDeck) {
		int handOneTypeValue = fullDeckTypeValue(this);
		int handTwoTypeValue = fullDeckTypeValue(otherHand);
		if (handOneTypeValue > handTwoTypeValue) {
			return 1;
		}
		else if (handOneTypeValue < handTwoTypeValue) {
			return 2;
		}
		else {
			//now we know we are dealing with the same hand.
			if (handOneTypeValue == 0) {
				return compareHighCard(this, otherHand);
			}
			else if (handOneTypeValue == 1) {
				return compareOnePair(this, otherHand);
			}
			else if (handOneTypeValue == 2) {
				return compareTwoPair(this, otherHand);
			}
			else if (handOneTypeValue == 3) {
				return compareStraight(this, otherHand, highestCardInDeck);
			}
			else if (handOneTypeValue == 4) {
				return compareThreeOfAKind(this, otherHand);
			}
			else if (handOneTypeValue == 5) {
				return compareFullHouse(this, otherHand);
			}
			else if (handOneTypeValue == 6) {
				return compareFlush(this, otherHand);
			}
			else if (handOneTypeValue == 7) {
				return compareFourOfAKind(this, otherHand);
			}
			else {
				return compareStraightFlush(this, otherHand, highestCardInDeck);
			}
		}
	}

	public int fullDeckTypeValue(HandType hand) {
		if (hand.getNameOfHand().equalsIgnoreCase("HighCard")) {
			return 0;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("OnePair")) {
			return 1;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("TwoPair")) {
			return 2;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("ThreeOfAKind")) {
			return 3;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("Straight")) {
			return 4;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("Flush")) {
			return 5;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("FullHouse")) {
			return 6;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("FourOfAKind")) {
			return 7;
		}
		else {
			return 8;
		}
	}

	public int shortDeckTypeValueOne(HandType hand) {
		if (hand.getNameOfHand().equalsIgnoreCase("HighCard")) {
			return 0;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("OnePair")) {
			return 1;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("TwoPair")) {
			return 2;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("ThreeOfAKind")) {
			return 3;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("Straight")) {
			return 4;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("FullHouse")) {
			return 5;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("Flush")) {
			return 6;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("FourOfAKind")) {
			return 7;
		}
		else {
			return 8;
		}
	}

	public int shortDeckTypeValueTwo(HandType hand) {
		if (hand.getNameOfHand().equalsIgnoreCase("HighCard")) {
			return 0;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("OnePair")) {
			return 1;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("TwoPair")) {
			return 2;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("Straight")) {
			return 3;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("ThreeOfAKind")) {
			return 4;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("FullHouse")) {
			return 5;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("Flush")) {
			return 6;
		}
		else if (hand.getNameOfHand().equalsIgnoreCase("FourOfAKind")) {
			return 7;
		}
		else {
			return 8;
		}
	}

	//precondition is must be same length
	//return 1 for firstHand victorious, return 2 for secondHand. 0 = tie.
	public int compareHighCard(HandType firstHand, HandType secondHand) {
		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		Card[] handOne = eval.sortHandByRank(firstHand.getHand());
		Card[] handTwo = eval.sortHandByRank(secondHand.getHand());

		for (int i = 0; i < handOne.length; i++) {
			if (handOne[i].compareRank(handTwo[i]) > 0) {
				return 1;
			}
			if (handOne[i].compareRank(handTwo[i]) < 0) {
				return 2;
			}
		}
		return 0;
	}

	//return 1 for firstHand victorious, return 2 for secondHand. 0 = tie.
	public int compareOnePair(HandType firstHand, HandType secondHand) {
		//It should already be displayed in the exact correct order.
		Card[] handOne = firstHand.getHand();
		Card[] handTwo = secondHand.getHand();

		for (int i = 0; i < handOne.length; i++) {
			if (handOne[i].compareRank(handTwo[i]) > 0) {
				return 1;
			}
			if (handOne[i].compareRank(handTwo[i]) < 0) {
				return 2;
			}
		}
		return 0;

	}

	//return 1 for firstHand victorious, return 2 for secondHand. 0 = tie.
	public int compareTwoPair(HandType firstHand, HandType secondHand) {
		//It should already be displayed in the exact correct order. Using the onePair is fine here.
		return compareOnePair(firstHand, secondHand);
	}

	//return 1 for firstHand victorious, return 2 for secondHand. 0 = tie
	public int compareThreeOfAKind(HandType firstHand, HandType secondHand) {
		//It should already be displayed in the exact correct order. Using the onePair is fine here.
		return compareOnePair(firstHand, secondHand);
	}

	//return 1 for firstHand victorious, return 2 for secondHand. 0 = tie.
	public int compareStraight(HandType firstHand, HandType secondHand, int highestCardInDeck) {
		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		int firstTotal = eval.addTotalRanks(firstHand.getHand(), highestCardInDeck);
		int secondTotal = eval.addTotalRanks(secondHand.getHand(), highestCardInDeck);
		if (firstTotal > secondTotal) {
			return 1;
		}
		else if ( firstTotal < secondTotal) {
			return 2;
		}
		else {
			return 0;
		}
	}

	//return 1 for firstHand victorious, return 2 for secondHand. 0 = tie.
	public int compareFlush(HandType firstHand, HandType secondHand) {
		//It should already be displayed in the exact correct order. Using the onePair is fine here.
		return compareOnePair(firstHand, secondHand);
	}

	//return 1 for firstHand victorious, return 2 for secondHand. 0 = tie.
	public int compareFullHouse(HandType firstHand, HandType secondHand) {
		//It should already be displayed in the exact correct order. Using the onePair is fine here.
		return compareOnePair(firstHand, secondHand);
	}

	//return 1 for firstHand victorious, return 2 for secondHand. 0 = tie.
	public int compareFourOfAKind(HandType firstHand, HandType secondHand) {
		//It should already be displayed in the exact correct order. Using the onePair is fine here.
		return compareOnePair(firstHand, secondHand);
	}

	//return 1 for firstHand victorious, return 2 for secondHand. 0 = tie.
	public int compareStraightFlush(HandType firstHand, HandType secondHand, int highestCardInDeck) {
		return compareStraight(firstHand, secondHand, highestCardInDeck);
	}	

}