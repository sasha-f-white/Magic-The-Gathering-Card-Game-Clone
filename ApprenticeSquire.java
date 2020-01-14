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

public class ApprenticeSquire extends Unit {
	
	private boolean curBoost = false;
	
	/*every single card should have a constructor that takes in a x,y location and a width/height, and a player it belongs to.*/
	public ApprenticeSquire(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();//at the end of every constructor u call init() which just sets all the stats of the card.
	}
	/*copy constructor which you also need.*/
	public ApprenticeSquire(ApprenticeSquire fk) {
		super(fk);
		init();//again call init.
	}
	/*make sure it is private method becuase it never needs to be called anywhere else besides in the constructors.*/
	private void init() {
		setAttack(3);//set attack
		setHealth(1);//set health
		setConCost(2);//set all the costs
		setId(ID.UNIT);//the id is either unit, spell, or building.
		setName("Apprentice Squire");//the name is just what the card is called.
		setSubType(SubType.CROWN);//the subtype is what archetype they are so like animal for dog or crown for king.
		setNameId(NameID.SQUIRE);//YOU MUST HAVE THIS! you set the nameId to NameID.WHATEVER. because NameID is an enum.
		image = Texture.cardSprites[getNameId().ordinal()];//YOU MUST HAVE THIS!!! this doesnt change between the different classes this line of
	}
	/*returns a new ExpertTracker using the copy constructor so you pass this in as the parameter because your are just making a copy.*/
	public Card makeCopy() {
		return new ApprenticeSquire(this);
	}
	
	/*activateEnterTrigger and activateDeathTrigger are only for if you are making it so you can pick cards from your hand or board or wherever
	 activate enter trigger is what happens after you pick the cards from the selection gui
	 so if you wanna add to cards you pick from you hand to the board. you would set up everything in enterTrigger().
	 but you would add the cards you picked to the board in activate enter trigger.
	 */
	
	@Override
	public void update(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler) {
		if (!playerHandler.getCardsOnBoard().contains(this)) { return;}
		boolean knight = false;
		for(int i =0 ;i < playerHandler.getCardsOnBoard().size();i++) {
			Card c = playerHandler.getCardsOnBoard().get(i);
				if(c instanceof Unit) {
					Unit unit = (Unit)c;
					if (unit.getSubType() == SubType.CROWN && !unit.equals(this)) { knight = true; }
			}
		}
		
		if (knight == true && curBoost == false) { 
			setHealth(getHealth() + 2);
			curBoost = true;
		} 
		if (knight == false && curBoost == true) {
			setHealth(getHealth() - 2);
			curBoost = false;
		} 
	}
	
	
}


