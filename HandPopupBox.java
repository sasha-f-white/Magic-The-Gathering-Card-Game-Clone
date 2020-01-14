/*
 Brendan Aucoin
 2018/04/27
 this class is for when you click on a card you want to play a box pops up
 and you have options to play or discard it.
 */
package popups;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import board.Board;
import cards.Building;
import cards.Card;
import cards.Spell;
import id.ID;
import player.Player;
import playing_panes.BoardGUI;
import playing_panes.HandGUI;
/*this class is very similar to gui classes but smaller and is used multiple times and not just once but its the same concept
 * thats why none of th gui classes are commented because they are all the same. they all involve mouse pressed, mouse moved,and drawing
 * thats the basic concepts.*/
public class HandPopupBox extends PopupBox{
	private Rectangle playBounds;
	private Rectangle discardBounds;
	private Card currCard;
	private Player player;
	private BoardGUI boardGUI;
	private Board board;
	private Player opponent;
	private HandGUI handGUI;
	public HandPopupBox(Player player,float x,float y,int width,int height,BoardGUI boardGUI,Board board,Player opponent) {
		super(x,y,width,height);
		
		setBounds(new Rectangle((int)x,(int)y,width,height));
		playBounds = new Rectangle(
				getBounds().x,getBounds().y,getBounds().width,getBounds().height/2);
		discardBounds  = new Rectangle(getBounds().x,getBounds().height/2,getBounds().width,getBounds().height/2);
		
		this.boardGUI = boardGUI;
		this.player = player;
		this.board = board;
	}
	public void update() {
		setBounds(new Rectangle((int)getX(),(int)getY(),getWidth(),getHeight()));
		playBounds = new Rectangle(
				getBounds().x,getBounds().y,getBounds().width,getBounds().height/2);
		discardBounds  = new Rectangle(getBounds().x,getBounds().y + getBounds().height/2,getBounds().width,getBounds().height/2);
	}
	
	public void render(Graphics2D g) {
		if(isShown()) {
			super.render(g);
			g.draw(playBounds);
			g.draw(discardBounds);
			g.setColor(Color.BLACK);
			g.drawString("Play",(int) (playBounds.x + playBounds.width/2 -  g.getFont().getSize()/1.5), playBounds.y + playBounds.height/2 + g.getFont().getSize()/2);
			g.drawString("Discard", (int) (discardBounds.x + discardBounds.width/2- g.getFont().getSize()*1.4), discardBounds.y + discardBounds.height/2 + g.getFont().getSize()/2);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if(currCard == null) {return;}
		setShown(false);
		if(mouseOver(playBounds)) {
			/*check if you have enough mana to play the card*/
			if(player.getNumChrMana() >= currCard.getChrCost() && player.getNumStrMana() >= currCard.getStrCost()&&player.getNumConMana() >= currCard.getConCost()&&player.getNumDexMana() >= currCard.getDexCost()&&player.getNumIntMana() >= currCard.getIntCost()) {
				player.setNumStrMana(player.getNumStrMana()- currCard.getStrCost());
				player.setNumIntMana(player.getNumIntMana()- currCard.getIntCost());
				player.setNumDexMana(player.getNumDexMana()- currCard.getDexCost());
				player.setNumChrMana(player.getNumChrMana()- currCard.getChrCost());
				player.setNumConMana(player.getNumConMana()- currCard.getConCost());
				
			}
			else {
				return;
			}
			/*check if the board has space*/
			if(currCard.getId() == ID.UNIT) {
				if(boardGUI.hasSpots(boardGUI.getPlayerSpots())) {
					handGUI.removeCardFromHand(currCard);
					boardGUI.addCardToBoard(currCard, boardGUI.getPlayerHandler(), boardGUI.getOpponentHandler(), true);
					board.updateBoard();
					setShown(false);
				}
			}
			else if(currCard instanceof Building) {
				if(boardGUI.hasSpots(boardGUI.getPlayerSpots())) {
					handGUI.removeCardFromHand(currCard);
					boardGUI.addCardToBoard(currCard, boardGUI.getPlayerHandler(), boardGUI.getOpponentHandler(), true);
					board.updateBoard();
					setShown(false);
				}
			}
			else if(currCard.getId() == ID.SPELL) {
				if(currCard instanceof Spell) {
					handGUI.removeCardFromHand(currCard);
					player.getDiscardPile().addCard(currCard);
					Spell spell = (Spell)currCard;
					spell.enterTrigger(boardGUI, boardGUI.getPlayerHandler(), boardGUI.getOpponentHandler());
					boardGUI.setCurrSpellCard(spell);
					boardGUI.setShowSpellCard(true);
					/*player.getHand().sortPlayerHand(player, currCard);
					player.getHand().removeCard(currCard);
					player.getDiscardPile().addCard(currCard);
					Spell spell = (Spell)currCard;
					spell.enterTrigger(boardGUI, boardGUI.getPlayerHandler(), boardGUI.getOpponentHandler());
					boardGUI.setCurrSpellCard(spell);
					boardGUI.setShowSpellCard(true);*/
				}
			}
			
		}
		if(mouseOver(discardBounds)) {
			//player.getHand().sortPlayerHand(player,currCard);
			//player.getHand().sortHand(player, currCard);
			handGUI.removeCardFromHand(currCard);
			player.discardCard(currCard);
		
			setShown(false);
			}
		}
	
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		if(currCard == null) {return;}
	}
	
	
	public Rectangle getPlayBounds() {
		return playBounds;
	}
	public void setPlayBounds(Rectangle playBounds) {this.playBounds = playBounds;}
	public Rectangle getDiscardBounds() {return discardBounds;}
	public void setDiscardBounds(Rectangle discardBounds) {	this.discardBounds = discardBounds;}
	public void setCurrCard(Card c) {this.currCard = c;}
	public Card getCurrCard() {return currCard;}
	public void setHandGUI(HandGUI handGUI) {this.handGUI = handGUI;}
	public HandGUI getHandGUI() {return handGUI;}
}
