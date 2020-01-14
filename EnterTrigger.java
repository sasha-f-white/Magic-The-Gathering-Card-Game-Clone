/*
 Brendan Aucoin
 2018/04/20
 this interface is for any card that has an effect when you play it.
 */
package triggers;

import java.util.List;

import cards.Card;
import player.PlayerHandler;
import playing_panes.BoardGUI;

public interface EnterTrigger {
	public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List <Card>list);
	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler);
	
}
