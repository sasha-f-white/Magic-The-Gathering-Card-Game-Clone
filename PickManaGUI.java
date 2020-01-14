/*
 Brendan Aucoin
 2018/04/12
 this class draws and holds everything for the a specific state when you pick mana.
 */
package playing_panes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import cards.Card;
import cards.ManaCard;
import game.Game;
import gui.Display;
import id.ManaType;
import phase.Phase;
import player.Player;

public class PickManaGUI extends InteractionPane {
	private Player player;
	private Game game;
	public PickManaGUI(Game game,Player player) {
		super(new Rectangle(
				//	(int)(Display.WIDTH/5),Display.HEIGHT/12,(int)((Display.WIDTH - Display.WIDTH/6)),(int)(Display.HEIGHT/1.2)
					(int)(Display.WIDTH/5),Display.HEIGHT/12,(int)((Display.WIDTH - Display.WIDTH/6)),(int)(( Display.HEIGHT/1.5)*1.03)
					));
		this.player = player;
		this.game = game;
	}

	public void update() {
		
	}
	public void render(Graphics2D g) {
		try {
			for(Card c:player.getMana().getHand()) {
					c.render(g);
			}
		}catch(ConcurrentModificationException ex) {return;}
	}

	public void mousePressed(MouseEvent e) {
		for(Card c:player.getMana().getHand()) {
			if(Game.phase == Phase.PICK_MANA) {
				if(mouseOver(c.getBounds())) {
					ManaCard manaCard = (ManaCard)c;
					if(manaCard.getType() == ManaType.STR) {player.setMaxStrMana(player.getMaxStrMana()+1);player.setNumStrMana(player.getNumStrMana()+1);}
					else if(manaCard.getType() == ManaType.INT) {player.setMaxIntMana(player.getMaxIntMana()+1);player.setNumIntMana(player.getNumIntMana()+1);}
					else if(manaCard.getType() == ManaType.DEX) {player.setMaxDexMana(player.getMaxDexMana()+1);player.setNumDexMana(player.getNumDexMana()+1);}
					else if(manaCard.getType() == ManaType.CHR) {player.setMaxChrMana(player.getMaxChrMana()+1);player.setNumChrMana(player.getNumChrMana()+1);}
					else if(manaCard.getType() == ManaType.CON) {player.setMaxConMana(player.getMaxConMana()+1);player.setNumConMana(player.getNumConMana()+1);}
					Game.phase = Phase.CARD_DRAW;
					Iterator<Card> iter = player.getMana().getHand().iterator();
					while(iter.hasNext()) {
						Card card = iter.next();
						iter.remove();
					}
					break;
				}
			}
		}
	}
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		for(int i =0 ; i< player.manaSize();i++) {
			if(mouseOver(player.getMana().getHand().get(i))) {
				
			}
		}
	}
}
