/*
 Brendan Aucoin and Aidan Dowdall
 2018/04/18
 this is the player class that holds all the decks a player needs. as well as all the hands a player needs.
 and methods to get the hands and decks and adding and removing cards from the hands and decks.
 */
package player;

import java.util.Collections;

import cards.Card;
import deck.Deck;
import hand.Hand;

public class Player {
	private Deck cardDeck;
	private Deck manaDeck;
	private Deck discardPile;
	private String name;
	private Hand hand;
	private Hand mana;
	private int health;
	private int numStrMana,numIntMana,numDexMana,numChrMana,numConMana;
	private int maxStrMana,maxIntMana,maxDexMana,maxChrMana,maxConMana;
	public Player(String name,int health) {
		this.name = name;
		this.health = health;
		cardDeck = new Deck();
		manaDeck = new Deck();
		discardPile = new Deck();
		hand = new Hand(10);
		mana = new Hand();
		numStrMana = 0;
		numIntMana= 0;
		numDexMana =0;
		numChrMana = 0;
		numConMana =0;
		maxStrMana = 0;
		maxIntMana =0;
		maxDexMana =0;
		maxChrMana =0;
		maxConMana = 0;
	}
	
	public Player(String name,int health,Deck cardDeck,Deck manaDeck) {
		this.name = name;
		this.health = health;
		this.cardDeck = cardDeck;
		this.manaDeck = manaDeck;
	}
	public Player(Player player) {
		this.cardDeck = player.cardDeck;
		this.manaDeck = player.manaDeck;
		this.name = player.name;
		this.hand = player.hand;
		this.mana = player.mana;
		this.health = player.health;
		this.discardPile = player.discardPile;
	}
	/*draws the first card from a deck and adds it to a hand*/
	public Card drawCard(Deck d,Hand h) {
		Card c = cardDeck.draw();
		d.removeCard(c);
		if(h.getSize() < h.getMaxSize()) {
		h.addCard(c);}
		else {discardPile.addCard(c);}
		return c;
	}
	/*returns a card array of how many you want to draw and adds it to the deck*/
	public Card[] drawManyCards(int amount,Deck deck) {
		Card[]cards = new Card[amount];
		for(int i =0;i<amount;i++) {
			cards[i] = deck.draw();
			deck.removeCard(cards[i]);
		}
		return cards;
	}
	/*returns a card array of how many you want to draw and adds it to the deck*/
	public Card[] drawManyCards(int amount,Deck deck,Hand hand) {
		Card[] cards = new Card[amount];
		for(int i =0; i < amount; i++) {
			cards[i] = deck.draw();
			deck.removeCard(cards[i]);
			hand.addCard(cards[i]);
		}
		return cards;
	}
	/*draws a number of manac ards*/
	public Card[] drawMana(int amount) {
		Card[] manaCards = new Card[amount];
		for(int i =0; i < amount; i++) {
			manaCards[i] = manaDeck.draw();
			manaDeck.removeCard(manaCards[i]);
			mana.addCard(manaCards[i]);
		}
		return manaCards;
	}
	/*removes a card*/
	public void discardCard(Card c) {
		hand.removeCard(c);
		discardPile.addCard(c);
	}
	/*methods to access the lists.*/
	public void addMana(Card c) {
		mana.addCard(c);
	}
	public void removeMana(Card c) {
		mana.removeCard(c);
	}
	public void removeMana(int index) {
		mana.removeCard(index);
	}
	public void shuffle(Deck d) {
		Collections.shuffle(d.getDeck());
	}
	public boolean lost() {
		return health<= 0;
	}
	
	public int manaSize() {
		return mana.getHand().size();
	}
	/*getters and setters*/
	public int handSize() {return hand.getHand().size();}
	public int cardDeckSize() {return cardDeck.getDeck().size();}
	public int manaDeckSize() {return manaDeck.getDeck().size();}
	
	public String toString() {
		return name;
	}
	
	
	public Deck getCardDeck() {return cardDeck;}
	public void setCardDeck(Deck cardDeck) {this.cardDeck = cardDeck;}
	public Deck getManaDeck() {return manaDeck;}
	public void setManaDeck(Deck manaDeck) {this.manaDeck = manaDeck;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public Hand getHand() {return new Hand(hand);}
	public void setHand(Hand hand) {this.hand = new Hand(hand);}
	public int getHealth() {return health;}
	public void setHealth(int health) {this.health = health;}
	public Hand getMana() {return new Hand(mana);}
	public void setMana(Hand mana) {this.mana = new Hand(mana);}
	public int getNumStrMana() {return numStrMana;}
	public void setNumStrMana(int numStrMana) {this.numStrMana = numStrMana;}
	public int getNumIntMana() {return numIntMana;}
	public void setNumIntMana(int numIntMana) {this.numIntMana = numIntMana;}
	public int getNumDexMana() {return numDexMana;}
	public void setNumDexMana(int numDexMana) {this.numDexMana = numDexMana;}
	public int getNumChrMana() {return numChrMana;}
	public void setNumChrMana(int numChrMana) {this.numChrMana = numChrMana;}
	public int getNumConMana() {return numConMana;}
	public void setNumConMana(int numConMana) {this.numConMana = numConMana;}
	public Deck getDiscardPile() {return discardPile;}
	public void setDiscardPile(Deck discardPile) {this.discardPile = discardPile;}

	public int getMaxStrMana() {return maxStrMana;}

	public void setMaxStrMana(int maxStrMana) {this.maxStrMana = maxStrMana;}

	public int getMaxIntMana() {return maxIntMana;}

	public void setMaxIntMana(int maxIntMana) {this.maxIntMana = maxIntMana;}

	public int getMaxDexMana() {return maxDexMana;}

	public void setMaxDexMana(int maxDexMana) {this.maxDexMana = maxDexMana;}

	public int getMaxChrMana() {return maxChrMana;}

	public void setMaxChrMana(int maxChrMana) {this.maxChrMana = maxChrMana;}

	public int getMaxConMana() {return maxConMana;}

	public void setMaxConMana(int maxConMana) {this.maxConMana = maxConMana;}
	
	
	
}
