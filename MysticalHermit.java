package cards;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import id.ID;
import id.NameID;
import id.SubType;
import phase.Phase;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;
import triggers.DeathTrigger;
import triggers.EnterTrigger;


public class MysticalHermit extends Unit implements EnterTrigger, DeathTrigger{
	
	/*every single card should have a constructor that takes in a x,y location and a width/height, and a player it belongs to.*/
	public MysticalHermit(float x,float y,int width,int height,Player player, SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init(selectCardGUI);//at the end of every constructor u call init() which just sets all the stats of the card.
	}
	/*copy constructor which you also need.*/
	public MysticalHermit(MysticalHermit fk, SelectCardGUI selectCardGUI) {
		super(fk);
		init(selectCardGUI);//again call init.
		this.selectCardGUI = fk.selectCardGUI;
	}
	/*make sure it is private method becuase it never needs to be called anywhere else besides in the constructors.*/
	private void init(SelectCardGUI selectCardGUI) {
		setAttack(2);//set attack
		setHealth(5);//set health
		setDexCost(3);//set all the costs
		setChrCost(2);
		setId(ID.UNIT);//the id is either unit, spell, or building.
		setName("Mystical Hermit");//the name is just what the card is called.
		setSubType(SubType.PEASANT);//the subtype is what archetype they are so like animal for dog or crown for king.
		setNameId(NameID.MYSTICAL_HERMIT);//YOU MUST HAVE THIS! you set the nameId to NameID.WHATEVER. because NameID is an enum.
		this.selectCardGUI = selectCardGUI;
		image = Texture.cardSprites[getNameId().ordinal()];//YOU MUST HAVE THIS!!! this doesnt change between the different classes this line of
		//code is always the same.
	}
	/*returns a new TowerGuard using the copy constructor so you pass this in as the parameter because your are just making a copy.*/
	public Card makeCopy() {
		return new MysticalHermit(this, selectCardGUI);
	}
	
	
	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		//Card[] cards = new Card[playerHandler.getPlayer().getCardDeck().getDeck().size()];
		ArrayList<Card>tempList = new ArrayList<Card>();
		for(int i =0;i< playerHandler.getPlayer().getCardDeck().getDeck().size(); i++) 
		{
			if (playerHandler.getPlayer().getCardDeck().getDeck().get(i) instanceof Unit) {
				Unit unit = (Unit) playerHandler.getPlayer().getCardDeck().getDeck().get(i);
				if (unit.getSubType() == SubType.ANIMAL) {
					//cards[i] = playerHandler.getPlayer().getCardDeck().getDeck().get(i);
					tempList.add(playerHandler.getPlayer().getCardDeck().getDeck().get(i));
				}
			}
		}
		Card[] cards = new Card[tempList.size()];
		for(int i =0;i< tempList.size(); i++) 
		{
			cards[i] = tempList.get(i);
		}
		selectCardGUI.set(2,Game.phase,this,Game.ENTER_TRIGGER,playerHandler,opponentHandler,cards);
		Game.phase = Phase.SELECT_CARD;
	}
	
	/*the triggers becuase the fallen king implements Enter and Death trigger.*/

	
	/*activateEnterTrigger and activateDeathTrigger are only for if you are making it so you can pick cards from your hand or board or wherever
	 activate enter trigger is what happens after you pick the cards from the selection gui
	 so if you wanna add to cards you pick from you hand to the board. you would set up everything in enterTrigger().
	 but you would add the cards you picked to the board in activate enter trigger.
	 */
	
	@Override
		public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list){
			for (int i = 0; i < list.size();i++) {
				playerHandler.getHandGUI().addCardToHand(list.get(i));
				getPlayer().getCardDeck().removeCard(list.get(i));
			}
		}
	@Override
	public void activateDeathTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler,
			List<Card> list) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deathTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler) {
		// TODO Auto-generated method stub
		for(int i =0;i< playerHandler.getCardsOnBoard().size(); i++) 
		{
			if (playerHandler.getCardsOnBoard().get(i) instanceof Unit) {
				Unit unit = (Unit) playerHandler.getCardsOnBoard().get(i);
				if (unit.getSubType() == SubType.ANIMAL) {
					unit.setHealth(unit.getHealth() + 2);
				}
			}
		}
	}		
}