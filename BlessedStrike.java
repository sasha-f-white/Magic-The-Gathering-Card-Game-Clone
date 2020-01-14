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

public class BlessedStrike extends Spell implements EnterTrigger
{
	
	public BlessedStrike(float x,float y,int width,int height,Player player, SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init(selectCardGUI);
	}
	public BlessedStrike(BlessedStrike fk, SelectCardGUI selectCardGUI) {
		super(fk);
		init(selectCardGUI);
		this.selectCardGUI = fk.selectCardGUI;
	}
	/*make sure it is private method becuase it never needs to be called anywhere else besides in the constructors.*/
	private void init(SelectCardGUI selectCardGUI) {
		setStrCost(3);
		setIntCost(1);
		setId(ID.SPELL);//the id is either unit, spell, or building.
		setName("Blessed Strike");//the name is just what the card is called.
		setNameId(NameID.BLESSED_STRIKE);//YOU MUST HAVE THIS! you set the nameId to NameID.WHATEVER. because NameID is an enum.
		this.selectCardGUI = selectCardGUI;
		image = Texture.cardSprites[getNameId().ordinal()];//YOU MUST HAVE THIS!!! this doesnt change between the different classes this line of
		//code is always the same.
	}

	public Card makeCopy() {
		return new BlessedStrike(this, selectCardGUI);
	}
	
	
	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		Card[] cards = new Card[boardGUI.getBoard().getBoard().size()];
		playerHandler.getPlayer().setHealth(playerHandler.getPlayer().getHealth() + 4);
		for(int i =0;i< cards.length; i++) 
		{
			cards[i] = boardGUI.getBoard().getBoard().get(i);//making the cards in the array equal to the cards in your hand.
		}
		selectCardGUI.set(1,Game.phase,this,Game.ENTER_TRIGGER,playerHandler,opponentHandler,cards);
		Game.phase = Phase.SELECT_CARD;
	}
	
	@Override
		public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list){
		
		for (int i = 0; i < list.size(); i++)
		{
			if(list.get(i) instanceof Unit) 
			{
			Unit unit = (Unit)list.get(i);
			unit.setHealth(unit.getHealth() - 6);
			}
		}							
		}	
}
