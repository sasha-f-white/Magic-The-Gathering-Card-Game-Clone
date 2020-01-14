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

public class LightInTheBlack extends Spell implements ActionListener{
	private Timer timer;
	private BoardGUI boardGUI;
	public LightInTheBlack(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();
	}
	public LightInTheBlack(LightInTheBlack l) {
		super(l);
		init();
	}
	private void init() {
		setStrCost(1);
		setId(ID.SPELL);
		setName("Light in the Black");
		setNameId(NameID.LIGHT_IN_THE_BLACK);
		image = Texture.cardSprites[getNameId().ordinal()];
		timer = new Timer(getTime(),this);
	}
	public Card makeCopy() {return new LightInTheBlack(this);}
	
	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		this.boardGUI = boardGUI;
		
		for(Card c : playerHandler.getCardsOnBoard()) {
			if(c instanceof Unit) {
				Unit unit = (Unit)c;
				if(unit.getSubType() == SubType.PEASANT) {
					unit.setAttack(unit.getAttack()+2);
					unit.setHealth(unit.getHealth()+2);
				}
			}
		}
		
		for(Card c : opponentHandler.getCardsOnBoard()) {
			if(c instanceof Unit) {
				Unit unit = (Unit)c;
				if(unit.getSubType() == SubType.CROWN) {
					unit.setAttack(unit.getAttack()-2);
					unit.setHealth(unit.getHealth()-1);
				}
			}
		}
		timer.start();
	}
	
	
	public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list) {
		
	}
	
	public void actionPerformed(ActionEvent e) {
		disposeSpell(boardGUI);
		timer.stop();
	}
	
}
