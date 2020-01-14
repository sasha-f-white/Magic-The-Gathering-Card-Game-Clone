/*
 Brendan Aucoin
 2018/04/15
 this class holds data for a lits of cards in the hand.
 as well as the maximum size of the hand.
 and a sorting the positions of the cards method to sort the cards in your hand and 
 */
package hand;

import java.util.ArrayList;

import cards.Card;
import player.Player;

public class Hand {
	private ArrayList<Card>hand;
	private int size;
	private int maxSize;
	public Hand() {init();}
	public Hand(int maxSize) {this.maxSize = maxSize;init();}
	public Hand(Hand h) {
		this.hand = h.hand;
		this.size = h.size;
		this.maxSize = h.maxSize;
	}
	/*initiliazes*/
	private void init() {
		hand = new ArrayList<Card>();
		size = 0;
	}
	/*adds a card*/
	public void addCard(Card c) {
		hand.add(c);
	}
	/*removes a card*/
	public void removeCard(Card c) {
		hand.remove(c);
	}
	/*removes a card based on index*/
	public void removeCard(int index) {
		hand.remove(index);
	}
	/*unused*/
	public void sortPlayerHand(Player player,Card startingCard) {
		int padding = 0;
		for(int i =1; i < player.handSize();i++) {
			Card c = player.getHand().getHand().get(i);
			Card c2 = player.getHand().getHand().get(i-1);
			padding = (int) (c2.getX() - c.getX());
		}
		
		for(int i = player.getHand().getHand().indexOf(startingCard); i < player.handSize(); i++) {
			Card c = player.getHand().getHand().get(i);
			c.setX(c.getX() - c.getWidth()+padding/12);
		}
	}
	/*unused*/
	public void sortAiHand(Player player,Card startingCard) {
		int padding = 0;
		for(int i =1; i < player.handSize();i++) {
			Card c = player.getHand().getHand().get(i);
			Card c2 = player.getHand().getHand().get(i-1);
			padding = (int) ((c2.getX() + c2.getWidth()) - c.getX());
		}
		
		for(int i = player.getHand().getHand().indexOf(startingCard); i < player.handSize(); i++) {
			Card c = player.getHand().getHand().get(i);
			c.setX(c.getX() - c.getWidth()+padding);
		}
	}
	
	/*getters and setters*/
	public int getSize() {return size;}
	public void setSize(int size) {this.size = size;}
	public ArrayList<Card>getHand(){return hand;}
	public void setHand(ArrayList<Card>hand) {this.hand = hand;}
	public void setMaxSize(int maxSize) {this.maxSize = maxSize;}
	public int getMaxSize() {return maxSize;}
}	
