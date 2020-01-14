package cards;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import game.Game;
import gui.Display;
import id.ID;
import id.NameID;
import music.Music;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class TheZapp extends Spell implements ActionListener{
	private Timer timer;
	private BoardGUI boardGUI;
	private String path;
	private final String wordsToDraw = " Very Generous";
	private float alpha = 0.0f;
	private boolean fadeOut; 
	public static  Music actualMusicPlayer;
	private Game game;
	public TheZapp(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI,Game game) {
		super(x,y,width,height,player,selectCardGUI);
		init(game);
	}
	public TheZapp(TheZapp l) {
		super(l);
		init(game);
		game = l.game;
	}
	private void init(Game game) {
		setIntCost(3);
		setId(ID.SPELL);
		setName("The Zapp");
		setNameId(NameID.THE_ZAPP);
		image = Texture.cardSprites[getNameId().ordinal()];
		timer = new Timer(getTime(),this);
		setTime(5000);
		this.game = game;
		path = "Straight out of the notes";
	}
	public Card makeCopy() {return new TheZapp(this);}
	
	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) 
	{
		actualMusicPlayer = game.getMusic();
		this.boardGUI = boardGUI;
		int randNum = new Random().nextInt(5)+1;
		if(randNum == 1) {path = "Straight out of the notes.wav";}
		else if(randNum == 2) {path = "Very Generous.wav";}
		else if(randNum == 3) {path = "Straight out of the notes.wav";}
		else if(randNum == 4) {path = "Why.wav";}
		else {path = "You cant be serious.wav";}
		for(Card c : opponentHandler.getCardsOnBoard()) {
			if(c instanceof Unit) {
				Unit unit = (Unit)c;
				unit.setHealth(unit.getHealth()-3);
				
			}
		}
		music.playSoundMaxVolume(path);
		//timer.start();
	}
	public void drawEffect(Graphics2D g) {
		//set the opacity
	    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	    g.setColor(Color.BLACK);
	    g.setFont(new Font("Arial",Font.BOLD,110));
	   // g.drawString(wordsToDraw, (int) (Display.WIDTH/2 - g.getFont().getSize()*2.2), Display.HEIGHT/2- g.getFont().getSize());
	    g.drawImage(Texture.zappImage,Display.WIDTH/2 - Texture.zappImage.getWidth()/2,
	    		Display.HEIGHT/2 - Texture.zappImage.getHeight()/2,
	    		Texture.zappImage.getWidth(),Texture.zappImage.getHeight(),null);
	    if(!fadeOut) {
		    alpha += 0.025f;
		    if (alpha >= 1.0f) {
		        alpha = 1.0f;
		        fadeOut = true;
		    }
	    }
	    if(fadeOut) {
	    	alpha-=0.025;
	    	if(alpha <=0) {
	    		alpha = 0;
	    		disposeSpell(boardGUI);
	    		fadeOut = false;
	    	}
	    }
	}
	
	public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list) {
		
	}
	
	public void actionPerformed(ActionEvent e) {
		disposeSpell(boardGUI);
		fadeOut = false;
		timer.stop();
	}
	
}
