package cards;

import java.util.ArrayList;
import java.util.List;

import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import triggers.DeathTrigger;

public abstract class Building extends Card implements DeathTrigger{
	private List<Card>cardsAffected;
	public Building(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		cardsAffected = new ArrayList<Card>();
	}
	public Building(Building b) {
		super(b);
		cardsAffected = new ArrayList<Card>();
	}
	public abstract void update(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler);
	
	public List<Card> getCardsAffected() {
		return cardsAffected;
	}
	public void setCardsAffected(List<Card> cardsAffected) {
		this.cardsAffected = cardsAffected;
	}
	
	
}
