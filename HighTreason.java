package cards;

import java.util.LinkedList;
import java.util.List;

import game.Game;
import id.ID;
import id.NameID;
import phase.Phase;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class HighTreason extends Spell{
	
	private LinkedList<Card> unused = new LinkedList<Card>();
	public HighTreason(float x,float y,int width,int height,Player player, SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init(selectCardGUI);
	}
	public HighTreason(HighTreason hiddenPlot,SelectCardGUI selectCardGUI) {
		super(hiddenPlot);
		init(selectCardGUI);
		this.selectCardGUI = hiddenPlot.selectCardGUI;
	}
	private void init(SelectCardGUI selectCardGUI) {
		setStrCost(2);
		setId(ID.SPELL);
		setName("High Treason");
		setNameId(NameID.HIGH_TREASON);
		image = Texture.cardSprites[getNameId().ordinal()];
		this.selectCardGUI = selectCardGUI;
	}

	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		Card[] cards = new Card[boardGUI.getBoard().getBoard().size()];
		for(int i =0;i< cards.length; i++) 
		{
			if (boardGUI.getBoard().getBoard().get(i) instanceof Unit) {
				Unit unit = (Unit) boardGUI.getBoard().getBoard().get(i);
				cards[i] = unit;
			}
			//making the cards in the array equal to the cards in your hand.
		}
		
		selectCardGUI.set(1,Game.phase,this,Game.ENTER_TRIGGER,playerHandler,opponentHandler,cards);
		Game.phase = Phase.SELECT_CARD;//this has to be the last line of code in this method if you are doing a selecting enter trigger.
		}
	
	@Override
	public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list) {
		for(Card c : list) {
			if(c instanceof Unit) {
				Unit unit = (Unit)c;
				unit.setHealth(unit.getHealth()-4);
			}
		}
		disposeSpell(boardGUI);
	}

	@Override
	public Card makeCopy() {
		return new HighTreason(this,selectCardGUI);
	}

}