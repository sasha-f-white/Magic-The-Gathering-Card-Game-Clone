/*
 Brendan Aucoin
 2018/04/12
 this class draws and holds everything for the discard pile.
 */
package playing_panes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import cards.Card;
import game.Game;
import gui.Display;
import phase.Phase;
import player.Player;
import sprites.Texture;

public class DiscardGUI extends InteractionPane{
	private Player player;
	private Rectangle discardDeckBounds;
	private Phase previousPhase;
	private boolean showGreenBorder;
	private BoardGUI boardGUI;
	private boolean showAmount = false;
	public DiscardGUI(Player player,BoardGUI boardGUI) {
		super(new Rectangle(
				0,(int)(Display.HEIGHT/1.225),(int)(Display.WIDTH/5),Display.HEIGHT -(int)(Display.HEIGHT/1.225) 
				
				));
		this.player = player;
		discardDeckBounds = new Rectangle(
				getBounds().x + getBounds().width/2 - Texture.backOfCard.getWidth()/2,
				(int) (getBounds().y + getBounds().height/2 - Texture.backOfCard.getHeight()/1.35),
				Texture.backOfCard.getWidth(),
				Texture.backOfCard.getHeight()
				);
		previousPhase = Game.phase;
		showGreenBorder = false;
		this.boardGUI = boardGUI;
	}
	
	public void update() {
		if(Game.phase != Phase.LOOK_AT_DISCARD) {
			previousPhase = Game.phase;
		}
	}
	public void render(Graphics2D g) {
		//g.setColor(Color.BLUE);
		//g.fill(getBounds());
		g.drawImage(Texture.discardBackground, getBounds().x, getBounds().y, getBounds().width, getBounds().height, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD,40));
		g.drawImage(Texture.discardBackImage,discardDeckBounds.x,discardDeckBounds.y,discardDeckBounds.width,discardDeckBounds.height,null);
		if(showGreenBorder) {
			g.setColor(Color.GREEN);
			g.setStroke(new BasicStroke(3));
			g.draw(discardDeckBounds);
		}
		g.setColor(Color.WHITE);
		if(showAmount) {
			g.drawString(String.valueOf(player.getDiscardPile().getDeck().size()),discardDeckBounds.x + discardDeckBounds.width/2 - g.getFont().getSize()/4,discardDeckBounds.y + discardDeckBounds.height/2);
		}
	}
	
	public void mousePressed(MouseEvent e) {
	}
	
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		if(Game.phase != Phase.LOOK_AT_DISCARD) {
			if(mouseOver(discardDeckBounds)) {
				showGreenBorder = true;
				showAmount = true;
			}
			else {
				showGreenBorder = false;
				showAmount = false;
			}
		}
	}
	
}
