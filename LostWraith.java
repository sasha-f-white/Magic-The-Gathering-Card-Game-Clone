package cards;

import id.ID;
import id.NameID;
import id.SubType;
import player.Player;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class LostWraith extends Unit
{
	/*every single card should have a constructor that takes in a x,y location and a width/height, and a player it belongs to.*/
	public LostWraith(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();//at the end of every constructor u call init() which just sets all the stats of the card.
	}
	/*copy constructor which you also need.*/
	public LostWraith(LostWraith fk) {
		super(fk);
		init();//again call init.
	}
	/*make sure it is private method becuase it never needs to be called anywhere else besides in the constructors.*/
	private void init() {
		setAttack(4);//set attack
		setHealth(5);//set health
		setIntCost(4);
		setId(ID.UNIT);//the id is either unit, spell, or building.
		setName("Lost Wraith");//the name is just what the card is called.
		setSubType(SubType.PEASANT);//the subtype is what archetype they are so like animal for dog or crown for king.
		setNameId(NameID.LOST_WRAITH);//YOU MUST HAVE THIS! you set the nameId to NameID.WHATEVER. because NameID is an enum.
		image = Texture.cardSprites[getNameId().ordinal()];//YOU MUST HAVE THIS!!! this doesnt change between the different classes this line of
		//code is always the same.
	}
	/*returns a new CaptainGuard using the copy constructor so you pass this in as the parameter because your are just making a copy.*/
	public Card makeCopy() {
		return new LostWraith(this);
	}

}
