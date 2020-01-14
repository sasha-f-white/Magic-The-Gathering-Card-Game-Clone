package cards;

import java.util.List;

import id.ID;
import id.NameID;
import id.SubType;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;
import triggers.EnterTrigger;

public class TheMuscle extends Unit implements EnterTrigger{
	/*every single card should have a constructor that takes in a x,y location and a width/height, and a player it belongs to.*/
	public TheMuscle(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();//at the end of every constructor u call init() which just sets all the stats of the card.
	}
	/*copy constructor which you also need.*/
	public TheMuscle(TheMuscle fk) {
		super(fk);
		init();//again call init.
	}
	/*make sure it is private method becuase it never needs to be called anywhere else besides in the constructors.*/
	private void init() {
		setAttack(2);//set attack
		setHealth(7);//set health
		setStrCost(2);//set all the costs
		setDexCost(2);
		setId(ID.UNIT);//the id is either unit, spell, or building.
		setName("The Muscle");//the name is just what the card is called.
		setSubType(SubType.THIEF);//the subtype is what archetype they are so like animal for dog or crown for king.
		setNameId(NameID.THE_MUSCLE);//YOU MUST HAVE THIS! you set the nameId to NameID.WHATEVER. because NameID is an enum.
		image = Texture.cardSprites[getNameId().ordinal()];//YOU MUST HAVE THIS!!! this doesnt change between the different classes this line of
		//code is always the same.
	}
	/*returns a new TheMuscle using the copy constructor so you pass this in as the parameter because your are just making a copy.*/
	public Card makeCopy() {
		return new TheMuscle(this);
	}
	
	
	/*the triggers becuase the fallen king implements Enter and Death trigger.*/
	@Override
	/*it takes in a BoardGUI, a List of the current player cards, and the list of the opponent cards*/
	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		for (int i = 0; i < playerHandler.getCardsOnBoard().size(); i++) {
			Card card = playerHandler.getCardsOnBoard().get(i);
			
			if (card.getId() == ID.UNIT) {
				Unit unit = (Unit) card;
				if (unit.getSubType() == SubType.THIEF) {
				if(!unit.equals(this)) {
					unit.setAttack(unit.getAttack() + 2);
					unit.setHealth(unit.getHealth() + 1);
				}
				}
			}
		}
	}

	@Override
	public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list) {
		
		
	}
}
