/*
 Brendan Aucoin
 2018/04/12
 this class draws and holds everything for the opponents mana.
 */
package playing_panes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import gui.Display;
import player.Player;

public class OpponentManaGUI extends InteractionPane{
	private int padding = 40;
	private Player opponent;
	public OpponentManaGUI(Player opponent) {
		super(new Rectangle(
				(int)(Display.WIDTH/5),(int)(Display.HEIGHT/26),(int)(Display.WIDTH/3.5),(int)(Display.HEIGHT/1.4 - Display.HEIGHT/1.5))
				);
		this.opponent = opponent;
	}
	public void update() {
		
	}
	public void render(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.fill(getBounds());
		
		/*drawing the 5 stats*/
		g.setFont(new Font("Arial",Font.PLAIN,18));
		
		/*drawing the 5 stats*/
		g.setFont(new Font("Arial",Font.PLAIN,18));
		
		g.setColor(new Color(255,0,0));
		g.drawString("STR",(getBounds().x + padding),getBounds().y + getBounds().height);
		g.drawString(String.valueOf(opponent.getNumStrMana()), (float)((getBounds().x + padding)),(float)( getBounds().y+getBounds().height/2));
		
		g.setColor(new Color(65,105,225));
		g.drawString("INT",(int)(getBounds().x + padding*2.5),getBounds().y + getBounds().height);
		g.drawString(String.valueOf(opponent.getNumIntMana()), (int)(getBounds().x + padding*2.5),(float)( getBounds().y+getBounds().height/2));
		
		g.setColor(new Color(34,139,34));
		g.drawString("DEX",(int)(getBounds().x + padding*3.8),getBounds().y + getBounds().height);
		g.drawString(String.valueOf(opponent.getNumDexMana()), (int)(getBounds().x + padding*3.8),(float)( getBounds().y+getBounds().height/2));
		
		g.setColor(new Color(255,215,0));
		g.drawString("CHR",(int)(getBounds().x + padding*5.3),getBounds().y + getBounds().height);
		g.drawString(String.valueOf(opponent.getNumChrMana()), (int)(getBounds().x + padding*5.3),(float)( getBounds().y+getBounds().height/2));
		
		g.setColor(new Color(148,0,211));
		g.drawString("CON",(int)(getBounds().x + padding*7),getBounds().y + getBounds().height);
		g.drawString(String.valueOf(opponent.getNumConMana()), (int)(getBounds().x + padding*7),(float)( getBounds().y+getBounds().height/2));
		
	}
	public void mousePressed(MouseEvent e) {
		
	}
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
	}
}
