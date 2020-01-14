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

import game.Game;
import gui.Display;
import player.Player;
import playing_panes.InteractionPane;
import state.State;

public class EndGameMenu extends InteractionPane{
	private Player winner;
	private Rectangle mainMenuBounds;
	private Game game;
	public EndGameMenu(Game game) {
		super(new Rectangle(0,0,Display.WIDTH,Display.HEIGHT));
		mainMenuBounds = new Rectangle(getBounds().x + getBounds().width/2 - getBounds().width/12,
				getBounds().y + getBounds().height - getBounds().height/5,
				getBounds().width/4,getBounds().height/7);
		this.game = game;
	}
	public void update() {
		
	}
	public void render(Graphics2D g) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getBounds().width, getBounds().height);
		if(winner !=null) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial",Font.BOLD,120));
			g.drawString(winner.getName() + " Wins", getBounds().x + getBounds().width/2 + getBounds().width/6 -(int)(g.getFont().getStringBounds(winner.getName() + " Wins", frc).getWidth()/2), getBounds().y + getBounds().height/2 -g.getFont().getSize()/4);
		}
		changeColour(g, mainMenuBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(mainMenuBounds.x, mainMenuBounds.y, mainMenuBounds.width, mainMenuBounds.height, 200, 200);
		g.setFont(new Font("Arial",Font.PLAIN,60));
		g.setColor(Color.BLACK);
		changeColour(g,mainMenuBounds,Color.RED,Color.BLACK);
		g.drawString("Main Menu",mainMenuBounds.x + mainMenuBounds.width/2 -(int)(g.getFont().getStringBounds("Main Menu", frc).getWidth()/2),mainMenuBounds.y + mainMenuBounds.height/2 + mainMenuBounds.height/10);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD,90));
		g.drawString("Credits:", getBounds().x + getBounds().width/18, getBounds().y + g.getFont().getSize());
		g.setFont(new Font("Arial",Font.PLAIN,45));
		g.drawString("Brendan Aucoin", getBounds().x + getBounds().width/18, (int) (getBounds().y + g.getFont().getSize()*3.5));
		g.drawString("Sasha White", getBounds().x + getBounds().width/18, getBounds().y + g.getFont().getSize()*5);
		g.drawString("Aidan Dowdall", getBounds().x + getBounds().width/18, (int) (getBounds().y + g.getFont().getSize()*6.5));
		g.drawString("Special Thanks to:", getBounds().x + getBounds().width/22, (int) (getBounds().y + g.getFont().getSize()*8));
		g.drawString("Oliver Trevett", getBounds().x + getBounds().width/22, (int) (getBounds().y + g.getFont().getSize()*9.5));
		g.setFont(new Font("Arial",Font.BOLD,40));
		g.drawString("and Josh",getBounds().x + getBounds().width/22, (int) (getBounds().y + g.getFont().getSize()*12));
		g.drawString("and google images",getBounds().x + getBounds().width/22, (int) (getBounds().y + g.getFont().getSize()*14));
	}
	public void mousePressed(MouseEvent e) {
		if(mouseOver(mainMenuBounds)) {
			Game.state = State.MAIN_MENU;
			winner = null;
			game.logicTest();
		}
	}
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
	}
	
	
	
	public Player getWinner() {return winner;}
	public void setWinner(Player winner) {this.winner = winner;}
	
	
}
