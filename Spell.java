/*
 Sasha White
 2018/04/20
 this class is an abstract class that all spells inherit from.
 because every spell has an enter trigger and thats pretty much it.
 */
package cards;

import java.awt.Graphics2D;

import player.Player;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import triggers.EnterTrigger;

public abstract class Spell extends Card implements EnterTrigger{
	private int time;
	public Spell(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		time = 850;
	}
	public Spell(Spell s) {
		super(s);
		time = 850;
	}
	public void disposeSpell(BoardGUI boardGUI) {
		boardGUI.setCurrSpellCard(null);
		boardGUI.setShowSpellCard(false);
	}
	public void drawEffect (Graphics2D g){
	}
	
	public int getTime() {return time;}
	public void setTime(int time){this.time = time;}
}
