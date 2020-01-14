/*
 Brendan Aucoin
 2018/04/12
 this class draws and holds everything for the board.
 */
package playing_panes;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import board.Board;
import board.Spot;
import cards.Building;
import cards.Card;
import cards.Spell;
import cards.Unit;
import game.Game;
import gui.Display;
import menu.EndGameMenu;
import phase.Phase;
import player.Player;
import player.PlayerHandler;
import sprites.Texture;
import state.State;
import triggers.DeathTrigger;
import triggers.EnterTrigger;
import visual_effects.Particle;

public class BoardGUI extends InteractionPane implements ActionListener{
	private Board board;
	private Player player1,player2;
	private Spot[] playerSpots;
	private Spot[] opponentSpots;
	private int spotWidth;
	private  int spotHeight;
	private CardDisplayGUI cardDisplayGUI;
	private boolean highlightOpponent;
	private Card currCard;
	private Rectangle playerIconBounds;
	private Rectangle opponentIconBounds;
	private boolean highlightPlayerIcon,highlightOpponentIcon;
	private ArrayList<Particle> particles;
	private PlayerHandler playerHandler,opponentHandler;
	private HandGUI handGUI;
	private OpponentHandGUI opponentHandGUI;
	private boolean showSpellCard;
	private Spell currSpellCard;
	private EndGameMenu endGameMenu;
	public BoardGUI(Board board,Player player1,Player player2,CardDisplayGUI cardDisplayGUI,HandGUI handGUI,OpponentHandGUI opponentHandGUI,EndGameMenu endGameMenu) {
		super(new Rectangle(
				(int)(Display.WIDTH/5),Display.HEIGHT/12,(int)((Display.WIDTH - Display.WIDTH/6)),(int)(( Display.HEIGHT/1.5)*1.03)
				));
		this.endGameMenu = endGameMenu;
		this.handGUI = handGUI;
		this.opponentHandGUI = opponentHandGUI;
		this.board = board;
		this.player1 = player1;
		this.player2 = player2;
		this.cardDisplayGUI = cardDisplayGUI;
		playerSpots = new Spot[6];
		opponentSpots = new Spot[6];
		this.spotWidth = 120;
		this.spotHeight = 180;
		for(int i =0; i < playerSpots.length; i++) {//spotWidth/4 for both
			playerSpots[i] = new Spot(new Rectangle((int) (getBounds().x - spotWidth + spotWidth/10 + (spotWidth) + (spotWidth* i * 1.3)),(int) (getBounds().y + getBounds().height - spotHeight*1.1),spotWidth,spotHeight	),true);
		}
		
		for(int i =0; i < opponentSpots.length;i++) {
			opponentSpots[i] = new Spot(new Rectangle((int) (getBounds().x + spotWidth/2  + (spotWidth) + (spotWidth* i * 1.3)),(int) (getBounds().y + spotHeight/25),spotWidth,spotHeight),true);
		}
		highlightOpponent = false;
		playerIconBounds = new Rectangle(
				(int) (getBounds().x + getBounds().width - Display.WIDTH/6.5),
				getBounds().y + getBounds().height - playerSpots[0].getBounds().height,
				Display.WIDTH/9,
				(int) (playerSpots[0].getBounds().height/1.3)
				);
		opponentIconBounds = new Rectangle(
				(int)(getBounds().x + Display.WIDTH/6.5 - Display.WIDTH/7),
				getBounds().y + opponentSpots[0].getBounds().height/8,
				Display.WIDTH/9,
				(int)(opponentSpots[0].getBounds().height/1.3)
				);
		highlightPlayerIcon = false;
		highlightOpponentIcon = false;
		particles = new ArrayList<Particle>();
		
		playerHandler = new PlayerHandler(player1,board.getPlayerCards(),playerSpots,handGUI);
		opponentHandler = new PlayerHandler(player2,board.getAICards(),opponentSpots,opponentHandGUI);
	}
	private void decideWinner(Player player,Player winner) {
		if(player.getHealth()<=0) {
			endGameMenu.setWinner(winner);
			Game.state= State.END_GAME;
		}
	}
	public void update() {
		decideWinner(player1,player2);
		decideWinner(player2,player1);
		board.updateBoard();
		for(int i =0; i < board.boardSize();i++) {
			if(board.getBoard().get(i) instanceof Unit){
				Unit unit = (Unit)(board.getBoard().get(i));
				if(unit.getHealth() <=0) {
					if(board.getPlayerCards().contains(unit)) {
						this.removeCard(unit, playerHandler, opponentHandler);
					}
					if(board.getAICards().contains(unit)) {
						this.removeCard(unit, opponentHandler, playerHandler);
					}
				}
				if(unit.getAttack() <0) {unit.setAttack(0);}
			}
		}
		try {
		Iterator <Particle>iter = particles.iterator();
		while(iter.hasNext()) {
			if(iter.next().getLife()<=0) {
				iter.remove();
			}
		}
		}catch(ConcurrentModificationException e) {
			update();
		}
	}
	public void render(Graphics2D g) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		//g.setColor(new Color(255,140,0));
		//g.fill(getBounds());
		g.drawImage(Texture.boardBackground, getBounds().x, getBounds().y, getBounds().width, getBounds().height, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD,50));
		//g.drawString("Board",(int)(getBounds().x + getBounds().width/2 - g.getFont().getStringBounds("Board", frc).getWidth()) , (int)((getBounds().y +  getBounds().height/2) - g.getFont().getStringBounds("Hand", frc).getHeight()*0.01));
		
		/*drawing the spots*/
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(1));
		for(int i=0; i < playerSpots.length;i++) {
			g.draw(playerSpots[i].getBounds());
		}
		
		for(int i=0; i < playerSpots.length;i++) {
			g.draw(opponentSpots[i].getBounds());
		}
		drawCardsToScreen(g);
		/*drawing the spell cards*/
		if(showSpellCard) {
			if(currSpellCard !=null) {
				g.drawImage(currSpellCard.getImage(),getBounds().x + getBounds().width/2 - currSpellCard.getWidth()*2,getBounds().y + getBounds().height/2,currSpellCard.getWidth()*2,currSpellCard.getHeight()*2,null);
				if(currSpellCard !=null) {currSpellCard.drawEffect(g);}
			}
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		/*drawing icons*/
		g.setColor(Color.BLUE);
		//g.fill(playerIconBounds.getBounds());
		if(highlightPlayerIcon && !(Game.firstTurn) && board.getPlayerCards().size() ==0) {
			g.setColor(Color.GREEN);
			g.setStroke(new BasicStroke(12));
			g.draw(playerIconBounds);
		}
		g.drawImage(Texture.playerIcon,playerIconBounds.x,playerIconBounds.y,playerIconBounds.width,playerIconBounds.height,null);
		if(highlightOpponentIcon && !(Game.firstTurn) && board.getAICards().size()==0) {
			g.setColor(Color.GREEN);
			g.setStroke(new BasicStroke(12));
			g.draw(opponentIconBounds);
		}
		
		g.drawImage(Texture.opponentIcon,opponentIconBounds.x,opponentIconBounds.y,opponentIconBounds.width,opponentIconBounds.height,null);
		g.setFont(new Font("Arial",Font.PLAIN,30));
		g.setColor(new Color(204,0,0));
		g.drawString(String.valueOf(player1.getHealth()), playerIconBounds.x + playerIconBounds.width/2, playerIconBounds.y - g.getFont().getSize()/2);
		g.drawString(String.valueOf(player2.getHealth()), opponentIconBounds.x + opponentIconBounds.width/2-g.getFont().getSize()/2, opponentIconBounds.y   + opponentIconBounds.height+g.getFont().getSize());
		Iterator <Particle>iter = particles.iterator();
		while(iter.hasNext()) {
			iter.next().render(g);
		}
	}
	
	public boolean hasSpots(Spot[]spots) {
		for(int i =0; i < spots.length;i++) {
			if(spots[i].isOpen()) {return true;}
		}
		return false;
	}
	/*public boolean addCardToPlayerBoard(Card c) {
		for(int i =0; i < playerSpots.length;i++) {
			if(playerSpots[i].isOpen()) {
				board.addCardToPlayer(c);
				c.setX(playerSpots[i].getBounds().x);
				c.setY(playerSpots[i].getBounds().y);
				c.setWidth(playerSpots[i].getBounds().width);
				c.setHeight(playerSpots[i].getBounds().height);
				playerSpots[i].setOpen(false);
				
				return true;
				
			}
		}
		return false;
	}*/
	public boolean addCardToBoard(Card c,PlayerHandler playerHandler,PlayerHandler opponentHandler,boolean doTrigger) {
		for(int i = 0; i < playerHandler.getSpots().length;i++) {
			if(playerHandler.getSpots()[i].isOpen()) {
				//playerList.add(i,c);
				playerHandler.getCardsOnBoard().add(c);
				c.setX(playerHandler.getSpots()[i].getBounds().x);
				c.setY(playerHandler.getSpots()[i].getBounds().y);
				c.setWidth(playerHandler.getSpots()[i].getBounds().width);
				c.setHeight(playerHandler.getSpots()[i].getBounds().height);
				c.setUsed(true);
				playerHandler.getSpots()[i].setOpen(false);
				if(doTrigger) {
					if(c instanceof EnterTrigger) {
						EnterTrigger enterTrigger = (EnterTrigger)c;
						enterTrigger.enterTrigger(this,playerHandler,opponentHandler);
					}
				}
				board.updateBoard();
				for(int k =0;k < board.getBoard().size();k++) {
					if(board.getBoard().get(k) instanceof Building) {
						Building building = (Building)board.getBoard().get(k);
						building.update(this,playerHandler,opponentHandler);
						
					}
				}
				for(int k =0; k < board.getBoard().size();k++) {
					board.getBoard().get(k).update(this, playerHandler, opponentHandler);
				}
				return true;
		}
			
				
	}
	return false;
	}
	/*public boolean addCardToBoard(Card c,Spot[]spots,List<Card>playerList,List<Card>opponentList,boolean doTrigger) {
			for(int i = 0; i < spots.length;i++) {
				if(spots[i].isOpen()) {
					//playerList.add(i,c);
					playerList.add(c);
					c.setX(spots[i].getBounds().x);
					c.setY(spots[i].getBounds().y);
					c.setWidth(spots[i].getBounds().width);
					c.setHeight(spots[i].getBounds().height);
					c.setUsed(true);
					spots[i].setOpen(false);
					if(doTrigger) {
						if(c instanceof EnterTrigger) {
							EnterTrigger enterTrigger = (EnterTrigger)c;
							//enterTrigger.enterTrigger(this,playerList,opponentList);
						}
					}
					return true;
			}
				
					
		}
		return false;
	}*/
	/*public boolean addCardToAiBoard(Card c) {
		for(int i =0; i < opponentSpots.length;i++) {
			if(opponentSpots[i].isOpen()) {
				board.getAICards().add(i,c);
				c.setX(opponentSpots[i].getBounds().x);
				c.setY(opponentSpots[i].getBounds().y);
				c.setWidth(opponentSpots[i].getBounds().width);
				c.setHeight(opponentSpots[i].getBounds().height);
				opponentSpots[i].setOpen(false);
				return true;
			}
		}
		
		return false;
	}*/
	public void removeBuilding(Building building,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		try {
				int index = playerHandler.getCardsOnBoard().indexOf(building);
				playerHandler.getSpots()[index].setOpen(true);
				for(int i =index; i < playerHandler.getCardsOnBoard().size()-1;i++) {
					if(playerHandler.getSpots()[i].isOpen()) {
						playerHandler.getCardsOnBoard().get(i+1).setX(playerHandler.getSpots()[i].getBounds().x);
						playerHandler.getCardsOnBoard().get(i+1).setY(playerHandler.getSpots()[i].getBounds().y);
						playerHandler.getCardsOnBoard().get(i+1).setWidth(playerHandler.getSpots()[i].getBounds().width);
						playerHandler.getCardsOnBoard().get(i+1).setHeight(playerHandler.getSpots()[i].getBounds().height);
						playerHandler.getSpots()[i+1].setOpen(true);
						playerHandler.getSpots()[i].setOpen(false);
					}
				}
				if(building instanceof DeathTrigger) {
					DeathTrigger deathTrigger = (DeathTrigger)building;
					deathTrigger.deathTrigger(this,playerHandler,opponentHandler);
				}
				board.updateBoard();
				cardDisplayGUI.setCurrCard(null);
				playerHandler.getCardsOnBoard().remove(index);
				building.getPlayer().getDiscardPile().addTopCard(building);
				for(int k =0;k < board.getBoard().size();k++) {
					if(board.getBoard().get(k) instanceof Building) {
						Building b = (Building)board.getBoard().get(k);
						b.update(this,playerHandler,opponentHandler);
						
					}
				}
				for(int k =0; k < board.getBoard().size();k++) {
					board.getBoard().get(k).update(this, playerHandler, opponentHandler);
				}
				this.addParticles(45, building.getX() + building.getWidth()/2, building.getY() + building.getHeight()/2, 3, 3,Color.black);
		}catch(Exception e) {}
	}
	public void removeCard(Unit unit,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		try {
			if(unit.getHealth() <=0) {
				int index = playerHandler.getCardsOnBoard().indexOf(unit);
				playerHandler.getSpots()[index].setOpen(true);
				for(int i =index; i < playerHandler.getCardsOnBoard().size()-1;i++) {
					if(playerHandler.getSpots()[i].isOpen()) {
						playerHandler.getCardsOnBoard().get(i+1).setX(playerHandler.getSpots()[i].getBounds().x);
						playerHandler.getCardsOnBoard().get(i+1).setY(playerHandler.getSpots()[i].getBounds().y);
						playerHandler.getCardsOnBoard().get(i+1).setWidth(playerHandler.getSpots()[i].getBounds().width);
						playerHandler.getCardsOnBoard().get(i+1).setHeight(playerHandler.getSpots()[i].getBounds().height);
						playerHandler.getSpots()[i+1].setOpen(true);
						playerHandler.getSpots()[i].setOpen(false);
					}
				}
				if(unit instanceof DeathTrigger) {
					DeathTrigger deathTrigger = (DeathTrigger)unit;
					deathTrigger.deathTrigger(this,playerHandler,opponentHandler);
				}
				board.updateBoard();
				
				cardDisplayGUI.setCurrCard(null);
				playerHandler.getCardsOnBoard().remove(index);
				unit.getPlayer().getDiscardPile().addTopCard(unit);
				for(int k =0;k < board.getBoard().size();k++) {
					if(board.getBoard().get(k) instanceof Building) {
						Building b = (Building)board.getBoard().get(k);
						b.update(this,playerHandler,opponentHandler);
						
					}
				}
				for(int k =0; k < board.getBoard().size();k++) {
					board.getBoard().get(k).update(this, playerHandler, opponentHandler);
				}
				this.addParticles(45, unit.getX() + unit.getWidth()/2, unit.getY() + unit.getHeight()/2, 3, 3,Color.black);
			}
		}catch(Exception e) {}//removeCard(unit,playerHandler,opponentHandler);}
	}
	private void playerAttack(Card playerCard,Card opponentCard) {
		Unit playerUnit = null;
		Unit opponentUnit = null;
		if(playerCard instanceof Unit) {playerUnit = (Unit)playerCard;}
		if(opponentCard instanceof Unit) {opponentUnit = (Unit)opponentCard;}
		opponentUnit.setHealth(opponentUnit.getHealth() - playerUnit.getAttack());
		/*retaliate against the player*/
		playerUnit.setHealth(playerUnit.getHealth() - opponentUnit.getAttack());
		//removeCard(opponentUnit,opponentSpots,board.getAICards(),board.getPlayerCards());
		//removeCard(playerUnit,playerSpots,board.getPlayerCards(),board.getAICards());
		removeCard(opponentUnit,opponentHandler,playerHandler);
		removeCard(playerUnit,playerHandler,opponentHandler);
	}
	
	public void mousePressed(MouseEvent e) {
		if(Game.phase != Phase.PLAY_ON_BOARD) {return;}
		if(showSpellCard == true) {return;}
		/*pressing your cards*/
			for(Card c:board.getPlayerCards()) {
				if(c.isUsed() == false) {
					if(mouseOver(c)) {
						if(c instanceof Unit) {
							highlightOpponent = true;
							highlightOpponentIcon = true;
							currCard = c;
						}
					}
				}
				
				
			}
		
		/*pressing you opponents cards*/
		if(highlightOpponent) {
			try {
				for(Card c : board.getAICards()) {
					if(mouseOver(c)) {
						if(c instanceof Unit) {
							currCard.setUsed(true);
							highlightOpponent = false;
							playerAttack(currCard,c);
							highlightOpponentIcon = false;
							currCard = null;
						}
					}
				}
			}catch(ConcurrentModificationException ex) {mousePressed(e);}
		}
		else {
			currCard = null;
		}
		clickOnIcons(e);
	}
	private void clickOnIcons(MouseEvent e) {
		if(mouseOver(playerIconBounds)) {
			
		}
		
		else if(mouseOver(opponentIconBounds)) {
			if(highlightOpponentIcon && board.getAICards().size() ==0) {
				if(!(Game.firstTurn)) {
					currCard.setUsed(true);
					highlightOpponent = false;
					Unit unit = (Unit)currCard;
					player2.setHealth(player2.getHealth() - unit.getAttack());
					currCard = null;
				}
			}
			
			highlightOpponentIcon = false;
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
			for(Card c : board.getPlayerCards()) {
				if(mouseOver(c)) {
					cardDisplayGUI.setCurrCard(c);
				}
			}
			
			for(Card c : board.getAICards()) {
				if(mouseOver(c)) {
					cardDisplayGUI.setCurrCard(c);
				}
			}
	}
	public void actionPerformed(ActionEvent e) {
		currSpellCard = null;
		showSpellCard= false;
	}
	private void drawCardsToScreen(Graphics2D g) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		/*drawing the cards*/
		g.setFont(new Font("Arial",Font.PLAIN,18));
		g.setColor(Color.WHITE);
		/*your cards*/
		for(int i=0; i < board.getPlayerCards().size();i++) {
			Card c = board.getPlayerCards().get(i);
			c.render(g);
			g.setFont(new Font("Arial",Font.PLAIN,18));
			g.setColor(Color.WHITE);
			
			
			if(c instanceof Unit) {
				Unit unit = (Unit)c;
				/*drawing health and attack*/
				//attack
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial",Font.BOLD,17));
				g.drawString(String.valueOf(unit.getAttack()), (int)(unit.getX() + unit.getWidth()/1.71 - g.getFont().getStringBounds(String.valueOf(unit.getAttack()), frc).getWidth()/2.5), (int)(unit.getY() + unit.getHeight() - unit.getHeight()/12.51));
				//health
				g.drawString(String.valueOf(unit.getHealth()), (int)(unit.getX() + unit.getWidth()/1.17 - g.getFont().getStringBounds(String.valueOf(unit.getHealth()), frc).getWidth()/2.2), (int)(unit.getY() + unit.getHeight() - unit.getHeight()/12.51));
			}	
			if(Game.phase == Phase.PLAY_ON_BOARD) {
				if(currCard != null &&currCard.equals(c)) {
					g.drawImage(Texture.attackingIcon, (int)(c.getX() + c.getWidth()/2 - Texture.attackingIcon.getWidth()/2),(int)(c.getY() - Texture.attackingIcon.getHeight()),null);
			 	}
				if(c.isUsed()) {
						g.setColor(Color.RED);
						g.setStroke(new BasicStroke(6));
						g.draw(c.getBounds());
					}
			}
		}
		g.setColor(Color.WHITE);
		/*the opponent cards*/
		for(int i =0; i <board.getAICards().size();i++) {
			g.setColor(Color.WHITE);
			//board.getAICards().get(i).render(g);
			g.drawImage(board.getAICards().get(i).getImage(), (int)(board.getAICards().get(i).getX()),(int)( board.getAICards().get(i).getY()), board.getAICards().get(i).getWidth(), board.getAICards().get(i).getHeight(), null);
			Unit unit = (Unit)board.getAICards().get(i);
			/*drawing the stats*/
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial",Font.BOLD,17));
			//attack
			g.drawString(String.valueOf(unit.getAttack()), (int)(unit.getX() + unit.getWidth()/1.71 - g.getFont().getStringBounds(String.valueOf(unit.getAttack()), frc).getWidth()/2.5), (int)(unit.getY() + unit.getHeight() - unit.getHeight()/12.51));
			//health
			g.drawString(String.valueOf(unit.getHealth()), (int)(unit.getX() + unit.getWidth()/1.17 - g.getFont().getStringBounds(String.valueOf(unit.getHealth()), frc).getWidth()/2.2), (int)(unit.getY() + unit.getHeight() - unit.getHeight()/12.51));
			if(Game.phase == Phase.PLAY_ON_BOARD) {
				if(highlightOpponent) {
					g.setColor(Color.GREEN);
					g.setStroke(new BasicStroke(6));
					g.draw(board.getAICards().get(i).getBounds());
				}
			}
		}
	}
	
	public void addParticles(int amount,float x,float y,int width,int height,Color col)
	{
		for(int i=0; i< amount; i++)
		{
			particles.add(new Particle(x + width/2,y + height/2,width,height,45,col,15));
		}
	}
	public void clearParticles() {
		particles.clear();
	}
	public void resetTurn() {
		currCard = null;
		highlightOpponent = false;
		highlightPlayerIcon = false;
		highlightOpponentIcon =false;
	}

	public Spot[] getPlayerSpots() {
		return playerSpots;
	}

	public void setPlayerSpots(Spot[] playerSpots) {
		this.playerSpots = playerSpots;
	}

	public Spot[] getOpponentSpots() {
		return opponentSpots;
	}

	public void setOpponentSpots(Spot[] opponentSpots) {
		this.opponentSpots = opponentSpots;
	}
	public Board getBoard() {
		return board;
	}

	public HandGUI getHandGUI() {
		return handGUI;
	}

	public void setHandGUI(HandGUI handGUI) {
		this.handGUI = handGUI;
	}

	public OpponentHandGUI getOpponentHandGUI() {
		return opponentHandGUI;
	}

	public void setOpponentHandGUI(OpponentHandGUI opponentHandGUI) {
		this.opponentHandGUI = opponentHandGUI;
	}

	public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}

	public void setPlayerHandler(PlayerHandler playerHandler) {
		this.playerHandler = playerHandler;
	}

	public PlayerHandler getOpponentHandler() {
		return opponentHandler;
	}

	public void setOpponentHandler(PlayerHandler opponentHandler) {
		this.opponentHandler = opponentHandler;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Spell getCurrSpellCard() {
		return currSpellCard;
	}

	public void setCurrSpellCard(Spell currSpellCard) {
		this.currSpellCard = currSpellCard;
	}

	public boolean isShowSpellCard() {
		return showSpellCard;
	}

	public void setShowSpellCard(boolean showSpellCard) {
		this.showSpellCard = showSpellCard;
	}
	
	
	
	
}
