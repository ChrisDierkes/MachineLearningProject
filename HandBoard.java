import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class HandBoard implements Comparable<HandBoard> {

	private Hand hand;
	private Card[] board;

	public HandBoard(Hand hand, Card[] board) {
		if (hand.getCardOne().getRank() < hand.getCardTwo().getRank()) {
			this.hand = new Hand(hand.getCardTwo(), hand.getCardOne());
		}
		else if (hand.getCardOne().getRank() == hand.getCardTwo().getRank() && 
				 hand.getCardOne().getSuit() < hand.getCardTwo().getSuit()) {
			this.hand = new Hand(hand.getCardTwo(), hand.getCardOne());
		}
		else {
			this.hand = hand;
		}
		ArrayList<Card> cardList = new ArrayList<Card>();
		for (int i = 0; i < 5; i++) {
			cardList.add(board[i]);
		}
		Card[] newBoard = new Card[5];
		for (int i = 0; i < 5; i++) {
			Card highestCard = cardList.get(0);
			int index = 0;
			for (int j = 0; j < cardList.size(); j++) {
				if (highestCard.getRank() < cardList.get(j).getRank()) {
					index = j;
					highestCard = cardList.get(j);
				}
				else if (highestCard.getRank() == cardList.get(j).getRank() &&
						 highestCard.getSuit() < cardList.get(j).getSuit()) {
					index = j;
					highestCard = cardList.get(j);
				}
			}
			newBoard[i] = highestCard;
			cardList.remove(index);
		}
		this.board = newBoard;
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