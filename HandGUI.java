/*
 Brendan Aucoin
 2018/04/12
 this class draws and holds everything for your hand.
 */
package playing_panes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import board.Spot;
import cards.Card;
import cards.ManaCard;
import cards.Unit;
import game.Game;
import gui.Display;
import hand_gui_interface.IsHandGUI;
import id.ManaType;
import phase.Phase;
import player.Player;
import popups.HandPopupBox;
import sprites.Texture;
import visual_effects.Particle;

public class HandGUI extends InteractionPane implements IsHandGUI{
	private Player player;
	private Rectangle cardDeckBounds;
	private Rectangle manaDeckBounds;
	private ManaGUI manaGUI;
	private int cardWidth;
	private int cardHeight;
	private int cardX;
	private int cardY;
	private HandPopupBox popupBox;
	private CardDisplayGUI cardDisplayGUI;
	public static boolean drawCards;
	private Spot [] spots;
	private ArrayList<Particle> particleList;
	public HandGUI(Player player,ManaGUI manaGUI,HandPopupBox popupBox,CardDisplayGUI cardDisplayGUI) {
		super(new Rectangle((int)(Display.WIDTH/5),(int)(Display.HEIGHT/1.3),(int)(Display.WIDTH - Display.WIDTH/5),(int)(Display.HEIGHT - Display.HEIGHT/1.3)));
		this.player = player;
		this.manaGUI = manaGUI;
		cardDeckBounds = new Rectangle(
				(int) (getBounds().x + getBounds().width - getBounds().width/100 -Texture.backOfManaCard.getWidth()),
				(int) (getBounds().y + getBounds().height/1.5 - Texture.backOfManaCard.getHeight()),
				Texture.backOfCard.getWidth(),Texture.backOfCard.getHeight()
				);
		manaDeckBounds = new Rectangle(
				(int) (getBounds().x + getBounds().width - Texture.backOfCard.getWidth() - getBounds().width/70 -Texture.backOfManaCard.getWidth()),
				(int) (getBounds().y + getBounds().height/1.5 - Texture.backOfManaCard.getHeight()),
				Texture.backOfManaCard.getWidth(),Texture.backOfManaCard.getHeight()
				);
		this.popupBox = popupBox;
		this.cardDisplayGUI = cardDisplayGUI;
		cardWidth = 60;
		cardHeight = 90;
		spots = new Spot[11];
		drawCards = true;
		for(int i =0; i<spots.length;i++) {
			spots[i] = new Spot(new Rectangle(
					(int) (manaGUI.getBounds().x + manaGUI.getBounds().width  - cardWidth*5  + (cardWidth* i * 1.3))
					,(int) (manaGUI.getBounds().y + manaGUI.getBounds().height+cardHeight/15),cardWidth,cardHeight),true);
		}
		particleList = new ArrayList<Particle>();
		
	}
	/*drawing your cards that you start with*/
	public void drawInitialCards(int amount) {
		if(amount >spots.length) {return;}
		for(int i =0; i < amount;i++) {
			try {
				/*Card c = player.drawCard(player.getCardDeck(),player.getHand());
				cardX = manaGUI.getBounds().x + manaGUI.getBounds().width  - cardWidth*4+ player.handSize()*65;
				c.setX(cardX);
				cardY = (int) (manaGUI.getBounds().y + manaGUI.getBounds().height+cardHeight/15);
				c.setY(cardY);
				c.setWidth(cardWidth);
				c.setHeight(cardHeight);*/
				if(hasSpots()) {
				Card c = player.drawCard(player.getCardDeck(), player.getHand());
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
	public void update() {
		/*for(int i =0; i < player.getHand().getHand().size(); i++) {
			Card c = player.getHand().getHand().get(i);
			if(c.getX() != spots[i].getBounds().x) {
				c.setX(spots[i].getBounds().x);
			}
		}*/
		if(player.getHand().getHand().size() != player.getHand().getMaxSize()) {
			for(int i = player.getHand().getHand().size()+1;i < spots.length;i++) {
				spots[i].setOpen(true);
			}
		}
		for(int i =0; i < player.getHand().getHand().size();i++) {
			Card c = player.getHand().getHand().get(i);
			if(c.getStrCost()<=0) {c.setStrCost(0);}
			if(c.getIntCost()<=0) {c.setIntCost(0);}
			if(c.getConCost()<=0) {c.setConCost(0);}
			if(c.getChrCost()<=0) {c.setChrCost(0);}
			if(c.getDexCost()<=0) {c.setDexCost(0);}
		}
		if(Game.phase == Phase.MANA_DRAW) {
			for(int i =0;i < 1;i++) {
				particleList.add(new Particle(manaDeckBounds.x + manaDeckBounds.width/2,manaDeckBounds.y + manaDeckBounds.height/2,3,3,3,new Color(224,255,255),2));
			}
			Iterator<Particle>iter = particleList.iterator();
			while(iter.hasNext()) {
				Particle p = iter.next();
				if(p.getX() + p.getWidth() > manaDeckBounds.x + manaDeckBounds.width || p.getX() < manaDeckBounds.x || p.getY() < manaDeckBounds.y || p.getY() + p.getHeight() > manaDeckBounds.y + manaDeckBounds.height) {
					iter.remove();
				}
			}
		}
		
		if(Game.phase == Phase.CARD_DRAW) {
			for(int i =0;i < 1;i++) {
				particleList.add(new Particle(cardDeckBounds.x + cardDeckBounds.width/2,cardDeckBounds.y + cardDeckBounds.height/2,3,3,3,new Color(224,255,255),2));
			}
			Iterator<Particle>iter = particleList.iterator();
			while(iter.hasNext()) {
				Particle p = iter.next();
				if(p.getX() + p.getWidth() > cardDeckBounds.x + cardDeckBounds.width || p.getX() < cardDeckBounds.x || p.getY() < cardDeckBounds.y || p.getY() + p.getHeight() > cardDeckBounds.y + cardDeckBounds.height) {
					iter.remove();
				}
			}
		}
	}
	
	public void render(Graphics2D g) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		//g.setColor(Color.YELLOW);
		//g.fill(getBounds());
		g.drawImage(Texture.handBackground,getBounds().x, getBounds().y, getBounds().width, getBounds().height, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD,50));
		
		/*drawing the card deck*/
		g.drawImage(Texture.backOfCard, cardDeckBounds.x, cardDeckBounds.y, cardDeckBounds.width,cardDeckBounds.height,null);
		g.drawImage(Texture.backOfManaCard,manaDeckBounds.x,manaDeckBounds.y,manaDeckBounds.width,manaDeckBounds.height,null);
		manaGUI.render(g);
		/*drawing the cards*/
		try {
		if(drawCards) {
			for(int i =0;i < player.getHand().getHand().size();i++) {
				player.getHand().getHand().get(i).render(g);
				g.setColor(Color.BLACK);
				if(player.getHand().getHand().get(i) instanceof Unit) {
				Unit unit = (Unit)player.getHand().getHand().get(i);
				g.setFont(new Font("Arial",Font.BOLD,10));
				g.drawString(String.valueOf(unit.getAttack()), (int)(unit.getX() + unit.getWidth()/1.69- g.getFont().getStringBounds(String.valueOf(unit.getAttack()), frc).getWidth()/2.5), (int)(unit.getY() + unit.getHeight() - unit.getHeight()/12.51));
				//health
				g.drawString(String.valueOf(unit.getHealth()), (int)(unit.getX() + unit.getWidth()/1.16 - g.getFont().getStringBounds(String.valueOf(unit.getHealth()), frc).getWidth()/2.2), (int)(unit.getY() + unit.getHeight() - unit.getHeight()/12.51));
				}
			}
		}
		}catch(IndexOutOfBoundsException e) {return;}
		for(int i =0; i < player.getHand().getHand().size();i++) {
			if(Game.phase == Phase.PLAY_CARD || Game.phase == Phase.PLAY_ON_BOARD) {
				if(player.getNumStrMana() >= player.getHand().getHand().get(i).getStrCost()
						&&player.getNumIntMana() >= player.getHand().getHand().get(i).getIntCost()
						&&player.getNumConMana() >= player.getHand().getHand().get(i).getConCost()
						&&player.getNumChrMana() >= player.getHand().getHand().get(i).getChrCost()
						&&player.getNumDexMana() >= player.getHand().getHand().get(i).getDexCost()) {
					g.setColor(Color.GREEN);
					g.setStroke(new BasicStroke(1));
					g.draw(player.getHand().getHand().get(i).getBounds());
				}
				else {
					g.setColor(Color.RED);
					g.setStroke(new BasicStroke(1));
					g.draw(player.getHand().getHand().get(i).getBounds());
				}
			}
		}
		
		
		/*drawing costs*/
		for(int i =0; i < player.getHand().getHand().size();i++) {
			Card c = player.getHand().getHand().get(i);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial",Font.PLAIN,8));
			g.drawString(String.valueOf(c.getStrCost()),c.getX() + c.getWidth()/13,(float) (c.getY() + c.getHeight() - c.getHeight()/6.8));
			g.drawString(String.valueOf(c.getDexCost()),c.getX() + c.getWidth()/4.5f,(float) (c.getY() + c.getHeight() - c.getHeight()/6.8));
			g.drawString(String.valueOf(c.getIntCost()),c.getX() + c.getWidth()/2.7f,(float) (c.getY() + c.getHeight() - c.getHeight()/6.8));
			g.drawString(String.valueOf(c.getConCost()),c.getX() + c.getWidth()/8f,(float) (c.getY() + c.getHeight() - c.getHeight()/20));
			g.drawString(String.valueOf(c.getChrCost()),c.getX() + c.getWidth()/3.3f,(float) (c.getY() + c.getHeight() - c.getHeight()/20));
		}
		/*drawing the particles*/
		if(Game.phase == Phase.MANA_DRAW) {
			for(int i =0;i<particleList.size();i++) {
				particleList.get(i).render(g);
			}
		}
		if(Game.phase == Phase.CARD_DRAW) {
			for(int i =0;i<particleList.size();i++) {
				particleList.get(i).render(g);
			}
		}
		/*g.setFont(new Font("Arial",Font.PLAIN,30));
		g.drawString(String.valueOf(player.getCardDeck().getDeck().size()), cardDeckBounds.x + cardDeckBounds.width/2, cardDeckBounds.y + cardDeckBounds.height/2);
	
	
		for(int i =0 ; i < spots.length;i++) {
			g.setStroke(new BasicStroke(3));
			if(spots[i].isOpen()) {
				g.setColor(Color.RED);
			}
			else {g.setColor(Color.WHITE);}
			g.draw(spots[i].getBounds());
		}*/
	}
	
	public void mousePressed(MouseEvent e) {
		if(Game.phase == Phase.CARD_DRAW) {
			if(mouseOver(this.cardDeckBounds)) {
				drawCard();
				Game.phase = Phase.PLAY_CARD;
			}
		}
		 if(Game.phase == Phase.MANA_DRAW) {
			if(mouseOver(this.manaDeckBounds)){drawMana(e);}
		}
		else if(Game.phase == Phase.PLAY_CARD || Game.phase == Phase.PLAY_ON_BOARD) {
			
			for(Card c: player.getHand().getHand()) {
				if(mouseOver(c)) {
					popupBox.setShown(true);
					popupBox.setX(c.getX() + popupBox.getWidth()/4);
					popupBox.setY(c.getY() - c.getHeight()/2f);
					
					popupBox.setCurrCard(c);
				}
				
			}
		}
	}
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		//if(Game.phase == Phase.PLAY_CARD) {
			for(Card c: player.getHand().getHand()) {
				int y1 = cardY;
				if(mouseOver(c)) {
					int newWidth = (int) (cardWidth*1.3);
					int newHeight = (int)(cardHeight*1.3);
					int y2 = (int)(y1 - newHeight/2);
					c.setWidth(newWidth);
					c.setHeight(newHeight);
					c.setY(y2);
					cardDisplayGUI.setCurrCard(c);
				}
				else {
					c.setWidth(cardWidth);
					c.setHeight(cardHeight);
					c.setY(y1);
					
				}
			}
		//}
	}
	public void removeCardFromHand(Card c) {
		int index = player.getHand().getHand().indexOf(c);	
		for(int i =index; i < player.getHand().getHand().size()-1;i++) {
				player.getHand().getHand().get(i+1).setX(spots[i].getBounds().x);
				player.getHand().getHand().get(i+1).setY(spots[i].getBounds().y);
				player.getHand().getHand().get(i+1).setWidth(spots[i].getBounds().width);
				player.getHand().getHand().get(i+1).setHeight(spots[i].getBounds().height);
				spots[i+1].setOpen(true);
				spots[i+1].setOpen(true);
				spots[i].setOpen(false);
		}
		player.getHand().getHand().remove(c);
		
		
		int spotsIndex = 0;
		for(int i =0; i < player.getHand().getHand().size(); i++) {
			Card card = player.getHand().getHand().get(i);
			if(card.getX() != spots[i].getBounds().x) {
				card.setX(spots[i].getBounds().x);
				spotsIndex = i;
				for(int k=spotsIndex;k < spots.length;k++) {
					spots[k].setOpen(true);
				}
			}
		}
			spots[index].setOpen(true);
		
	}
	public void addCardToHand(Card c) {
		if(hasSpots()) {
			for(int i= 0; i< spots.length;i++) {
				if(spots[i].isOpen()) {
					if(player.handSize() <= player.getHand().getMaxSize()) {
						cardX = spots[i].getBounds().x;
						cardY = spots[i].getBounds().y;
						c.setX(spots[i].getBounds().x);
						c.setY(spots[i].getBounds().y);
						c.setWidth(spots[i].getBounds().width);
						c.setHeight(spots[i].getBounds().height);
						spots[i].setOpen(false);
						player.getHand().addCard(c);
						break;
					}
				}
			}
			
		}
		int spotsIndex =0;
		for(int i =0; i < player.getHand().getHand().size(); i++) {
			Card card = player.getHand().getHand().get(i);
			if(card.getX() != spots[i].getBounds().x) {
				card.setX(spots[i].getBounds().x);
				spotsIndex = i;
				for(int k=spotsIndex;k < spots.length;k++) {
					spots[k].setOpen(true);
				}
			}
		}
	}
	public void drawCard() {
		if(hasSpots()) {
			for(int i= 0; i< spots.length;i++) {
				if(spots[i].isOpen()) {
					if(player.handSize() <= player.getHand().getMaxSize()) {
						Card c = player.drawCard(player.getCardDeck(),player.getHand());
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
		int spotsIndex=0;
		for(int i =0; i < player.getHand().getHand().size(); i++) {
			Card card = player.getHand().getHand().get(i);
			if(card.getX() != spots[i].getBounds().x) {
				card.setX(spots[i].getBounds().x);
				spotsIndex = i;
				for(int k=spotsIndex;k < spots.length;k++) {
					spots[k].setOpen(true);
				}
			}
		}
	}
	public boolean hasSpots() {
		for(int i =0; i < spots.length;i++) {
			if(spots[i].isOpen()) {
				return true;
			}
		}
		return false;
	}
	public void drawMana() {
		Card [] manaCards = player.drawManyCards(3,player.getManaDeck(),player.getMana());
		for(int i =0;i < manaCards.length;i++) {
			manaCards[i].setX(Display.WIDTH/1.5f - cardWidth*4 * (i));
			manaCards[i].setY(Display.HEIGHT/2 - cardHeight*2);
			manaCards[i].setWidth(cardWidth*3);
			manaCards[i].setHeight(cardHeight*3);
		}
	}
	/*private void drawMana(MouseEvent e) {
		if(mouseOver(manaDeckBounds)) {
			//Card [] manaCards = player.drawManyCards(3,player.getManaDeck(),player.getMana());
			Card[] manaCards = player.drawMana(3);
			for(int i =0;i < manaCards.length;i++) {
			manaCards[i].setX(Display.WIDTH/1.5f - cardWidth*4 * (i));
				manaCards[i].setY(Display.HEIGHT/2 - cardHeight*2);
				manaCards[i].setWidth(cardWidth*3);
				manaCards[i].setHeight(cardHeight*3);
				ManaCard manaCard = (ManaCard)manaCards[i];
			}
			Game.phase = Phase.PICK_MANA;
		}
	}*/
	//player.getMana().addCard(new ManaCard(0,0,0,0,player,ManaType.STR,null));
	private void drawMana(MouseEvent e) {
		if(mouseOver(manaDeckBounds)) {
			Random rand = new Random();
			Card[] manaCards = {
					new ManaCard(0,0,0,0,player,ManaType.STR,null),
					new ManaCard(0,0,0,0,player,ManaType.INT,null),
					new ManaCard(0,0,0,0,player,ManaType.DEX,null),
					new ManaCard(0,0,0,0,player,ManaType.CON,null),
					new ManaCard(0,0,0,0,player,ManaType.CHR,null)};
			HashSet<Card> manaCardSet = new HashSet<Card>();
			while(manaCardSet.size() <3) {
				manaCardSet.add(manaCards[rand.nextInt(manaCards.length)]);
			}
			//Set<Card> mySet = new HashSet<>(Arrays.asList(manaCards));
			List<Card> list = new ArrayList<Card>(manaCardSet);
			for(int i =0;i < list.size();i++) {
				player.getMana().addCard(list.get(i));
			}
			for(int i =0;i < player.getMana().getHand().size();i++) {
				player.getMana().getHand().get(i).setX(Display.WIDTH/1.5f - cardWidth*4 * (i));
				player.getMana().getHand().get(i).setY(Display.HEIGHT/2 - cardHeight*2);
				player.getMana().getHand().get(i).setWidth(cardWidth*3);
				player.getMana().getHand().get(i).setHeight(cardHeight*3);
				//ManaCard manaCard = (ManaCard)player.getMana().getHand().get(i);
				}
				Game.phase = Phase.PICK_MANA;
		}
	}

	
}
