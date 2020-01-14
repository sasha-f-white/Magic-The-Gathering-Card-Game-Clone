package playing_panes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import cards.Card;
import cards.Unit;
import game.Game;
import phase.Phase;
import player.PlayerHandler;
import triggers.DeathTrigger;
import triggers.EnterTrigger;

public class SelectCardGUI extends InteractionPane{
	private Card[]cards;
	private int numPicks;
	private Phase previousPhase;
	private BoardGUI boardGUI;
	private Rectangle bounds;
	private float[] originalCardX,originalCardY;
	private int []originalCardWidth,originalCardHeight;
	private int currPick;
	private boolean [] cardPicked;
	private Card sender;
	private String trigger;
	private ArrayList<Card>selectedCards;
	private Rectangle confirmBounds;
	private CardDisplayGUI cardDisplayGUI;
	private PlayerHandler playerHandler,opponentHandler;
	public SelectCardGUI(BoardGUI boardGUI,CardDisplayGUI cardDisplayGUI) {
		super(new Rectangle(
				0,0,0,0));
		this.boardGUI = boardGUI;
		this.cardDisplayGUI = cardDisplayGUI;
		bounds = new Rectangle(boardGUI.getBounds().x,boardGUI.getBounds().y,boardGUI.getBounds().x + boardGUI.getBounds().width,boardGUI.getBounds().y + boardGUI.getBounds().height);
		cards = new Card[0];
		cardPicked = new boolean[0];
		currPick = 0;
		selectedCards = new ArrayList<Card>();
		confirmBounds = new Rectangle(
				bounds.x + bounds.width/2 + bounds.width/8,
				bounds.y + bounds.height/2 + bounds.height/4,
				bounds.width/8,
				bounds.height/8
				);
	}
	public void update() {
		
	}
	private void drawCardsInGrid(Graphics2D g,Rectangle bounds,Card[] cards,Color col,Stroke stroke,boolean [] cardPicked) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		int counter = 0;
		int rowSize = (int) (((bounds.x + bounds.width)/((cards.length*3.5f)-cards.length))/3);
		int rows =(int) ((int) cards.length/rowSize + 1);
		
        for(int i =0;i< rows;i++) {
 	        for (int j = 0; j < rowSize; j++){
 	        	if (counter < cards.length){
 	        	  cards[counter].setHeight(90);
 	 	          cards[counter].setWidth(60);
 	        	  cards[counter].setX(bounds.x + cards[counter].getWidth()/2 + (j*cards.length*rowSize/3));	        
 	 	          cards[counter].setY(bounds.y+ cards[counter].getHeight()/2 + (i*cards.length*rowSize/3));
 	 	           if(cardPicked[counter]) {
 	 	        	  g.setColor(Color.WHITE);
 	 	        	  g.setStroke(new BasicStroke(6));
 	 	        	  g.draw(cards[counter].getBounds());
 	 	           }
 	 	        cards[counter].render(g);
 	 	      if(cards[counter] instanceof Unit) {
	 	        	Unit unit = (Unit)cards[counter];
	 	        	g.setColor(Color.BLACK);
					g.setFont(new Font("Arial",Font.BOLD,10));
					g.drawString(String.valueOf(unit.getAttack()), (int)(unit.getX() + unit.getWidth()/1.71 - g.getFont().getStringBounds(String.valueOf(unit.getAttack()), frc).getWidth()/2.5), (int)(unit.getY() + unit.getHeight() - unit.getHeight()/12.51));
					//health
					g.drawString(String.valueOf(unit.getHealth()), (int)(unit.getX() + unit.getWidth()/1.17 - g.getFont().getStringBounds(String.valueOf(unit.getHealth()), frc).getWidth()/2.2), (int)(unit.getY() + unit.getHeight() - unit.getHeight()/12.51));
	 	        }
 	 	        counter++;
 	        	}
 	        }
        }
	}
	public void render(Graphics2D g) {
		 /*drawing the confrim button*/
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        if(currPick == numPicks || currPick == cards.length) {
        	changeColour(g,confirmBounds,new Color(192,192,192),new Color(255,255,224));
        }
        else {
        	g.setColor(new Color(192,192,192));
        }
		g.fillRoundRect(confirmBounds.x,confirmBounds.y,confirmBounds.width,confirmBounds.height,125,125);
		g.setFont(new Font("Arial",Font.PLAIN,30));
		g.setColor(Color.BLACK);
		g.drawString("Confirm",(int) (confirmBounds.x + confirmBounds.width/2 - confirmBounds.width/3.5), confirmBounds.y + confirmBounds.height/2 + confirmBounds.height/8);
		
		drawCardsInGrid(g,bounds,cards,Color.WHITE,new BasicStroke(8),cardPicked);
		for(int i =0; i <cards.length;i++) {
			if(mouseOver(cards[i])) {
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial",Font.PLAIN,20));
				g.drawString("Owner: " + cards[i].getPlayer(), cards[i].getX() + cards[i].getWidth(), 
				cards[i].getY() + cards[i].getHeight() + 
				(int)(g.getFont().getStringBounds("Owner: " + cards[i].getPlayer(), frc).getHeight()));
			}
		}
      }
	private void finishPhase() {
		/*finished with this phase its gonna be chagned to when you press a button though*/
		if(currPick == numPicks || currPick == cards.length) {
			Game.phase = previousPhase;
			for(int i =0;i<cards.length;i++) {
				cards[i].setX(originalCardX[i]);
				cards[i].setY(originalCardY[i]);
				cards[i].setWidth(originalCardWidth[i]);
				cards[i].setHeight(originalCardHeight[i]);
				currPick = 0;
			}
			if(trigger.equals(Game.ENTER_TRIGGER)) {
				if(sender instanceof EnterTrigger) {
					EnterTrigger enterTrigger = (EnterTrigger)sender;
					enterTrigger.activateEnterTrigger(boardGUI,playerHandler,opponentHandler,selectedCards);
				}
			}
			else if(trigger.equals(Game.DEATH_TRIGGER)) {
				if(sender instanceof DeathTrigger) {
					DeathTrigger deathTrigger = (DeathTrigger)sender;
					deathTrigger.activateDeathTrigger(boardGUI,playerHandler,opponentHandler,selectedCards);
				}
			}
			selectedCards.clear();
		}
			
	}
	public void mousePressed(MouseEvent e) {
		if(mouseOver(confirmBounds)) {
			finishPhase();
		}
		/*the cards*/
		for(int i =0;i<cards.length;i++) {
			if(mouseOver(cards[i])) {
				if(cardPicked[i] == false) {
					if(currPick < numPicks) {
						cardPicked[i] = true;
						currPick++;
						selectedCards.add(cards[i]);
					}
				}
				else if(cardPicked[i] == true) {
					cardPicked[i] = false;
					currPick--;
					selectedCards.remove(cards[i]);
				}
			}
		}//end of for loop
		if(currPick > numPicks) {currPick = numPicks;}
		
	}
	
	
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		for(int i =0;i<cards.length;i++) {
			if(mouseOver(cards[i])) {
				cardDisplayGUI.setCurrCard(cards[i]);
				
			}
		}
	}
	
	public void setCards(Card ... cards) {
		this.cards = new Card[cards.length];
		originalCardX = new float[cards.length];
		originalCardY = new float[cards.length];
		originalCardWidth = new int[cards.length];
		originalCardHeight = new int[cards.length];
		for(int i =0; i  < cards.length; i++) {
			this.cards[i] = cards[i];
			originalCardX [i]= cards[i].getX();
			originalCardY[i] = cards[i].getY();
			originalCardWidth [i]= cards[i].getWidth();
			originalCardHeight [i]= cards[i].getHeight();
		}
	}
	public void set(int numPicks,Phase previousPhase,Card sender,String trigger,PlayerHandler playerHandler,PlayerHandler opponentHandler,Card...cards) {
		this.numPicks = numPicks;
		this.previousPhase = previousPhase;
		this.cards = new Card[cards.length];
		originalCardX = new float[cards.length];
		originalCardY = new float[cards.length];
		originalCardWidth = new int[cards.length];
		originalCardHeight = new int[cards.length];
		for(int i =0; i  < cards.length; i++) {
			this.cards[i] = cards[i];
			originalCardX [i]= cards[i].getX();
			originalCardY[i] = cards[i].getY();
			originalCardWidth [i]= cards[i].getWidth();
			originalCardHeight [i]= cards[i].getHeight();
		}
		cardPicked = new boolean[cards.length];
		this.sender = sender;
		this.trigger = trigger;
		this.playerHandler = playerHandler;
		this.opponentHandler = opponentHandler;
	}

	public Card[] getCards() {return cards;}
	public int getNumPicks() {return numPicks;}

	public void setNumPicks(int numPicks) {this.numPicks = numPicks;}

	public Phase getPreviousPhase() {return previousPhase;}
	public void setPreviousPhase(Phase previousPhase) {this.previousPhase = previousPhase;}
	public int getCurrPick() {return currPick;}
	public void setCurrPick(int currPick) {this.currPick = currPick;}
	public boolean[] getCardPicked() {return cardPicked;}
	public void setCardPicked(boolean[] cardPicked) {this.cardPicked = cardPicked;}
	public Card getSender() {return sender;}
	public void setSender(Card sender) {this.sender = sender;}
	public void setTrigger(String trigger) {this.trigger = trigger;}
	public String getTrigger() {return trigger;}
	public PlayerHandler getPlayerHandler() {return playerHandler;}
	public void setPlayerHandler(PlayerHandler playerHandler) {this.playerHandler = playerHandler;}
	public PlayerHandler getOpponentHandler() {return opponentHandler;}
	public void setOpponentHandler(PlayerHandler opponentHandler) {this.opponentHandler = opponentHandler;}
	public BoardGUI getBoardGUI() {
		return boardGUI;
	}
	public void setBoardGUI(BoardGUI boardGUI) {
		this.boardGUI = boardGUI;
	}
	public Rectangle getBounds() {return bounds;}
	public void setBounds(Rectangle bounds) {this.bounds = bounds;}
	public float[] getOriginalCardX() {return originalCardX;}
	public void setOriginalCardX(float[] originalCardX) {this.originalCardX = originalCardX;}
	public float[] getOriginalCardY() {return originalCardY;}
	public void setOriginalCardY(float[] originalCardY) {this.originalCardY = originalCardY;}
	public int[] getOriginalCardWidth() {return originalCardWidth;}
	public void setOriginalCardWidth(int[] originalCardWidth) {this.originalCardWidth = originalCardWidth;}
	public int[] getOriginalCardHeight() {	return originalCardHeight;}
	public void setOriginalCardHeight(int[] originalCardHeight) {this.originalCardHeight = originalCardHeight;}
	public ArrayList<Card> getSelectedCards() {return selectedCards;}
	public void setSelectedCards(ArrayList<Card> selectedCards) {this.selectedCards = selectedCards;}
	public Rectangle getConfirmBounds() {return confirmBounds;}
	public void setConfirmBounds(Rectangle confirmBounds) {this.confirmBounds = confirmBounds;}
	public CardDisplayGUI getCardDisplayGUI() {return cardDisplayGUI;}
	public void setCardDisplayGUI(CardDisplayGUI cardDisplayGUI) {this.cardDisplayGUI = cardDisplayGUI;}
	
	
}
