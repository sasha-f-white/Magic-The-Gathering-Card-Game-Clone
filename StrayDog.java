package cards;

import java.util.List;

import id.ID;
import id.NameID;
import id.SubType;
import player.Player;
import player.PlayerHandler;
import playing_panes.BoardGUI;
import playing_panes.SelectCardGUI;
import sprites.Texture;
import triggers.EnterTrigger;

public class StrayDog extends Unit implements EnterTrigger{
	//any card where you wanna select things as an enter trigger you must have a field as SelectCardGUI.
	/*and pass it in throught the constructors and do this.selectCardGUI = selectCardGUI.*/
	public StrayDog(float x,float y,int width,int height,Player player,SelectCardGUI selectCardGUI) {
		super(x,y,width,height,player,selectCardGUI);
		init(selectCardGUI);
		
	}
	private void init(SelectCardGUI selectCardGUI) {
		setAttack(3);
		setHealth(2);
		setConCost(2);
		setName("Stray Dog");
		setId(ID.UNIT);
		setSubType(SubType.ANIMAL);
		setNameId(NameID.STRAY_DOG);
		image = Texture.cardSprites[getNameId().ordinal()];
		this.selectCardGUI = selectCardGUI;
	}
	public StrayDog(StrayDog strayDog,SelectCardGUI selectCardGUI) {
		super(strayDog);
		init(selectCardGUI);
	}
	public Card makeCopy() {
		return new StrayDog(this,selectCardGUI);
	}
	public void enterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler) {
		music.playSoundMaxVolume("Stray dog.wav");
	}
	public void activateEnterTrigger(BoardGUI boardGUI,PlayerHandler playerHandler,PlayerHandler opponentHandler,List<Card>list) {
		
	}
	
}
