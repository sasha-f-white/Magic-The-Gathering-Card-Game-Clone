	package cards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Timer;

import game.Game;
import id.ID;
import id.NameID;
import phase.Phase;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class RatInfestation extends Spell implements ActionListener{
	
	private Timer timer;
	private BoardGUI boardGUI;
	public RatInfestation(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();
	}
	public RatInfestation(RatInfestation hiddenPlot) {
		super(hiddenPlot);
		init();
	}
	private void init() {
		setDexCost(2);
		setConCost(2);
		setId(ID.SPELL);
		setName("Rat Infestation");
		setNameId(NameID.RAT_INFESTATION);
		image = Texture.cardSprites[getNameId().ordinal()];
		timer = new Timer(getTime(),this);
	}

	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		boardGUI.addCardToBoard(new SewerRat(0,0,0,0,getPlayer(),selectCardGUI), playerHandler, opponentHandler, false);
		boardGUI.addCardToBoard(new SewerRat(0,0,0,0,getPlayer(),selectCardGUI), playerHandler, opponentHandler, false);
		boardGUI.addCardToBoard(new SewerRat(0,0,0,0,getPlayer(),selectCardGUI), playerHandler, opponentHandler, false);
		
		this.boardGUI = boardGUI;
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		disposeSpell(boardGUI);
		timer.stop();
	}
		
	
	@Override
	public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list) {
	}

	@Override
	public Card makeCopy() {
		return new RatInfestation(this);
	}

}