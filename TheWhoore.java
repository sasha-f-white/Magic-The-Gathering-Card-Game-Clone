package cards;

import id.ID;
import id.NameID;
import id.SubType;
import player.Player;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class TheWhoore extends Unit
{
	public TheWhoore(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();//at the end of every constructor u call init() which just sets all the stats of the card.
	}
	/*copy constructor which you also need.*/
	public TheWhoore(TheWhoore fk) {
		super(fk);
		init();//again call init.
		this.selectCardGUI = fk.selectCardGUI;
	}
	/*make sure it is private method becuase it never needs to be called anywhere else besides in the constructors.*/
	private void init() {
		setAttack(2);//set attack
		setHealth(3);//set health
		setChrCost(2);//set all the costs
		setId(ID.UNIT);//the id is either unit, spell, or building.
		setName("The Whoore");//the name is just what the card is called.
		setSubType(SubType.THIEF);//the subtype is what archetype they are so like animal for dog or crown for king.
		setNameId(NameID.WHORE);//YOU MUST HAVE THIS! you set the nameId to NameID.WHATEVER. because NameID is an enum.
		image = Texture.cardSprites[getNameId().ordinal()];//YOU MUST HAVE THIS!!! this doesnt change between the different classes this line of
	}
	public Card makeCopy() {
		return new TheWhoore(this);
	}
}
