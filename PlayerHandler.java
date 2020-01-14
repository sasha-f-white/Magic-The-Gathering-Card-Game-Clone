/*
 Brendan Aucoin
 2018/05/23
 this holds all the information you ever need for a player, like a hand and spots for the board.
 */
package player;

import java.util.List;

import board.Spot;
import cards.Card;
import hand_gui_interface.IsHandGUI;

public class PlayerHandler {
	private Spot[] spots;
	private List<Card> cardsOnBoard;
	private IsHandGUI handGUI;
	private Player player;
	public PlayerHandler(Player player,List<Card>cardsOnBoard,Spot[] spots,IsHandGUI handGUI) {
		this.player = player;
		this.cardsOnBoard = cardsOnBoard;
		this.spots =spots;
		this.handGUI = handGUI;
	}
	public Spot[] getSpots() {return spots;}
	public void setSpots(Spot[] spots) {this.spots = spots;}
	public List<Card> getCardsOnBoard() {return cardsOnBoard;}
	public void setCardsOnBoard(List<Card> cardsOnBoard) {this.cardsOnBoard = cardsOnBoard;}
	public IsHandGUI getHandGUI() {	return handGUI;}
	public void setHandGUI(IsHandGUI handGUI) {this.handGUI = handGUI;}
	public Player getPlayer() {return player;}
	public void setPlayer(Player player) {this.player = player;}
	
}
