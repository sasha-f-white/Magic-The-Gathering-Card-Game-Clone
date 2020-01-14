package cards;

import java.util.List;

import game.Game;
import id.ID;
import id.ManaType;
import id.NameID;
import phase.Phase;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class RainbowEyes extends Spell {
	public RainbowEyes(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init(selectCardGUI);
	}
	public RainbowEyes(RainbowEyes s,SelectCardGUI selectCardGUI) {
		super(s);
		init(selectCardGUI);
		this.selectCardGUI  = s.selectCardGUI;
	}
	private void init(SelectCardGUI selectCardGUI) {
		setId(ID.SPELL);
		setName("Rainbow Eyes");
		setNameId(NameID.RAINBOW_EYES);
		image = Texture.cardSprites[getNameId().ordinal()];
		this.selectCardGUI = selectCardGUI;
	}
	@Override
	public void activateEnterTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler,List<Card> list) {
		for(Card c : list) {
			if(c instanceof ManaCard) {
				ManaCard manaCard=  (ManaCard)c;
				if(manaCard.getType() == ManaType.STR) {
					playerHandler.getPlayer().setMaxStrMana(playerHandler.getPlayer().getMaxStrMana()+1);
					playerHandler.getPlayer().setNumStrMana(playerHandler.getPlayer().getNumStrMana()+1);
				}
				else if(manaCard.getType() == ManaType.INT) {
					playerHandler.getPlayer().setMaxIntMana(playerHandler.getPlayer().getMaxIntMana()+1);
					playerHandler.getPlayer().setNumIntMana(playerHandler.getPlayer().getNumIntMana()+1);
				}
				else if(manaCard.getType() == ManaType.CON) {
					playerHandler.getPlayer().setMaxConMana(playerHandler.getPlayer().getMaxConMana()+1);
					playerHandler.getPlayer().setNumConMana(playerHandler.getPlayer().getNumConMana()+1);
				}
				else if(manaCard.getType() == ManaType.CHR) {
					playerHandler.getPlayer().setMaxChrMana(playerHandler.getPlayer().getMaxChrMana()+1);
					playerHandler.getPlayer().setNumChrMana(playerHandler.getPlayer().getNumChrMana()+1);
				}
				else if(manaCard.getType() == ManaType.DEX) {
					playerHandler.getPlayer().setMaxDexMana(playerHandler.getPlayer().getMaxDexMana()+1);
					playerHandler.getPlayer().setNumDexMana(playerHandler.getPlayer().getNumDexMana()+1);
				}
			}
		}
		disposeSpell(boardGUI);
	}
	public void enterTrigger(BoardGUI boardGUI, PlayerHandler playerHandler, PlayerHandler opponentHandler) {
		Card[] cards = new Card[5];
		ManaCard str = new ManaCard(0,0,0,0,getPlayer(),ManaType.STR,selectCardGUI);
		ManaCard inte = new ManaCard(0,0,0,0,getPlayer(),ManaType.INT,selectCardGUI);
		ManaCard con = new ManaCard(0,0,0,0,getPlayer(),ManaType.CON,selectCardGUI);
		ManaCard chr = new ManaCard(0,0,0,0,getPlayer(),ManaType.CHR,selectCardGUI);
		ManaCard dex = new ManaCard(0,0,0,0,getPlayer(),ManaType.DEX,selectCardGUI);
		cards[0] = str;cards[1] = inte;cards[2] = con;cards[3] = chr;cards[4] = dex;
		selectCardGUI.set(1, Game.phase, this, Game.ENTER_TRIGGER, playerHandler, opponentHandler, cards);
		Game.phase = Phase.SELECT_CARD;
	}
	public Card makeCopy() {
		return new RainbowEyes(this,selectCardGUI);
	}
	
	
}
