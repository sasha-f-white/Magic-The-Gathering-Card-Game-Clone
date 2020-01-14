/*
 Aidan Dowdall
 2018/04/20
 this class holds the data for the mana cards
 */
package cards;

import java.awt.Graphics2D;

import board.Board;
import id.ManaType;
import player.Player;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class ManaCard extends Card{
	private ManaType type;
	public ManaCard(float x,float y,int width,int height,Player owner,ManaType type,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,owner,selectCardGUI);
		this.type = type;
		setName(type.toString());
		image = Texture.manaSprites[type.ordinal()];
	}
	public ManaCard(ManaCard manaCard) {
		super(manaCard);
	}
	public void render(Graphics2D g) {
		g.drawImage(image,(int)getX(),(int)getY(),getWidth(),getHeight(),null);
	}
	public Card makeCopy(){
		return new ManaCard(this);
	}
	public void update(Board board) {}

	public ManaType getType() {return type;}
	public void setManaType(ManaType type) {this.type = type;}
}
