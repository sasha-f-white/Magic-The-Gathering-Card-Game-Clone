package playing_panes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import game.Game;
import gui.Display;
import phase.Phase;

public class SwitchTurnGUI implements ActionListener{
	private String whosTurn;
	private Timer timer;
	private int x,velX;
	private boolean moving;
	private boolean firstTime;
	private Phase nextPhase;
	public SwitchTurnGUI(String whosTurn) {
		this.whosTurn = whosTurn;
		timer = new Timer(800,this);
		x =Display.WIDTH;
		velX = -15;
		moving = false;
		firstTime = true;
	}
	
	public void render(Graphics2D g) {
		g.setFont(new Font("Arial",Font.PLAIN,100));
		g.setColor(Color.BLACK);
		g.drawString(whosTurn + "'s turn",x , (int) (Display.HEIGHT/2 - g.getFont().getSize()/4.5));
		if(moving){x += velX;}
		if(firstTime) {
			if(x <= (Display.WIDTH/2 - g.getFont().getSize()*1.65 - whosTurn.length()*2)) {
				moving = false;
				timer.start();
				firstTime = false;
			}
		}
		if(x + g.getFont().getSize()*8 <=0) {
			moving = false;
			x = Display.WIDTH;
			Game.phase = nextPhase;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		moving = true;
		timer.stop();
	}
	public void setWhosTurn(String whosTurn) {this.whosTurn  = whosTurn;}
	public String getWhosTurn() {return whosTurn;}
	public void setMoving(boolean moving) {this.moving = moving;}
	public void setNextPhase(Phase nextPhase) {this.nextPhase = nextPhase;}
	public Phase getNextPhase() {return nextPhase;}
	public void setFirstTime(boolean firstTime) {this.firstTime = firstTime;}
}
