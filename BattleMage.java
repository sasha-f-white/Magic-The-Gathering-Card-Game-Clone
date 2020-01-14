package cards;

import id.ID;
import id.NameID;
import id.SubType;
import player.Player;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class BattleMage extends Unit
{
	public BattleMage(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();//at the end of every constructor u call init() which just sets all the stats of the card.
	}
	/*copy constructor which you also need.*/
	public BattleMage(BattleMage fk) {
		super(fk);
		init();//again call init.
		this.selectCardGUI = fk.selectCardGUI;
	}
	/*make sure it is private method becuase it never needs to be called anywhere else besides in the constructors.*/
	private void init() {
		setAttack(6);//set attack
		setHealth(6);//set health
		setStrCost(2);//set all the costs
		setIntCost(3);
		setId(ID.UNIT);//the id is either unit, spell, or building.
		setName("Battle Mage");//the name is just what the card is called.
		setSubType(SubType.PEASANT);//the subtype is what archetype they are so like animal for dog or crown for king.
		setNameId(NameID.BATTLE_MAGE);//YOU MUST HAVE THIS! you set the nameId to NameID.WHATEVER. because NameID is an enum.
		image = Texture.cardSprites[getNameId().ordinal()];//YOU MUST HAVE THIS!!! this doesnt change between the different classes this line of
	}
	public Card makeCopy() {
		return new BattleMage(this);
	}

}

