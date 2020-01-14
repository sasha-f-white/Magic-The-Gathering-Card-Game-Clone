package cards;

import java.util.List;

import id.ID;
import id.NameID;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class SilverMountain extends Building {
	public SilverMountain(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();
	}
	public SilverMountain(SilverMountain sm) {
		super(sm);
		init();
	}
	private void init() {
		//setIntCost(1);
		//setChrCost(1);
		setStrCost(1);
		setName("Silver Mountain");
		setId(ID.BUILDING);
		setNameId(NameID.THE_SILVER_MOUNTAIN);
		image = Texture.cardSprites[getNameId().ordinal()];
	}
	public Card makeCopy() {return new SilverMountain(this);}
	public void update(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		if(playerHandler.getCardsOnBoard().contains(this) == false) {return;}
		for(int i =0 ;i < playerHandler.getCardsOnBoard().size();i++) {
			Card c = playerHandler.getCardsOnBoard().get(i);
			if(getCardsAffected().contains(c) == false) {
				getCardsAffected().add(c);
				if(c instanceof Unit) {
					Unit unit = (Unit)c;
					unit.setHealth(unit.getHealth()+3);
				}
			}
		}
	}
	
	
	@Override
	public void activateDeathTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler,List<Card> list) {
		
	}
	@Override
	public void deathTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler) {
		for(int i =0; i < getCardsAffected().size();i++) {
			Card c = getCardsAffected().get(i);
			if(c instanceof Unit) {
				Unit unit = (Unit)c;
				unit.setHealth(unit.getHealth()-3);
			}
		}
	}
	
	
}
