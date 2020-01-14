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
import triggers.DeathTrigger;

public class AngryFarmer extends Unit implements DeathTrigger
{
	/*every single card should have a constructor that takes in a x,y location and a width/height, and a player it belongs to.*/
	public AngryFarmer(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();//at the end of every constructor u call init() which just sets all the stats of the card.
	}
	/*copy constructor which you also need.*/
	public AngryFarmer(AngryFarmer fk) {
		super(fk);
		init();//again call init.
	}
	/*make sure it is private method becuase it never needs to be called anywhere else besides in the constructors.*/
	private void init() {
		setAttack(2);//set attack
		setHealth(1);//set health
		setStrCost(2);//set all the costs
		setId(ID.UNIT);//the id is either unit, spell, or building.
		setName("Angry Farmer");//the name is just what the card is called.
		setSubType(SubType.PEASANT);//the subtype is what archetype they are so like animal for dog or crown for king.
		setNameId(NameID.ANGRY_FARMER);//YOU MUST HAVE THIS! you set the nameId to NameID.WHATEVER. because NameID is an enum.
		image = Texture.cardSprites[getNameId().ordinal()];//YOU MUST HAVE THIS!!! this doesnt change between the different classes this line of
		//code is always the same.
	}
	/*returns a new CaptainGuard using the copy constructor so you pass this in as the parameter because your are just making a copy.*/
	public Card makeCopy() {
		return new AngryFarmer(this);
	}
	@Override
	public void activateDeathTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler,
			List<Card> list) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deathTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler) {
		for (int i = 0; i < playerHandler.getCardsOnBoard().size(); i++) {
			Card card = playerHandler.getCardsOnBoard().get(i);
			
			if (card.getId() == ID.UNIT) {
				Unit unit = (Unit) card;
				if (unit.getSubType() == SubType.PEASANT) {
				if(!unit.equals(this)) {
					unit.setAttack(unit.getAttack() + 1);
				}
				}
			}
		}
	}
	
	

}
