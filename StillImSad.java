package cards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import id.ID;
import id.NameID;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class StillImSad extends Spell implements ActionListener{
	private Timer timer;
	private BoardGUI boardGUI;
	public StillImSad(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();
	}
	public StillImSad(StillImSad s) {
		super(s);
		init();
	}
	private void init() {
		setStrCost(0);
		setId(ID.SPELL);
		setName("Still I'm Sad");
		setNameId(NameID.STILL_IM_SAD);
		image = Texture.cardSprites[getNameId().ordinal()];
		timer = new Timer(getTime(),this);
	}
	public Card makeCopy() {return new StillImSad(this);}
	
	public void activateEnterTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler,List<Card> list) {
		
	}
	public void enterTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler) {
		playerHandler.getPlayer().setNumIntMana(playerHandler.getPlayer().getNumIntMana()-1);
		playerHandler.getPlayer().setMaxIntMana(playerHandler.getPlayer().getMaxIntMana()-1);
		if(playerHandler.getPlayer().getNumIntMana() <=0) {playerHandler.getPlayer().setNumIntMana(0);}
		if(playerHandler.getPlayer().getMaxIntMana() <=0) {playerHandler.getPlayer().setMaxIntMana(0);}
		for(Card c : opponentHandler.getCardsOnBoard()) {
			if(c.totalCost() > 3) {
				if(c instanceof Unit) {
					Unit unit = (Unit)c;
					unit.setHealth(0);
					break;
				}
			}
		}
		this.boardGUI = boardGUI;
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		disposeSpell(boardGUI);
		timer.stop();
	}
	
}
