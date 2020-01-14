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

public class TheDing extends Spell implements ActionListener{
	private Timer timer;
	private String path;
	private BoardGUI boardGUI;
	public TheDing(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();
	}
	public TheDing(TheDing l) {
		super(l);
		init();
	}
	private void init() {
		setIntCost(3);
		setId(ID.SPELL);
		setName("The Ding");
		setNameId(NameID.THE_DING);
		image = Texture.cardSprites[getNameId().ordinal()];
		timer = new Timer(getTime(),this);
	}
	public Card makeCopy() {return new TheDing(this);}
	
	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) 
	{
		path = "Very Generous.wav";
		this.boardGUI = boardGUI;
		
		for(Card c : opponentHandler.getCardsOnBoard()) {
			if(c instanceof Unit) {
				Unit unit = (Unit)c;
				unit.setHealth(unit.getAttack()-3);
				
			}
		}
		music.playSoundMaxVolume(path);
		timer.start();
	}
	
	
	public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list) {
		
	}
	
	public void actionPerformed(ActionEvent e) {
		disposeSpell(boardGUI);
		timer.stop();
	}
	
}
