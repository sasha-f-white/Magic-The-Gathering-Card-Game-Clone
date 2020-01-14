/*
 Brendan Aucoin
 2018/04/12
 this class draws and holds everything for the card display.
 */
package playing_panes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import cards.Building;
import cards.Card;
import cards.Spell;
import cards.Unit;
import file_loader.FileLoader;
import game.Game;
import gui.Display;
import menu.DeckBuilderGUI;
import sprites.Texture;

public class CardDisplayGUI extends InteractionPane{
	private Card currCard;
	private FileLoader fileLoader;
	public CardDisplayGUI() {
		super(new Rectangle(
				0,Display.HEIGHT/12,Display.WIDTH/5,(int)(Display.HEIGHT/1.365)
				
				));
		fileLoader = new FileLoader();
	}
	
	public void update() {
		
	}
	public void render(Graphics2D g) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		//g.setColor(Color.GREEN);
		//g.fill(getBounds());
		g.drawImage(Texture.cardDisplayBackground, getBounds().x, getBounds().y, getBounds().width, getBounds().height, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD,40));
		//drawing the card image big
		if(currCard != null) {
			g.drawImage(currCard.getImage(),getBounds().x,getBounds().y,(int) getBounds().width,(int) ((int) getBounds().height/1.3),null);
			//g.drawImage(currCard.getImage(),getBounds().x,getBounds().y,(int) (getBounds().width/1.1),(int) (getBounds().height/1.5),null);
		}
		if(currCard instanceof Unit) {
			Unit unit = (Unit)currCard;
			//drawing the stats
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial",Font.BOLD,25));
			//the attack
			g.drawString(String.valueOf(unit.getAttack()),(int)(getBounds().x + getBounds().width/1.7 - g.getFont().getStringBounds(String.valueOf(unit.getAttack()), frc).getWidth()/2.5 ) , (int)(getBounds().y + getBounds().height/1.3 -getBounds().height/14));
			//health
			g.drawString(String.valueOf(unit.getHealth()), (int)(getBounds().x + getBounds().width/1.17 -g.getFont().getStringBounds(String.valueOf(unit.getHealth()), frc).getWidth()/2.2), (int)(getBounds().y + getBounds().height/1.3 -getBounds().height/12));
			//g.drawString(String.valueOf(unit.getHealth()), (int)(unit.getX() + unit.getWidth()/1.17 - g.getFont().getStringBounds(String.valueOf(unit.getHealth()), frc).getWidth()/2.2), (int)(unit.getY() + unit.getHeight() - unit.getHeight()/12.51));
			}
		
		/*drawing costs*/
			if(currCard != null) {
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial",Font.PLAIN,18));
				g.drawString(String.valueOf(currCard.getStrCost()),
						getBounds().x + getBounds().width/11,
						getBounds().y + getBounds().height/1.55f);
				
				g.drawString(String.valueOf(currCard.getDexCost()),
						getBounds().x + getBounds().width/4.2f,
						getBounds().y + getBounds().height/1.55f);
				
				g.drawString(String.valueOf(currCard.getIntCost()),
						getBounds().x + getBounds().width/2.6f,
						getBounds().y + getBounds().height/1.55f);
				
				g.drawString(String.valueOf(currCard.getConCost()),
						getBounds().x + getBounds().width/6.45f,
						getBounds().y + getBounds().height/1.38f);
				
				g.drawString(String.valueOf(currCard.getChrCost()),
						getBounds().x + getBounds().width/3.35f,
						getBounds().y + getBounds().height/1.38f);
				
				
				/*drawing names*/
				g.drawString(String.valueOf(currCard.getName()),(float) (getBounds().x + getBounds().width/2 -g.getFont().getStringBounds(String.valueOf(currCard.getName()), frc).getWidth()/2)
						
						,getBounds().y + getBounds().height/2.1f);
				if(currCard instanceof Unit) {
					Unit unit = (Unit)currCard;
					g.drawString(String.valueOf(unit.getSubType()),(float) (getBounds().x + getBounds().width/2 -g.getFont().getStringBounds(String.valueOf(unit.getSubType()), frc).getWidth()/2)
							
							,getBounds().y + getBounds().height/1.85f);
				}
				
				if(currCard instanceof Spell) {
					Spell spell = (Spell)currCard;
					g.drawString(String.valueOf("Spell"),(float) (getBounds().x + getBounds().width/2 -g.getFont().getStringBounds(String.valueOf("Spell"), frc).getWidth()/2)
							
							,getBounds().y + getBounds().height/1.85f);
				}
				
				if(currCard instanceof Building) {
					Building building = (Building)currCard;
					g.drawString(String.valueOf("Building"),(float) (getBounds().x + getBounds().width/2 -g.getFont().getStringBounds(String.valueOf("Building"), frc).getWidth()/2)
							
							,getBounds().y + getBounds().height/1.85f);
				}
				
			}
			if(currCard != null) {
				for(Card c : DeckBuilderGUI.cardList) {
					if(c.getName().equals(currCard.getName())) {
						fileLoader.setFileName(Game.EFFECT_PATH + currCard.getName() + "_effect.txt"); 
						ArrayList<String>effectList = fileLoader.read();
						for(int i =0; i < effectList.size();i++) {
							g.setColor(Color.BLACK);
							g.setFont(new Font("Arial",Font.PLAIN,18));
							g.drawString(effectList.get(i), getBounds().x + g.getFont().getSize()/2,(int) (getBounds().y + getBounds().height/1.25) + g.getFont().getSize()*i + (i*effectList.size()));
						}
						
					}
				}
			}
		}
	public void mousePressed(MouseEvent e) {
		
	}
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
	}
	
	public void setCurrCard(Card currCard) {this.currCard = currCard;}
	public Card getCurrCard() {return currCard;}
}
