package playing_panes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import game.Game;
import gui.Display;
import state.State;

public class StartScreen extends InteractionPane{
	private Rectangle startBounds;
	private Game game;
	public StartScreen(Game game) {
		super(new Rectangle(Display.WIDTH/2 - Display.WIDTH/15,Display.HEIGHT/2 - Display.HEIGHT/8,Display.WIDTH/4,Display.HEIGHT/8));
		startBounds = new Rectangle(getBounds());
		this.game = game;
	}
	
	public void update() {}
	public void render(Graphics2D g) {
		g.setColor(new Color(255,255,224));
		changeColour(g,startBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(getBounds().x,getBounds().y,getBounds().width,getBounds().height,125,125);
		g.setFont(new Font("Arial",Font.PLAIN,80));
		g.setColor(Color.BLACK);
		g.drawString("Start", getBounds().x + getBounds().width/2 - getBounds().width/4, getBounds().y + getBounds().height/2 + getBounds().height/4);
	}
	
	public void mousePressed(MouseEvent e) {
		if(mouseOver(startBounds)) {
			Game.state = State.PLAYING;
			game.drawInitialCards();
		}
	}
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
	}
}
