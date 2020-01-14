package playing_panes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import board.Board;
import game.Game;
import phase.Phase;

public class SwitchPhaseButtons extends InteractionPane{
	private Rectangle battlePhaseBounds;
	private Rectangle endPhaseBounds;
	private Board board;
	private Game game;
	public SwitchPhaseButtons(Game game,BoardGUI boardGUI,Board board) {
		super(new Rectangle(
				boardGUI.getBounds().x,boardGUI.getBounds().y + boardGUI.getBounds().height/2 - boardGUI.getBounds().height/8,boardGUI.getBounds().width,(int) (boardGUI.getBounds().height/4.5)
				));
		
		battlePhaseBounds = new Rectangle(
				(int) (boardGUI.getBounds().x + boardGUI.getBounds().width - boardGUI.getBounds().width/2.3),
				boardGUI.getBounds().y + boardGUI.getBounds().height/2 - boardGUI.getBounds().height/24,
				boardGUI.getBounds().width/6
				,boardGUI.getBounds().height/12
				);
		
		endPhaseBounds = new Rectangle(
				(int) (boardGUI.getBounds().x + boardGUI.getBounds().width - boardGUI.getBounds().width/2.3) + 25 + battlePhaseBounds.width,
				boardGUI.getBounds().y + boardGUI.getBounds().height/2 - boardGUI.getBounds().height/24,
				boardGUI.getBounds().width/6
				,boardGUI.getBounds().height/12
				);
		this.board = board;
		this.game = game;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics2D g) {
		if(!(Game.phase == Phase.PLAY_CARD)) {g.setColor(Color.GRAY.brighter());}
		else {
		changeColour(g,battlePhaseBounds,new Color(192,192,192),new Color(255,255,224));}
		g.fillRoundRect(battlePhaseBounds.x,battlePhaseBounds.y,battlePhaseBounds.width,battlePhaseBounds.height,125,125);
		
		
		if(Game.phase == Phase.PLAY_ON_BOARD) {changeColour(g,endPhaseBounds,new Color(192,192,192),new Color(255,255,224));}
		else {g.setColor(Color.GRAY.brighter());}
		g.fillRoundRect(endPhaseBounds.x,endPhaseBounds.y, endPhaseBounds.width, endPhaseBounds.height, 125, 125);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial",Font.PLAIN,20));
		g.drawString("Battle Phase",(int) (battlePhaseBounds.x +battlePhaseBounds.width/2 - g.getFont().getSize()*2.5),battlePhaseBounds.y + battlePhaseBounds.height/2 + g.getFont().getSize()/4);
		g.drawString("End Phase",(int) (endPhaseBounds.x +endPhaseBounds.width/2 - g.getFont().getSize()*2.5),endPhaseBounds.y + endPhaseBounds.height/2 + g.getFont().getSize()/4);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(mouseOver(battlePhaseBounds)) {
			if(Game.phase == Phase.PLAY_CARD) {Game.phase = Phase.PLAY_ON_BOARD;}
		}
		if(Game.phase == Phase.PLAY_ON_BOARD) {
		 if(mouseOver(endPhaseBounds)) {
				game.switchToOpponentsTurn();
			}
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
	}
}
