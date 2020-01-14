package cards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import ai.Ai;
import game.Game;
import id.ID;
import id.NameID;
import music.Music;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;

public class DeepDarkPlace extends Spell implements ActionListener{
	private Game game;
	private BoardGUI boardGUI;
	private Timer timer;
	public static  Music actualMusicPlayer;
	public DeepDarkPlace(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI,Game game) {
		super(x,y,width,height,player,selectCardGUI);
		init(selectCardGUI,game);
		
	}
	public DeepDarkPlace(DeepDarkPlace d) {
		super(d);
		init(selectCardGUI,game);
		this.selectCardGUI = d.selectCardGUI;
		this.game = d.game;
	}
	private void init(SelectCardGUI selectCardGUI,Game game) {
		setStrCost(1);
		setId(ID.SPELL);
		setName("Deep Dark Place");
		setNameId(NameID.DEEP_DARK_PLACE);
		image = Texture.cardSprites[getNameId().ordinal()];
		this.selectCardGUI = selectCardGUI;
		this.game = game;
		setTime(3000);
		timer = new Timer(getTime(),this);
	}
	public DeepDarkPlace makeCopy() {return new DeepDarkPlace(this);}
	
	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		actualMusicPlayer = game.getMusic();
		this.boardGUI = boardGUI;
		if(getPlayer() instanceof Ai) {
			game.switchToOpponentsTurn();
		}
		else {
			game.switchToPlayersTurn();
		}
		actualMusicPlayer.stopSound();
		music.playSoundMaxVolume("deep dark place.wav");
		timer.start();
	}
	
	public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list) {
		
	}
	public void actionPerformed(ActionEvent e) {
		disposeSpell(boardGUI);
		actualMusicPlayer.startSound();
		timer.stop();
	}
}
