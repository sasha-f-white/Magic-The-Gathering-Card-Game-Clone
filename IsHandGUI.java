/*
 Brendan Aucoin
 2018/05/19
 this is an interface that is used for polymorphism with the hands, for the player handler
 */
package hand_gui_interface;

import cards.Card;

public interface IsHandGUI {
	/*all the methods that every handGUI need.*/
	public void drawCard();
	public void drawInitialCards(int amount);
	public void removeCardFromHand(Card c);
	public void addCardToHand(Card c);
}
