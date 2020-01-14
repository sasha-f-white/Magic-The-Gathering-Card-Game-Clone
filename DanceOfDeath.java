package cards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.*;

import javax.swing.Timer;

import id.ID;
import id.NameID;
import id.SubType;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class DanceOfDeath extends Spell implements ActionListener{
	private Timer timer;
	private BoardGUI boardGUI;
	private Random generator = new Random();
	public DanceOfDeath(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();
	}
	public DanceOfDeath(DanceOfDeath s) {
		super(s);
		init();
		this.selectCardGUI = s.selectCardGUI;
	}
	private void init() {
		setStrCost(2);
		setDexCost(2);
		setIntCost(2);
		setConCost(2);
		setChrCost(2);
		this.selectCardGUI = selectCardGUI;
		setId(ID.SPELL);
		setName("Dance of Death");
		setNameId(NameID.DANCE_OF_DEATH);
		image = Texture.cardSprites[getNameId().ordinal()];
		timer = new Timer(getTime(),this);
	}
	public Card makeCopy() {return new DanceOfDeath(this);}
	
	public void activateEnterTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler,List<Card> list) {
		
	}
	
	public void enterTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler) 
	{
		int choice = generator.nextInt(2)+1;
		
		if (choice == 1) {
			playerHandler.getPlayer().setHealth(0);
		}
		else {
			opponentHandler.getPlayer().setHealth(0);
		}
				
		this.boardGUI = boardGUI;
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		disposeSpell(boardGUI);
		timer.stop();
	}
}
