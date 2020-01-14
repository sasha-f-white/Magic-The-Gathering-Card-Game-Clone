/*
 Brendan Aucoin
 2018/04/12
 this class is for a section of any gui that you want to click on to inherit.
 becuase this class already has all the methods for mouseing over clickable objects
 and rectangles as well as mouse moved and mouse pressed methods.
 */
package mouse;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
public abstract class MousePane implements MouseListener,MouseMotionListener,MouseWheelListener{
	protected int mouseX,mouseY;//protected because you want any mouse object to have these
	  public MousePane(){
		  super();
		  mouseX = 0;
		  mouseY = 0;
	  }
	  /*checks if a x and y location are in the bounds of a width and height*/
	  protected boolean mouseOver(int mx,int my,int x,int y,int width,int height)
	 {
	   if(mx > x && mx < x+width)
	   {
	     if(my > y && my <y +height)
	     {
	       return true;
	     }
	   }
	   return false;
	 }
	  /*checks if your mouse if over a clickable object*/
	   protected boolean mouseOver(ClickableObject co)
	 {
	   if(mouseX > co.getX() && mouseX < co.getX()+co.getWidth())
	   {
	     if(mouseY > co.getY() && mouseY <co.getY() +co.getHeight())
	     {
	       return true;
	     }
	   }
	   return false;
	 }
	   /*checks if an x and y positions is over a clickable object*/
	  protected boolean mouseOver(int mx,int my,ClickableObject co)
	 {
	   if(mx > co.getX() && mx < co.getX()+co.getWidth())
	   {
	     if(my > co.getY() && my <co.getY() +co.getHeight())
	     {
	       return true;
	     }
	   }
	   return false;
	 }
	  /*checks if an x and y position is over a rectangle*/
	  protected boolean mouseOver(int mx,int my,Rectangle bounds)
	 {
	   if(mx > bounds.x && mx < bounds.x+bounds.width)
	   {
	     if(my > bounds.y && my <bounds.y +bounds.height)
	     {
	       return true;
	     }
	   }
	   return false;
	 }
	  /*checks if your mouse if over a rectangle*/
	  protected boolean mouseOver(Rectangle bounds)
	 {
	   if(mouseX > bounds.x && mouseX < bounds.x+bounds.width)
	   {
	     if(mouseY > bounds.y && mouseY <bounds.y +bounds.height)
	     {
	       return true;
	     }
	   }
	   return false;
	 }
	  /*if you are over a certain bounds change its colour of the graphics*/
	  protected void changeColour(Graphics2D g,Rectangle bounds,Color newCol,Color oldCol)
	 {
	  if(mouseOver(mouseX,mouseY,bounds.x,bounds.y,bounds.width,bounds.height)) {g.setColor(newCol);}
	  else {g.setColor(oldCol);}
	 }
	  /*if you are over a certain bounds change its colour of the graphics*/
	  protected void changeColour(Graphics2D g,int x,int y, int width,int height,Color newCol,Color oldCol)
	 {
	  if(mouseOver(mouseX,mouseY,x,y,width,height)) {g.setColor(newCol);}
	  else {g.setColor(oldCol);}
	 }
	  /*if you are over a certain bounds change its colour of the graphics*/
	  protected void changeColour(Graphics g,Rectangle bounds,Color newCol,Color oldCol)
	 {
	  if(mouseOver(mouseX,mouseY,bounds.x,bounds.y,bounds.width,bounds.height)) {g.setColor(newCol);}
	  else {g.setColor(oldCol);}
	 }
	  /*if you are over a certain bounds change its colour of the graphics*/
	  protected void changeColour(Graphics g,int x,int y, int width,int height,Color newCol,Color oldCol)
	 {
	  if(mouseOver(mouseX,mouseY,x,y,width,height)) {g.setColor(newCol);}
	  else {g.setColor(oldCol);}
	 }
	  /*moving mouse*/
	  public void mouseMoved(MouseEvent e){
	    this.mouseX = e.getX();
	    this.mouseY = e.getY();
	  }
	  /*implemented methods.*/
	  public void mouseDragged(MouseEvent e) {}
	 public void mouseClicked(MouseEvent arg0) {}
	 public void mouseEntered(MouseEvent arg0) {}
	 public void mouseExited(MouseEvent arg0) {}
	 public void mousePressed(MouseEvent arg0) {}
	 public void mouseReleased(MouseEvent arg0) {}
	  public void setMouseX(int mouseX){this.mouseX = mouseX;}
	  public void setMouseY(int mouseY){this.mouseY = mouseY;}
	  
	  public int getMouseX(){return mouseX;}
	  public int getMouseY(){return mouseY;}
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		
	}
  
}