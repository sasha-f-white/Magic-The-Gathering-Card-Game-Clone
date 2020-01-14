/*
 Brendan Aucoin
 2018/04/12
 this class draws and holds everything for the opponents hand.
 */
package playing_panes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import board.Spot;
import cards.Card;
import cards.ManaCard;
import gui.Display;
import hand_gui_interface.IsHandGUI;
import id.ManaType;
import player.Player;
import sprites.Texture;

public class OpponentHandGUI extends InteractionPane implements IsHandGUI{
	private OpponentManaGUI opponentManaGUI;
	private Player opponent;
	private int cardWidth;
	private int cardHeight;
	private int cardX = 0;
	private int cardY = 0;
	private Rectangle cardDeckBounds;
	private Rectangle manaDeckBounds;
	private Spot[]spots;
	public OpponentHandGUI(OpponentManaGUI opponentManaGUI,Player opponent) {
		super(new Rectangle(
				(int)(Display.WIDTH/5),0,(int)(Display.WIDTH - Display.WIDTH/5),Display.HEIGHT/12
				));
		
		cardDeckBounds = new Rectangle(
				(int) (getBounds().x + getBounds().width - getBounds().width/100 -Texture.backOfManaCard.getWidth()),
				getBounds().y,
				Texture.backOfCard.getWidth(),getBounds().height
				);
		manaDeckBounds = new Rectangle(
				(int) (getBounds().x + getBounds().width - Texture.backOfCard.getWidth() - getBounds().width/70 -Texture.backOfManaCard.getWidth()),
				getBounds().y,
				Texture.backOfManaCard.getWidth(),getBounds().height
				);
		spots = new Spot[11];
		
		this.opponent = opponent;
		cardWidth = 30;
		cardHeight = 45;
		cardX = 0;
		cardY = 0;
		this.opponentManaGUI= opponentManaGUI;
		for(int i =0; i<spots.length;i++) {
			spots[i] = new Spot(new Rectangle(
					(int)(opponentManaGUI.getBounds().x + opponentManaGUI.getBounds().width + ((cardWidth* i * 1.3))),
					(int) (opponentManaGUI.getBounds().y + opponentManaGUI.getBounds().height-cardHeight*1.1),
					cardWidth,cardHeight
					),true);
		}
	}
	
	public void update() {
		/*for(int i =0; i < opponent.getHand().getHand().size(); i++) {
			Card c = opponent.getHand().getHand().get(i);
			if(c.getX() != spots[i].getBounds().x) {
				c.setX(spots[i].getBounds().x);
				System.out.println(i-1);
			}
		}*/
	}
	public boolean hasSpots() {
		for(int i =0; i < spots.length;i++) {
			if(spots[i].isOpen()) {return true;}
		}
		return false;
	}
	public void drawInitialCards(int amount) {
		if(amount >spots.length) {return;}
		for(int i =0; i < amount;i++) {
			try {
				/*Card c = opponent.drawCard(opponent.getCardDeck(),opponent.getHand());
				cardX = opponentManaGUI.getBounds().x + opponentManaGUI.getBounds().width  - cardWidth+ opponent.handSize()*45;
				c.setX(cardX);
				cardY = (int) (opponentManaGUI.getBounds().y + opponentManaGUI.getBounds().height-cardHeight*1.1);
				c.setY(cardY);
				c.setWidth(cardWidth);
				c.setHeight(cardHeight);*/
				if(hasSpots()) {
					Card c = opponent.drawCard(opponent.getCardDeck(), opponent.getHand());
					cardX = spots[i].getBounds().x;
					cardY = spots[i].getBounds().y;
					c.setX(spots[i].getBounds().x);
					c.setY(spots[i].getBounds().y);
					c.setWidth(spots[i].getBounds().width);
					c.setHeight(spots[i].getBounds().height);
					spots[i].setOpen(false);
				}
				Thread.sleep(175);
			}
			catch(InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public void render(Graphics2D g) {
		//g.setColor(Color.BLUE);
		//g.fill(getBounds());
		g.drawImage(Texture.opponentHandBackground, getBounds().x, getBounds().y, getBounds().width, getBounds().height, null);
		/*drawing the deck and mana sprites*/
		/*drawing the card deck*/
		g.drawImage(Texture.backOfCard, cardDeckBounds.x, cardDeckBounds.y, cardDeckBounds.width,cardDeckBounds.height ,null);
		g.drawImage(Texture.backOfManaCard,manaDeckBounds.x,manaDeckBounds.y,manaDeckBounds.width,manaDeckBounds.height,null);
		
		
		/*drawing the cards*/
		for(int i =0;i <opponent.getHand().getHand().size();i++) {
			Card c = opponent.getHand().getHand().get(i);
			g.drawImage(Texture.backOfCard,(int)c.getX(),(int)c.getY(),c.getWidth(),c.getHeight(),null);
			//g.drawImage(c.getImage(),(int)c.getX(),(int)c.getY(),c.getWidth(),c.getHeight(),null);
		}
	}
	
	public void removeCardFromHand(Card c) {
		int index = opponent.getHand().getHand().indexOf(c);	
		for(int i =index; i < opponent.getHand().getHand().size()-1;i++) {
			opponent.getHand().getHand().get(i+1).setX(spots[i].getBounds().x);
			opponent.getHand().getHand().get(i+1).setY(spots[i].getBounds().y);
			opponent.getHand().getHand().get(i+1).setWidth(spots[i].getBounds().width);
			opponent.getHand().getHand().get(i+1).setHeight(spots[i].getBounds().height);
			spots[i+1].setOpen(true);
			spots[i].setOpen(false);
			
		}
		opponent.getHand().getHand().remove(c);
		for(int i =0; i < opponent.getHand().getHand().size(); i++) {
			Card card = opponent.getHand().getHand().get(i);
			if(card.getX() != spots[i].getBounds().x) {
				card.setX(spots[i].getBounds().x);
				System.out.println(i-1);
			}
		}
	}
	
	public void drawCard() {
		/*if(opponent.handSize() <= opponent.getHand().getMaxSize()) {
			Card c = opponent.drawCard(opponent.getCardDeck(),opponent.getHand());
			cardX = opponentManaGUI.getBounds().x + opponentManaGUI.getBounds().width  - cardWidth+ opponent.handSize()*45;
			c.setX(cardX);
			cardY = (int) (opponentManaGUI.getBounds().y + opponentManaGUI.getBounds().height-cardHeight*1.1);
			c.setY(cardY);
			c.setWidth(cardWidth);
			c.setHeight(cardHeight);
		}*/
		if(hasSpots()) {
			for(int i= 0; i< spots.length;i++) {
				if(spots[i].isOpen()) {
					if(opponent.handSize() <= opponent.getHand().getMaxSize()) {
						Card c = opponent.drawCard(opponent.getCardDeck(),opponent.getHand());
						cardX = spots[i].getBounds().x;
						cardY = spots[i].getBounds().y;
						c.setX(spots[i].getBounds().x);
						c.setY(spots[i].getBounds().y);
						c.setWidth(spots[i].getBounds().width);
						c.setHeight(spots[i].getBounds().height);
						spots[i].setOpen(false);
						break;
					}
				}
			}
		}
		for(int i =0; i < opponent.getHand().getHand().size(); i++) {
			Card card = opponent.getHand().getHand().get(i);
			if(card.getX() != spots[i].getBounds().x) {
				card.setX(spots[i].getBounds().x);
				System.out.println(i-1);
			}
		}
	}
	public void addCardToHand(Card c) {
		/*if(opponent.handSize() <= opponent.getHand().getMaxSize()) {
			//c = opponent.drawCard(opponent.getCardDeck(),opponent.getHand());
			cardX = opponentManaGUI.getBounds().x + opponentManaGUI.getBounds().width  - cardWidth+ opponent.handSize()*45;
			c.setX(cardX);
			cardY = (int) (opponentManaGUI.getBounds().y + opponentManaGUI.getBounds().height-cardHeight*1.1);
			c.setY(cardY);
			c.setWidth(cardWidth);
			c.setHeight(cardHeight);
			opponent.getHand().addCard(c);
			return c;
		}
		return null;*/
		if(hasSpots()) {
			for(int i= 0; i< spots.length;i++) {
				if(spots[i].isOpen()) {
					if(opponent.handSize() <= opponent.getHand().getMaxSize()) {
						cardX = spots[i].getBounds().x;
						cardY = spots[i].getBounds().y;
						c.setX(spots[i].getBounds().x);
						c.setY(spots[i].getBounds().y);
						c.setWidth(spots[i].getBounds().width);
						c.setHeight(spots[i].getBounds().height);
						spots[i].setOpen(false);
						break;
					}
				}
			}
		}
		for(int i =0; i < opponent.getHand().getHand().size(); i++) {
			Card card = opponent.getHand().getHand().get(i);
			if(card.getX() != spots[i].getBounds().x) {
				card.setX(spots[i].getBounds().x);
				System.out.println(i-1);
			}
		}
	}
	
	/*public Card[] drawMana() {
		Card [] manaCards = opponent.drawManyCards(3,opponent.getManaDeck(),opponent.getMana());
		for(int i =0;i < manaCards.length;i++) {
			manaCards[i].setX(Display.WIDTH/1.5f - cardWidth*6 * (i*1.5f));
			manaCards[i].setY(Display.HEIGHT/2 - cardHeight*4.5f);
			manaCards[i].setWidth(cardWidth*8);
			manaCards[i].setHeight(cardHeight*8);
		}
		return manaCards;
	}*/
	public Card[]drawMana(){
		Random rand = new Random();
		ArrayList<ManaCard>manaList = new ArrayList<ManaCard>();
		Card[] manaCards = new Card[3];
		for(int i =0;i < 3;i++) {
			int randNum = rand.nextInt(5)+1;
			if(randNum == 1) {manaList.add(new ManaCard(0,0,0,0,opponent,ManaType.STR,null));}
			else if(randNum == 2) {manaList.add(new ManaCard(0,0,0,0,opponent,ManaType.DEX,null));}
			else if(randNum == 3) {manaList.add(new ManaCard(0,0,0,0,opponent,ManaType.INT,null));}
			else if(randNum == 4) {manaList.add(new ManaCard(0,0,0,0,opponent,ManaType.CON,null));}
			else {manaList.add(new ManaCard(0,0,0,0,opponent,ManaType.CHR,null));}
		}
		for(int i =0; i < manaList.size();i++) {
			manaCards[i] = manaList.get(i);
		}
		return manaCards;
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
	}

	
}
