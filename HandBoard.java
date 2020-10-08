public class HandBoard implements Comparable<HandBoard> {

	private Hand hand;
	private Card[] board;

	public HandBoard(Hand hand, Card[] board) {
		this.hand = hand;
		this.board = board;
	}

	public Hand getHand() {
		return this.hand;
	}

	//5 card board
	public Card[] getBoard() {
		return this.board;
	}

	//all 7 cards
	public Card[] getCompleteBoard() {
		Card[] completeBoard = new Card[7];
		for (int i = 0; i < 5; i++) {
			completeBoard[i] = this.board[i];
		}
		completeBoard[5] = hand.getCardOne();
		completeBoard[6] = hand.getCardTwo();

		return completeBoard;
	}


	@Override
	public int compareTo(HandBoard otherHand) {
		TypeOfHandEvaluation eval = new TypeOfHandEvaluation();
		HandType hand1 = eval.bestHand(this.getCompleteBoard(), 13);
		HandType hand2 = eval.bestHand(otherHand.getCompleteBoard(), 13);

		int comparison = hand1.compareHandsFullDeck(hand2, 13);

		if (comparison > 1) {
			return -1;
		}
		else {
			return comparison;
		}
	}
}