package cards;

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
import triggers.EnterTrigger;

public class AbusivePatrolman extends Unit implements EnterTrigger
{
	
	/*every single card should have a constructor that takes in a x,y location and a width/height, and a player it belongs to.*/
	public AbusivePatrolman(float x,float y,int width,int height,Player player, SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init(selectCardGUI);//at the end of every constructor u call init() which just sets all the stats of the card.
	}
	/*copy constructor which you also need.*/
	public AbusivePatrolman(AbusivePatrolman fk,SelectCardGUI selectCardGUI) {
		super(fk);
		init(selectCardGUI);//again call init.
		this.selectCardGUI = fk.selectCardGUI;
	}
	/*make sure it is private method becuase it never needs to be called anywhere else besides in the constructors.*/
	private void init(SelectCardGUI selectCardGUI) {
		setAttack(3);//set attack
		setHealth(1);//set health
		setStrCost(1);//set all the costs
		setId(ID.UNIT);//the id is either unit, spell, or building.
		setName("Abusive Patrolman");//the name is just what the card is called.
		setSubType(SubType.CROWN);//the subtype is what archetype they are so like animal for dog or crown for king.
		setNameId(NameID.ABUSIVE_PATROLMAN);//YOU MUST HAVE THIS! you set the nameId to NameID.WHATEVER. because NameID is an enum.
		image = Texture.cardSprites[getNameId().ordinal()];//YOU MUST HAVE THIS!!! this doesnt change between the different classes this line of
		this.selectCardGUI = selectCardGUI;
	}
	/*returns a new ExpertTracker using the copy constructor so you pass this in as the parameter because your are just making a copy.*/
	public Card makeCopy() {
		return new AbusivePatrolman(this, selectCardGUI);
	}
	@Override
	public void activateEnterTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler,
			List<Card> list) {

		for(int i =0; i < opponentHandler.getPlayer().getHand().getHand().size();i++) {
			for(Card c : list) {
				if(c.equals(opponentHandler.getPlayer().getHand().getHand().get(i))) {
					Card realCard = opponentHandler.getPlayer().getHand().getHand().get(i);
					opponentHandler.getHandGUI().removeCardFromHand(realCard);
				}
			}
		}
	}
	@Override
	public void enterTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler) {
		
		Card[] cards = new Card[opponentHandler.getPlayer().getHand().getHand().size()];
		for(int i =0;i< cards.length; i++) 
		{
			cards[i] = opponentHandler.getPlayer().getHand().getHand().get(i).makeCopy();//making the cards in the array equal to the cards in your hand.
		}
		selectCardGUI.set(1,Game.phase,this,Game.ENTER_TRIGGER,playerHandler,opponentHandler,cards);
		Game.phase = Phase.SELECT_CARD;
	}
	
	

}
