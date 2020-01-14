/*
 Brendan Aucoin
 2018/04/28
 this is a abstract class so just in case we wanted a different popup it would be less code to write 
 the positions and all that stuff again.
 */
package popups;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import mouse.MousePane;

public abstract class PopupBox extends MousePane{
	private float x,y;
	private int width,height;
	private boolean shown;
	private Rectangle bounds;
	public PopupBox(float x,float y,int width,int height) {
		this.x = x;
		this.y =y;
		this.width = width;
		this.height = height;
	}
	/*always draws this box.*/
	public void render(Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		g.setStroke(new BasicStroke(3));
		g.draw(getBounds());
		g.setStroke(new BasicStroke(1));
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.PLAIN,20));
	}
	/*getters and setters*/
	public float getX() {return x;}
	public void setX(float x) {this.x = x;}
	public float getY() {return y;}
	public void setY(float y) {this.y = y;}
	public int getWidth() {return width;}
	public void setWidth(int width) {this.width = width;}
	public int getHeight() {return height;}
	public void setHeight(int height) {this.height = height;}
	public void setShown(boolean shown) {this.shown = shown;}
	public boolean isShown() {return shown;}
	public Rectangle getBounds() {return bounds;}
	public void setBounds(Rectangle bounds) {this.bounds = bounds;}
}
