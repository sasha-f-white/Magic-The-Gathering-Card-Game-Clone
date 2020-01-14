package cards;

import java.util.List;

import id.ID;
import id.NameID;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class CastleBarracks extends Building{
	public CastleBarracks(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init();
	}
	public CastleBarracks(CastleBarracks b) {
		super(b);
		init();
	}
	private void init() {
		setConCost(3);//set all the costs
		setId(ID.BUILDING);//the id is either unit, spell, or building.
		setName("Castle Barracks");//the name is just what the card is called.
		setNameId(NameID.CASTLE_BARRACKS);//YOU MUST HAVE THIS! you set the nameId to NameID.WHATEVER. because NameID is an enum.
		image = Texture.cardSprites[getNameId().ordinal()];//YOU MUST HAVE THIS!!! this doesnt change between the different classes this line of
	}
	public void update(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		for(int i =0 ;i < boardGUI.getBoard().getBoard().size();i++) {
			Card c = boardGUI.getBoard().getBoard().get(i);
			if(getCardsAffected().contains(c) == false) {
				getCardsAffected().add(c);
				if(c instanceof Unit) {
					Unit unit = (Unit)c;
					unit.setAttack(unit.getAttack() +2);
				}
			}
		}
		
	}
	public void deathTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		for(int i =0; i < getCardsAffected().size();i++) {
			Card c = getCardsAffected().get(i);
			if(c instanceof Unit) {
				Unit unit = (Unit)c;
				unit.setAttack(unit.getAttack()-2);
			}
		}
	}
	@Override
	public void activateDeathTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler,List<Card> list) {
		
	}
	public Card makeCopy() {
		return new CastleBarracks(this);
	}
}
