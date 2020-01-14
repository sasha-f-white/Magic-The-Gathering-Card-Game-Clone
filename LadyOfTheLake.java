package cards;

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

public class LadyOfTheLake extends Spell{
	public LadyOfTheLake(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init(selectCardGUI);
	}
	public LadyOfTheLake(LadyOfTheLake l) {
		super(l);
		init(selectCardGUI);
		this.selectCardGUI = l.selectCardGUI;
	}
	private void init(SelectCardGUI selectCardGUI) {
		//setConCost(2);
		//setChrCost(2);
		//setIntCost(1);
		setStrCost(1);
		setName("Lady of the Lake");
		setId(ID.SPELL);
		setNameId(NameID.LADY_OF_THE_LAKE);
		image = Texture.cardSprites[getNameId().ordinal()];
		this.selectCardGUI = selectCardGUI;
	}
	public Card makeCopy() {return new LadyOfTheLake(this);}
	
	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		Card [] cards = new Card[getPlayer().getHand().getHand().size()];
		for(int i =0; i < playerHandler.getPlayer().getHand().getHand().size();i++) {
				cards[i] = playerHandler.getPlayer().getHand().getHand().get(i).makeCopy();
		}
		/*for(int i =0; i < cards.length;i++) {
			System.out.println(cards[i].getX() + " , " + cards[i].getY());
		}*/

		selectCardGUI.set(1, Game.phase, this, Game.ENTER_TRIGGER, playerHandler, opponentHandler, cards);
		Game.phase = Phase.SELECT_CARD;
	}
	public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card> list) {
		for(int i =0; i < getPlayer().getHand().getHand().size();i++) {
			for(Card c : list) {
				if(c.equals(getPlayer().getHand().getHand().get(i))) {
					Card realCard = getPlayer().getHand().getHand().get(i);
					if(realCard.getStrCost() > 0) { realCard.setStrCost(c.getStrCost()-1); }
					if (realCard.getIntCost() > 0) { realCard.setIntCost(c.getIntCost()-1); }
					if (realCard.getChrCost() > 0) { realCard.setChrCost(c.getChrCost()-1); }
					if (realCard.getConCost() > 0) { realCard.setConCost(c.getConCost()-1); }
					if (realCard.getDexCost() > 0) { realCard.setDexCost(c.getDexCost()-1); }
				}
			}
		}
		/*for(Card c : list) {
			c.setChrCost(c.getChrCost()-1);
			c.setConCost(c.getConCost()-1);
			c.setDexCost(c.getDexCost()-1);
			c.setStrCost(c.getStrCost()-1);
			c.setIntCost(c.getIntCost()-1);
		}*/
		disposeSpell(boardGUI);
	}
}
