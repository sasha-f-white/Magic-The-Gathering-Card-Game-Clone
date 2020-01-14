	package cards;

import java.util.ArrayList;
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

public class ProtestersOil extends Spell{
	public ProtestersOil(float x,float y,int width,int height,Player player, SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init(selectCardGUI);
	}
	public ProtestersOil(ProtestersOil protestorsOil,SelectCardGUI selectCardGUI) {
		super(protestorsOil);
		init(selectCardGUI);
		this.selectCardGUI = protestorsOil.selectCardGUI;
	}
	private void init(SelectCardGUI selectCardGUI) {
		setStrCost(1);
		setId(ID.SPELL);
		setName("Protesters Oil");
		setNameId(NameID.PROTESTERS_OIL);
		image = Texture.cardSprites[getNameId().ordinal()];
		this.selectCardGUI = selectCardGUI;
	}

	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		try {
		Card cards[];
		//ArrayList<Card>cardList = new ArrayList<Card>();
		ArrayList<Card>buildingList = new ArrayList<Card>();
		for(int i =0; i < boardGUI.getBoard().getBoard().size(); i++) {
			Card c = boardGUI.getBoard().getBoard().get(i);
			if(c instanceof Building) {
				buildingList.add(c);
			}
		}
		cards = new Card[buildingList.size()];
		for(int i=0; i < buildingList.size();i++) {
			cards[i] = buildingList.get(i);
		}
		
		selectCardGUI.set(1,Game.phase,this,Game.ENTER_TRIGGER,playerHandler,opponentHandler,cards);
		Game.phase = Phase.SELECT_CARD;
		}catch(Exception e) {}
		}
	
	@Override
	public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list) {
		for(int i =0; i < list.size();i++) {
			Building b = (Building)list.get(0);
			boardGUI.removeBuilding(b, playerHandler, opponentHandler);
		}
		disposeSpell(boardGUI);
	}

	@Override
	public Card makeCopy() {
		return new ProtestersOil(this,selectCardGUI);
	}

}