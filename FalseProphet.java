package cards;

import java.util.ArrayList;
import java.util.List;

import id.ID;
import id.NameID;
import id.SubType;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;
import triggers.EnterTrigger;

public class FalseProphet extends Unit implements EnterTrigger{
	
	public FalseProphet(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();
	}
	public FalseProphet(FalseProphet fk) {
		super(fk);
		init();
	}
	private void init() {
		setAttack(1);
		setHealth(4);
		setChrCost(3);
		setId(ID.UNIT);
		setName("False Prophet");
		setSubType(SubType.THIEF);
		setNameId(NameID.FALSE_PROPHET);
		image = Texture.cardSprites[getNameId().ordinal()];
	}
	public Card makeCopy() {
		return new FalseProphet(this);
	}
	
	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		List<Card>tempList = new ArrayList<Card>();
		for(int i =0; i < opponentHandler.getCardsOnBoard().size();i++) {
			Card c = opponentHandler.getCardsOnBoard().get(i);
			if(c instanceof Unit) {
				Unit unit = (Unit)c;
				if(unit.getAttack() <=2 && playerHandler.getCardsOnBoard().size()<7) {
						if(boardGUI.hasSpots(playerHandler.getSpots())) {
							tempList.add(unit);
							boardGUI.addCardToBoard(unit, playerHandler, opponentHandler, false);
							opponentHandler.getSpots()[i].setOpen(true);
						}
				}
			}
		}
		for(Card c: tempList) {
			//opponentList.remove(c);
			opponentHandler.getCardsOnBoard().remove(c);
		}
	}

	
	
	@Override
	public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list) {
		
		
	}
}

