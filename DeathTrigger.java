/*
 Brendan Aucoin
 2018/04/20
 this interface is for any card that has an effect when they die.
 */
package triggers;

import java.util.List;

import cards.Card;
import player.PlayerHandler;
import playing_panes.BoardGUI;

public interface DeathTrigger {
	public void activateDeathTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list);
	public void deathTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler);
}
