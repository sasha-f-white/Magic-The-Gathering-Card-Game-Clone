/*
 Brendan Aucoin
 2018/04/12
 this class is a class that all the gui parts inherit from.
 */
package playing_panes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import mouse.MousePane;

public abstract class InteractionPane extends MousePane{
	private Rectangle bounds;
	private int mouseX,mouseY;
	public InteractionPane(Rectangle bounds) {
		this.bounds = bounds;
		mouseX = 0;
		mouseY = 0;
	}
	public InteractionPane() {
		bounds = new Rectangle(0,0,0,0);
		mouseX = 0;
		mouseY = 0;
	}
	public abstract void update();
	public abstract void render(Graphics2D g);
	public abstract void mousePressed(MouseEvent e);
	
	  public String toString() {
		  return (getBounds().width + "," + getBounds().height);
	  }
	public void setBounds(Rectangle bounds) {
		this.bounds = new Rectangle(bounds);
	}
	public Rectangle getBounds() {
		return new Rectangle(bounds);
	}
	public int getMouseX() {return mouseX;}
	public int getMouseY() {return mouseY;}
}
