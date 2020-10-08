public class Hand {

	private Card cardOne;
	private Card cardTwo;
	private Card cardThree;
	private Card cardFour;

	public Hand(Card cardOne, Card cardTwo) {
		this.cardOne = cardOne;
		this.cardTwo = cardTwo;
	}

	public Hand(Card cardOne, Card cardTwo, Card cardThree, Card cardFour) {
		this.cardOne = cardOne;
		this.cardTwo = cardTwo;
		this.cardThree = cardThree;
		this.cardFour = cardFour;
	}

	public Card getCardOne() {
		return cardOne;
	}

	public Card getCardTwo() {
		return cardTwo;
	}

	public Card getCardThree() {
		return cardThree;
	}

	public Card getCardFour() {
		return cardFour;
	}

	//precondition only NL hands
	public SpecialHand convertHandToSpecialHand() {
		if (cardOne.getRank() == cardTwo.getRank()) {
			String name = cardOne.singleCharFormat() + cardTwo.singleCharFormat();
			SpecialHand hand = new SpecialHand(name);
			return hand;
		}
		if (cardOne.getSuit() == cardTwo.getSuit()) {
			String name = cardOne.singleCharFormat() + cardTwo.singleCharFormat() + "s";
			SpecialHand hand = new SpecialHand(name);
			return hand;
		}
		else {
			String name = cardOne.singleCharFormat() + cardTwo.singleCharFormat() + "o";
			SpecialHand hand = new SpecialHand(name);
			return hand;
		}

	}

	public Card[] getArrayOfPLOCards() {
		Card[] hand = new Card[4];
		hand[0] = cardOne;
		hand[1] = cardTwo;
		hand[2] = cardThree;
		hand[3] = cardFour;
		return hand;
	}

	public String toStringPLO() {
		TypeOfHandEvaluation hse = new TypeOfHandEvaluation();
		Card[] hand = new Card[4];
		hand[0] = cardOne;
		hand[1] = cardTwo;
		hand[2] = cardThree;
		hand[3] = cardFour;
		hand = hse.sortHandByRank(hand);
		return singleCharFormat(hand[0]) + suitConverter(hand[0]) + singleCharFormat(hand[1]) + suitConverter(hand[1]) + 
			  singleCharFormat(hand[2]) + suitConverter(hand[2]) + singleCharFormat(hand[3]) + suitConverter(hand[3]);	
	}

	public String suitConverter(Card card) {
		if (card.getSuit() == 1) {
			return "c";
		}
		else if (card.getSuit() == 2) {
			return "d";
		}
		else if (card.getSuit() == 3) {
			return "h";
		}
		else {
			return "s";
		}
	}

	public String singleCharFormat(Card card)  {
		if (card.getRank() == 9) {
			return "T";
		}
		else if (card.getRank() == 10) {
			return "J";
		}
		else if (card.getRank() == 11) {
			return "Q";
		}
		else if (card.getRank() == 12) {
			return "K";
		}
		else if (card.getRank() == 13) {
			return "A";
		}
		else {
			return "" + (card.getRank() + 1);
		}
	}

	public String toStringShortDeck() {
		cardOne.changeRank(cardOne.getRank() + 4);
		cardTwo.changeRank(cardTwo.getRank() + 4);
		String ans = cardOne.toStringFullDeck() + " and " + cardTwo.toStringFullDeck();
		return ans;
	}

	public String toStringFullDeck() {
		String ans = cardOne.toStringFullDeck() + " and " + cardTwo.toStringFullDeck();
		return ans;
	}

	public void convertHandToShortDeck() {
		cardOne.convertCardToShortDeck();
		cardTwo.convertCardToShortDeck();
	}

	public String shortToStringShortDeck() {
		cardOne.changeRank(cardOne.getRank() + 4);
		cardTwo.changeRank(cardTwo.getRank() + 4);
		String ans = null;
		String card1 = null;
		String card2 = null;
		if (cardOne.getRank() == 9) {
			card1 = "T";
		}
		else if (cardOne.getRank() == 10) {
			card1 = "J";
		}
		else if (cardOne.getRank() == 11) {
			card1 = "Q";
		}
		else if (cardOne.getRank() == 12) {
			card1 = "K";
		}
		else if (cardOne.getRank() == 13) {
			card1 = "A";
		}
		else {
			card1 = "" + (cardOne.getRank() + 1);
		}

		if (cardTwo.getRank() == 9) {
			card2 = "T";
		}
		else if (cardTwo.getRank() == 10) {
			card2 = "J";
		}
		else if (cardTwo.getRank() == 11) {
			card2 = "Q";
		}
		else if (cardTwo.getRank() == 12) {
			card2 = "K";
		}
		else if (cardTwo.getRank() == 13) {
			card2 = "A";
		}
		else {
			card2 = "" + (cardTwo.getRank() + 1);
		}


		if (cardOne.compareRank(cardTwo) > 0) {
			ans = card1 + card2;
			if (cardOne.compareSuit(cardTwo) == 0) {
				ans = ans + "s";
			}
			else {
				ans = ans + "o";
			}
			return ans;
		}	
		else if (cardOne.compareRank(cardTwo) < 0) {
			ans = card2 + card1;
			if (cardOne.compareSuit(cardTwo) == 0) {
				ans = ans + "s";
			}
			else {
				ans = ans + "o";
			}
			return ans;
		}
		else {
			ans = card1 + card2;
			return ans;
		}
	}
}
