/*
 Brendan Aucoin and Sasha White
 2018/05/24
 this class is for typing in a custom textfield
 */
package text_field;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import javax.swing.Timer;

public class TextField implements ActionListener {
	private Rectangle bounds;
	private String search;//search is th words in the textfield
	private boolean canType;
	private boolean showCursor;
	private int mx,my;
	private Color backColour;
	private Timer timer;
	private Color textColour;
	public TextField(Rectangle bounds) {
		this.bounds = bounds;
		search = "";
		backColour = Color.GRAY;
		timer  = new Timer(400,this);
		timer.start();
	}
	public TextField(Rectangle bounds,String search) {
		this.bounds = bounds;
		this.search = search;
		backColour = Color.GRAY;
		timer  = new Timer(400,this);
		timer.start();
	}
	public TextField(int x,int y,int width,int height) {
		this.bounds = new Rectangle(x,y,width,height);
		search = "";
		backColour = Color.GRAY;
		timer  = new Timer(400,this);
		timer.start();
	}
	/*if the timer is activated show the cursor blinking*/
	public void actionPerformed(ActionEvent e) {
		if(canType) {
			showCursor = !showCursor;
		}
	}
	/*draws everything needed for a textfield*/
	public void render(Graphics2D g) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(3));
		g.setColor(backColour);
		g.draw(bounds);
		g.setFont(new Font("arial",Font.PLAIN,15));
		g.setColor(Color.RED);
		/*drawing the words*/
		g.drawString(search, bounds.x,(int)(bounds.y+bounds.height/1.4));
		if(showCursor) {
			g.setColor(Color.BLACK);
			g.drawLine(bounds.x+(int)(g.getFont().getStringBounds(search, frc).getWidth()),bounds.y,bounds.x+(int)(g.getFont().getStringBounds(search, frc).getWidth()),bounds.y + bounds.height);
			System.out.println(bounds.y);
		}
	}
	/*concatinates the keys typed to the search string to draw it.*/
	public void keyPressed(KeyEvent e) {
		if(canType) {
			if(search.length() <= (getBounds().x + getBounds().width)/20) {
				if(e.getKeyCode() >=65 && e.getKeyCode()<=90) {
					search+= KeyEvent.getKeyText(e.getKeyCode());
				}
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					search += " ";
				}
			}
				if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if(search.length() > 0) {search = search.substring(0, search.length()-1);}
				}
			}
		
	}
	
	public Rectangle getBounds() {return bounds;}	
	public void setBounds(Rectangle bounds) {this.bounds = bounds;}
	public String getSearch() {return search;}
	public void setSearch(String search) {this.search = search;	}
	public boolean isCanType() {return canType;}
	public void setCanType(boolean canType) {this.canType = canType;}
	public boolean isShowCursor() {return showCursor;}
	public void setShowCursor(boolean showCursor) {this.showCursor = showCursor;}

	public int getMx() {return mx;}

	public void setMx(int mx) {this.mx = mx;}

	public int getMy() {return my;}

	public void setMy(int my) {this.my = my;}
	public Color getBackColour() {
		return backColour;
	}
	public void setBackColour(Color backColour) {
		this.backColour = backColour;
	}
	public Color getTextColour() {
		return textColour;
	}
	public void setTextColor(Color textColour) {
		this.textColour = textColour;
	}
	public Timer getTimer() {return timer;}
	
	
	
}
