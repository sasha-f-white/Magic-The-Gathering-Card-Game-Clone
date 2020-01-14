/*
 Sasha White
 2018/04/25
 this class holds data for the all the cards on the board, and the cards on your side of the board
 and the cards on the oppoenents side of the board.
 */
package board;

import java.util.LinkedList;

import cards.Card;
import cards.ManaCard;

public class Board {
	/*the boards hold data for the entire board,the players side of the board,and the ai's side of the board*/
	private LinkedList<Card>board;
	private LinkedList<Card>playerCards;
	private LinkedList<Card>AICards;
	public Board() {
		board = new LinkedList<Card>();
		playerCards = new LinkedList<Card>();
		AICards = new LinkedList<Card>();
	}
	/*adding all the cards on the players and ai's side of the board to the entire board list.*/
	public void updateBoard() {
		board.clear();
		for(Card c : playerCards) {
			board.add(c);
		}
		for(Card c: AICards) {
			board.add(c);
		}
	}
	/*add card to the board*/
	public void addCard(Card c) {
		if(c instanceof ManaCard) {return;}
		else {
			board.add(c);
		}
	}
	/*add card to the players side of the board*/
	public void addCardToPlayer(Card c) {
			playerCards.add(c);
	}
	/*add card to the ai's side of the board*/
	public void addCardToAI(Card c) {
			AICards.add(c);
	}
	/*the entire board size*/
	public int boardSize() {
		return board.size();
	}
	/*loops through a list and resets the uses on all the cards*/
	public void resetCardUses(LinkedList<Card>board) {
		for(Card c: board) {
			c.setUsed(false);
		}
	}
	/*remove methods*/
	public void removeCard(Card c) {board.remove(c);}
	public void removeCard(int index) {board.remove(index);}
	public void removeCardFromPlayer(Card c) {playerCards.remove(c);}
	public void removeCardFromAI(int index) {AICards.remove(index);}
	public void removeCardFromAI(Card c) {AICards.remove(c);}
	public void removeCardFromPlayer(int index) {playerCards.remove(index);}
	
	/*getters and setters*/
	public LinkedList<Card>getBoard(){return board;}
	public void setBoard(LinkedList<Card>board) {this.board = board;}
	public LinkedList<Card> getPlayerCards() {return playerCards;}
	public void setPlayerCards(LinkedList<Card> playerCards) {this.playerCards = playerCards;}
	public LinkedList<Card> getAICards() {return AICards;}
	public void setAICards(LinkedList<Card> aICards) {AICards = aICards;}
	
	
}
