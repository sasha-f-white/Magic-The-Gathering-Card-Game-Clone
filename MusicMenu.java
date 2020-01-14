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
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import game.Game;
import gui.Display;
import phase.Phase;
import playing_panes.InteractionPane;
import sprites.Texture;
import state.State;

public class MusicMenu extends InteractionPane{
	private BufferedImage background;
	private Rectangle option1Bounds,option2Bounds,option3Bounds,returnBounds;
	private Game game;
	private Phase previousPhase;
	private State previousState;
	public MusicMenu(Game game) {
		super(new Rectangle(0,0,Display.WIDTH,Display.HEIGHT));
		this.game = game;
		background = Texture.mainMenuBackground;
		option1Bounds = new Rectangle((int) (getBounds().x + getBounds().width/2 - getBounds().width/2.1),
				getBounds().y + getBounds().height/2 - getBounds().height/6,
				(int) (getBounds().width/3.5),(int) (getBounds().height/3.5));
		option2Bounds = new Rectangle((int)(option1Bounds.x + option1Bounds.width*1.2),
				option1Bounds.y,option1Bounds.width,option1Bounds.height);
		option3Bounds = new Rectangle((int) (option2Bounds.x + option2Bounds.width*1.2),option2Bounds.y,option2Bounds.width,option2Bounds.height);
	}

	public void update() {
		
	}
	private void drawButtons(Graphics2D g) {
		this.changeColour(g, option1Bounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(option1Bounds.x,option1Bounds.y,option1Bounds.width,option1Bounds.height,100,100);
		if(mouseOver(option1Bounds)) {
			g.setColor(Color.GREEN);
			g.setStroke(new BasicStroke(3));
			g.draw(option1Bounds);
		}
		this.changeColour(g, option2Bounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(option2Bounds.x,option2Bounds.y,option2Bounds.width,option2Bounds.height,100,100);
		if(mouseOver(option2Bounds)) {
			g.setColor(Color.GREEN);
			g.setStroke(new BasicStroke(3));
			g.draw(option2Bounds);
		}
		this.changeColour(g, option3Bounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(option3Bounds.x,option3Bounds.y,option3Bounds.width,option3Bounds.height,100,100);
		if(mouseOver(option3Bounds)) {
			g.setColor(Color.GREEN);
			g.setStroke(new BasicStroke(3));
			g.draw(option3Bounds);
		}
		
		
	}
	public void render(Graphics2D g) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		g.drawImage(background,0,0,Display.WIDTH,Display.HEIGHT,null);
		g.setFont(new Font("Lucida Handwriting",Font.PLAIN,80));
		g.drawString("RAINBOW", getBounds().width/2 -(int)(g.getFont().getStringBounds("RAINBOW", frc).getWidth()/2), 0+(int)(g.getFont().getStringBounds("RAINBOW", frc).getHeight()));
		
		drawButtons(g);
		g.setFont(new Font("arial",Font.PLAIN,30));
		g.setColor(Color.BLACK);
		g.drawString("Man on the Silver Mountain", option1Bounds.x + option1Bounds.width/2 - (int)(g.getFont().getStringBounds("Man on the Silver Mountain", frc).getWidth()/2), option1Bounds.y + option1Bounds.height/2 - (int)(g.getFont().getStringBounds("Man on the Silver Mountain", frc).getHeight()/2));
		g.drawString("Sails of Charon", option2Bounds.x +option2Bounds.width/2 - (int)(g.getFont().getStringBounds("Sails of Charon", frc).getWidth()/2) , option2Bounds.y + option2Bounds.height/2 - (int)(g.getFont().getStringBounds("Sails of Charon", frc).getHeight()/2));
		g.drawString("Losing my religion", option3Bounds.x +option3Bounds.width/2 - (int)(g.getFont().getStringBounds("Losing my religion", frc).getWidth()/2) , option3Bounds.y + option3Bounds.height/2 - (int)(g.getFont().getStringBounds("Losing my religion", frc).getHeight()/2));
		g.setFont(new Font("arial",Font.PLAIN,60));
		if(returnBounds==null) {returnBounds = new Rectangle(
				//getBounds().x + getBounds().width/2 - (int)(g.getFont().getStringBounds("Back", frc).getWidth()/2),
				//getBounds().y + getBounds().height - (int)(g.getFont().getStringBounds("Back", frc).getHeight()),
				//(int)(g.getFont().getStringBounds("Back", frc).getWidth()/2),
				//(int)(g.getFont().getStringBounds("Back", frc).getHeight()*2)
				getBounds().x + getBounds().width/2 - (int)(g.getFont().getStringBounds("Back", frc).getWidth()/2)
				,getBounds().y + getBounds().height - (int)(g.getFont().getStringBounds("Back", frc).getHeight()*2),
				(int)(g.getFont().getStringBounds("Back", frc).getWidth()),(int)(g.getFont().getStringBounds("Back", frc).getHeight())
				);}
		this.changeColour(g, returnBounds, Color.RED, Color.WHITE);
		g.drawString("Back",returnBounds.x,returnBounds.y + returnBounds.height);
	}

	public void mousePressed(MouseEvent e) {
		if(mouseOver(option1Bounds)) {
			game.playClip("Man on the silver mountain.wav");
		}
		else if(mouseOver(option2Bounds)) {
			game.playClip("Sails of charon.wav");
		}
		else if(mouseOver(option3Bounds)) {
			game.playClip("Losing my religion.wav");
		}
		else if(mouseOver(returnBounds)) {
			Game.state = previousState;
			Game.phase = previousPhase;
			Game.goToMusicMenu = false;
		}
	}
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
	}
	public void setPreviousPhase(Phase previousPhase) {this.previousPhase = previousPhase;}
	public Phase getPreviousPhase() {return previousPhase;}

	public State getPreviousState() {return previousState;}

	public void setPreviousState(State previousState) {this.previousState = previousState;}
	
}
