/*
 Brendan Aucoin
 2018/04/28
 this class holds data for a boundary and if that boundary is open or not.
 its used for putting cards on the board and it needs to check if the boundary where you 
 want to put it is taken or not.
 */
package board;

import java.awt.Rectangle;

public class Spot {
	/*holds data for a location on the screen and if it is being used currently*/
	private Rectangle bounds;
	private boolean open;
	/*constructors*/
	public Spot(Rectangle bounds,boolean open) {
		this.bounds = bounds;
		this.open = open;
	}
	public Spot() {}
	public Spot(Spot s) {
		this.bounds = s.bounds;
		this.open = s.open;
	}
	
	public Rectangle getBounds() {return bounds;}
	public void setBounds(Rectangle bounds) {this.bounds = bounds;}
	public boolean isOpen() {return open;}
	public void setOpen(boolean open) {this.open = open;}
	
	
	
}
