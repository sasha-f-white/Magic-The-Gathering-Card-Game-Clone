package cards;

import java.util.List;

import id.ID;
import id.NameID;
import id.SubType;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.HandGUI;
import playing_panes.OpponentHandGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;
import triggers.EnterTrigger;

public class Executioner extends Unit implements EnterTrigger{
	private HandGUI handGUI;
	private OpponentHandGUI opponentHandGUI;
	public Executioner(float x,float y,int width,int height,Player player, HandGUI handGUI,OpponentHandGUI opponentHandGUI,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init(handGUI,opponentHandGUI);
	}
	/*copy constructor which you also need.*/
	public Executioner(Executioner fk) {
		super(fk);
		init(handGUI,opponentHandGUI);//again call init.
	}
	/*make sure it is private method becuase it never needs to be called anywhere else besides in the constructors.*/
	private void init(HandGUI handGUI,OpponentHandGUI opponentHandGUI) {
		setAttack(4);//set attack
		setHealth(2);//set health
		setStrCost(1);//set all the costs
		setChrCost(2);
		setId(ID.UNIT);//the id is either unit, spell, or building.
		setName("Executioner");//the name is just what the card is called.
		setSubType(SubType.CROWN);//the subtype is what archetype they are so like animal for dog or crown for king.
		setNameId(NameID.EXECUTIONER);//YOU MUST HAVE THIS! you set the nameId to NameID.WHATEVER. because NameID is an enum.
		image = Texture.cardSprites[getNameId().ordinal()];//YOU MUST HAVE THIS!!! this doesnt change between the different classes this line of
		this.handGUI =handGUI;
		this.opponentHandGUI = opponentHandGUI;
		//code is always the same.
	}
	/*returns a new Executioner using the copy constructor so you pass this in as the parameter because your are just making a copy.*/
	public Card makeCopy() {
		return new Executioner(this);
	}
	
	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		int deadCards = 0;
		
		for (int i = 0; i < playerHandler.getCardsOnBoard().size(); i++) {
			Card card = playerHandler.getCardsOnBoard().get(i);

			if (card.getId() == ID.UNIT && card.getNameId() != NameID.EXECUTIONER) {
				Unit unit = (Unit) card;
				if (unit.getHealth() == 1) {
					deadCards++;
				}
				unit.setHealth(unit.getHealth() - 1);
				unit.setAttack(unit.getAttack() - 1);
			}
		}
		for(int i =0; i < deadCards;i++) {
			playerHandler.getHandGUI().drawCard();
		}
	}
	
	public void enterTrigger(BoardGUI boardGUI,List<Card> playerList,List<Card>opponentList) {
		/*player cards isnt always necessarily always brendans cards it is based on who played the card.
		 so if the AI plays the card the playerList is the ai list, and the opponent list would be brendans cards on the board.
		 
		 but if brendan plays the card the player list is brendans board, and the opponent list is the AI's cards on the board.
		 */
		int deadCards = 0;
		
		for (int i = 0; i < playerList.size(); i++) {
			Card card = playerList.get(i);

			if (card.getId() == ID.UNIT && card.getNameId() != NameID.EXECUTIONER) {
				Unit unit = (Unit) card;
				if (unit.getHealth() == 1) {
					deadCards++;
				}
				unit.setHealth(unit.getHealth() - 1);
				unit.setAttack(unit.getAttack() - 1);
			}
		}
		
		for (int i = 0; i < deadCards; i++) {
			
			//if(getPlayer() instanceof Ai) {opponentHandGUI.drawCard();}
			//else{handGUI.drawCard();}
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
