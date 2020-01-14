package cards;

import java.util.List;

import id.ID;
import id.NameID;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class TempleOfTheKing extends Building{
	public TempleOfTheKing(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();
	}
	public TempleOfTheKing(TempleOfTheKing t) {
		super(t);
		init();
		this.selectCardGUI =t.selectCardGUI;
	}
	private void init() {
		setStrCost(2);
		setId(ID.BUILDING);
		setName("Temple of the King");
		setNameId(NameID.TEMPLE_OF_THE_KING);
		image = Texture.cardSprites[getNameId().ordinal()];
	}
	public void update(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		if(playerHandler.getCardsOnBoard().contains(this) == false) {return;}
		for(int i =0 ;i < playerHandler.getCardsOnBoard().size();i++) {
			Card c = playerHandler.getCardsOnBoard().get(i);
			if(getCardsAffected().contains(c) == false) {
				getCardsAffected().add(c);
				if(c instanceof Unit) {
					Unit unit = (Unit)c;
					unit.setAttack(unit.getAttack() *2);
				}
			}
		}
	}
	
	public void activateDeathTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler,List<Card> list) {
		
	}
	@Override
	public void deathTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler) {
		for(int i =0; i < getCardsAffected().size();i++) {
			Card c = getCardsAffected().get(i);
			if(c instanceof Unit) {
				Unit unit = (Unit)c;
				unit.setAttack(unit.getAttack()/2);
			}
		}
	}
	@Override
	public Card makeCopy() {
		return new TempleOfTheKing(this);
	}
}
