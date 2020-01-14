package ai;
/*
 Brendan Aucoin
 2018/04/23
 this class is the ai's logic and moslty random based. it picks a card then plays it if it can and will attack most of the time.
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

import javax.swing.Timer;

import board.Board;
import cards.Building;
import cards.Card;
import cards.ManaCard;
import cards.Spell;
import cards.Unit;
import deck.Deck;
import game.Game;
import id.ID;
import id.ManaType;
import player.Player;
import playing_panes.BoardGUI;
import playing_panes.OpponentHandGUI;
import playing_panes.SwitchTurnGUI;
import state.AiState;

public class Ai extends Player implements ActionListener{
	private AiState aiState;//to keep track of what the AI should be doing
	private OpponentHandGUI opponentHandGUI;//the ai's hand
	private Card manaCardChoices[];
	private Card manaCardChoice;
	private SecureRandom rand;//a random
	private Timer timer;
	private int currDelay;
	private int count;
	private boolean drawWhiteBorder;
	private Board board;
	private BoardGUI boardGUI;
	private SwitchTurnGUI switchTurnGUI;
	private Game game;
	private Timer attackTimer;
	private Card cardPicked;//the card they picked to play
	/*all the constructors*/
	public Ai(String name,int health,Deck cardDeck,Deck manaDeck,Game game) {
		super(name,health,cardDeck,manaDeck);
		init(game);
	}
	public Ai(String name,int health,Game game) {
		super(name,health);
		init(game);
	}
	
	public Ai(Ai ai,Game game) {
		super(ai);
		init(game);
	}
	/*initilializes all the fields.*/
	private void init(Game game) {
		cardPicked = null;
		aiState = AiState.PLAYERS_TURN;
		rand = new SecureRandom();
		currDelay = 0;
		timer = new Timer(currDelay,this);
		count = 0;
		drawWhiteBorder = false;
		this.game  =game;
		attackTimer = new Timer(950,this);
	}
	/*where the logic of the ai happens*/
	public void update() {
		if(attackTimer.isRunning() == false) {
			manaCardChoices = opponentHandGUI.drawMana();//draw the mana
			int randNum = rand.nextInt(3)+1;
			manaCardChoice = manaCardChoices[randNum-1];
			ManaCard manaCard = (ManaCard)manaCardChoice;
			if(manaCard.getType() == ManaType.STR) {setNumStrMana(getNumStrMana()+1);}
			else if(manaCard.getType() == ManaType.INT) {setNumIntMana(getNumIntMana()+1);}
			else if(manaCard.getType() == ManaType.DEX) {setNumDexMana(getNumDexMana()+1);}
			else if(manaCard.getType() == ManaType.CHR) {setNumChrMana(getNumChrMana()+1);}
			else if(manaCard.getType() == ManaType.CON) {setNumConMana(getNumConMana()+1);}
			opponentHandGUI.drawCard();
			playCard();
		}
	}
	/*this method attacks the player*/
	private void attackPlayer() {
		int size = 0;
		for(int i =0; i < boardGUI.getBoard().getPlayerCards().size();i++) {
			if(boardGUI.getBoard().getPlayerCards().get(i) instanceof Unit) {
				size++;
			}
		}
		if(size == 0) {
			for(int i =0; i < boardGUI.getBoard().getAICards().size();i++) {
				Card opponentCard= boardGUI.getBoard().getAICards().get(i);
				//if(opponentCard.isUsed()) {return;}
				if(cardPicked != null && !cardPicked.equals(opponentCard)) {
				if(opponentCard instanceof Unit) {
					Unit opponentUnit = (Unit)opponentCard;
					boardGUI.getPlayerHandler().getPlayer().setHealth(boardGUI.getPlayerHandler().getPlayer().getHealth()-opponentUnit.getAttack());
					opponentCard.setUsed(true);
					
				}
				}
			}
		}
		for(int i =0; i < boardGUI.getBoard().getAICards().size();i++) {
			Card opponentCard = boardGUI.getBoard().getAICards().get(i);
			if(opponentCard.isUsed()) {return;}
			for(int k = 0;k < boardGUI.getBoard().getPlayerCards().size();k++) {
				Card playerCard = boardGUI.getBoard().getPlayerCards().get(k);
				if(opponentCard instanceof Unit) {
					Unit opponentUnit = (Unit)opponentCard;
					if(playerCard instanceof Unit) {
						Unit playerUnit = (Unit)playerCard;
						if(opponentUnit.getAttack() >= playerUnit.getAttack()) {
							opponentUnit.setHealth(opponentUnit.getHealth() - playerUnit.getAttack());
							/*retaliate against the player*/
							playerUnit.setHealth(playerUnit.getHealth() - opponentUnit.getAttack());
						}
					}
				}
			}
		}
	}
	/*switches the turn to the players turn after a certain time.*/
	public void actionPerformed(ActionEvent e) {
		attackPlayer();
		game.switchToPlayersTurn();//after the ai is done switch over to the players turn to repeat*/
		attackTimer.stop();	
	}
	/*the ai will pick a card to play and add it to the board.*/
	private void playCard() {
		attackTimer.start();
		Card cardToPlay = null;
		for(int i =0 ; i < getHand().getHand().size();i++) {
			Card c = getHand().getHand().get(i);
			if(getNumStrMana() >= c.getStrCost() && getNumIntMana() >= c.getIntCost()&&getNumConMana() >= c.getConCost()&&getNumChrMana() >= c.getChrCost()&&getNumDexMana() >= c.getDexCost()) {
				cardToPlay = c;
				break;
			}
		}
		if(cardToPlay != null) {cardPicked = cardToPlay;cardToPlay.setUsed(true);}
		//if(cardToPlay == null) {return;}
		if(cardToPlay != null) {
				if(getNumChrMana() >= cardToPlay.getChrCost() && getNumStrMana() >= cardToPlay.getStrCost()&&getNumConMana() >= cardToPlay.getConCost()&&getNumDexMana() >= cardToPlay.getDexCost()&&getNumIntMana() >= cardToPlay.getIntCost()) {
				setNumStrMana(getNumStrMana()- cardToPlay.getStrCost());
				setNumIntMana(getNumIntMana()- cardToPlay.getIntCost());
				setNumDexMana(getNumDexMana()- cardToPlay.getDexCost());
				setNumChrMana(getNumChrMana()- cardToPlay.getChrCost());
				setNumConMana(getNumConMana()- cardToPlay.getConCost());
			}
						
				if(cardToPlay.getId() == ID.UNIT) {
					if(boardGUI.hasSpots(boardGUI.getOpponentSpots())) {
						opponentHandGUI.removeCardFromHand(cardToPlay);
						boardGUI.addCardToBoard(cardToPlay, boardGUI.getOpponentHandler(), boardGUI.getPlayerHandler(), true);
						board.updateBoard();
					}
				}
				
				else if(cardToPlay instanceof Building) {
					if(boardGUI.hasSpots(boardGUI.getOpponentSpots())) {
						opponentHandGUI.removeCardFromHand(cardToPlay);
						boardGUI.addCardToBoard(cardToPlay, boardGUI.getOpponentHandler(), boardGUI.getPlayerHandler(), true);
						board.updateBoard();
					}
				}
				if(cardToPlay.getId() == ID.SPELL) {
					if(cardToPlay instanceof Spell) {
						opponentHandGUI.removeCardFromHand(cardToPlay);
						getDiscardPile().addCard(cardToPlay);
						Spell spell = (Spell)cardToPlay;
						spell.enterTrigger(boardGUI, boardGUI.getOpponentHandler(), boardGUI.getPlayerHandler());
						boardGUI.setCurrSpellCard(spell);
						boardGUI.setShowSpellCard(true);
						
					}
				}
		}
		else {
				for(int i=0; i  <getHand().getHand().size();i++) {
					if(getHand().getHand().get(i) instanceof Unit) {
						Unit unit = (Unit)getHand().getHand().get(i);
						if(unit.totalCost() <=2 || unit.getHealth() <=2) {
							if(boardGUI.hasSpots(boardGUI.getOpponentSpots())) {
								opponentHandGUI.removeCardFromHand(unit);
								boardGUI.addCardToBoard(unit, boardGUI.getOpponentHandler(), boardGUI.getPlayerHandler(), true);
								board.updateBoard();
								
								break;
							}
						}
					}
				}
		}
		aiState = AiState.PLAY_ON_BOARD;
		
	}
	/*private void playCard() {
		int randomIndex = rand.nextInt(getHand().getHand().size());
		Card c = getHand().getHand().get(randomIndex);
		
		
		
		/*if(getNumChrMana() >= c.getChrCost() && getNumStrMana() >= c.getStrCost()&&getNumConMana() >= c.getConCost()&&getNumDexMana() >= c.getDexCost()&&getNumIntMana() >= c.getIntCost()) {
			setNumStrMana(getNumStrMana()- c.getStrCost());
			setNumIntMana(getNumIntMana()- c.getIntCost());
			setNumDexMana(getNumDexMana()- c.getDexCost());
			setNumChrMana(getNumChrMana()- c.getChrCost());
			setNumConMana(getNumConMana()- c.getConCost());
		}
		else {
			return;
		}*/
		
		/*if(c.getId() == ID.UNIT || c.getId() == ID.BUILDING) {
			if(boardGUI.hasSpots(boardGUI.getOpponentSpots())) {
				//getHand().sortAiHand(this, c);
				//boardGUI.addCardToBoard(c, boardGUI.getOpponentSpots(), boardGUI.getBoard().getAICards(),boardGUI.getBoard().getPlayerCards(),true);
				opponentHandGUI.removeCardFromHand(c);
				boardGUI.addCardToBoard(c, boardGUI.getOpponentHandler(), boardGUI.getPlayerHandler(), true);
				//getHand().removeCard(c);
				boardGUI.getBoard().updateBoard();
			}
		}
		if(c.getId() == ID.SPELL) {
			Spell spell = (Spell)c;
			spell.enterTrigger(boardGUI, boardGUI.getOpponentHandler(), boardGUI.getPlayerHandler());
		}
		aiState = AiState.PLAY_ON_BOARD;*/
	//}*/
	
	/*all these methods are not used but for things we didnt have to do.*/
	
	
	private void drawCardAction() {
		if(aiState == AiState.DRAW_CARD) {
			count++;
			if(count == currDelay/100) {
				opponentHandGUI.drawCard();
				count = 0;
				timer.stop();
				aiState = AiState.PLAY_CARD;
			}
		}
	}
	private void playCardAction() {
		if(aiState == AiState.PLAY_CARD) {
			count++;
			if(count == currDelay/100) {
				playCard();
				timer.stop();
				count = 0;
			}
		}
	}
	private void pickManaAction() {
		if(aiState == AiState.PICK_MANA) {
			count++;
			if(count == (currDelay/100)/2) {drawWhiteBorder = true;}
			if(count == currDelay/100) {
				aiState = AiState.DRAW_CARD;
				timer.stop();
				count = 0;
				drawWhiteBorder = false;
				
				ManaCard manaCard = (ManaCard)manaCardChoice;
				if(manaCard.getType() == ManaType.STR) {setNumStrMana(getNumStrMana()+1);}
				else if(manaCard.getType() == ManaType.INT) {setNumIntMana(getNumIntMana()+1);}
				else if(manaCard.getType() == ManaType.DEX) {setNumDexMana(getNumDexMana()+1);}
				else if(manaCard.getType() == ManaType.CHR) {setNumChrMana(getNumChrMana()+1);}
				else if(manaCard.getType() == ManaType.CON) {setNumConMana(getNumConMana()+1);}
				manaCardChoice = null;
			}
		}
	}
	public void render(Graphics2D g) {
		drawPickManaPhase(g);
	}
	/*draws the mana from the mana deck*/
	private void drawPickManaPhase(Graphics2D g) {
		if(aiState == AiState.PICK_MANA) {
			for(int i =0;i < manaCardChoices.length;i++) {
				manaCardChoices[i].render(g);
				if(manaCardChoice!=null) {
					if(drawWhiteBorder) {
						if(manaCardChoice.equals(manaCardChoices[i])){
							g.setStroke(new BasicStroke(12));
							g.setColor(Color.WHITE);
							g.draw(manaCardChoice.getBounds());
						}
					}
				}
			}
		}
	}
	
	public AiState getAiState() {return aiState;}
	public void setAiState(AiState aiState) {this.aiState = aiState;}
	public OpponentHandGUI getOpponentHandGUI() {return opponentHandGUI;}
	public void setOpponentHandGUI(OpponentHandGUI opponentHandGUI) {this.opponentHandGUI = opponentHandGUI;}
	public Board getBoard() {return board;}
	public void setBoard(Board board) {this.board = board;}
	public BoardGUI getBoardGUI() {return boardGUI;}
	public void setBoardGUI(BoardGUI boardGUI) {this.boardGUI = boardGUI;}
	public SwitchTurnGUI getSwitchTurnGUI() {return switchTurnGUI;}
	public void setSwitchTurnGUI(SwitchTurnGUI switchTurnGUI) {this.switchTurnGUI = switchTurnGUI;}
	
	/*public void chumbaDog() {
		ArrayList<String> list = fileLoader.read();
		ArrayList<Integer> listOfNums = new ArrayList<Integer>();
		for(int i =0; i  <list.size();i++) {
			String str = list.get(i);
			for(int k =0; k < str.length();k++) {
				char c = str.charAt(k);
				if(c == ',') {
					String numberStr = str.charAt(k+1);
					
				}
			}
		}
	}*/
	
	
}
