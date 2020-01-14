/*
 Brendan Aucoin
 2018/05/14
 all the menus are the same as all the gui just with differnet logic in them for pressing on certain things.
 */
package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import game.Game;
import gui.Display;
import playing_panes.InteractionPane;
import sprites.Texture;
import state.State;

public class MainMenu extends InteractionPane{
	private BufferedImage background;
	private Rectangle startBounds,deckBuilderBounds,musicBounds,rulesBounds,quitBounds;
	private MusicMenu musicMenu;
	public MainMenu(MusicMenu musicMenu) {
		super(new Rectangle(0,0,Display.WIDTH,Display.HEIGHT));
		background = Texture.mainMenuBackground;
		this.musicMenu = musicMenu;
	}
	
	public void update() {
		
	}
	
	public void render(Graphics2D g) {
		g.drawImage(background,0,0,Display.WIDTH,Display.HEIGHT,null);
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Lucida Handwriting",Font.PLAIN,45));
		/*if(startBounds == null) {startBounds = new Rectangle(Display.WIDTH/40,Display.HEIGHT/4,(int)(((int)(g.getFont().getStringBounds("Start", frc).getWidth()))-g.getFont().getSize()/10),((int)(g.getFont().getStringBounds("Start", frc).getHeight()))-g.getFont().getSize());}
		if(deckBuilderBounds == null) {deckBuilderBounds = new Rectangle(Display.WIDTH/40,(int)(Display.HEIGHT/2.7),(int)(((int)(g.getFont().getStringBounds("Deck Builder", frc).getWidth()))-g.getFont().getSize()*2.8),(int)(((int)(g.getFont().getStringBounds("Deck Builder", frc).getHeight()))-g.getFont().getSize()/1.2));}
		if(settingsBounds == null) {settingsBounds = new Rectangle(Display.WIDTH/40,(int)(Display.HEIGHT/1.9)-g.getFont().getSize()/5,(int)(((int)(g.getFont().getStringBounds("Settings", frc).getWidth())-g.getFont().getSize()*3.75)),(int)(((int)(g.getFont().getStringBounds("Settings", frc).getHeight()))-g.getFont().getSize()/1.3));}
		if(rulesBounds == null) {rulesBounds = new Rectangle(Display.WIDTH/40,(int)(Display.HEIGHT/1.5 - g.getFont().getSize()/3),(int)(((int)(g.getFont().getStringBounds("Rules", frc).getWidth())-g.getFont().getSize()*2.5)),(int)((int)(g.getFont().getStringBounds("Rules", frc).getHeight()) -g.getFont().getSize()/1.5));}
		if(quitBounds == null) {quitBounds = new Rectangle(Display.WIDTH/40,(int)(Display.HEIGHT/1.25 - g.getFont().getSize()/3),(int)(((int)(g.getFont().getStringBounds("Quit", frc).getWidth())-g.getFont().getSize()*1.2)),(int)((int)(g.getFont().getStringBounds("Quit", frc).getHeight()) -g.getFont().getSize()/1.4));}
		*/
		if(startBounds == null) {startBounds = new Rectangle(getBounds().width/40,getBounds().height/7,(int)(g.getFont().getStringBounds("Start", frc).getWidth()),(int)(g.getFont().getStringBounds("Start", frc).getHeight()/1.5));	}
		if(deckBuilderBounds == null) {deckBuilderBounds = new Rectangle(getBounds().width/40,(int) (getBounds().height/3.3),(int)(g.getFont().getStringBounds("Deck Builder", frc).getWidth()),(int)(g.getFont().getStringBounds("Deck Builder", frc).getHeight()/1.5));}
		if(musicBounds == null) {musicBounds = new Rectangle(getBounds().width/40,(int) (getBounds().height/2.15),(int)(g.getFont().getStringBounds("Music", frc).getWidth()),(int)(g.getFont().getStringBounds("Music", frc).getHeight()/1.5));}
		if(rulesBounds == null) {rulesBounds = new Rectangle(getBounds().width/40,(int) (getBounds().height/1.6),(int)(g.getFont().getStringBounds("Rules", frc).getWidth()),(int)(g.getFont().getStringBounds("Rules", frc).getHeight()/1.5));}
		if(quitBounds == null) {
			quitBounds = new Rectangle(
					getBounds().width/40,(int) (getBounds().height/1.3),
					(int)(g.getFont().getStringBounds("Quit", frc).getWidth()),(int)(g.getFont().getStringBounds("Quit", frc).getHeight()/1.5)
					);
		}
		this.changeColour(g, startBounds, Color.RED, Color.WHITE);
		g.drawString("Start",startBounds.x,startBounds.y + startBounds.height);
		
		this.changeColour(g, deckBuilderBounds, Color.RED, Color.WHITE);
		g.drawString("Deck Builder",deckBuilderBounds.x,deckBuilderBounds.y + deckBuilderBounds.height);
		
		this.changeColour(g, musicBounds, Color.RED, Color.WHITE);
		g.drawString("Music", musicBounds.x, musicBounds.y + musicBounds.height);
		
		this.changeColour(g, rulesBounds, Color.GRAY, Color.WHITE);
		g.drawString("Rules", rulesBounds.x, rulesBounds.y + rulesBounds.height);
		
		this.changeColour(g, quitBounds, Color.RED, Color.WHITE);
		g.drawString("Quit", quitBounds.x, quitBounds.y + quitBounds.height);
		
	}
	
	public void mousePressed(MouseEvent e) {
		if(mouseOver(startBounds)) {
			//Game.state = State.PICK_DECK;
			Game.goToPickDeckMenu = true;
		}
		else if(mouseOver(deckBuilderBounds)) {
			//Game.state = State.DECK_BUILDER;
			Game.goToDeckBuilder = true;
		}
		else if(mouseOver(musicBounds)) {
			musicMenu.setPreviousState(Game.state);
			musicMenu.setPreviousPhase(Game.phase);
			//Game.state = State.MUSIC;
			Game.goToMusicMenu = true;
		}
		else if(mouseOver(rulesBounds)){
		//	Game.state =State.RULES;
		}
		else if (mouseOver(quitBounds)) {
			System.exit(0);
		}
	}
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		
	}
}
