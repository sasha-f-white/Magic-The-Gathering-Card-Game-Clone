/*
 Brendan Aucoin, Aidan Dowdall, Sasha White
 2018/04/25
 this is the main calss that holds the game loop and all the logic for how the game is going to be played.
 */
package game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JPanel;

import ai.Ai;
import board.Board;
import cards.ApprenticeSquire;
import cards.CaptainGuard;
import cards.Card;
import cards.Executioner;
import cards.FallenKing;
import cards.GuardPatrol;
import cards.ManaCard;
import cards.MysticalHermit;
import cards.RabiesRiddenWolf;
import cards.RatInfestation;
import cards.SilverMountain;
import cards.StillImSad;
import cards.StrayDog;
import cards.TheDing;
import cards.TheZapp;
import deck.Deck;
import file_loader.FileLoader;
import gui.Display;
import id.ManaType;
import menu.DeckBuilderGUI;
import menu.EndGameMenu;
import menu.MainMenu;
import menu.MusicMenu;
import menu.PickDeckMenu;
import mouse.MousePane;
import music.Music;
import phase.Phase;
import player.Player;
import playing_panes.BoardGUI;
import playing_panes.CardDisplayGUI;
import playing_panes.DiscardGUI;
import playing_panes.HandGUI;
import playing_panes.ManaGUI;
import playing_panes.OpponentHandGUI;
import playing_panes.OpponentManaGUI;
import playing_panes.PickManaGUI;
import playing_panes.SelectCardGUI;
import playing_panes.StartScreen;
import playing_panes.SwitchPhaseButtons;
import playing_panes.SwitchTurnGUI;
import playing_panes.TurnGUI;
import popups.HandPopupBox;
import sprites.Texture;
import state.AiState;
import state.State;

public class Game extends MousePane implements Runnable,KeyListener{
	/*all the fields for the game*/
	private Canvas canvas;
	private boolean running;
	private Thread thread;
	public static final String PLAYERS_TURN = "Player";
	public static final String OPPONENTS_TURN = "Opponent";
	public static final String ENTER_TRIGGER = "Enter Trigger";
	public static final String DEATH_TRIGGER = "Death Trigger";
	public static final String DECK_PATH = System.getProperty("user.dir")+ System.getProperty("file.separator")+"texts"+System.getProperty("file.separator") + "decks" +System.getProperty("file.separator");
	public static final String EFFECT_PATH = System.getProperty("user.dir")+ System.getProperty("file.separator")+"texts"+System.getProperty("file.separator") + "effects" +System.getProperty("file.separator");
	public static final String MUSIC_PATH = System.getProperty("user.dir")+ System.getProperty("file.separator")+"texts"+System.getProperty("file.separator") + "music" +System.getProperty("file.separator");
	//all the special gui's
	private HandGUI handGUI;
	private BoardGUI boardGUI;
	private TurnGUI turnGUI;
	private ManaGUI manaGUI;
	private OpponentHandGUI opponentHandGUI;
	private OpponentManaGUI opponentManaGUI;
	private CardDisplayGUI cardDisplayGUI;
	private PickManaGUI pickManaGUI;
	private DiscardGUI discardGUI;
	private StartScreen startScreen;
	private SwitchTurnGUI switchTurnGUI;
	private SwitchPhaseButtons switchPhaseButtonsGUI;
	private SelectCardGUI selectCardGUI;
	private MainMenu mainMenu;
	private DeckBuilderGUI deckBuilderGUI;
	private PickDeckMenu pickDeckMenu;
	private EndGameMenu endGameMenu;
	private MusicMenu musicMenu;
	@SuppressWarnings("unused")
	private Texture texture;
	private JPanel mainPanel;
	/*the logic behind game*/
	private Player player;
	private Ai opponent;
	private Deck cardDeck;
	private Deck manaDeck;
	private Music music;
	public static Phase phase;
	public static State state;
	private Board board;
	private FileLoader fileLoader;
	private HandPopupBox popupBox;
	public static boolean firstTurn;
	public static boolean goToDeckBuilder;
	public static boolean goToMusicMenu;
	public static boolean goToPickDeckMenu;
	/*constructor*/
	public Game() {
		super();
		logicTest();//testing by adding all the test cards to the player and ai.
		fileLoader = new FileLoader();
		/*initializing everything*/
		mainPanel = new JPanel();
		mainPanel.addMouseListener(this);
		mainPanel.addMouseMotionListener(this);
		mainPanel.addMouseWheelListener(this);
		mainPanel.setMaximumSize(new Dimension(Display.WIDTH,Display.HEIGHT));
		mainPanel.setMinimumSize(new Dimension(Display.WIDTH,Display.HEIGHT));
		mainPanel.setPreferredSize(new Dimension(Display.WIDTH,Display.HEIGHT));
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(Display.WIDTH,Display.HEIGHT));
		canvas.setMaximumSize(new Dimension(Display.WIDTH,Display.HEIGHT));
		canvas.setMinimumSize(new Dimension(Display.WIDTH,Display.HEIGHT));
		mainPanel.add(canvas);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.setFocusable(true);
		canvas.addKeyListener(this);
		music = new Music();
		goToDeckBuilder = false;
		goToMusicMenu = false;
		goToPickDeckMenu = false;
		FileLoader tempFileLoader = new FileLoader();
		tempFileLoader.setFileName(Game.MUSIC_PATH);
		if(!tempFileLoader.exists()) {
			tempFileLoader.makeDirectory(tempFileLoader.getFileName());
		}
	}
	/*temp where you add cards to the players deck and ai's deck*/
	public void logicTest() {
		phase = Phase.MANA_DRAW;
		board = new Board();
		player = new Player("Brendan",25);
		opponent = new Ai("AI",25,this);
		texture = new Texture();
		/*the special panes*/
		musicMenu = new MusicMenu(this);
		mainMenu = new MainMenu(musicMenu);
		cardDisplayGUI = new CardDisplayGUI();
		endGameMenu = new EndGameMenu(this);
		boardGUI = new BoardGUI(board,player,opponent,cardDisplayGUI,handGUI,opponentHandGUI,endGameMenu);
		popupBox = new HandPopupBox(player,0,0,Display.WIDTH/15,Display.HEIGHT/15,boardGUI,board,opponent);
		pickDeckMenu = new PickDeckMenu(this);
		turnGUI = new TurnGUI();
		
		manaGUI = new ManaGUI(player);
		handGUI = new HandGUI(player,manaGUI,popupBox,cardDisplayGUI);
		popupBox.setHandGUI(handGUI);
		opponentManaGUI = new OpponentManaGUI(opponent);
		opponentHandGUI = new OpponentHandGUI(opponentManaGUI,opponent);
		
		boardGUI.setHandGUI(handGUI);
		boardGUI.setOpponentHandGUI(opponentHandGUI);
		boardGUI.getPlayerHandler().setHandGUI(handGUI);
		boardGUI.getOpponentHandler().setHandGUI(opponentHandGUI);
		
		discardGUI = new DiscardGUI(player,boardGUI);
		pickManaGUI = new PickManaGUI(this,player);
		startScreen = new StartScreen(this);
		switchTurnGUI = new SwitchTurnGUI(PLAYERS_TURN);
		switchPhaseButtonsGUI = new SwitchPhaseButtons(this,boardGUI,board);
		selectCardGUI = new SelectCardGUI(boardGUI,cardDisplayGUI);
		deckBuilderGUI = new DeckBuilderGUI(cardDisplayGUI,this);
		opponent.setOpponentHandGUI(opponentHandGUI);
		opponent.setBoard(board);
		opponent.setBoardGUI(boardGUI);
		opponent.setSwitchTurnGUI(switchTurnGUI);
		
		/*your temp deck for cards*/
		cardDeck = new Deck();
		for(int i =0; i < 20; i++) {
			//cardDeck.addCard(new StrayDog(0,0,0,0,player,selectCardGUI));
			//cardDeck.addCard(new CastleBarracks(0,0,0,0,player));
			//c/ardDeck.addCard(new SilverMountain(0,0,0,0,player));
		//c/ardDeck.addCard(new LightInTheBlack(0,0,0,0,player));
			//cardDeck.addCard(new StillImSad(0,0,0,0,player));
			//cardDeck.addCard(new RainbowEyes(0,0,0,0,player,selectCardGUI));
			//cardDeck.addCard(new ProtestersOil(0,0,0,0,player,selectCardGUI));
			//cardDeck.addCard(new GuardPatrol(0,0,0,0,player));
			//cardDeck.addCard(new MysticalHermit(0,0,0,0,player,selectCardGUI));
			//cardDeck.addBottomCard(new ExpertTracker(0,0,0,0,player));
			//cardDeck.addCard(new FalseProphet(0,0,0,0,player));
			//cardDeck.addCard(new TheMuscle(0,0,0,0,player));
		}
		for(int i = 0; i <15; i++) {
			//cardDeck.addCard(new FallenKing(0,0,0,0,player));
			//cardDeck.addCard(new Executioner(0,0,0,0,player,handGUI,opponentHandGUI));
			//cardDeck.addCard(new HiddenPlot(0,0,0,0,player,selectCardGUI));
		}
		//player.setCardDeck(cardDeck);
		//player.shuffle(cardDeck);
		
		
		/*temp cards for ai*/
		Deck opponentCardDeck = new Deck();
		for(int i =0; i < 3; i++) {
			opponentCardDeck.addCard(new ApprenticeSquire(0,0,0,0,player,selectCardGUI));
			opponentCardDeck.addCard(new FallenKing(0,0,0,0,player,selectCardGUI));
			opponentCardDeck.addCard(new Executioner(0,0,0,0,player,null,null,selectCardGUI));
			opponentCardDeck.addCard(new RabiesRiddenWolf(0,0,0,0,player,selectCardGUI));
			opponentCardDeck.addCard(new StrayDog(0,0,0,0,player,selectCardGUI));
			opponentCardDeck.addCard(new MysticalHermit(0,0,0,0,player,selectCardGUI));
			opponentCardDeck.addCard(new RatInfestation(0,0,0,0,player,selectCardGUI));
			opponentCardDeck.addCard(new StrayDog(0,0,0,0,player,selectCardGUI));
			opponentCardDeck.addCard(new StillImSad(0,0,0,0,player,selectCardGUI));
			opponentCardDeck.addCard(new TheZapp(0,0,0,0,player,selectCardGUI,this));
			opponentCardDeck.addCard(new TheDing(0,0,0,0,player,selectCardGUI));
			opponentCardDeck.addCard(new CaptainGuard(0,0,0,0,player,selectCardGUI));
			opponentCardDeck.addCard(new GuardPatrol(0,0,0,0,player,selectCardGUI));
		}
		
		opponent.setCardDeck(opponentCardDeck);
		opponent.shuffle(opponentCardDeck);
		
		/*your temp mana deck.*/
		manaDeck = new Deck();
		for(int i =0;i < 190; i ++) {
			manaDeck.addCard(new ManaCard(0,0,0,0,player,ManaType.STR,selectCardGUI));
		}
		for(int i =0;i < 17; i ++) {
			manaDeck.addCard(new ManaCard(0,0,0,0,player,ManaType.INT,selectCardGUI));
		}
		for(int i =0;i < 14; i ++) {
			manaDeck.addCard(new ManaCard(0,0,0,0,player,ManaType.CON,selectCardGUI));
		}
		for(int i =0;i < 12; i ++) {
			manaDeck.addCard(new ManaCard(0,0,0,0,player,ManaType.CHR,selectCardGUI));
		}
		player.shuffle(manaDeck);
		player.setManaDeck(manaDeck);
		
		/*temp for the ai*/
		opponent.setManaDeck(manaDeck);
		state = State.MAIN_MENU;
		firstTurn =true;
	}
	/*updating the logic of the game. not that much becuase its a card game but u know*/
	private void update() {
		if(goToDeckBuilder) {Game.state = State.DECK_BUILDER;}
		if(goToMusicMenu) {Game.state = State.MUSIC;}
		if(goToPickDeckMenu) {Game.state = State.PICK_DECK;}
		if(state == State.PICK_DECK) {pickDeckMenu.update();}
		if(phase == Phase.OPPONENTS_TURN) {opponent.update();}
		if(state == State.END_GAME) {endGameMenu.update();}
		if(state == State.PLAYING) {
			popupBox.update();
			handGUI.update();
			boardGUI.update();
			cardDisplayGUI.update();
			manaGUI.update();
			turnGUI.update();
			opponentHandGUI.update();
			opponentManaGUI.update();
			discardGUI.update();
		}
		if(phase == Phase.PICK_MANA) {pickManaGUI.update();}
		if(phase == Phase.SELECT_CARD) {selectCardGUI.update();}
		if(state == State.MAIN_MENU) {mainMenu.update();}
		if(state == State.DECK_BUILDER) {deckBuilderGUI.update();}
		if(state == State.MUSIC) {musicMenu.update();}
		outOfCards(player);
		outOfCards(opponent);
	}
	/*checks if the deck is out of cards*/
	public void outOfCards(Player player){
		if(player.getCardDeck().getDeck().size() <=5) {
			for(Card c : player.getDiscardPile().getDeck()) {
				player.getCardDeck().getDeck().addLast(c);
			}
			
		}
	}
	/*reads from a deck text file and converts all those strings into cards that you can add to your deck.*/
	public void makeCardDeck(String deckPath) {
		fileLoader.setFileName(deckPath);
		ArrayList<String>namesFromFile = fileLoader.read();
		
		for(int i =0; i <namesFromFile.size();i++) {
			for(int k =0; k < DeckBuilderGUI.cardList.size();k++) {
				if(namesFromFile.get(i).equals(DeckBuilderGUI.cardList.get(k).getName())) {
					Card c =DeckBuilderGUI.cardList.get(k).makeCopy();
					c.setPlayer(player);
					c.setSelectCardGUI(selectCardGUI);
					cardDeck.addCard(c);
				}
			}
		}
		cardDeck.shuffle();
		player.setCardDeck(cardDeck);
		for(int i =0; i < player.getCardDeck().getDeck().size();i++) {
		}
		Game.goToPickDeckMenu = false;
		Game.state = State.START;
	}
	/*drawing everything to the screen.*/
	private void render() {
		 BufferStrategy bs = canvas.getBufferStrategy();
		   if(bs == null){canvas.createBufferStrategy(3);return;}
		   Graphics g = bs.getDrawGraphics();
		   Graphics2D g2d = (Graphics2D)g;
		   g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		   canvas.paint(g);
		   g.setColor(Color.white);
		   g.fillRect(0,0,Display.WIDTH,Display.HEIGHT);
		   
		   /*begining of rendering code*/
		   if(state == State.PLAYING || state == State.START) {
		   turnGUI.render(g2d);
		   boardGUI.render(g2d);
		   switchPhaseButtonsGUI.render(g2d);
		   cardDisplayGUI.render(g2d);
		   discardGUI.render(g2d);
		   handGUI.render(g2d);
		   //manaGUI.render(g2d);
		   
		   opponentHandGUI.render(g2d);
		   opponentManaGUI.render(g2d);
		   }
		   if(state == State.START) {
			   startScreen.render(g2d);
		   }
		   if(state == State.MAIN_MENU) {
			   mainMenu.render(g2d);
		   }
		   if(state == State.DECK_BUILDER) {
			   deckBuilderGUI.render(g2d);
		   }
		   if(state == State.END_GAME) {endGameMenu.render(g2d);}
		   if(state == State.PICK_DECK) {pickDeckMenu.render(g2d);}
		   if(state == State.MUSIC) {musicMenu.render(g2d);}
		   if(popupBox.isShown()) {popupBox.render(g2d);}
		   /*drawing the specific actions like picking mana*/
		   if(phase == Phase.PICK_MANA) {
			   pickManaGUI.render(g2d);
		   }
		   if(phase == Phase.OPPONENTS_TURN) {
			   opponent.render(g2d);
		   }
		   if(phase == Phase.SELECT_CARD) {selectCardGUI.render(g2d);}
		   if(phase == Phase.SWITCH_TURN) {switchTurnGUI.render(g2d);}
		   /*end of rendering code*/
		   g.dispose();
		   bs.show();
	}
	/*switches the turn to the ai*/
	public void switchToOpponentsTurn() {
		Game.phase = Phase.SWITCH_TURN;
		switchTurnGUI.setWhosTurn(opponent.getName());
		switchTurnGUI.setMoving(true);//start the animation
		switchTurnGUI.setNextPhase(Phase.OPPONENTS_TURN);//set the phase you want it to go to after the animation
		switchTurnGUI.setFirstTime(true);//so you initialize the timer in the switchturngui class.
		
		//board.resetCardUses(board.getPlayerCards());
		/*making all the players cards un-used so you can use them again*/
		for(int i =0; i < board.getPlayerCards().size();i++) {
			Card c  =board.getPlayerCards().get(i);
			c.setUsed(false);
		}
		board.resetCardUses(board.getAICards());
		boardGUI.resetTurn();
		opponent.setAiState(AiState.DRAW_MANA);//starting the ai's logic
	}
	/*switch to the players turn again*/
	public void switchToPlayersTurn() {
		Game.phase = Phase.SWITCH_TURN;
		switchTurnGUI.setWhosTurn(player.getName());
		switchTurnGUI.setMoving(true);
		switchTurnGUI.setFirstTime(true);
		switchTurnGUI.setNextPhase(Phase.MANA_DRAW);
		Game.phase = Phase.SWITCH_TURN;
		board.resetCardUses(board.getAICards());
		board.resetCardUses(board.getPlayerCards());
		boardGUI.resetTurn();
		opponent.setAiState(AiState.PLAYERS_TURN);
		TurnGUI.turn++;
		if(firstTurn) {firstTurn = false;}
		/*making the mana keep after your turn*/
		player.setNumStrMana(player.getMaxStrMana());
		player.setNumIntMana(player.getMaxIntMana());
		player.setNumDexMana(player.getMaxDexMana());
		player.setNumConMana(player.getMaxConMana());
		player.setNumChrMana(player.getMaxChrMana());
	}
	/*handling key presses*/
	public void keyPressed(KeyEvent e) {
		if(state == State.DECK_BUILDER) {
			deckBuilderGUI.keyPressed(e);
		}
	}
	
	/*all the mouse stuff*/
	@Override
	public void mousePressed(MouseEvent e) {
		if(state == State.MAIN_MENU) {mainMenu.mousePressed(e);}
		if(state == State.DECK_BUILDER) {deckBuilderGUI.mousePressed(e);}
		if(state == State.MUSIC) {musicMenu.mousePressed(e);}
		if(state == State.START) {startScreen.mousePressed(e);}
		if(state == State.PICK_DECK) {pickDeckMenu.mousePressed(e);}
		if(state == State.END_GAME) {endGameMenu.mousePressed(e);}
		if(state != State.PLAYING) {return;}
		if(popupBox.isShown()) {if(!mouseOver(popupBox.getBounds())){popupBox.setShown(false);}}
		if(mouseOver(handGUI.getBounds())) {
			handGUI.mousePressed(e);
		}
		
		else if(mouseOver(boardGUI.getBounds())) {
			boardGUI.mousePressed(e);
		}
		
		else if(mouseOver(turnGUI.getBounds())) {
			turnGUI.mousePressed(e);
		}
		
		else if(mouseOver(manaGUI.getBounds())) {
			manaGUI.mousePressed(e);
		}
		
		else if(mouseOver(opponentHandGUI.getBounds())) {
			opponentHandGUI.mousePressed(e);
		}
		
		else if(mouseOver(opponentManaGUI.getBounds())) {
			opponentManaGUI.mousePressed(e);
		}
		
		else if(mouseOver(cardDisplayGUI.getBounds())) {
			cardDisplayGUI.mousePressed(e);
		}
		
		else if(mouseOver(discardGUI.getBounds())) {
			discardGUI.mousePressed(e);
		}
		if(phase == Phase.PICK_MANA) {
			if(mouseOver(pickManaGUI.getBounds())) {
			
				pickManaGUI.mousePressed(e);}
		}
		if(phase == Phase.PLAY_CARD || phase == Phase.PLAY_ON_BOARD) {
			switchPhaseButtonsGUI.mousePressed(e);
		}
		if(phase == Phase.SELECT_CARD) {selectCardGUI.mousePressed(e);}
		if(popupBox.isShown()) {
			if(mouseOver(popupBox.getBounds())) {
				popupBox.mousePressed(e);
			}
		}
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		if(state == State.MAIN_MENU) {mainMenu.mouseMoved(e);}
		if(state == State.DECK_BUILDER) {deckBuilderGUI.mouseMoved(e);}
		if(state == State.MUSIC) {musicMenu.mouseMoved(e);}
		if(state == State.START) {startScreen.mouseMoved(e);}
		if(state == State.PICK_DECK) {pickDeckMenu.mouseMoved(e);}
		if(state == State.END_GAME) {endGameMenu.mouseMoved(e);}
		if(state != State.PLAYING) {return;}
		if(mouseOver(handGUI.getBounds())) {
			handGUI.mouseMoved(e);
		}
		
		else if(mouseOver(boardGUI.getBounds())) {
			boardGUI.mouseMoved(e);
		}
		
		else if(mouseOver(turnGUI.getBounds())) {
			turnGUI.mouseMoved(e);
		}
		else if(mouseOver(manaGUI.getBounds())) {
			manaGUI.mouseMoved(e);
		}
		else if(mouseOver(opponentHandGUI.getBounds())) {
			opponentHandGUI.mouseMoved(e);
		}
		else if(mouseOver(opponentManaGUI.getBounds())) {
			opponentManaGUI.mouseMoved(e);
		}
		else if(mouseOver(cardDisplayGUI.getBounds())) {
			cardDisplayGUI.mouseMoved(e);
		}
		else if(mouseOver(discardGUI.getBounds())) {
			discardGUI.mouseMoved(e);
		}
		if(phase == Phase.PICK_MANA) {
			if(mouseOver(pickManaGUI.getBounds())) {
				pickManaGUI.mouseMoved(e);}
		}
		if(phase == Phase.PLAY_CARD || phase == Phase.PLAY_ON_BOARD) {
			switchPhaseButtonsGUI.mouseMoved(e);
		}
		if(phase == Phase.SELECT_CARD) {selectCardGUI.mouseMoved(e);}
		
		if(popupBox.isShown()) {
			if(mouseOver(popupBox.getBounds())) {
				popupBox.mouseMoved(e);
			}
		}
		
	}
	/*moving mouse wheel*/
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(state == State.DECK_BUILDER) {
			if(mouseOver(deckBuilderGUI.getBounds())) {
				deckBuilderGUI.mouseWheelMoved(e);
			}
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	/*drawing an initial cards for your hand.*/
	public void drawInitialCards() {
		handGUI.drawInitialCards(7);
		opponentHandGUI.drawInitialCards(7);
	}
	/*plays a specified clip.*/
	public void playClip(String path) {
		music.playSound(path);
	}
	/*all the thread stuff*/
	public void run(){
		int fps = 60;//the frames per second./ ticks per second
		double timePerTick = 1000000000/fps;//becuase 1 billion nano seconds in 1 second
		double delta = 0;//how much time you have until you have to call the tick and render methods again.
		long now;//the time right now.
		long lastTime = System.nanoTime();//returns how many nano seconds our computer is running at.
		long timer = 0;
		int ticks =0;
		while(running)//game loop
		{
			now = System.nanoTime();//now is current time.
			delta += (now - lastTime)/timePerTick;
			timer += now -lastTime;//how much time has passed since the last call of tick and render
			lastTime = now;
			if(delta >= 1)//this is to achieve 60 fps.
			{
				update();
				render();
				ticks++;
				delta--;
			}	
			if(timer >= 1000000000)
			{
				//System.out.println("Updates: " + ticks);
				ticks = 0;
				timer = 0;
			}
			try{Thread.sleep(8);}catch(InterruptedException ie){System.out.println("Game loop sleeping interuppted");}//could be 8
		}
		stop();
	}
	/*getters and setters.*/
	
	public Music getMusic() {
		return music;
	}
	public void setMusic(Music music) {
		this.music = music;
	}
	public void start(){
		   if(running){return;}
		   running = true;
		   if(thread == null){thread = new Thread(this);}
		   thread.start();
		   
		 }
		 
		 public void stop(){
		   if(!running){return;}
		   running  = false;
		   try{thread.join();}catch(Exception e){e.printStackTrace();}
		 }
		 
		 public JPanel getMainPanel() {return mainPanel;}
		 public Canvas getCanvas() {return canvas;}
		 public SelectCardGUI getSelectCardGUI() {return selectCardGUI;}
}