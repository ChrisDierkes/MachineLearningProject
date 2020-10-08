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
import java.security.SecureRandom;
	
public class TypeOfHandEvaluation {

	public boolean isHighCard(Card[] completeBoardList) {
		completeBoardList = sortHandByRank(completeBoardList);
		for (int i = 0; i < completeBoardList.length - 1; i++) {
			if (completeBoardList[i].compareRank(completeBoardList[i + 1]) == 0) {
				return false;
			}
		}
		return true;
	}

	public int numberOfOccurencesMostPopularCardAppears(Card[] completeBoardList) {
		completeBoardList = sortHandByRank(completeBoardList);

		int numberOfOccurences = 1;
		int currentOccurence = 1;
		for (int i = 0; i < completeBoardList.length - 1; i++) {
			if (completeBoardList[i].compareRank(completeBoardList[i + 1]) == 0) {
				currentOccurence++;
			}
			else {
				if (currentOccurence > numberOfOccurences) {
					numberOfOccurences = currentOccurence;
				}
				currentOccurence = 1;
			}
		}
		if (currentOccurence > numberOfOccurences) {
			numberOfOccurences = currentOccurence;
		}
		return numberOfOccurences;
	}

	public int countNumberOfPairs(Card[] completeBoardList) {
		completeBoardList = sortHandByRank(completeBoardList);

		int i = 0;
		int numberOfPairs = 0;
		while (i < completeBoardList.length - 1) {
			if (completeBoardList[i].compareRank(completeBoardList[i + 1]) == 0) {
				numberOfPairs++;
				i = i + 2;
			}
			else {
				i++;
			}
		}
		return numberOfPairs;

	}

	public int countNumberOfCardsInARow(Card[] completeBoardList, int rankOfHighestCardInDeck) {

		completeBoardList = deleteDuplicateRanks(completeBoardList);

		for (int i = 0; i < completeBoardList.length; i++) {
			if (completeBoardList[i].getRank() == rankOfHighestCardInDeck) {
				completeBoardList[i].changeRank(0);
			}
		}
		completeBoardList = sortHandByRank(completeBoardList);

		int highestNumbersInARow = 1;
		int currentHighestNumberInARow = 1;

		for (int i = 0; i < completeBoardList.length - 1; i++) {
			if (completeBoardList[i].compareRank(completeBoardList[i+1]) == 1) {
				currentHighestNumberInARow++;
			}
			else {
				if (currentHighestNumberInARow > highestNumbersInARow) {
					highestNumbersInARow = currentHighestNumberInARow;
				}
				currentHighestNumberInARow = 1;
			}
		}
		if (currentHighestNumberInARow > highestNumbersInARow) {
			highestNumbersInARow = currentHighestNumberInARow;
		}


		//The above will change all aces to a value of 0, then calculate straights, and the below will keep aces as their same value.

		currentHighestNumberInARow = 1;
		for (int i = 0; i < completeBoardList.length; i++) {
			if (completeBoardList[i].getRank() == 0) {
				completeBoardList[i].changeRank(rankOfHighestCardInDeck);
			}
		}

		completeBoardList = sortHandByRank(completeBoardList);

		for (int i = 0; i < completeBoardList.length - 1; i++) {
			if (completeBoardList[i].compareRank(completeBoardList[i+1]) == 1) {
				currentHighestNumberInARow++;
			}
			else {
				if (currentHighestNumberInARow > highestNumbersInARow) {
					highestNumbersInARow = currentHighestNumberInARow;
				}
				currentHighestNumberInARow = 1;
			}
		}

		if (currentHighestNumberInARow > highestNumbersInARow) {
			highestNumbersInARow = currentHighestNumberInARow;
		}

		return highestNumbersInARow;

	}

	public int numberOfOccurencesOfHighestSuit(Card[] completeBoardList) {
		completeBoardList = sortHandBySuit(completeBoardList);
		int currentNumberOfCardsOfSameSuit = 1;
		int highestNumberOfCardsOfSameSuit = 1;
		for (int i = 0; i < completeBoardList.length - 1; i++) {
			if (completeBoardList[i].compareSuit(completeBoardList[i + 1]) == 0) {
				currentNumberOfCardsOfSameSuit++;
			}
			else {
				if ( currentNumberOfCardsOfSameSuit > highestNumberOfCardsOfSameSuit ) {
					highestNumberOfCardsOfSameSuit = currentNumberOfCardsOfSameSuit;
				}
				currentNumberOfCardsOfSameSuit = 1;
			}
		}
		if (currentNumberOfCardsOfSameSuit > highestNumberOfCardsOfSameSuit) {
			highestNumberOfCardsOfSameSuit = currentNumberOfCardsOfSameSuit;
		}
		return highestNumberOfCardsOfSameSuit;


	} 

	public int rankOfMostPopularSuit(Card[] completeBoardList) {
		completeBoardList = sortHandBySuit(completeBoardList);
		int numberToHit = numberOfOccurencesOfHighestSuit(completeBoardList);
		if (completeBoardList.length == 1) {
			return completeBoardList[0].getSuit();
		}
		int numberOfTimesSuitAppeared = 1;
		for (int i = 0; i < completeBoardList.length - 1; i++) {
			if (completeBoardList[i].compareSuit(completeBoardList[i + 1]) == 0) {
				numberOfTimesSuitAppeared++;
				if (numberToHit == numberOfTimesSuitAppeared) {
					return completeBoardList[i].getSuit();
				}
			}
			else {
				if (numberToHit == numberOfTimesSuitAppeared) {
					return completeBoardList[i].getSuit();
				}
				numberOfTimesSuitAppeared = 1;
			}
		}
		return - 1;


	}

	public int rankOfMostPopularCard(Card[] completeBoardList) {
		completeBoardList = sortHandByRank(completeBoardList);
		int numberToHit = numberOfOccurencesMostPopularCardAppears(completeBoardList);
		if (completeBoardList.length == 1) {
			return completeBoardList[0].getRank();
		}
		int numberOfTimesRankedAppeared = 1;
		for (int i = 0; i < completeBoardList.length - 1; i++) {
			if (completeBoardList[i].compareRank(completeBoardList[i + 1]) == 0) {
				numberOfTimesRankedAppeared++;
				if (numberToHit == numberOfTimesRankedAppeared) {
					return completeBoardList[i].getRank();
				}
			}
			else {
				if (numberToHit == numberOfTimesRankedAppeared) {
					return completeBoardList[i].getRank();
				}
				numberOfTimesRankedAppeared = 1;
			}
		}
		return - 1;


	}

	//helper method for computing straights only. This method is only getting called when there is a straight.
	public int addTotalRanks(Card[] completeBoardList, int rankOfHighestCardInDeck) {
		completeBoardList = sortHandByRank(completeBoardList);
		int total = 0;
		for (int i = 0; i < completeBoardList.length; i++) {
			total = total + completeBoardList[i].getRank();
		}
		if (completeBoardList[0].getRank() - completeBoardList[1].getRank() > 4) {
			total = total - rankOfHighestCardInDeck;
		}
		return total;
	}

	//precondition is ONLY 5 cards in list
	public boolean isStraightFlush( Card[] completeBoardList, int highestRankInDeck) {

		completeBoardList = sortHandByRank(completeBoardList);
		if (completeBoardList.length == 5) {
			if ( numberOfOccurencesOfHighestSuit(completeBoardList) == 5 && countNumberOfCardsInARow(completeBoardList, highestRankInDeck) == 5) {
				return true;
			}
		}
		if (completeBoardList.length == 6) {
			for (int i = 0; i < completeBoardList.length; i++) {
				Card[] tempCard = deleteACard(completeBoardList, completeBoardList[i]);
				if ( numberOfOccurencesOfHighestSuit(tempCard) == 5 && countNumberOfCardsInARow(tempCard, highestRankInDeck) == 5) {
					return true;
				}
			}
		}
		else {
			for (int i = 0; i < completeBoardList.length - 1; i++) {
				for (int j = i + 1; j < completeBoardList.length; j++) {
					Card[] tempCard1 = deleteACard(completeBoardList, completeBoardList[i]);
					Card[] tempCard = deleteACard(tempCard1, completeBoardList[j]);
					if ( numberOfOccurencesOfHighestSuit(tempCard) == 5 && countNumberOfCardsInARow(tempCard, highestRankInDeck) == 5) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public Card getHighestRankedCard(Card[] completeBoardList) {
		completeBoardList = sortHandByRank(completeBoardList);
		return completeBoardList[0];
	}

	public Card[] sortWithMostPopularAppearancesFirst( Card[] completeBoardList ) {
		Card[] orderOfHand = new Card[completeBoardList.length];
		int rankOfPopCard = rankOfMostPopularCard(completeBoardList);

		int j = 0;
		while (j < orderOfHand.length ) {
			for (int i = 0; i < completeBoardList.length; i++) {
				if ( rankOfPopCard == completeBoardList[i].getRank() ) {
					orderOfHand[j] = completeBoardList[i];
					j++;
					if ( j >= orderOfHand.length ) {
						break;
					}
				}
			}

			completeBoardList = deleteARankFromHand(completeBoardList, rankOfPopCard);
			rankOfPopCard = rankOfMostPopularCard(completeBoardList);
		}

		return orderOfHand;

	} 

	public Card[] deleteDuplicateRanks(Card[] completeBoardList) {

		while ( numberOfOccurencesMostPopularCardAppears(completeBoardList) > 1 ) {
			completeBoardList = sortWithMostPopularAppearancesFirst(completeBoardList);
			completeBoardList = deleteACard(completeBoardList, completeBoardList[0]);
		}

		return completeBoardList;
	}

	//precondition, cardToDelete is NOT NULL and the card is in the list
	public Card[] deleteACard(Card[] completeBoardList, Card cardToDelete) {
		Card[] hand = new Card[completeBoardList.length - 1];
		boolean hasCardBeenSkipped = false;
		int j = 0;
		for (int i = 0; i < completeBoardList.length; i++) {
			if (!cardToDelete.isEqual(completeBoardList[i]) || hasCardBeenSkipped) {
				hand[j] = completeBoardList[i];
				j++;
			}
			else {
				hasCardBeenSkipped = true;
			}
		}
		return hand;
	}

	//precondition no null lists
	public Card[] sortHandByRank(Card[] completeBoardList) {
		for (int i = 0; i < completeBoardList.length; i++) {
			for (int j = 0; j < completeBoardList.length - 1; j++) {
				if (completeBoardList[j].compareRank(completeBoardList[j+1]) < 0) {
					Card cardToSwap = completeBoardList[j];
					Card cardToSwapTwo = completeBoardList[j+1];
					completeBoardList[j] = cardToSwapTwo;
					completeBoardList[j+1] = cardToSwap;
				}
			}
		}

		return completeBoardList;
	}

	//precondition no null lists
	public Card[] sortHandBySuit(Card[] completeBoardList) {

		for (int i = 0; i < completeBoardList.length; i++) {
			for (int j = 0; j < completeBoardList.length - 1; j++) {
				if (completeBoardList[j].compareSuit(completeBoardList[j+1]) < 0) {
					Card cardToSwap = completeBoardList[j];
					Card cardToSwapTwo = completeBoardList[j+1];
					completeBoardList[j] = cardToSwapTwo;
					completeBoardList[j+1] = cardToSwap;
				}
			}
		}

		return completeBoardList;
	}

	public Card[] sortWithMostPopularAppearingSuitFirst( Card[] completeBoardList ) {
		Card[] orderOfHand = new Card[completeBoardList.length];
		int suitOfMostPopSuit = rankOfMostPopularSuit(completeBoardList);
		int j = 0;
		while (j < orderOfHand.length ) {
			for (int i = 0; i < completeBoardList.length; i++) {
				if ( suitOfMostPopSuit == completeBoardList[i].getSuit() ) {
					orderOfHand[j] = completeBoardList[i];
					j++;
					if ( j >= orderOfHand.length ) {
						break;
					}
				}
			}
		}

		return orderOfHand;

	} 

	public Card[] deleteARankFromHand(Card[] completeBoardList, int rankToDelete) {
		int numberOfCardsToDelete = 0;
		for (int i = 0; i < completeBoardList.length; i++) {
			if (rankToDelete == completeBoardList[i].getRank()) {
				numberOfCardsToDelete++;
			}
		}

		Card[] handToMake = new Card[completeBoardList.length - numberOfCardsToDelete];
		if (handToMake.length == 0) {
			return handToMake;
		}
		int j = 0;
		for (int i = 0; i < completeBoardList.length; i++) {
			if (rankToDelete != completeBoardList[i].getRank()) {
				handToMake[j] = completeBoardList[i];
				j++;
			}
		}

		return handToMake;

	}

	public Card[] deleteASuitFromHand(Card[] completeBoardList, int suitToDelete) {
		int numberOfCardsToDelete = 0;

		for (int i = 0; i < completeBoardList.length; i++) {
			if (suitToDelete == completeBoardList[i].getSuit()) {
				numberOfCardsToDelete++;
			}
		}

		Card[] handToMake = new Card[completeBoardList.length - numberOfCardsToDelete];
		if (handToMake.length == 0) {
			return handToMake;
		}
		int j = 0;
		for (int i = 0; i < completeBoardList.length; i++) {
			if (suitToDelete != completeBoardList[i].getSuit()) {
				handToMake[j] = completeBoardList[i];
				j++;
			}
		}

		return handToMake;

	}
	
	public HandType bestHand( Card[] completeBoardList, int highestRankInDeck ) {
		completeBoardList = sortHandByRank(completeBoardList);
		Card[] hand = null;
		if ( completeBoardList.length >= 5) {
			hand = new Card[5];
		}
		else {
			hand = new Card[completeBoardList.length];
		}

		if ( isStraightFlush(completeBoardList, highestRankInDeck)) {
			return createStraightFlush(completeBoardList, highestRankInDeck);
		}

		//Determining if quads
		if ( numberOfOccurencesMostPopularCardAppears(completeBoardList) == 4) {
			return createQuads(completeBoardList, hand);
		}

		//Determine if boat
		if ( numberOfOccurencesMostPopularCardAppears(completeBoardList) == 3 && countNumberOfPairs(completeBoardList) >= 2) {
			return createTripsOrBoat(completeBoardList, hand);
		}

		//Determining if flush
		if ( numberOfOccurencesOfHighestSuit(completeBoardList) >= 5 ) {
			return createFlush(completeBoardList, hand);
		}

		//Determing if straight
		if (  countNumberOfCardsInARow(completeBoardList, highestRankInDeck) >= 5 ) {
			return createStraight(completeBoardList, highestRankInDeck);
		}

		//Determing if trips
		if ( numberOfOccurencesMostPopularCardAppears(completeBoardList) == 3) {
			return createTripsOrBoat(completeBoardList, hand);
		}

		//Determing if one/two pair
		if ( numberOfOccurencesMostPopularCardAppears(completeBoardList) == 2) {
			return createOneOrTwoPair(completeBoardList, hand);
		}

		//Determining if the board is a high card;
		if ( isHighCard(completeBoardList) ) {
			return createHighCardHand(completeBoardList, hand);
		} 


		return null;

	}

	public ArrayList<Card> loadDeck() {
		ArrayList<Card> deck = new ArrayList<Card>();
		for (int i = 1; i <= 13; i++) {
			for (int j = 1; j <= 4; j++) {
				deck.add(new Card(j, i));
			}
		}
		return deck;
	}

	//precondition is it is a high Card.
	public HandType createHighCardHand( Card[] completeBoardList, Card[] handToMake) {
		completeBoardList = sortHandByRank(completeBoardList);
		for (int i = 0; i < handToMake.length; i++) {
				handToMake[i] = completeBoardList[i];
		}
		return new HandType(handToMake, "HighCard");
	}


	//precondition is highest freq of cards is 2
	public HandType createOneOrTwoPair( Card[] completeBoardList, Card[] handToMake) {
		completeBoardList = sortWithMostPopularAppearancesFirst(completeBoardList);
		if (countNumberOfPairs(completeBoardList) == 1) {
			completeBoardList = sortWithMostPopularAppearancesFirst(completeBoardList);
			for (int i = 0; i < handToMake.length; i++) {
				handToMake[i] = completeBoardList[i];
			}
			return new HandType(handToMake, "OnePair");
		}
		else {
			// below is the one line that fixes it.
			completeBoardList = sortWithMostPopularAppearancesFirst(completeBoardList);
			if ( handToMake.length == 5 ) {
				for (int i = 0; i < 4; i++) {
					handToMake[i] = completeBoardList[i];
				}
				Card[] tempCard = new Card[ completeBoardList.length - 4];
				int j = 0;
				for (int i = 4; i < completeBoardList.length; i++) {
					tempCard[j] = completeBoardList[i];
					j++;
				}
				handToMake[4] = getHighestRankedCard(tempCard);
				return new HandType(handToMake, "TwoPair");

			}
			else {
				for (int i = 0; i < handToMake.length; i++) {
					handToMake[i] = completeBoardList[i];
				}
				return new HandType(handToMake, "TwoPair");
			} 
		}

	} 

	//precondition is highest freq of cards is 3
	public HandType createTripsOrBoat( Card[] completeBoardList, Card[] handToMake) {
		completeBoardList = sortWithMostPopularAppearancesFirst(completeBoardList);
		for (int i = 0; i < handToMake.length; i++) {
				handToMake[i] = completeBoardList[i];
		}
		if (countNumberOfPairs(completeBoardList) == 1) {
			return new HandType(handToMake, "ThreeOfAKind");
		}
		else {
			return new HandType(handToMake, "FullHouse");
		}

	} 

	//precondition is highest freq of cards is 4
	public HandType createQuads( Card[] completeBoardList, Card[] handToMake) {
		completeBoardList = sortWithMostPopularAppearancesFirst(completeBoardList);
		for (int i = 0; i < 4; i++) {
			handToMake[i] = completeBoardList[i];
		}
		if (handToMake.length == 5) {
			Card[] tempCard = new Card[ completeBoardList.length - 4];
			int j = 0;
			for (int i = 4; i < completeBoardList.length; i++) {
				tempCard[j] = completeBoardList[i];
				j++;
			}
			handToMake[4] = getHighestRankedCard(tempCard);
		}
		return new HandType(handToMake, "FourOfAKind");

	}

	//precondition is flush is possible
	public HandType createFlush( Card[] completeBoardList, Card[] handToMake) {
		completeBoardList = sortWithMostPopularAppearingSuitFirst(completeBoardList);
		for (int i = 0; i < handToMake.length; i++) {
				handToMake[i] = completeBoardList[i];
		}
		return new HandType(handToMake, "Flush");

	}

	//precondition is list of cards is 5+ AND there is a straight possible
	public HandType createStraight( Card[] completeBoardList, int highestRankInDeck) {
		completeBoardList = sortHandByRank(completeBoardList);
		if (completeBoardList.length == 5) {
			return new HandType(completeBoardList, "Straight");
		}
		if (completeBoardList.length == 6) {
			Card[] bestHand = null;
			int currentBestHandTotal = 0;
			for (int i = 0; i < completeBoardList.length; i++) {
				Card[] tempCard = deleteACard(completeBoardList, completeBoardList[i]);
				if (countNumberOfCardsInARow(tempCard, highestRankInDeck) == 5 && addTotalRanks(tempCard, highestRankInDeck) > currentBestHandTotal) {
					bestHand = tempCard;
					currentBestHandTotal = addTotalRanks(tempCard, highestRankInDeck);
				}
			}
			return new HandType(bestHand, "Straight");
		}
		else {
			Card[] bestHand = null;
			int currentBestHandTotal = 0;
			for (int i = 0; i < completeBoardList.length - 1; i++) {
				for (int j = i + 1; j < completeBoardList.length; j++) {
					Card[] tempCard1 = deleteACard(completeBoardList, completeBoardList[i]);
					Card[] tempCard = deleteACard(tempCard1, completeBoardList[j]);
					if (countNumberOfCardsInARow(tempCard, highestRankInDeck) == 5 && addTotalRanks(tempCard, highestRankInDeck) > currentBestHandTotal) {
						bestHand = tempCard;
						currentBestHandTotal = addTotalRanks(tempCard, highestRankInDeck);
					}
				}
			}
			return new HandType(bestHand, "Straight");
		}


	}

	//precondition str8flush is available
	public HandType createStraightFlush(Card[] completeBoardList, int highestRankInDeck) {

		completeBoardList = sortHandByRank(completeBoardList);
		if (completeBoardList.length == 5) {
			return new HandType(completeBoardList, "StraightFlush");
		}
		if (completeBoardList.length == 6) {
			Card[] bestHand = null;
			int currentBestHandTotal = 0;
			for (int i = 0; i < completeBoardList.length; i++) {
				Card[] tempCard = deleteACard(completeBoardList, completeBoardList[i]);
				if (isStraightFlush(tempCard, highestRankInDeck) && addTotalRanks(tempCard, highestRankInDeck) > currentBestHandTotal) {
					bestHand = tempCard;
					currentBestHandTotal = addTotalRanks(tempCard, highestRankInDeck);
				}
			}
			return new HandType(bestHand, "StraightFlush");
		}
		else {
			Card[] bestHand = null;
			int currentBestHandTotal = 0;
			for (int i = 0; i < completeBoardList.length - 1; i++) {
				for (int j = i + 1; j < completeBoardList.length; j++) {
					Card[] tempCard1 = deleteACard(completeBoardList, completeBoardList[i]);
					Card[] tempCard = deleteACard(tempCard1, completeBoardList[j]);
					if (isStraightFlush(tempCard, highestRankInDeck) && addTotalRanks(tempCard, highestRankInDeck) > currentBestHandTotal) {
						bestHand = tempCard;
						currentBestHandTotal = addTotalRanks(tempCard, highestRankInDeck);
					}
				}
			}
			return new HandType(bestHand, "StraightFlush");
		}

	}

	public String toStringHand(Card[] hand) {
		return hand[0].singleCharFormat() + hand[0].suitConverter() + hand[1].singleCharFormat() + hand[1].suitConverter() +
			   hand[2].singleCharFormat() + hand[2].suitConverter() + hand[3].singleCharFormat() + hand[3].suitConverter() +
			   hand[4].singleCharFormat() + hand[4].suitConverter();
	}
}


	/*
	public HashMap<String, HandType> createMapOfFiveCardHandsFullDeck() throws Exception {
		HashMap<String, HandType> map = new HashMap<String, HandType>();
		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		ArrayList<Card> deck = eval.loadDeck();
		for (int i = 1; i <= 48; i++) {
			for (int j = i + 1; j <= 49; j++ ) {
				for (int k = j + 1; k <= 50; k++ ) {
					for (int l = k + 1; l <= 51; l++ ) {
						for (int m = l + 1; m <= 52; m++ ) {
							Card[] boardListForSim = new Card[5];
							boardListForSim[0] = deck.get(i-1);
							boardListForSim[1] = deck.get(j-1);
							boardListForSim[2] = deck.get(k-1);
							boardListForSim[3] = deck.get(l-1);
							boardListForSim[4] = deck.get(m-1);

							HandType handTester = eval.bestHand(boardListForSim, 13);
							boardListForSim = eval.sortHandBySuit(boardListForSim);
							boardListForSim = eval.sortHandByRank(boardListForSim);

							map.put(eval.toStringHand(boardListForSim), handTester);
						}
					}
				}
			}
		}
		
		FileOutputStream fos = new FileOutputStream( "/Users/kids/desktop/HuBot/hands.txt" );
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject( map ); 

		return map;
	}

	public HashMap<String, Integer> createMapWithInteger() throws Exception {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		ArrayList<Card> deck = eval.loadDeck();
		int counter = 0;
		for (int i = 1; i <= 48; i++) {
			for (int j = i + 1; j <= 49; j++ ) {
				for (int k = j + 1; k <= 50; k++ ) {
					for (int l = k + 1; l <= 51; l++ ) {
						for (int m = l + 1; m <= 52; m++ ) {
							Card[] boardListForSim = new Card[5];
							boardListForSim[0] = deck.get(i-1);
							boardListForSim[1] = deck.get(j-1);
							boardListForSim[2] = deck.get(k-1);
							boardListForSim[3] = deck.get(l-1);
							boardListForSim[4] = deck.get(m-1);

							boardListForSim = eval.sortHandBySuit(boardListForSim);
							boardListForSim = eval.sortHandByRank(boardListForSim);

							map.put(eval.toStringHand(boardListForSim), new Integer(counter));
							counter++;
						}
					}
				}
			}
		}
		
		FileOutputStream fos = new FileOutputStream( "/Users/kids/desktop/HuBot/hands.txt" );
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject( map ); 

		return map;
	} */

	/*
	//PreCOndition: board is size 7, and map contains all 5 card combos.
	public HandType bestHandUsingMapFullDeck(Card[] board, HashMap<String, HandType> map) {
		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		eval.sortHandBySuit(board);
		eval.sortHandByRank(board);
		Card[] fiveCardsToCheckInMap = new Card[5];
		HandType currentBestHand = null;
		for (int i = 0; i < 3; i++) {
			for (int j = i + 1; j < 4; j++) {
				for (int k = j + 1; k < 5; k++) {
					for (int l = k + 1; l < 6; l++) {
						for (int m = l + 1; m < 7; m++) {
							fiveCardsToCheckInMap[0] = board[i];
							fiveCardsToCheckInMap[1] = board[j];
							fiveCardsToCheckInMap[2] = board[k];
							fiveCardsToCheckInMap[3] = board[l];
							fiveCardsToCheckInMap[4] = board[m];
							if (currentBestHand == null) {
								currentBestHand = map.get(eval.toStringHand(fiveCardsToCheckInMap)); 
							}
							else {						
								HandType hand = map.get(eval.toStringHand(fiveCardsToCheckInMap));
								if (currentBestHand.compareHandsFullDeck( hand, 13 ) == 2) {
									currentBestHand = hand;
								}
							} 

						}
					}
				}
			}
		}
		return currentBestHand;
	} 

	public int bestHandUsingMapFullDeckNumberOutput(Card[] board, HashMap<String, Integer> map) {
		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		eval.sortHandBySuit(board);
		eval.sortHandByRank(board);
		Card[] fiveCardsToCheckInMap = new Card[5];
		int currentValue = -1;
		for (int i = 0; i < 3; i++) {
			for (int j = i + 1; j < 4; j++) {
				for (int k = j + 1; k < 5; k++) {
					for (int l = k + 1; l < 6; l++) {
						for (int m = l + 1; m < 7; m++) {
							fiveCardsToCheckInMap[0] = board[i];
							fiveCardsToCheckInMap[1] = board[j];
							fiveCardsToCheckInMap[2] = board[k];
							fiveCardsToCheckInMap[3] = board[l];
							fiveCardsToCheckInMap[4] = board[m];
							if (currentValue < map.get(eval.toStringHand(fiveCardsToCheckInMap))) {
								currentValue = map.get(eval.toStringHand(fiveCardsToCheckInMap)); 
							}

						}
					}
				}
			}
		}
		return currentValue;
	} 
	*/