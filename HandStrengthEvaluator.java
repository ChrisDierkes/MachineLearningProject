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
	
public class HandStrengthEvaluator {

	private Random RANDOM = new SecureRandom();

	public void dispOrderedHand(Card[] completeBoardList) {
		for (int i = 0; i < completeBoardList.length; i++) {
			System.out.println(completeBoardList[i].toStringFullDeck());
		}
	}

	public int compare(SpecialHand handOne, SpecialHand handTwo) {
		return handOne.compareTo(handTwo);
	}

	//returns 0 to 1 depending on win. NOT 0 or 1.
	public double simulateAHandEquityVsRangeForFullDeck(Hand handOne, int numberOfOpponents) {
		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		ArrayList<Card> deck = eval.loadDeck();
		Card[] completeBoardList = new Card[7];
		ArrayList<Card[]> otherHands = new ArrayList<Card[]>();

		for (int i = 0; i < numberOfOpponents; i++) {
			otherHands.add(new Card[7]);
		}

		completeBoardList[0] = handOne.getCardOne();
		completeBoardList[1] = handOne.getCardTwo();

		//delete the given hand from deck.
		for (int i = deck.size() - 1; i >= 0; i--) {
			if (deck.get(i).isEqual(handOne.getCardOne()) || deck.get(i).isEqual(handOne.getCardTwo())) {
				deck.remove(i);
			}
		}

		//populate all the opps with hands.
		for (int i = 0; i < otherHands.size(); i++) {
			Random rand1 = new SecureRandom();
			int number1 = RANDOM.nextInt(deck.size());
			otherHands.get(i)[0] = deck.get(number1);
			deck.remove(number1);
			int number2 = RANDOM.nextInt(deck.size());
			otherHands.get(i)[1] = deck.get(number2);
			deck.remove(number2);
		}

		//creating flop
		for (int i = 0; i < 5; i++) {
			int number = RANDOM.nextInt(deck.size());
			completeBoardList[2 + i] = deck.get(number);
			for (int j = 0; j < otherHands.size(); j++) {
				otherHands.get(j)[2 + i] = deck.get(number);
			}
			deck.remove(number);
		}

		HandType hand1 = eval.bestHand(completeBoardList, 13);

		boolean isTie = false;
		double numberOfPeopleToSplitThePot = 1;
		for (int i = 0; i < otherHands.size(); i++) {
			int comparisonNumber = hand1.compareHandsFullDeck(eval.bestHand(otherHands.get(i), 13), 13);
			if (comparisonNumber == 2) {
				return 0;
			}
			if (comparisonNumber == 0) {
				isTie = true;
				numberOfPeopleToSplitThePot++;
			}
		}

		if (isTie) {
			return 1/numberOfPeopleToSplitThePot;
		}
		else {
			return 1;
		}
	}

 	// map of hands WITH % of range needs to be done beforehand, this maximizes the efficiency
	public double simEquityVsPercentageOfRange(Hand handOne, int numberOfOpponents, HashMap<String, SpecialHand> mapOfHands) {

		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		ArrayList<Card> deck = eval.loadDeck();
		Card[] completeBoardList = new Card[7];
		ArrayList<Card[]> otherHands = new ArrayList<Card[]>();

		for (int i = 0; i < numberOfOpponents; i++) {
			otherHands.add(new Card[7]);
		}

		completeBoardList[0] = handOne.getCardOne();
		completeBoardList[1] = handOne.getCardTwo();

		//delete the given hand from deck.
		for (int i = deck.size() - 1; i >= 0; i--) {
			if (deck.get(i).isEqual(handOne.getCardOne()) || deck.get(i).isEqual(handOne.getCardTwo())) {
				deck.remove(i);
			}
		}

		for (int i = 0; i < otherHands.size(); i++) {
			boolean isCardInRangeFound = false;

			while (!isCardInRangeFound) {
				int number1 = RANDOM.nextInt(deck.size());
				Card cardOne = deck.get(number1);
				deck.remove(number1);
				int number2 = RANDOM.nextInt(deck.size());
				Card cardTwo = deck.get(number2);
				deck.remove(number2);
				Hand hand = new Hand(cardOne, cardTwo);
				if (mapOfHands.get(hand.convertHandToSpecialHand().getName()) != null) {
					isCardInRangeFound = true;
					otherHands.get(i)[0] = cardOne;
					otherHands.get(i)[1] = cardTwo;
				}
				else {
					deck.add(cardOne);
					deck.add(cardTwo);
				}
			}
		}

		//creating flop
		for (int i = 0; i < 5; i++) {
			int number = RANDOM.nextInt(deck.size());
			completeBoardList[2 + i] = deck.get(number);
			for (int j = 0; j < otherHands.size(); j++) {
				otherHands.get(j)[2 + i] = deck.get(number);
			}
			deck.remove(number);
		}

		HandType hand1 = eval.bestHand(completeBoardList, 13);

		boolean isTie = false;
		double numberOfPeopleToSplitThePot = 1;
		for (int i = 0; i < otherHands.size(); i++) {
			int comparisonNumber = hand1.compareHandsFullDeck(eval.bestHand(otherHands.get(i), 13), 13);
			if (comparisonNumber == 2) {
				return 0;
			}
			if (comparisonNumber == 0) {
				isTie = true;
				numberOfPeopleToSplitThePot++;
			}
		}

		if (isTie) {
			return 1/numberOfPeopleToSplitThePot;
		}
		else {
			return 1;
		}


	}

	public HashMap<String, SpecialHand> convertListToMapOfHands(ArrayList<SpecialHand> listOfHands) {
		HashMap<String, SpecialHand> mapOfHands = new HashMap<String, SpecialHand>();
		for (int i = 0; i < listOfHands.size(); i++) {
			mapOfHands.put(listOfHands.get(i).getName(), listOfHands.get(i));
		}

		return mapOfHands;
	}

	public ArrayList<SpecialHand> getTopPercentageOfHands(double percentRangeOfHands, ArrayList<SpecialHand> listOfHands) {
		//Always 1326 combos
		HandStrengthEvaluator hse = new HandStrengthEvaluator();
		listOfHands = hse.sortHandList(listOfHands); // Sorting just in case, should already be sorted
		double numberOfCombosToAdd = percentRangeOfHands * 1326 / 100;
		double totalNumberOfCombosAdded = 0;
		ArrayList<SpecialHand> newRange = new ArrayList<SpecialHand>();
		for (int i = 0; i < listOfHands.size(); i++) {
			totalNumberOfCombosAdded = totalNumberOfCombosAdded + listOfHands.get(i).getNumberOfCombinations();
			if (totalNumberOfCombosAdded < numberOfCombosToAdd) {
				newRange.add(listOfHands.get(i));
			}
			else {
				break;
			}
		}

		return newRange;
	} 

	public int simTwoHandsFullDeck(Hand handOne, Hand handTwo) {
		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		ArrayList<Card> deck = eval.loadDeck();

		Card[] completeBoardListOne = new Card[7];
		Card[] completeBoardListTwo = new Card[7];

		completeBoardListOne[0] = handOne.getCardOne();
		completeBoardListOne[1] = handOne.getCardTwo();

		completeBoardListTwo[0] = handTwo.getCardOne();
		completeBoardListTwo[1] = handTwo.getCardTwo();

		//delete the given hand from deck.
		for (int i = deck.size() - 1; i >= 0; i--) {
			if (deck.get(i).isEqual(handOne.getCardOne()) || deck.get(i).isEqual(handOne.getCardTwo()) ||
				deck.get(i).isEqual(handTwo.getCardOne()) || deck.get(i).isEqual(handTwo.getCardTwo())) {
				deck.remove(i);
			}
		}

		for (int i = 0; i < 5; i++) {
			int number = RANDOM.nextInt(deck.size());
			completeBoardListOne[2 + i] = deck.get(number);
			completeBoardListTwo[2 + i] = deck.get(number);
			deck.remove(number);
		}

		HandType hand1 = eval.bestHand(completeBoardListOne, 13);
		HandType hand2 = eval.bestHand(completeBoardListTwo, 13);

		return hand1.compareHandsFullDeck(hand2, 13);
	}

	public ArrayList<SpecialHand> sortHandList(ArrayList<SpecialHand> listOfHands) {
		ArrayList<SpecialHand>  sortedList = new ArrayList<SpecialHand>();
		ArrayList<SpecialHand>  listOfHandsDupe = new ArrayList<SpecialHand>();
		double currentHighestEquity = listOfHands.get(0).getEquity();
		int placeInArrayOfEquity = 0;
		int sizeOfList = listOfHands.size();

		for (int i = 0; i < sizeOfList; i++) {
			listOfHandsDupe.add(listOfHands.get(i));
		}

		for (int i = 0; i < sizeOfList; i++) {
			for (int j = 0; j < listOfHandsDupe.size(); j++) {
				if ( listOfHandsDupe.get(j).getEquity() > currentHighestEquity ) {
					currentHighestEquity = listOfHandsDupe.get(j).getEquity();
					placeInArrayOfEquity = j;
				}
			}
			sortedList.add(listOfHandsDupe.get(placeInArrayOfEquity));
			listOfHandsDupe.remove(placeInArrayOfEquity);
			if (listOfHandsDupe.size() > 0) {
				currentHighestEquity = listOfHandsDupe.get(0).getEquity();
				placeInArrayOfEquity = 0;
			}
		}

		return sortedList;
	}

	public ArrayList<SpecialHand> getListOfHandRankings(String filename) throws Exception {
		      String fileName = filename;

              String line = null;

              FileReader fileReader = new FileReader(fileName);

              BufferedReader bufferedReader = new BufferedReader(fileReader);

              ArrayList<SpecialHand> handRankings = new ArrayList<SpecialHand>();

              int equity = 200;
              while ( (line = bufferedReader.readLine()) != null ) {
                     String[] parts = line.split(",");
                     handRankings.add(new SpecialHand(parts[0], equity));
                     equity--;
              }
         return handRankings;
	}

	public ArrayList<Hand> getListOfAllStartingHands(Card[] board) {
		TypeOfHandEvaluation handEval = new TypeOfHandEvaluation();

		ArrayList<Hand> handList = new ArrayList<Hand>();

		for (int i = 0; i < 52; i++) {
			for (int j = i + 1; j < 52; j++ ) {

				int suitOne = (i % 4) + 1;
				Card cardOne = new Card(suitOne, i % 13 + 1);

				int suitTwo = (j % 4) + 1;
				Card cardTwo = new Card(suitTwo, j % 13 + 1);

				if (cardOne.isEqual(board[0]) || cardOne.isEqual(board[1]) || cardOne.isEqual(board[2]) ||
					cardOne.isEqual(board[3]) || cardOne.isEqual(board[4]) || cardTwo.isEqual(board[0]) ||
					cardTwo.isEqual(board[1]) || cardTwo.isEqual(board[2]) || cardTwo.isEqual(board[3]) ||
					cardTwo.isEqual(board[4])) {
					continue;
				}

				Hand hand = new Hand(cardOne, cardTwo);

				handList.add(hand);
			}
		}

		return handList;
	}

	public ArrayList<HandBoard> getHandBoardRanking(Card[] board) {
		ArrayList<Hand> listOfHands = getListOfAllStartingHands(board);
		ArrayList<HandBoard> listOfHandBoards = new ArrayList<HandBoard>();
		for (int i = 0; i < listOfHands.size(); i++) {
			listOfHandBoards.add(new HandBoard(listOfHands.get(i), board));
		}

		Collections.sort(listOfHandBoards);

		return listOfHandBoards;
	}


	public static void main(String[] args) throws Exception {
		int NUMBER_OF_SIMS = 100;
		int NUMBER_OF_HANDS = 20;
		boolean inputHands = false;
		boolean addSuitCounter = false;
		if (args.length == 1) {
			NUMBER_OF_SIMS = Integer.parseInt(args[0]);
		}
		else if (args.length == 2) {
			NUMBER_OF_SIMS = Integer.parseInt(args[0]);
			if (args[1].equalsIgnoreCase("t")) {
				addSuitCounter = true;
			}

		}
		else if (args.length == 3) {
			NUMBER_OF_SIMS = Integer.parseInt(args[0]);
			NUMBER_OF_HANDS = Integer.parseInt(args[1]);
			inputHands = true;
			if (args[1].equalsIgnoreCase("t")) {
				addSuitCounter = true;
			}
		}

		boolean randomlyGenerateBoards = true;
		if (args.length == 5) {
			randomlyGenerateBoards = false;
			NUMBER_OF_SIMS = 1;
		}
		Random RANDOMGEN = new SecureRandom();
		HandStrengthEvaluator temp = new HandStrengthEvaluator();

		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		for (int i = 0; i < NUMBER_OF_SIMS; i++) {
			ArrayList<Card> deck = eval.loadDeck();
			Card[] board = new Card[5];
			if (randomlyGenerateBoards) {
				for (int j = 0; j < 5; j++) {
					int number = RANDOMGEN.nextInt(deck.size());
					board[j] = deck.get(number);
					deck.remove(number);
				} 
			}
			else {
				board[0] = new Card(args[0]);
				board[1] = new Card(args[1]);
				board[2] = new Card(args[2]);
				board[3] = new Card(args[3]);
				board[4] = new Card(args[4]);
			}
			int[] numberOfSuits = new int[4];
			for (int j = 0; j < 5; j++) {
				numberOfSuits[board[j].getSuit() - 1]++;
			}
			ArrayList<HandBoard> listOfHandBoards = temp.getHandBoardRanking(board);
			ArrayList<Integer> buckets = new ArrayList<Integer>();
			ArrayList<Integer> rangeOfNumbersOfBoard = new ArrayList<Integer>();
			int counter = 0;
			for (int j = 0; j < listOfHandBoards.size() - 1; j++) {
				rangeOfNumbersOfBoard.add(j);
				counter++;
				if (listOfHandBoards.get(j).compareTo(listOfHandBoards.get(j + 1)) != 0) {
					buckets.add(counter);
				}
			}
			rangeOfNumbersOfBoard.add(counter);
			counter++;
			buckets.add(counter);
			counter = 0;
			int bucketCounter = 0;
			if (!inputHands) {
				for (int j = 0; j < listOfHandBoards.size(); j++) {
					System.out.print(listOfHandBoards.get(j).getHand().getCardOne().getSuit() + "," + 
									   listOfHandBoards.get(j).getHand().getCardTwo().getSuit() + "," + 
									   listOfHandBoards.get(j).getBoard()[0].getSuit() + "," +
									   listOfHandBoards.get(j).getBoard()[1].getSuit() + "," +
									   listOfHandBoards.get(j).getBoard()[2].getSuit() + "," +
									   listOfHandBoards.get(j).getBoard()[3].getSuit() + "," +
									   listOfHandBoards.get(j).getBoard()[4].getSuit() + ",");
					System.out.print(listOfHandBoards.get(j).getHand().getCardOne().getRank() + "," + 
									   listOfHandBoards.get(j).getHand().getCardTwo().getRank() + "," + 
									   listOfHandBoards.get(j).getBoard()[0].getRank() + "," +
									   listOfHandBoards.get(j).getBoard()[1].getRank() + "," +
									   listOfHandBoards.get(j).getBoard()[2].getRank() + "," +
									   listOfHandBoards.get(j).getBoard()[3].getRank() + "," +
									   listOfHandBoards.get(j).getBoard()[4].getRank() + ",");

					if (j >= buckets.get(bucketCounter)) {
						bucketCounter++;
					}
					if (j < buckets.get(bucketCounter)) {
						double tot = 0;
						if (bucketCounter == 0) {
							tot += buckets.get(bucketCounter);
						}
						else {
							tot = buckets.get(bucketCounter - 1) + buckets.get(bucketCounter);
						}
						tot = tot / 2;
						double doubleCreator = (double) tot / listOfHandBoards.size(); 
						if (addSuitCounter) {
							System.out.println(doubleCreator * 100 + "," + numberOfSuits[0] + "," + numberOfSuits[1] + 
												"," + numberOfSuits[2] + "," + numberOfSuits[3]);
						}
						else {
							System.out.println(doubleCreator * 100);
						}
					}
				}
			}
			else {
				Collections.shuffle(rangeOfNumbersOfBoard);
				for (int j = 0; j < NUMBER_OF_HANDS; j++) {
					int numberInList = rangeOfNumbersOfBoard.get(j);
					System.out.print(listOfHandBoards.get(numberInList).getHand().getCardOne().getSuit() + "," + 
									   listOfHandBoards.get(numberInList).getHand().getCardTwo().getSuit() + "," + 
									   listOfHandBoards.get(numberInList).getBoard()[0].getSuit() + "," +
									   listOfHandBoards.get(numberInList).getBoard()[1].getSuit() + "," +
									   listOfHandBoards.get(numberInList).getBoard()[2].getSuit() + "," +
									   listOfHandBoards.get(numberInList).getBoard()[3].getSuit() + "," +
									   listOfHandBoards.get(numberInList).getBoard()[4].getSuit() + ",");
					System.out.print(listOfHandBoards.get(numberInList).getHand().getCardOne().getRank() + "," + 
									   listOfHandBoards.get(numberInList).getHand().getCardTwo().getRank() + "," + 
									   listOfHandBoards.get(numberInList).getBoard()[0].getRank() + "," +
									   listOfHandBoards.get(numberInList).getBoard()[1].getRank() + "," +
									   listOfHandBoards.get(numberInList).getBoard()[2].getRank() + "," +
									   listOfHandBoards.get(numberInList).getBoard()[3].getRank() + "," +
									   listOfHandBoards.get(numberInList).getBoard()[4].getRank() + ",");
					while (numberInList >= buckets.get(bucketCounter)) {
						bucketCounter++;
					}
					double tot = 0;
					if (bucketCounter == 0) {
						tot += buckets.get(bucketCounter);
					}
					else {
						tot = buckets.get(bucketCounter - 1) + buckets.get(bucketCounter);
					}
					tot = tot / 2;
					double doubleCreator = (double) tot / listOfHandBoards.size(); 
					if (addSuitCounter) {
						System.out.println(doubleCreator * 100 + "," + numberOfSuits[0] + "," + numberOfSuits[1] + 
												"," + numberOfSuits[2] + "," + numberOfSuits[3]);
					}
					else {
						System.out.println(doubleCreator * 100);
					}
					bucketCounter = 0;
				}
			}
		}

		/*
		for (int i = 0; i < listOfHands.size(); i++) {
			for (int j = i + 1; j < listOfHands.size(); j++) {
				Card[] completeBoardListOne = new Card[7];
				Card[] completeBoardListTwo = new Card[7]; 

				completeBoardListOne[0] = listOfHands.get(i).getCardOne();
				completeBoardListOne[1] = listOfHands.get(i).getCardTwo();
				completeBoardListTwo[0] = listOfHands.get(j).getCardOne();
				completeBoardListTwo[1] = listOfHands.get(j).getCardTwo();
			}
		}

		for (int x = deck.size() - 1; x >= 0; x--) {
			if (deck.get(x).isEqual(cardOne) || deck.get(x).isEqual(cardTwo) ) {
				deck.remove(x);
			}
		}

		int number = RANDOM.nextInt(deck.size()); */



	}
}






	/*
	// map of hands WITH % of range needs to be done beforehand, this maximizes the efficiency
	public double simEquityVsPercentageOfRangeUsingMap(Hand handOne, int numberOfOpponents, 
													   HashMap<String, SpecialHand> mapOfHands, HashMap<String, HandType>  map) {

		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		ArrayList<Card> deck = eval.loadDeck();
		Card[] completeBoardList = new Card[7];
		ArrayList<Card[]> otherHands = new ArrayList<Card[]>();

		for (int i = 0; i < numberOfOpponents; i++) {
			otherHands.add(new Card[7]);
		}

		completeBoardList[0] = handOne.getCardOne();
		completeBoardList[1] = handOne.getCardTwo();

		//delete the given hand from deck.
		for (int i = deck.size() - 1; i >= 0; i--) {
			if (deck.get(i).isEqual(handOne.getCardOne()) || deck.get(i).isEqual(handOne.getCardTwo())) {
				deck.remove(i);
			}
		}

		for (int i = 0; i < otherHands.size(); i++) {
			boolean isCardInRangeFound = false;

			while (!isCardInRangeFound) {
				int number1 = RANDOM.nextInt(deck.size());
				Card cardOne = deck.get(number1);
				deck.remove(number1);
				int number2 = RANDOM.nextInt(deck.size());
				Card cardTwo = deck.get(number2);
				deck.remove(number2);
				Hand hand = new Hand(cardOne, cardTwo);
				if (mapOfHands.get(hand.convertHandToSpecialHand().getName()) != null) {
					isCardInRangeFound = true;
					otherHands.get(i)[0] = cardOne;
					otherHands.get(i)[1] = cardTwo;
				}
				else {
					deck.add(cardOne);
					deck.add(cardTwo);
				}
			}
		}

		//creating flop
		for (int i = 0; i < 5; i++) {
			int number = RANDOM.nextInt(deck.size());
			completeBoardList[2 + i] = deck.get(number);
			for (int j = 0; j < otherHands.size(); j++) {
				otherHands.get(j)[2 + i] = deck.get(number);
			}
			deck.remove(number);
		}

		HandType hand1 = eval.bestHandUsingMapFullDeck(completeBoardList, map);

		boolean isTie = false;
		double numberOfPeopleToSplitThePot = 1;
		for (int i = 0; i < otherHands.size(); i++) {
			int comparisonNumber = hand1.compareHandsFullDeck(eval.bestHandUsingMapFullDeck(otherHands.get(i), map), 13);
			if (comparisonNumber == 2) {
				return 0;
			}
			if (comparisonNumber == 0) {
				isTie = true;
				numberOfPeopleToSplitThePot++;
			}
		}

		if (isTie) {
			return 1/numberOfPeopleToSplitThePot;
		}
		else {
			return 1;
		}


	}

	// map of hands WITH % of range needs to be done beforehand, this maximizes the efficiency
	public double simEquityVsPercentageOfRangeUsingMapWithInteger(Hand handOne, int numberOfOpponents, 
													   HashMap<String, SpecialHand> mapOfHands, HashMap<String, Integer>  map) {

		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		ArrayList<Card> deck = eval.loadDeck();
		Card[] completeBoardList = new Card[7];
		ArrayList<Card[]> otherHands = new ArrayList<Card[]>();

		for (int i = 0; i < numberOfOpponents; i++) {
			otherHands.add(new Card[7]);
		}

		completeBoardList[0] = handOne.getCardOne();
		completeBoardList[1] = handOne.getCardTwo();

		//delete the given hand from deck.
		for (int i = deck.size() - 1; i >= 0; i--) {
			if (deck.get(i).isEqual(handOne.getCardOne()) || deck.get(i).isEqual(handOne.getCardTwo())) {
				deck.remove(i);
			}
		}

		for (int i = 0; i < otherHands.size(); i++) {
			boolean isCardInRangeFound = false;

			while (!isCardInRangeFound) {
				int number1 = RANDOM.nextInt(deck.size());
				Card cardOne = deck.get(number1);
				deck.remove(number1);
				int number2 = RANDOM.nextInt(deck.size());
				Card cardTwo = deck.get(number2);
				deck.remove(number2);
				Hand hand = new Hand(cardOne, cardTwo);
				if (mapOfHands.get(hand.convertHandToSpecialHand().getName()) != null) {
					isCardInRangeFound = true;
					otherHands.get(i)[0] = cardOne;
					otherHands.get(i)[1] = cardTwo;
				}
				else {
					deck.add(cardOne);
					deck.add(cardTwo);
				}
			}
		}

		//creating flop
		for (int i = 0; i < 5; i++) {
			int number = RANDOM.nextInt(deck.size());
			completeBoardList[2 + i] = deck.get(number);
			for (int j = 0; j < otherHands.size(); j++) {
				otherHands.get(j)[2 + i] = deck.get(number);
			}
			deck.remove(number);
		}

		int hand1Value = eval.bestHandUsingMapFullDeckNumberOutput(completeBoardList, map);

		boolean isTie = false;
		double numberOfPeopleToSplitThePot = 1;
		for (int i = 0; i < otherHands.size(); i++) {
			int otherHandValue = eval.bestHandUsingMapFullDeckNumberOutput(otherHands.get(i), map);
			if (otherHandValue > hand1Value) {
				return 0;
			}
			if (otherHandValue == hand1Value) {
				isTie = true;
				numberOfPeopleToSplitThePot++;
			}
		}

		if (isTie) {
			return 1/numberOfPeopleToSplitThePot;
		}
		else {
			return 1;
		}


	} */


	/*
	//returns 0 to 1 depending on win. NOT 0 or 1.
	public double simulateAHandEquityVsRangeForFullDeckUsingMap(Hand handOne, int numberOfOpponents, HashMap<String, HandType> map) {
		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		ArrayList<Card> deck = eval.loadDeck();
		Card[] completeBoardList = new Card[7];
		ArrayList<Card[]> otherHands = new ArrayList<Card[]>();

		for (int i = 0; i < numberOfOpponents; i++) {
			otherHands.add(new Card[7]);
		}

		completeBoardList[0] = handOne.getCardOne();
		completeBoardList[1] = handOne.getCardTwo();

		//delete the given hand from deck.
		for (int i = deck.size() - 1; i >= 0; i--) {
			if (deck.get(i).isEqual(handOne.getCardOne()) || deck.get(i).isEqual(handOne.getCardTwo())) {
				deck.remove(i);
			}
		}

		//populate all the opps with hands.
		for (int i = 0; i < otherHands.size(); i++) {
			int number1 = RANDOM.nextInt(deck.size());
			otherHands.get(i)[0] = deck.get(number1);
			deck.remove(number1);
			int number2 = RANDOM.nextInt(deck.size());
			otherHands.get(i)[1] = deck.get(number2);
			deck.remove(number2);
		}

		//creating flop
		for (int i = 0; i < 5; i++) {
			int number = RANDOM.nextInt(deck.size());
			completeBoardList[2 + i] = deck.get(number);
			for (int j = 0; j < otherHands.size(); j++) {
				otherHands.get(j)[2 + i] = deck.get(number);
			}
			deck.remove(number);
		}

		HandType hand1 = eval.bestHandUsingMapFullDeck(completeBoardList, map);

		boolean isTie = false;
		double numberOfPeopleToSplitThePot = 1;
		for (int i = 0; i < otherHands.size(); i++) {
			int comparisonNumber = hand1.compareHandsFullDeck(eval.bestHandUsingMapFullDeck(otherHands.get(i), map), 13);
			if (comparisonNumber == 2) {
				return 0;
			}
			if (comparisonNumber == 0) {
				isTie = true;
				numberOfPeopleToSplitThePot++;
			}
		}

		if (isTie) {
			return 1/numberOfPeopleToSplitThePot;
		}
		else {
			return 1;
		}
	}


	//returns 0 to 1 depending on win. NOT 0 or 1.
	public double simulateAHandEquityVsRangeForFullDeckUsingMapWithInteger(Hand handOne, int numberOfOpponents, HashMap<String, Integer> map) {
		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		ArrayList<Card> deck = eval.loadDeck();
		Card[] completeBoardList = new Card[7];
		ArrayList<Card[]> otherHands = new ArrayList<Card[]>();

		for (int i = 0; i < numberOfOpponents; i++) {
			otherHands.add(new Card[7]);
		}

		completeBoardList[0] = handOne.getCardOne();
		completeBoardList[1] = handOne.getCardTwo();

		//delete the given hand from deck.
		for (int i = deck.size() - 1; i >= 0; i--) {
			if (deck.get(i).isEqual(handOne.getCardOne()) || deck.get(i).isEqual(handOne.getCardTwo())) {
				deck.remove(i);
			}
		}

		//populate all the opps with hands.
		for (int i = 0; i < otherHands.size(); i++) {
			int number1 = RANDOM.nextInt(deck.size());
			otherHands.get(i)[0] = deck.get(number1);
			deck.remove(number1);
			int number2 = RANDOM.nextInt(deck.size());
			otherHands.get(i)[1] = deck.get(number2);
			deck.remove(number2);
		}

		//creating flop
		for (int i = 0; i < 5; i++) {
			int number = RANDOM.nextInt(deck.size());
			completeBoardList[2 + i] = deck.get(number);
			for (int j = 0; j < otherHands.size(); j++) {
				otherHands.get(j)[2 + i] = deck.get(number);
			}
			deck.remove(number);
		}

		int hand1Value = eval.bestHandUsingMapFullDeckNumberOutput(completeBoardList, map);

		boolean isTie = false;
		double numberOfPeopleToSplitThePot = 1;
		for (int i = 0; i < otherHands.size(); i++) {
			int otherHandValue = eval.bestHandUsingMapFullDeckNumberOutput(otherHands.get(i), map);
			if (otherHandValue > hand1Value) {
				return 0;
			}
			if (otherHandValue == hand1Value) {
				isTie = true;
				numberOfPeopleToSplitThePot++;
			}
		}

		if (isTie) {
			return 1/numberOfPeopleToSplitThePot;
		}
		else {
			return 1;
		}
	} */