/*
 Brendan Aucoin
 2018/04/12
 this class draws and holds everything for which turn your on.
 */
package playing_panes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import game.Game;
import gui.Display;

public class TurnGUI extends InteractionPane{
	public static int turn;
	public TurnGUI() {
		super(new Rectangle(0,0,Display.WIDTH/5,Display.HEIGHT/12));
		turn = 1;
	}
	public void update() {
		
	}
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fill(getBounds());
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString("Turn: "+String.valueOf(turn),(int)( getBounds().x + getBounds().width/2 -g.getFont().getStringBounds("Turn: " + String.valueOf(turn), frc).getWidth()/2), (int)(getBounds().y + getBounds().height/2 + g.getFont().getStringBounds(String.valueOf(turn), frc).getHeight()/3));
		g.setFont(new Font("Arial",Font.PLAIN,18));
		g.drawString("Current Phase: " + Game.phase.getName(), getBounds().x + getBounds().width/10 - Game.phase.getName().length(),getBounds().height- getBounds().height/20);
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
	}
}
