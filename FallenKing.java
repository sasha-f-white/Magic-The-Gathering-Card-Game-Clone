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

public class FallenKing extends Unit implements DeathTrigger, EnterTrigger{
	/*every single card should have a constructor that takes in a x,y location and a width/height, and a player it belongs to.*/
	public FallenKing(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();//at the end of every constructor u call init() which just sets all the stats of the card.
	}
	/*copy constructor which you also need.*/
	public FallenKing(FallenKing fk) {
		super(fk);
		init();//again call init.
	}
	/*make sure it is private method becuase it never needs to be called anywhere else besides in the constructors.*/
	private void init() {
		setAttack(3);//set attack
		setHealth(2);//set health
		setIntCost(2);//set all the costs
		setChrCost(3);
		setId(ID.UNIT);//the id is either unit, spell, or building.
		setName("Fallen King");//the name is just what the card is called.
		setSubType(SubType.CROWN);//the subtype is what archetype they are so like animal for dog or crown for king.
		setNameId(NameID.FALLEN_KING);//YOU MUST HAVE THIS! you set the nameId to NameID.WHATEVER. because NameID is an enum.
		image = Texture.cardSprites[getNameId().ordinal()];//YOU MUST HAVE THIS!!! this doesnt change between the different classes this line of
		//code is always the same.
	}
	/*returns a new FallenKing using the copy constructor so you pass this in as the parameter because your are just making a copy.*/
	public Card makeCopy() {
		return new FallenKing(this);
	}
	
	
	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		setAttack(getAttack() + 2*(playerHandler.getCardsOnBoard().size()-1));
		setHealth(getHealth() + 2*(playerHandler.getCardsOnBoard().size()-1));
	}
	
	public void enterTrigger(BoardGUI boardGUI,List<Card> playerList,List<Card>opponentList) {
		/*player cards isnt always necessarily always brendans cards it is based on who played the card.
		 so if the AI plays the card the playerList is the ai list, and the opponent list would be brendans cards on the board.
		 
		 but if brendan plays the card the player list is brendans board, and the opponent list is the AI's cards on the board.
		 */
		setAttack(getAttack() + 2*(playerList.size()-1));
		setHealth(getHealth() + 2*(playerList.size()-1));
	}

	
	public void deathTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		for (int i = 0; i < boardGUI.getBoard().getBoard().size(); i++) {
			Card card = boardGUI.getBoard().getBoard().get(i);

			if (card.getId() == ID.UNIT) {
				Unit unit = (Unit) card;
				unit.setAttack(unit.getAttack() - 4);
				unit.setHealth(unit.getHealth() - 4);
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
	@Override
	public void activateDeathTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list) {
		
		
	}
	
	
}

