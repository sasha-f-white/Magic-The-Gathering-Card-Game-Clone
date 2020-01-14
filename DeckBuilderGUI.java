/*
 Brendan Aucoin
 2018/05/14
 all the menus are the same as all the gui just with differnet logic in them for pressing on certain things.
 */
package menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import camera.Camera;
import cards.AbusivePatrolman;
import cards.AngryFarmer;
import cards.ApprenticeSquire;
import cards.BattleMage;
import cards.BlackMarketBodyguard;
import cards.BlessedStrike;
import cards.CaptainGuard;
import cards.Card;
import cards.CastleBarracks;
import cards.CastleBuilder;
import cards.DanceOfDeath;
import cards.DeepDarkPlace;
import cards.Executioner;
import cards.ExpertTracker;
import cards.FallenKing;
import cards.FalseProphet;
import cards.GuardPatrol;
import cards.HiddenPlot;
import cards.HighTreason;
import cards.KillTheKing;
import cards.LadyOfTheLake;
import cards.LadyOfTheNight;
import cards.LightInTheBlack;
import cards.LostWraith;
import cards.MysticalHermit;
import cards.OldBeggar;
import cards.PhantomOfTheOpera;
import cards.ProtestersOil;
import cards.RabiesRiddenWolf;
import cards.RainbowEyes;
import cards.RatInfestation;
import cards.RejuvinatingElixir;
import cards.RoyalInformant;
import cards.RoyalKnight;
import cards.SilverMountain;
import cards.StillImSad;
import cards.StrayDog;
import cards.TempleOfTheKing;
import cards.TheDing;
import cards.TheMuscle;
import cards.TheWhoore;
import cards.TheZapp;
import cards.TowerGuard;
import cards.Unit;
import file_loader.FileLoader;
import game.Game;
import gui.Display;
import playing_panes.CardDisplayGUI;
import playing_panes.InteractionPane;
import state.State;
import text_field.TextField;

public class DeckBuilderGUI extends InteractionPane implements ActionListener{
	private CardDisplayGUI cardDisplayGUI;
	private Rectangle displayBounds,savedCardsBounds;
	public static  List<Card> cardList;
	private List<Card>savedList;
	private List<Card>cardListToSearch;
	private boolean pickAmount;
	private Rectangle oneBounds,twoBounds,threeBounds,noneBounds;
	private Rectangle returnBounds,saveBounds,sortBounds;
	//private Rectangle textFieldBounds;
	private Card currCard = null;
	private final int MAX_AMOUNT = 35;
	private ArrayList<Rectangle> cardNameBounds;
	private Camera cam;
	private float camIndex;
	private FileLoader fileLoader;
	private TextField topSearchBar;
	private TextField nameTextField;
	private Game game;
	//private boolean canType,showCursor;
	//private Timer timer;
	//private String search;
	public DeckBuilderGUI(CardDisplayGUI cardDisplayGUI,Game game) {
		super(new Rectangle(0,0,Display.WIDTH,Display.HEIGHT));
		this.game = game;
		this.cardDisplayGUI = cardDisplayGUI;
		fileLoader = new FileLoader();
		displayBounds = new Rectangle(cardDisplayGUI.getBounds().x + cardDisplayGUI.getBounds().width,
				0,(int) (getBounds().width/1.1 - (cardDisplayGUI.getBounds().x + cardDisplayGUI.getBounds().width) - getBounds().width/15),
				getBounds().height);
		oneBounds = new Rectangle((int) (displayBounds.x + displayBounds.width/3 -displayBounds.width/3.5),displayBounds.y + displayBounds.height -displayBounds.height/6,displayBounds.width/8,displayBounds.height/15);
		twoBounds = new Rectangle((int)(oneBounds.x + oneBounds.width*1.1),oneBounds.y,oneBounds.width,oneBounds.height);
		threeBounds = new Rectangle((int)(twoBounds.x +twoBounds.width*1.1),twoBounds.y,twoBounds.width,twoBounds.height);
		noneBounds = new Rectangle((int) (threeBounds.x + threeBounds.width*1.1),threeBounds.y,threeBounds.width,threeBounds.height);
		savedCardsBounds = new Rectangle(
				displayBounds.x + displayBounds.width,
				0,
				(getBounds().x + getBounds().width) - (displayBounds.x + displayBounds.width),
				getBounds().height
				);
		saveBounds = new Rectangle((int) (displayBounds.x + displayBounds.width-displayBounds.width/2.8),
				displayBounds.y + displayBounds.height-displayBounds.height/6,
				displayBounds.width/6,
				displayBounds.height/10);
		sortBounds = new Rectangle((int) (saveBounds.x + saveBounds.width*1.11),
				saveBounds.y,saveBounds.width,saveBounds.height);
		returnBounds = new Rectangle(0,0,displayBounds.x,cardDisplayGUI.getBounds().y);
		
		//textFieldBounds = new Rectangle(displayBounds.x,displayBounds.y,displayBounds.width,90/4);
		//canType= false;
		//showCursor = false;
		cardList = new ArrayList<Card>();
		savedList = new ArrayList<Card>();
		cardListToSearch = new ArrayList<Card>();
		cardNameBounds=  new ArrayList<Rectangle>();
		//timer = new Timer(400,this);
		topSearchBar = new TextField(new Rectangle(displayBounds.x,displayBounds.y,displayBounds.width-2,90/4));
		nameTextField = new TextField(new Rectangle(displayBounds.x,(int) (displayBounds.y + displayBounds.height- 90/1.35),saveBounds.x/2-2,90/4));
		/*nameTextField = new TextField(new Rectangle(displayBounds.x,
				(int) (displayBounds.y + displayBounds.height- 90/1.35),
				saveBounds.x/2-2,90/4
				));*/
		
		/*
		 adding all the cards
		 */
		cardList.add(new AbusivePatrolman(0,0,0,0,null,game.getSelectCardGUI()));
		cardList.add(new AngryFarmer(0,0,0,0,null,null));
		cardList.add(new ApprenticeSquire(0,0,0,0,null,null));
		cardList.add(new CaptainGuard(0,0,0,0,null,null));
		cardList.add(new CastleBarracks(0,0,0,0,null,null));
		cardList.add(new CastleBuilder(0,0,0,0,null,null));
		cardList.add(new Executioner(0,0,0,0,null,null,null,null));
		cardList.add(new ExpertTracker(0,0,0,0,null,null));
		cardList.add(new FallenKing(0,0,0,0,null,null));
		cardList.add(new FalseProphet(0,0,0,0,null,null));
		cardList.add(new GuardPatrol(0,0,0,0,null,null));
		cardList.add(new HiddenPlot(0,0,0,0,null,game.getSelectCardGUI()));
		cardList.add(new LadyOfTheNight(0,0,0,0,null,game.getSelectCardGUI()));
		cardList.add(new LostWraith(0,0,0,0,null,null));
		cardList.add(new OldBeggar(0,0,0,0,null,null));
		cardList.add(new ProtestersOil(0,0,0,0,null,game.getSelectCardGUI()));
		cardList.add(new RabiesRiddenWolf(0,0,0,0,null,null));
		cardList.add(new RoyalInformant(0,0,0,0,null,game.getSelectCardGUI()));
		cardList.add(new RoyalKnight(0,0,0,0,null,null));
		cardList.add(new RatInfestation(0,0,0,0,null,null));
		cardList.add(new StrayDog(0,0,0,0,null,game.getSelectCardGUI()));
		cardList.add(new TheMuscle(0,0,0,0,null,null));
		cardList.add(new TowerGuard(0,0,0,0,null,game.getSelectCardGUI()));
		cardList.add(new MysticalHermit(0,0,0,0,null,game.getSelectCardGUI()));
		cardList.add(new HighTreason(0,0,0,0,null,game.getSelectCardGUI()));
		cardList.add(new SilverMountain(0,0,0,0,null,null));
		cardList.add(new LadyOfTheLake(0,0,0,0,null,game.getSelectCardGUI()));
		cardList.add(new LightInTheBlack(0,0,0,0,null,null));
		cardList.add(new StillImSad(0,0,0,0,null,null));
		cardList.add(new KillTheKing(0,0,0,0,null,null));
		cardList.add(new RainbowEyes(0,0,0,0,null,game.getSelectCardGUI()));
		cardList.add(new BlackMarketBodyguard(0,0,0,0,null,null));
		cardList.add(new TheWhoore(0,0,0,0,null,null));
		cardList.add(new RejuvinatingElixir(0,0,0,0,null,null));
		cardList.add(new BattleMage(0,0,0,0,null,null));
		cardList.add(new DeepDarkPlace(0,0,0,0,null,null,game));
		
		cardList.add(new TheDing(0,0,0,0,null,null));
		cardList.add(new TempleOfTheKing(0,0,0,0,null,game.getSelectCardGUI()));
		cardList.add(new BlessedStrike(0,0,0,0,null,null));
		cardList.add(new DanceOfDeath(0,0,0,0,null,null));
		cardList.add(new PhantomOfTheOpera(0,0,0,0,null,null));
		cardList.add(new TheZapp(0,0,0,0,null,null,game));
		
		pickAmount =false;
		cam = new Camera(0,0);
		camIndex = 0;
		fileLoader.setFileName(Game.DECK_PATH);
		if(!fileLoader.exists()) {
			fileLoader.makeDirectory(fileLoader.getFileName());
		}
	}

	public void update() {
		cam.update(camIndex);
		if(topSearchBar.getSearch().equals("") || topSearchBar.getSearch() == null) {return;}
		if(nameTextField.getSearch().equals("") || nameTextField.getSearch() == null) {return;}
	}
	
	private void renderDisplayPanel(Graphics2D g) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		g.setColor(Color.LIGHT_GRAY);
		int counter = 0;
		//int rowSize = 9;//(int) (((displayBounds.x + displayBounds.width)/((cardList.size()*3.5f)-cardList.size()))/3);
		//int rows = 10;//(int) ((int) cardList.size()/rowSize + 1);
		//int rowSize =(int) (((displayBounds.x + displayBounds.width)/((cardList.size()*3.5f)-cardList.size()))/1.9);
		//int rowSize = (int) (displayBounds.x/(cardList.size()*1.8));
		//int rows = (int) ((int) cardList.size()/rowSize+1);
		int rows = 17;
		int rowSize = 7;
		//int rowSize = 10;
		//int rows =(int) ((int) cardList.size()/rowSize + 1);
		
		for(int i =0;i<rows;i++) {
			for(int j = 0; j<rowSize;j++) {
				
				if (counter < cardList.size()){
					if(!pickAmount) {
					cardList.get(counter).setHeight(90);
					cardList.get(counter).setWidth(60);}
					cardList.get(counter).setX((float) (displayBounds.x + (j*cardList.size()/1.2*rowSize/1.9f)));	        
					cardList.get(counter).setY((float) (displayBounds.y + cardList.get(counter).getHeight()/4 + (i*cardList.size()/1.2*rowSize/2.4f)));
	 	 	        cardList.get(counter).render(g);
	 	 	      	if(cardList.get(counter) instanceof Unit) {
	 	 	        	Unit unit = (Unit)cardList.get(counter);
	 	 	        	g.setColor(Color.BLACK);
	 					g.setFont(new Font("Arial",Font.BOLD,10));
	 					g.drawString(String.valueOf(unit.getAttack()), (int)(unit.getX() + unit.getWidth()/1.71 - g.getFont().getStringBounds(String.valueOf(unit.getAttack()), frc).getWidth()/2.5), (int)(unit.getY() + unit.getHeight() - unit.getHeight()/12.51));
	 					//health
	 					g.drawString(String.valueOf(unit.getHealth()), (int)(unit.getX() + unit.getWidth()/1.17 - g.getFont().getStringBounds(String.valueOf(unit.getHealth()), frc).getWidth()/2.2), (int)(unit.getY() + unit.getHeight() - unit.getHeight()/12.51));
	 	 	        }
	 	 	        counter++;
	 	 	        
				}
				
			}
		}//end of outer for loop
	}
	
	private void renderSearchCardList(Graphics2D g) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		g.setColor(Color.LIGHT_GRAY);
		int counter = 0;
		//int rowSize = 10;//
		//int rowSize = (int) (((displayBounds.x + displayBounds.width)/((cardList.size()*3.5f)-cardList.size()))/3);
		//int rowSize =(int) (((displayBounds.x + displayBounds.width)/((cardList.size()*3.5f)-cardList.size()))/2.05);
		//int rows = (int) ((int) cardListToSearch.size()/rowSize + 1);
		//int rows = (int) ((int) cardListToSearch.size());
		//int rows = 5;//
		int rows = 17;
		int rowSize = 7;
		
		//int rowSize = 10;
		//int rows =(int) ((int) cardList.size()/rowSize + 1);
		try {
		for(int i =0;i<rows;i++) {
			for(int j = 0; j<rowSize;j++) {
				if (counter < cardListToSearch.size()){
					if(!pickAmount) {
						cardListToSearch.get(counter).setHeight(90);
						cardListToSearch.get(counter).setWidth(60);}
						cardListToSearch.get(counter).setX((float) (displayBounds.x + (j*cardList.size()/1.2*rowSize/1.9)));	        
						cardListToSearch.get(counter).setY((float) (displayBounds.y + cardList.get(counter).getHeight()/4 + (i*cardList.size()/1.2*rowSize/2.4f)));
						cardListToSearch.get(counter).render(g);
						if(cardListToSearch.get(counter) instanceof Unit) {
		 	 	        	Unit unit = (Unit)cardListToSearch.get(counter);
		 	 	        	g.setColor(Color.BLACK);
		 					g.setFont(new Font("Arial",Font.BOLD,10));
		 					g.drawString(String.valueOf(unit.getAttack()), (int)(unit.getX() + unit.getWidth()/1.71 - g.getFont().getStringBounds(String.valueOf(unit.getAttack()), frc).getWidth()/2.5), (int)(unit.getY() + unit.getHeight() - unit.getHeight()/12.51));
		 					//health
		 					g.drawString(String.valueOf(unit.getHealth()), (int)(unit.getX() + unit.getWidth()/1.17 - g.getFont().getStringBounds(String.valueOf(unit.getHealth()), frc).getWidth()/2.2), (int)(unit.getY() + unit.getHeight() - unit.getHeight()/12.51));
		 	 	        }
						counter++;
		 	 	        
				}
				
			}
		}//end of outer for loop
		}catch(IndexOutOfBoundsException e) {return;}
	}
	
	private void renderSavedCardsPanel(Graphics2D g) {
		g.setFont(new Font("arial",Font.PLAIN,15));
		g.setColor(Color.BLACK);
		for(int i =0; i < savedList.size();i++) {
			try {
			g.draw(cardNameBounds.get(i));
			}catch(IndexOutOfBoundsException e) {return;}
			g.drawString(savedList.get(i).getName(), cardNameBounds.get(i).x, cardNameBounds.get(i).y +cardNameBounds.get(i).height/2);
		}
	}
	private void drawButtons(Graphics2D g) {
		/*return button*/
		changeColour(g, returnBounds,new Color(192,192,192),new Color(255,255,224));
		g.fill3DRect(returnBounds.x, returnBounds.y, returnBounds.width,returnBounds.height, mouseOver(returnBounds));
		g.setColor(Color.BLACK);
		g.setFont(new Font("arial",Font.PLAIN,40));
		g.drawString("Main Menu", (int) (returnBounds.x + returnBounds.width/2 - g.getFont().getSize()*2.3), returnBounds.y + returnBounds.height/2+ g.getFont().getSize()/4);
		
		/*the save button*/
		changeColour(g, saveBounds,new Color(192,192,192),new Color(255,255,224));
		g.drawRoundRect(saveBounds.x, saveBounds.y, saveBounds.width, saveBounds.height,125,125);
		if(mouseOver(saveBounds)) {
			g.setColor(new Color(192,192,192));
			g.fillRoundRect(saveBounds.x, saveBounds.y, saveBounds.width, saveBounds.height,125,125);
			g.setColor(Color.BLACK);
			g.drawString(savedList.size() + "/" + MAX_AMOUNT,saveBounds.x + saveBounds.width/2 - g.getFont().getSize() , saveBounds.y - g.getFont().getSize()/2);
			
			if(nameTextField.getSearch().length() ==0) {
				g.setColor(Color.RED);
				g.setFont(new Font("Arial",Font.PLAIN,20));
				g.drawString("Name Deck Before Saving", saveBounds.x - saveBounds.width/2, saveBounds.y- saveBounds.height/10);
			}
		}
		g.setColor(Color.BLACK);
		g.drawString("Save", (int) (saveBounds.x + saveBounds.width/2 - g.getFont().getSize()*1.1), saveBounds.y + saveBounds.height/2 + g.getFont().getSize()/4);
	
		/*the sort button*/
		changeColour(g, sortBounds,new Color(192,192,192),new Color(255,255,224));
		g.drawRoundRect(sortBounds.x, sortBounds.y, sortBounds.width, sortBounds.height,125,125);
		if(mouseOver(sortBounds)) {
			g.setColor(new Color(192,192,192));
			g.fillRoundRect(sortBounds.x, sortBounds.y, sortBounds.width, sortBounds.height,125,125);
		}
		g.setColor(Color.BLACK);
		g.drawString("Sort", (int) (sortBounds.x + sortBounds.width/2 - g.getFont().getSize()), sortBounds.y + sortBounds.height/2 + g.getFont().getSize()/4);
	}
	private void drawTextField(Graphics2D g) {
		/*AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(3));
		if(mouseOver(textFieldBounds)) {
			g.setColor(Color.CYAN);
		}
		g.draw(textFieldBounds);
		g.setFont(new Font("arial",Font.PLAIN,15));
		g.setColor(Color.RED);

		
		//g.drawString(search, textFieldBounds.x,(int)(textFieldBounds.y+textFieldBounds.height/1.4));
		if(showCursor) {
			g.setColor(Color.BLACK);
			g.drawLine(textFieldBounds.x+(int)(g.getFont().getStringBounds(search, frc).getWidth()),textFieldBounds.y,textFieldBounds.x+(int)(g.getFont().getStringBounds(search, frc).getWidth()),textFieldBounds.height);
		}*/
		if(mouseOver(topSearchBar.getBounds())){
			topSearchBar.setBackColour(Color.CYAN);
		}
		else {
			topSearchBar.setBackColour(Color.GRAY);
		}
		topSearchBar.render(g);
		
		if(mouseOver(nameTextField.getBounds())){
			
			nameTextField.setBackColour(Color.CYAN);
		}
		else {
			nameTextField.setBackColour(Color.GRAY);
		}
		nameTextField.render(g);
	}
	public void render(Graphics2D g) {
		g.setColor(Color.CYAN.darker().darker());
		g.fillRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.WHITE);
		g.draw(displayBounds);
		if(topSearchBar.getSearch().equals("") || topSearchBar.getSearch()== null) {
			renderDisplayPanel(g);
		}
		else {
			renderSearchCardList(g);
		}
		g.translate(cam.getX(), cam.getY());
		renderSavedCardsPanel(g);
		g.translate(-cam.getX(),-cam.getY());
		cardDisplayGUI.render(g);
		drawButtons(g);
		drawTextField(g);
		if(pickAmount) {
			this.changeColour(g, oneBounds,new Color(192,192,192),new Color(255,255,224));
			g.fillRoundRect(oneBounds.x,oneBounds.y,oneBounds.width,oneBounds.height,50,50);
			this.changeColour(g, twoBounds,new Color(192,192,192),new Color(255,255,224));
			g.fillRoundRect(twoBounds.x,twoBounds.y,twoBounds.width,twoBounds.height,50,50);
			this.changeColour(g, threeBounds,new Color(192,192,192),new Color(255,255,224));
			g.fillRoundRect(threeBounds.x,threeBounds.y,threeBounds.width,threeBounds.height,50,50);
			this.changeColour(g, noneBounds,new Color(192,192,192),new Color(255,255,224));
			g.fillRoundRect(noneBounds.x,noneBounds.y,noneBounds.width,noneBounds.height,50,50);
			/*drawing the words*/
			g.setFont(new Font("arial",Font.PLAIN,50));
			g.setColor(Color.BLACK);
			g.drawString("1", oneBounds.x + oneBounds.width/2 - g.getFont().getSize()/3, oneBounds.y + oneBounds.height/2 + g.getFont().getSize()/3);
			g.drawString("2", twoBounds.x + twoBounds.width/2- g.getFont().getSize()/3, twoBounds.y + twoBounds.height/2+ g.getFont().getSize()/3);
			g.drawString("3", threeBounds.x + threeBounds.width/2- g.getFont().getSize()/3, threeBounds.y + threeBounds.height/2+ g.getFont().getSize()/3);
			g.drawString("None", (int) (noneBounds.x + noneBounds.width/2 - g.getFont().getSize()*1.1), noneBounds.y + noneBounds.height/2 + g.getFont().getSize()/3);
		}
		g.setColor(Color.RED);
		g.setFont(new Font("Arial",Font.PLAIN,30));
		g.drawString("Name Deck", nameTextField.getBounds().x,
				nameTextField.getBounds().y - g.getFont().getSize()/3);
	}
	private void clickOnCards() {
		int oldWidth=0;
		int oldHeight=0;
		/*picking the cards*/
		if(pickAmount ) {return;}
		if(!pickAmount && savedList.size()<MAX_AMOUNT) {
			for(int i =0; i < cardList.size();i++) {
				if(mouseOver(cardList.get(i))) {
					currCard = cardList.get(i);
					oldWidth = currCard.getWidth();
					oldHeight =currCard.getHeight();
					currCard.setWidth((int) (oldWidth*1.5));
					currCard.setHeight((int) (oldHeight*1.5));
					pickAmount = true;
					return;
				}
			}
		}
	}
	private void clickOnAmount() {
		/*picking the amount*/
		if(pickAmount) {
			if(mouseOver(oneBounds)) {
				if(savedList.size() < MAX_AMOUNT) {
					int count = 0;
					for(int i =0; i < savedList.size();i++) {
						if(savedList.get(i).getName().equals(currCard.getName())) {
							count++;
						}
					}
					if(count <4) {savedList.add(currCard);}
					int padding = 0;
					for(int i =0; i < cardNameBounds.size();i++) {
						padding = cardNameBounds.get(i).y + cardNameBounds.get(i).height;
					}
					
					cardNameBounds.add(new Rectangle(savedCardsBounds.x,savedCardsBounds.y + padding,savedCardsBounds.x + savedCardsBounds.width,savedCardsBounds.height/25));
					pickAmount = false;
				}
			}
			
			else if(mouseOver(twoBounds)) {
				int count =0;
				for(int i =0; i < 2; i++) {
					if(savedList.size() < MAX_AMOUNT) {
						
						int padding = 0;
						for(int k =0; k < cardNameBounds.size();k++) {
							padding = cardNameBounds.get(k).y + cardNameBounds.get(k).height;
						}
						for(int k =0; k < savedList.size();k++) {
							if(savedList.get(k).getName().equals(currCard.getName())) {
								count++;
							}
						}
						if(count <4) {savedList.add(currCard);}
						cardNameBounds.add(new Rectangle(savedCardsBounds.x,savedCardsBounds.y +padding,savedCardsBounds.x + savedCardsBounds.width,savedCardsBounds.height/25));
						pickAmount = false;
					}
				}
			}
			
			else if(mouseOver(threeBounds)) {
				int count = 0;
				for(int i =0; i < 3; i++) {
					if(savedList.size() < MAX_AMOUNT) {
						
						int padding = 0;
						for(int k =0; k < cardNameBounds.size();k++) {
							padding = cardNameBounds.get(k).y + cardNameBounds.get(k).height;
						}
						for(int k =0; k < savedList.size();k++) {
							if(savedList.get(k).getName().equals(currCard.getName())) {
								count++;
							}
						}
						if(count<4) {savedList.add(currCard);}
						cardNameBounds.add(new Rectangle(savedCardsBounds.x,savedCardsBounds.y + padding,savedCardsBounds.x + savedCardsBounds.width,savedCardsBounds.height/25));
						pickAmount = false;
					}
				}
			}
			else if(mouseOver(noneBounds)) {
				pickAmount = false;
			}
		}
		
	}
	private void clickOnSavedCards() {
		int index = 0;
		for(int i =0; i < cardNameBounds.size();i++) {
			if(mouseOver(cardNameBounds.get(i))) {
				savedList.remove(i);
				cardNameBounds.remove(i);
				index = i;
			}
		}
		for(int i = index;i < cardNameBounds.size();i++) {
			Rectangle currCardBounds = cardNameBounds.get(i);
			currCardBounds.y = (int) (currCardBounds.getY() - currCardBounds.getHeight());
		}
	}
	private void clickButtons() {
		if(mouseOver(saveBounds)) {
			if(savedList.size() == MAX_AMOUNT) {
				if(nameTextField.getSearch().length()==0) {return;}
				fileLoader.setFileName(Game.DECK_PATH + nameTextField.getSearch() + "_deck.txt");
				fileLoader.createFile();
				String [] cardNames = new String[savedList.size()];
				for(int i =0; i < savedList.size();i++) {
					cardNames[i] = savedList.get(i).getName();
				}
				fileLoader.write(cardNames);
				fileLoader.closeOutput();
			}
		}
		else if(mouseOver(sortBounds)) {
			Card temp;
			for(int i =0; i < savedList.size() -1;i++) {
				for(int j = 0; j < savedList.size()-1-i;j++) {
					if(savedList.get(j).totalCost() > savedList.get(j+1).totalCost()) {
						temp = savedList.get(j);
						savedList.set(j, savedList.get(j+1));
						savedList.set(j+1, temp);
					}
				}
			}
		}
	}
	private void clickOnNameTextField() {
		if(mouseOver(nameTextField.getBounds())) {
			nameTextField.setCanType(true);
		}
	}
	private void clickOnTextField() {
		if(mouseOver(topSearchBar.getBounds())) {
			topSearchBar.setCanType(true);
		}
	}
	private void clickOnSearchedCardsList() {
		int oldWidth=0;
		int oldHeight=0;
		/*picking the cards*/
		if(!pickAmount && savedList.size()<MAX_AMOUNT) {
			for(int i =0; i < cardListToSearch.size();i++) {
				if(mouseOver(cardListToSearch.get(i))) {
					currCard = cardListToSearch.get(i);
					oldWidth = currCard.getWidth();
					oldHeight =currCard.getHeight();
					currCard.setWidth((int) (oldWidth*1.5));
					currCard.setHeight((int) (oldHeight*1.5));
					pickAmount = true;
					return;
				}
			}
		}
	}
	public void mousePressed(MouseEvent e) {
		if(mouseOver(returnBounds)) {
			if(Game.state == State.DECK_BUILDER) {
				savedList.clear();
				Game.state = State.MAIN_MENU;
				Game.goToDeckBuilder = false;
			}
		}
		else if(mouseOver(saveBounds) || mouseOver(sortBounds)) {clickButtons();}
		if(pickAmount) {clickOnAmount();}
		
		if(topSearchBar.getSearch().equals("")) {if(!pickAmount) {clickOnCards();}}
		else {
			clickOnSearchedCardsList();
		}
		if(mouseOver(topSearchBar.getBounds())){clickOnTextField();}
		else {topSearchBar.setCanType(false);topSearchBar.setShowCursor(false);}
		
		if(mouseOver(nameTextField.getBounds())) {clickOnNameTextField();}
		else {nameTextField.setCanType(false);nameTextField.setShowCursor(false);}
		
		for(int i =0; i < cardNameBounds.size();i++) {
			if(mouseOver(cardNameBounds.get(i))) {
				clickOnSavedCards();
			}
		}
	}
	public void keyPressed(KeyEvent e) {
		if(topSearchBar.isCanType()) {
			cardListToSearch.clear();
			topSearchBar.keyPressed(e);
			for(int i =0; i < cardList.size();i++) {
				if(topSearchBar.getSearch().equals("")) {cardListToSearch.clear();break;}
				if(cardList.get(i).getName().toLowerCase().contains(topSearchBar.getSearch().toLowerCase())) {
					System.out.println(cardList.get(i).getName());
					if(cardListToSearch.contains(cardList.get(i)) == false) {
						cardListToSearch.add(cardList.get(i));
					}
				}
			}
			
		}
		if(nameTextField.isCanType()) {
			nameTextField.keyPressed(e);
		}
		/*if(canType) {
			cardListToSearch.clear();
			if(e.getKeyCode() >=65 && e.getKeyCode()<=90) {
				search+= KeyEvent.getKeyText(e.getKeyCode());
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				search += " ";
			}
			if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if(search.length() > 0) {search = search.substring(0, search.length()-1);}
			}
				for(int i =0; i < cardList.size();i++) {
					if(search.equals("")) {cardListToSearch.clear();break;}
					if(cardList.get(i).getName().toLowerCase().contains(search.toLowerCase())) {
						System.out.println(cardList.get(i).getName());
						if(cardListToSearch.contains(cardList.get(i)) == false) {
							cardListToSearch.add(cardList.get(i));
						}
					}
				}
		}*/
	}
	public void actionPerformed(ActionEvent e) {
	//	if(canType) {
		//	showCursor = !showCursor;
		//}
	}
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		for(Card c: cardList) {
			if(mouseOver(c)) {
				cardDisplayGUI.setCurrCard(c);
			}
		}
	}
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(mouseOver(savedCardsBounds)) {
			int rotations = e.getWheelRotation();
			/*moved mouse wheel up*/
			if(rotations <0) {
				//System.out.println("mouse wheel went up");
				camIndex+=15;
			}
			/*moved mouse wheel down*/
			else {
				//System.out.println("mouse wheel went down");
				camIndex-=15;
			}
			if(camIndex <=0) {camIndex = 0;}
		}
	}
}
