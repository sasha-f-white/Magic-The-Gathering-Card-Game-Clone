	package cards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Timer;

import game.Game;
import id.ID;
import id.NameID;
import phase.Phase;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class HiddenPlot extends Spell implements ActionListener{
	
	private LinkedList<Card> unused = new LinkedList<Card>();
	private Timer timer;
	private BoardGUI boardGUI;
	public HiddenPlot(float x,float y,int width,int height,Player player, SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init(selectCardGUI);
	}
	public HiddenPlot(HiddenPlot hiddenPlot,SelectCardGUI selectCardGUI) {
		super(hiddenPlot);
		init(selectCardGUI);
		this.selectCardGUI = hiddenPlot.selectCardGUI;
	}
	private void init(SelectCardGUI selectCardGUI) {
		setIntCost(1);
		setId(ID.SPELL);
		setName("Hidden Plot");
		setNameId(NameID.HIDDEN_PLOT);
		image = Texture.cardSprites[getNameId().ordinal()];
		this.selectCardGUI = selectCardGUI;
		timer = new Timer(2000,this);
	}

	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		this.boardGUI = boardGUI;
		Card cards[] = new Card[3];//getPlayer().drawManyCards(3,getPlayer().getCardDeck());
		for(int i =0;i < cards.length;i++) {
			unused.add(cards[i]);
			cards[i] = playerHandler.getPlayer().getCardDeck().getDeck().get(i);
		}
		selectCardGUI.set(0,Game.phase,this,Game.ENTER_TRIGGER,playerHandler,opponentHandler,cards);
		Game.phase = Phase.SELECT_CARD;//this has to be the last line of code in this method if you are doing a selecting enter trigger.
		}
	
	@Override
	public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list) {
		playerHandler.getHandGUI().drawCard();
		//
		//unused.remove(list.get(0));
		for(Card c : unused) {
			//getPlayer().getCardDeck().addBottomCard(c);
		}
		//disposeSpell(boardGUI);
		timer.start();
	}

	@Override
	public Card makeCopy() {
		return new HiddenPlot(this,selectCardGUI);
	}
	public void actionPerformed(ActionEvent e) {
		disposeSpell(boardGUI);
		timer.stop();
	}

}