/*
 Brendan Aucoin
 2018/04/20
 this class is the broad type of card that every card in the game inherits from.
 it holds data for what every card in the game has.
 */
package cards;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import file_loader.FileLoader;
import id.ID;
import id.NameID;
import mouse.ClickableObject;
import music.Music;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
/*you are allowed to click on a card thats why it extends CLickableObject*/
public abstract class Card extends ClickableObject{
	/*all the fields common throughout every single card in the game.*/
	private int strCost;
	private int dexCost;
	private int intCost;
	private int conCost;
	private int chrCost;
	private String name;
	private String effect;
	private ID id;
	protected BufferedImage image;
	private Player player;
	private NameID nameId;
	private boolean used;
	private FileLoader fileLoader;
	protected Music music;
	protected SelectCardGUI selectCardGUI;
	/*constructors*/
	public Card(){super();fileLoader = new FileLoader();
	music = new Music();}
	
	public Card(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height);
		this.player  = player;
		this.selectCardGUI = selectCardGUI;
		fileLoader = new FileLoader();
		music = new Music();
	}
	public Card(Card c) {
		this.strCost = c.strCost;
		this.dexCost = c.dexCost;
		this.intCost = c.intCost;
		this.conCost = c.conCost;
		this.chrCost = c.chrCost;
		this.name = c.name;
		this.id = c.id;
		this.image = c.image;
		this.player = c.player;
		this.effect = c.effect;
		this.selectCardGUI = c.selectCardGUI;
		this.fileLoader = c.fileLoader;
		this.music = c.music;
		this.setX(c.getX());
		this.setY(c.getY());
		this.setWidth(c.getWidth());
		this.setHeight(c.getHeight());
	}
	/*some cards update and some do not.*/
	public void update(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {}
	/*the reason this is not abstract is becuase you always just draw the card at the position its at u never do anything special with it.*/
	public void render(Graphics2D g) {
		g.drawImage(getImage(),(int)getX(),(int)getY(),getWidth(),getHeight(),null);
	}
	/*this abstract method makes a copy of the card if u ever need too*/
	public abstract Card makeCopy();
	/*you return the string representation of the card*/
	public String toString() {
		return (player.getName() + "'s " + name);
	}
	/*two cards are equal if they are in the same location and have the same cost values.*/
	public boolean equals(Card c) {
		if(this == c) {return true;}
		if(this.strCost == c.strCost && this.dexCost == c.dexCost &&this.chrCost == c.chrCost &&this.intCost == c.intCost && 
				this.conCost == c.conCost && this.name == c.name && this.id == c.id && 
				this.getX() == c.getX() && this.getY() == c.getY() &&this.getWidth() == c.getWidth()
				&&this.getHeight() == c.getHeight()) {return true;}
		
		else {
			return false;
		}
	}
	
	public int totalCost() {
		return strCost + intCost + dexCost+ chrCost + conCost;
	}
	/*getters and setters*/
	public int getStrCost() {return strCost;}
	public void setStrCost(int strCost) {this.strCost = strCost;}
	public int getDexCost() {return dexCost;}
	public void setDexCost(int dexCost) {this.dexCost = dexCost;}
	public int getIntCost() {return intCost;}
	public void setIntCost(int intCost) {this.intCost = intCost;}
	public int getConCost() {return conCost;}
	public void setConCost(int conCost) {this.conCost = conCost;}
	public int getChrCost() {return chrCost;}
	public void setChrCost(int chrCost) {this.chrCost = chrCost;}
	public ID getId() {return id;}
	public void setId(ID id) {this.id = id;}
	public BufferedImage getImage() {return image;}
	public void setImage(BufferedImage image) {this.image = image;}
	public Player getPlayer() {return new Player(player);}
	public void setPlayer(Player player) {this.player = new Player(player);}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public NameID getNameId() {return nameId;}
	public void setNameId(NameID nameId) {this.nameId = nameId;}
	public void setUsed(boolean used) {this.used = used;}
	public boolean isUsed() {return used;}
	public void setEffect(String effect) {this.effect = effect;}
	public String setEffect() {return effect;}
	public void setSelectCardGUI(SelectCardGUI selectCardGUI) {this.selectCardGUI = selectCardGUI;}
	public SelectCardGUI getSelectCardGUI() {return selectCardGUI;}
}

