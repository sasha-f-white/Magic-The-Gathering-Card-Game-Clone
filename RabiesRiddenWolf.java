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
import triggers.EnterTrigger;

public class RabiesRiddenWolf extends Unit implements EnterTrigger{
	/*every single card should have a constructor that takes in a x,y location and a width/height, and a player it belongs to.*/
	public RabiesRiddenWolf(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();//at the end of every constructor u call init() which just sets all the stats of the card.
	}
	/*copy constructor which you also need.*/
	public RabiesRiddenWolf(RabiesRiddenWolf fk) {
		super(fk);
		init();//again call init.
	}
	/*make sure it is private method becuase it never needs to be called anywhere else besides in the constructors.*/
	private void init() {
		setAttack(5);//set attack
		setHealth(4);//set health
		setDexCost(4);//set all the costs
		setId(ID.UNIT);//the id is either unit, spell, or building.
		setName("Rabies Ridden Wolf");//the name is just what the card is called.
		setSubType(SubType.ANIMAL);//the subtype is what archetype they are so like animal for dog or crown for king.
		setNameId(NameID.RABIES_RIDDEN_WOLF);//YOU MUST HAVE THIS! you set the nameId to NameID.WHATEVER. because NameID is an enum.
		image = Texture.cardSprites[getNameId().ordinal()];//YOU MUST HAVE THIS!!! this doesnt change between the different classes this line of
		//code is always the same.
	}
	/*returns a new FallenKing using the copy constructor so you pass this in as the parameter because your are just making a copy.*/
	public Card makeCopy() {
		return new RabiesRiddenWolf(this);
	}
	
	
	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		for (int i = 0; i < playerHandler.getCardsOnBoard().size(); i++) {
			Card card = playerHandler.getCardsOnBoard().get(i);
			if (card.getId() == ID.UNIT) {
				Unit unit = (Unit) card;
				if (unit.getSubType() == SubType.ANIMAL) {
				if(!unit.equals(this)) {
					unit.setAttack(unit.getAttack() + 1);
				}
				}
			}
		}
	}

	
	/*activateEnterTrigger and activateDeathTrigger are only for if you are making it so you can pick cards from your hand or board or wherever
	 activate enter trigger is what happens after you pick the cards from the selection gui
	 so if you wanna add to cards you pick from you hand to the board. you would set up everything in enterTrigger().
	 but you would add the cards you picked to the board in activate enter trigger.
	 */
	
	@Override
	public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list) {
		
		
	}
	
}