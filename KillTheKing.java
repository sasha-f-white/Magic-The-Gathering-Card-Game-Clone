package cards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import id.ID;
import id.NameID;
import id.SubType;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class KillTheKing extends Spell implements ActionListener{
	private Timer timer;
	private BoardGUI boardGUI;
	public KillTheKing(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();
	}
	public KillTheKing(KillTheKing s) {
		super(s);
		init();
	}
	private void init() {
		setStrCost(0);
		setId(ID.SPELL);
		setName("Kill the King");
		setNameId(NameID.KILL_THE_KING);
		image = Texture.cardSprites[getNameId().ordinal()];
		timer = new Timer(getTime(),this);
	}
	public Card makeCopy() {return new KillTheKing(this);}
	
	public void activateEnterTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler,List<Card> list) {
		
	}
	public void enterTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler) {
		
		int dmg = 0;
		
		for (int i = 0; i < playerHandler.getCardsOnBoard().size(); i++) {
			Card card = playerHandler.getCardsOnBoard().get(i);
			
			if (card.getId() == ID.UNIT) {
				Unit unit = (Unit) card;
				if (unit.getSubType() == SubType.PEASANT) {
					dmg++;
				}
			}
		}
		
		opponentHandler.getPlayer().setHealth(opponentHandler.getPlayer().getHealth() - dmg);
				
		this.boardGUI = boardGUI;
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		disposeSpell(boardGUI);
		timer.stop();
	}
}
