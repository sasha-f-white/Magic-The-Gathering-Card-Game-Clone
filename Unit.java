/*
 Sasha White and Aidan Dowdall
 2018/04/20
 this class is the unit class that holds an attack, health and everything else that
 every unit in the game has.
 every unit in the game inherits from this class.
 */
package cards;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.List;

import id.SubType;
import player.Player;
import playing_panes.SelectCardGUI;

public abstract class Unit extends Card{
	/*every unit holds an attack,health,weather its been used and a subtype for id's*/
	private int attack;
	private int health;
	private boolean used;
	private SubType subType;
	/*constructors*/
	public Unit(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
	}
	public Unit(Unit unit) {
		super(unit);
		this.attack = unit.attack;
		this.health = unit.health;
		this.used = unit.used;
		this.subType = unit.subType;
	}
	/*if the health is <0 remove the card*/
	public void die(List<Card>list) {
		list.remove(this);
	}
	/*getters and setters*/
	public int getAttack() {return attack;}
	public void setAttack(int attack) {this.attack = attack;}
	public int getHealth() {return health;}
	public void setHealth(int health) {this.health = health;}
	public boolean isUsed() {return used;}
	public void setUsed(boolean used) {this.used = used;}

	public SubType getSubType() {return subType;}

	public void setSubType(SubType subType) {this.subType = subType;}
	
	
	
}	
