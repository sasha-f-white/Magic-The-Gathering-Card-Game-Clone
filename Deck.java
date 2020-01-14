/*
 Aidan Dowdall
 2018/04/21
 this class holds a linked list of cards that is the players deck and has
 methods to add and remove cards from it.
 */
package deck;

import java.util.Collections;
import java.util.LinkedList;

import cards.Card;

public class Deck {
	private LinkedList<Card>deck;
	private int maxSize;
	/*no arg constructor*/
	public Deck() {
		deck = new LinkedList<Card>();
		maxSize = 0;
	}
	/*copy constructor*/
	public Deck(Deck d) {
		this.deck = d.deck;
		this.maxSize = d.maxSize;
	}
	/*constructor that puts in a card array inside the deck*/
	public Deck(int maxSize,Card ...cards) {
		deck = new LinkedList<Card>();
		this.maxSize = maxSize;
		for(Card c : cards) {
			deck.add(c);
		}
	}
	/*all the methods are used to access the list so that you do not have to use the get method every time you wanna use the list.
	 * examples are adding and removing first, last and at an index.*/
	
	
	public Card draw() {
		return deck.getFirst();
	}
	public void addTopCard(Card c) {
		deck.addFirst(c);
	}
	public void addBottomCard(Card c) {
		deck.addLast(c);
	}
	public void shuffle() {
		Collections.shuffle(deck);
	}
	public void addCard(Card c) {
		deck.add(c);
	}
	public void removeCard(Card c) {
		deck.remove(c);
	}
	public void removeCard(int index) {
		deck.remove(index);
	}
	
	public void removeTopCard() {
		deck.removeFirst();
	}
	public void removeBottomCard() {
		deck.removeLast();
	}
	
	public LinkedList<Card> getDeck() {return deck;}
	public void setDeck(LinkedList<Card> deck) {this.deck = deck;}
	public int getMaxSize() {return maxSize;}
	public void setMaxSize(int maxSize) {this.maxSize = maxSize;}
	
	
	
}
