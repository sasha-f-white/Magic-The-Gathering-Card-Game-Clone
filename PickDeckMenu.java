/*
 Brendan Aucoin
 2018/05/14
 all the menus are the same as all the gui just with differnet logic in them for pressing on certain things.
 */
package menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import cards.Card;
import cards.FallenKing;
import cards.FalseProphet;
import cards.RabiesRiddenWolf;
import cards.Unit;
import file_chooser.JSystemFileChooser;
import game.Game;
import gui.Display;
import playing_panes.InteractionPane;
import sprites.Texture;
import state.State;

public class PickDeckMenu extends InteractionPane{
	private float velX,velY;
	private Rectangle middleBounds;
	private Rectangle animalDeckBounds,crownDeckBounds,thiefDeckBounds,customDeckBounds;
	private float animalDeckX,animalDeckY,originalAnimalDeckX,originalAnimalDeckY;
	private float crownDeckX,crownDeckY,originalCrownDeckX,originalCrownDeckY;
	private float thiefDeckX,thiefDeckY,originalThiefDeckX,originalThiefDeckY;
	private float customDeckX,customDeckY,originalCustomDeckX,originalCustomDeckY;
	private boolean moveAnimalDeck,moveCrownDeck,moveThiefDeck,moveCustomDeck;
	private boolean animalDeckInPosition,crownDeckInPosition,thiefDeckInPosition,customDeckInPosition;
	
	private boolean showBasicButtons;
	private Rectangle pickDeckBounds,refuseDeckBounds;
	private Rectangle useCustomDeckBounds,createDeckBounds,refuseDeckBounds2;
	private Card animalCard,thiefCard,crownCard;
	private Game game;
	public PickDeckMenu(Game game) {
		super(new Rectangle(0,0,Display.WIDTH,Display.HEIGHT));
		this.game = game;
		animalDeckBounds = new Rectangle(getBounds().x + getBounds().width/6,getBounds().y + getBounds().height/20,getBounds().width/7,(int) (getBounds().height/2.5));
		crownDeckBounds = new Rectangle(animalDeckBounds.x + animalDeckBounds.width + animalDeckBounds.width/6,
				animalDeckBounds.y,animalDeckBounds.width,animalDeckBounds.height	);
		thiefDeckBounds = new Rectangle(crownDeckBounds.x + crownDeckBounds.width + crownDeckBounds.width/6,
				crownDeckBounds.y,crownDeckBounds.width,crownDeckBounds.height);
		customDeckBounds = new Rectangle(thiefDeckBounds.x + thiefDeckBounds.width + thiefDeckBounds.width/6,
				thiefDeckBounds.y,thiefDeckBounds.width,thiefDeckBounds.height);
		middleBounds = new Rectangle(getBounds().x + getBounds().width/2 - customDeckBounds.width/2,
				getBounds().y +getBounds().height/2 - customDeckBounds.height/2,
				customDeckBounds.width,customDeckBounds.height);
		
		pickDeckBounds = new Rectangle((int) (middleBounds.x - middleBounds.width/1.5),
				middleBounds.y + middleBounds.height + middleBounds.height/6,
				(int) (middleBounds.width*1.1f),middleBounds.height/4);
		
		refuseDeckBounds = new Rectangle(pickDeckBounds.x + pickDeckBounds.width + pickDeckBounds.width/6,
				pickDeckBounds.y,pickDeckBounds.width,pickDeckBounds.height);
		
		useCustomDeckBounds = new Rectangle(pickDeckBounds.x - pickDeckBounds.width/2,pickDeckBounds.y,pickDeckBounds.width,pickDeckBounds.height);
		createDeckBounds = new Rectangle(useCustomDeckBounds.x + useCustomDeckBounds.width + useCustomDeckBounds.width/6,useCustomDeckBounds.y,useCustomDeckBounds.width,useCustomDeckBounds.height);
		refuseDeckBounds2 = new Rectangle(createDeckBounds.x + createDeckBounds.width + createDeckBounds.width/6,createDeckBounds.y,createDeckBounds.width,createDeckBounds.height);
		
		animalCard = new RabiesRiddenWolf(animalDeckBounds.x,animalDeckBounds.y,animalDeckBounds.width,animalDeckBounds.height,null,null);
		crownCard = new FallenKing(crownDeckBounds.x,crownDeckBounds.y,crownDeckBounds.width,crownDeckBounds.height,null,null);
		thiefCard = new FalseProphet(thiefDeckBounds.x,thiefDeckBounds.y,thiefDeckBounds.width,thiefDeckBounds.height,null,null);
		
		animalDeckX = setInitialDeckCoordinatesX(animalDeckX,animalCard);
		animalDeckY = setInitialDeckCoordinatesY(animalDeckY,animalCard);
		crownDeckX = setInitialDeckCoordinatesX(crownDeckX,crownCard);
		crownDeckY = setInitialDeckCoordinatesY(crownDeckY,crownCard);
		thiefDeckX = setInitialDeckCoordinatesX(thiefDeckX,thiefCard);
		thiefDeckY = setInitialDeckCoordinatesY(thiefDeckY,thiefCard);
		customDeckX = setInitialDeckCoordinatesX(customDeckX,customDeckBounds);
		customDeckY  = setInitialDeckCoordinatesY(customDeckY,customDeckBounds);
		
		originalAnimalDeckX = animalDeckBounds.x;
		originalAnimalDeckY = animalDeckBounds.y;
		originalCrownDeckX = crownDeckBounds.x;
		originalCrownDeckY = crownDeckBounds.y;
		originalThiefDeckX = thiefDeckBounds.x;
		originalThiefDeckY = thiefDeckBounds.y;
		originalCustomDeckX = customDeckBounds.x;
		originalCustomDeckY = customDeckBounds.y;
		showBasicButtons = true;
	}
	private float setInitialDeckCoordinatesX(float deckX,Rectangle r) {
		deckX =r.x;
		return deckX;
	}
	private float setInitialDeckCoordinatesY(float deckY,Rectangle r) {
		deckY =r.y;
		return deckY;
	}
	private float setInitialDeckCoordinatesX(float deckX,Card c) {
		deckX = c.getX();
		return deckX;
	}
	private float setInitialDeckCoordinatesY(float deckY,Card c) {
		deckY = c.getY();
		return deckY;
	}
	public void update() {
		animalDeckBounds.x = (int) animalDeckX;
		animalDeckBounds.y = (int)animalDeckY;
		
		crownDeckBounds.x = (int)crownDeckX;
		crownDeckBounds.y = (int)crownDeckY;
		
		thiefDeckBounds.x = (int)thiefDeckX;
		thiefDeckBounds.y = (int)thiefDeckY;
		
		customDeckBounds.x = (int)customDeckX;
		customDeckBounds.y = (int)customDeckY;
		
		if(moveAnimalDeck) {
			animalDeckX += velX;
			animalDeckY += velY;
			if(!animalDeckInPosition) {
				if(animalDeckX + animalDeckBounds.width >= middleBounds.x + middleBounds.width &&
						animalDeckY + animalDeckBounds.height >= middleBounds.y + middleBounds.height) {
					moveAnimalDeck= false;
					animalDeckX = middleBounds.x;
					animalDeckY = middleBounds.y;
					animalDeckInPosition = true;
					showBasicButtons = true;
				}
			}
			else {
				if(animalDeckX + animalDeckBounds.width <= originalAnimalDeckX + animalDeckBounds.width &&
						animalDeckY + animalDeckBounds.height <= originalAnimalDeckY + animalDeckBounds.height) {
					moveAnimalDeck= false;
					animalDeckX = originalAnimalDeckX;
					animalDeckY = originalAnimalDeckY;
					animalDeckInPosition = false;
				}
			}
		}
		
		if(moveCrownDeck) {
			crownDeckX += velX;
			crownDeckY += velY;
			if(!crownDeckInPosition) {
				if(crownDeckX + crownDeckBounds.width >= middleBounds.x + middleBounds.width &&
						crownDeckY + crownDeckBounds.height >= middleBounds.y + middleBounds.height) {
					moveCrownDeck= false;
					crownDeckX = middleBounds.x;
					crownDeckY = middleBounds.y;
					crownDeckInPosition = true;
					showBasicButtons = true;
				}
			}
			else {
				if(crownDeckX + crownDeckBounds.width <= originalCrownDeckX + crownDeckBounds.width &&
						crownDeckY + crownDeckBounds.height <= originalCrownDeckY + crownDeckBounds.height) {
					moveCrownDeck= false;
					crownDeckX = originalCrownDeckX;
					crownDeckY = originalCrownDeckY;
					crownDeckInPosition = false;
				}
			}
		}
		
		if(moveThiefDeck) {
			thiefDeckX += velX;
			thiefDeckY += velY;
			if(!thiefDeckInPosition) {
				if(thiefDeckX + thiefDeckBounds.width >= middleBounds.x + middleBounds.width &&
						thiefDeckY + thiefDeckBounds.height >= middleBounds.y + middleBounds.height) {
					moveThiefDeck= false;
					thiefDeckX = middleBounds.x;
					thiefDeckY = middleBounds.y;
					thiefDeckInPosition = true;
					showBasicButtons = true;
				}
			}
			else {
				if(thiefDeckX + thiefDeckBounds.width >= originalThiefDeckX + thiefDeckBounds.width &&
						thiefDeckY + thiefDeckBounds.height <= originalThiefDeckY + thiefDeckBounds.height) {
					moveThiefDeck= false;
					thiefDeckX = originalThiefDeckX;
					thiefDeckY = originalThiefDeckY;
					thiefDeckInPosition = false;
				}
			}
		}
		if(moveCustomDeck) {
			customDeckX += velX;
			customDeckY += velY;
			if(!customDeckInPosition) {
				if(customDeckX + customDeckBounds.width >= middleBounds.x + middleBounds.width &&
						customDeckY + customDeckBounds.height >= middleBounds.y + middleBounds.height) {
					moveCustomDeck= false;
					customDeckX = middleBounds.x;
					customDeckY = middleBounds.y;
					customDeckInPosition = true;
					showBasicButtons = true;
				}
			}
			else {
				if(customDeckX + customDeckBounds.width >= originalCustomDeckX + customDeckBounds.width &&
						customDeckY + customDeckBounds.height <= originalCustomDeckY + customDeckBounds.height) {
					moveCustomDeck= false;
					customDeckX = originalCustomDeckX;
					customDeckY = originalCustomDeckY;
					customDeckInPosition = false;
				}
			}
		}
		
	}
	private void renderStats(Graphics2D g,Card c,float deckX,float deckY) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		/*drawing costs*/
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial",Font.PLAIN,18));
			g.drawString(String.valueOf(c.getStrCost()),
					deckX + c.getWidth()/11,
					deckY + c.getHeight()/1.18f);
			
			g.drawString(String.valueOf(c.getDexCost()),
					deckX + c.getWidth()/4.2f,
					deckY+ c.getHeight()/1.18f);
			
			g.drawString(String.valueOf(c.getIntCost()),
					deckX+ c.getWidth()/2.6f,
					deckY + c.getHeight()/1.18f);
			
			g.drawString(String.valueOf(c.getConCost()),
					deckX + c.getWidth()/6.45f,
					deckY + c.getHeight()/1.05f);
			
			g.drawString(String.valueOf(c.getChrCost()),
					deckX + c.getWidth()/3.35f,
					deckY + c.getHeight()/1.05f);
			
		/*drawing names*/
			g.drawString(String.valueOf(c.getName()),(float) (deckX + c.getWidth()/2 -g.getFont().getStringBounds(String.valueOf(c.getName()), frc).getWidth()/2)
					
					,deckY + c.getHeight()/1.6f);
			if(c instanceof Unit) {
				Unit unit = (Unit)c;
				g.drawString(String.valueOf(unit.getSubType()),(float) (deckX+ c.getWidth()/2 -g.getFont().getStringBounds(String.valueOf(unit.getSubType()), frc).getWidth()/2)
						
						,deckY+ c.getHeight()/1.4f);
			}
			/*attack and health*/
			if(c instanceof Unit) {
				Unit unit = (Unit)c;
				g.drawString(String.valueOf(unit.getAttack()),(int)(deckX + c.getWidth()/1.7 - g.getFont().getStringBounds(String.valueOf(unit.getAttack()), frc).getWidth()/2.5 ) , (int)(deckY + c.getHeight()/1.1 ));
				//health
				g.drawString(String.valueOf(unit.getHealth()), (int)(deckX + c.getWidth()/1.17 -g.getFont().getStringBounds(String.valueOf(unit.getHealth()), frc).getWidth()/2.2), (int)(deckY + c.getHeight()/1.1 ));
			}
		}
	private void renderBorder(Graphics2D g,Rectangle deck) {
		if(mouseOver(deck)) {
			g.setStroke(new BasicStroke(6));
			g.setColor(Color.GREEN);
			g.draw(deck);
		}
	}
	public void render(Graphics2D g) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		g.drawImage(Texture.blackBackground,getBounds().x,getBounds().y,getBounds().width,getBounds().height,null);
		if((!moveCrownDeck && !crownDeckInPosition)
				&&(!moveThiefDeck && !thiefDeckInPosition)
				&&(!moveCustomDeck && !customDeckInPosition)
				) {
			g.drawImage(Texture.backOfCard,(int)animalDeckX+ 10,(int)animalDeckY-10,animalDeckBounds.width,animalDeckBounds.height,null);
			g.drawImage(animalCard.getImage(), (int)animalDeckX,(int) animalDeckY, animalDeckBounds.width, animalDeckBounds.height, null);
			renderStats(g,animalCard,animalDeckX,animalDeckY);
			if(!animalDeckInPosition && !moveAnimalDeck) {renderBorder(g,animalDeckBounds);}
			if(animalDeckInPosition) {
				if(showBasicButtons) {
					g.setColor(Color.WHITE);
					g.setStroke(new BasicStroke(12));
					g.draw(animalDeckBounds);
				
					changeColour(g,pickDeckBounds,new Color(192,192,192),new Color(255,255,224));
					g.fillRoundRect(pickDeckBounds.x,pickDeckBounds.y,pickDeckBounds.width,pickDeckBounds.height,80,80);
					g.setColor(Color.BLACK);
					g.setFont(new Font("Arial",Font.PLAIN,40));
					g.drawString("Use Deck", (int)(pickDeckBounds.x + pickDeckBounds.width/2 - g.getFont().getStringBounds("Use Deck", frc).getWidth()/2), (int) (pickDeckBounds.y + pickDeckBounds.height/2 + g.getFont().getStringBounds("Use Deck", frc).getHeight()/8));
					
					changeColour(g,refuseDeckBounds,new Color(192,192,192),new Color(255,255,224));
					g.fillRoundRect(refuseDeckBounds.x,refuseDeckBounds.y,refuseDeckBounds.width,refuseDeckBounds.height,80,80);
					g.setColor(Color.BLACK);
					g.drawString("Pick Again",(int)(refuseDeckBounds.x + refuseDeckBounds.width/2 - g.getFont().getStringBounds("Pick Again", frc).getWidth()/2), (int) (refuseDeckBounds.y + refuseDeckBounds.height/2 + g.getFont().getStringBounds("Pick Again", frc).getHeight()/8));
				}
				}
		}
		
		
		if((!moveAnimalDeck && !animalDeckInPosition)
				&&(!moveThiefDeck && !thiefDeckInPosition)
				&&(!moveCustomDeck && !customDeckInPosition)
				) {
			g.drawImage(Texture.backOfCard,(int)crownDeckX + 10,(int)crownDeckY-10,crownDeckBounds.width,crownDeckBounds.height,null);
			g.drawImage(crownCard.getImage(), (int)crownDeckX, (int)crownDeckY,crownDeckBounds.width,crownDeckBounds.height,null);
			renderStats(g,crownCard,crownDeckX,crownDeckY);
			if(!crownDeckInPosition && !moveCrownDeck) {renderBorder(g,crownDeckBounds);}
			if(crownDeckInPosition) {
				if(showBasicButtons) {
					g.setColor(Color.WHITE);
					g.setStroke(new BasicStroke(12));
					g.draw(crownDeckBounds);
					
					changeColour(g,pickDeckBounds,new Color(192,192,192),new Color(255,255,224));
					g.fillRoundRect(pickDeckBounds.x,pickDeckBounds.y,pickDeckBounds.width,pickDeckBounds.height,80,80);
					g.setColor(Color.BLACK);
					g.setFont(new Font("Arial",Font.PLAIN,40));
					g.drawString("Use Deck", (int)(pickDeckBounds.x + pickDeckBounds.width/2 - g.getFont().getStringBounds("Use Deck", frc).getWidth()/2), (int) (pickDeckBounds.y + pickDeckBounds.height/2 + g.getFont().getStringBounds("Use Deck", frc).getHeight()/8));
					
					changeColour(g,refuseDeckBounds,new Color(192,192,192),new Color(255,255,224));
					g.fillRoundRect(refuseDeckBounds.x,refuseDeckBounds.y,refuseDeckBounds.width,refuseDeckBounds.height,80,80);
					g.setColor(Color.BLACK);
					g.drawString("Pick Again",(int)(refuseDeckBounds.x + refuseDeckBounds.width/2 - g.getFont().getStringBounds("Pick Again", frc).getWidth()/2), (int) (refuseDeckBounds.y + refuseDeckBounds.height/2 + g.getFont().getStringBounds("Pick Again", frc).getHeight()/8));
				}
				}
		}
		
		
		if((!moveCrownDeck && !crownDeckInPosition)
				&&(!moveAnimalDeck && !animalDeckInPosition)
				&&(!moveCustomDeck && !customDeckInPosition)
				) {
			g.drawImage(Texture.backOfCard,(int)thiefDeckX + 10,(int)thiefDeckY-10,(int)thiefCard.getWidth(),(int)thiefCard.getHeight(),null);
			g.drawImage(thiefCard.getImage(), (int)thiefDeckX, (int)thiefDeckY,thiefCard.getWidth(),(int)thiefCard.getHeight(),null);
			renderStats(g,thiefCard,thiefDeckX,thiefDeckY);
			if(!thiefDeckInPosition && !moveThiefDeck) {renderBorder(g,thiefDeckBounds);}
			if(thiefDeckInPosition) {
				if(showBasicButtons) {
					g.setColor(Color.WHITE);
					g.setStroke(new BasicStroke(12));
					g.draw(thiefDeckBounds);
				
					changeColour(g,pickDeckBounds,new Color(192,192,192),new Color(255,255,224));
					g.fillRoundRect(pickDeckBounds.x,pickDeckBounds.y,pickDeckBounds.width,pickDeckBounds.height,80,80);
					g.setColor(Color.BLACK);
					g.setFont(new Font("Arial",Font.PLAIN,40));
					g.drawString("Use Deck", (int)(pickDeckBounds.x + pickDeckBounds.width/2 - g.getFont().getStringBounds("Use Deck", frc).getWidth()/2), (int) (pickDeckBounds.y + pickDeckBounds.height/2 + g.getFont().getStringBounds("Use Deck", frc).getHeight()/8));
					
					changeColour(g,refuseDeckBounds,new Color(192,192,192),new Color(255,255,224));
					g.fillRoundRect(refuseDeckBounds.x,refuseDeckBounds.y,refuseDeckBounds.width,refuseDeckBounds.height,80,80);
					g.setColor(Color.BLACK);
					g.drawString("Pick Again",(int)(refuseDeckBounds.x + refuseDeckBounds.width/2 - g.getFont().getStringBounds("Pick Again", frc).getWidth()/2), (int) (refuseDeckBounds.y + refuseDeckBounds.height/2 + g.getFont().getStringBounds("Pick Again", frc).getHeight()/8));
				}
				}
		}
		
		
		if((!moveCrownDeck && !crownDeckInPosition)
				&&(!moveThiefDeck && !thiefDeckInPosition)
				&&(!moveAnimalDeck && !animalDeckInPosition)
				) {
			g.drawImage(Texture.backOfCard,(int)customDeckX + 10,(int)customDeckY-10,(int)customDeckBounds.getWidth(),(int)customDeckBounds.getHeight(),null);
			g.drawImage(Texture.backOfCard,(int)customDeckX ,(int)customDeckY,(int)customDeckBounds.getWidth(),(int)customDeckBounds.getHeight(),null);
			if(!customDeckInPosition && !moveCustomDeck) {renderBorder(g,customDeckBounds);}
			if(customDeckInPosition) {
				if(showBasicButtons) {
					g.setColor(Color.WHITE);
					g.setStroke(new BasicStroke(12));
					g.draw(customDeckBounds);
				//	g.setStroke(new BasicStroke(1));
					
					changeColour(g,useCustomDeckBounds,new Color(192,192,192),new Color(255,255,224));
					g.fillRoundRect(useCustomDeckBounds.x,useCustomDeckBounds.y,useCustomDeckBounds.width,useCustomDeckBounds.height,80,80);
					g.setColor(Color.BLACK);
					g.setFont(new Font("Arial",Font.PLAIN,25));
					g.drawString("Use Custom Deck", (int)(useCustomDeckBounds.x + useCustomDeckBounds.width/2 - g.getFont().getStringBounds("Use Custom Deck", frc).getWidth()/2), (int) (useCustomDeckBounds.y + useCustomDeckBounds.height/2 + g.getFont().getStringBounds("Use Custom Deck", frc).getHeight()/8));
					
					changeColour(g,createDeckBounds,new Color(192,192,192),new Color(255,255,224));
					g.fillRoundRect(createDeckBounds.x,createDeckBounds.y,createDeckBounds.width,createDeckBounds.height,80,80);
					g.setColor(Color.BLACK);
					g.setFont(new Font("Arial",Font.PLAIN,25));
					g.drawString("Create Deck", (int)(createDeckBounds.x + createDeckBounds.width/2 - g.getFont().getStringBounds("Create Deck", frc).getWidth()/2), (int) (createDeckBounds.y + createDeckBounds.height/2 + g.getFont().getStringBounds("Create Deck", frc).getHeight()/8));
					
					changeColour(g,refuseDeckBounds2,new Color(192,192,192),new Color(255,255,224));
					g.fillRoundRect(refuseDeckBounds2.x,refuseDeckBounds2.y,refuseDeckBounds2.width,refuseDeckBounds2.height,80,80);
					g.setColor(Color.BLACK);
					g.setFont(new Font("Arial",Font.PLAIN,25));
					g.drawString("Pick Again", (int)(refuseDeckBounds2.x + refuseDeckBounds2.width/2 - g.getFont().getStringBounds("Pick Again", frc).getWidth()/2), (int) (refuseDeckBounds2.y + refuseDeckBounds2.height/2 + g.getFont().getStringBounds("Pick Again", frc).getHeight()/8));
										
				}
			}
			
			g.setFont(new Font("Arial",Font.PLAIN,100));
			g.setColor(Color.WHITE);
			g.drawString("?", (int) (customDeckBounds.x + customDeckBounds.width/2 - g.getFont().getStringBounds("?", frc).getWidth()/2.5),
					(int) (customDeckBounds.y + customDeckBounds.height/2 -  g.getFont().getStringBounds("?", frc).getHeight()/10));
		}
		
		}//end of render
	
	

	public void setVelocityVector(float destinationX,float destinationY,float x,float y,float speed) {
		float dx = destinationX - x;
	    float dy =  destinationY - y;
	    float theta = (float) Math.atan(dy / dx); 
	    velX = (float) (speed * Math.cos(theta));
	    velY = (float) (speed * Math.sin(theta));
	}
	private void clickOnPickDeckButton() {
		if(animalDeckInPosition) {
			game.makeCardDeck(Game.DECK_PATH + "BEAST_deck.txt");
		}
		
		else if(crownDeckInPosition) {
			game.makeCardDeck(Game.DECK_PATH +"CROWN_deck.txt");
		}
		else if(thiefDeckInPosition) {
			game.makeCardDeck(Game.DECK_PATH +"THIEVES_deck.txt");
		}
	}
	private String getPathFromFileChooser() {
		File currentDir = new File(Game.DECK_PATH);
		JSystemFileChooser fileChooser = new JSystemFileChooser(new File("File to start in"));
		fileChooser.setCurrentDirectory(currentDir);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
		fileChooser.setFileFilter(filter);
		int buttonPressed = fileChooser.showOpenDialog(null);
		String path = "";
		if(buttonPressed == JFileChooser.APPROVE_OPTION) {
			   path =  fileChooser.getSelectedFile().getPath();
		}
		if(path.endsWith("_deck.txt")){return path;}
		else {return null;}
	}
	private void clickOnRefuseDeckButton() {
		if(animalDeckInPosition) {
			setVelocityVector(originalAnimalDeckX,originalAnimalDeckY,middleBounds.x,middleBounds.y,6);
			velX *=-0.99;
			velY *=-1;
			moveAnimalDeck = true;
			showBasicButtons = false;
		}
		
		else if(crownDeckInPosition) {
			setVelocityVector(originalCrownDeckX,originalCrownDeckY,middleBounds.x,middleBounds.y,6);
			velX *=-0.99;
			velY *=-1;
			moveCrownDeck = true;
			showBasicButtons = false;
		}
		else if(thiefDeckInPosition) {
			setVelocityVector(originalThiefDeckX,originalThiefDeckY,middleBounds.x,middleBounds.y,6);
			moveThiefDeck = true;
			showBasicButtons = false;
		}
	}
	public void mousePressed(MouseEvent e) {
		if(mouseOver(animalDeckBounds)) {
			if(moveCrownDeck == true || moveThiefDeck == true || moveCustomDeck == true ||
			crownDeckInPosition || thiefDeckInPosition ||customDeckInPosition || animalDeckInPosition
					) {return;}
			setVelocityVector(middleBounds.x,middleBounds.y,animalDeckX,animalDeckY,6);
			moveAnimalDeck = true;
		}
		else if(mouseOver(crownDeckBounds)) {
			if(moveAnimalDeck == true || moveThiefDeck == true || moveCustomDeck == true ||
					crownDeckInPosition || thiefDeckInPosition ||customDeckInPosition || animalDeckInPosition
							) {return;}
				setVelocityVector(middleBounds.x,middleBounds.y,crownDeckX,crownDeckY,6);
				moveCrownDeck = true;
		}
		else if(mouseOver(thiefDeckBounds)) {
			if(moveCrownDeck == true || moveAnimalDeck == true || moveCustomDeck == true ||
					crownDeckInPosition || thiefDeckInPosition ||customDeckInPosition || animalDeckInPosition
							) {return;}
				setVelocityVector(middleBounds.x,middleBounds.y,thiefDeckX,thiefDeckY,6);
		    	moveThiefDeck = true;
		    	velY*=-1;
		    	velX *=-0.99;
		}
		else if(mouseOver(customDeckBounds)) {
			if(moveCrownDeck == true || moveThiefDeck == true || moveAnimalDeck == true || moveCustomDeck ==true||
					crownDeckInPosition || thiefDeckInPosition ||customDeckInPosition || animalDeckInPosition
							) {return;}
				setVelocityVector(middleBounds.x,middleBounds.y,customDeckX,customDeckY,8);
		    	moveCustomDeck = true;
		    	velY*=-1;
		    	velX *=-0.99;
			}
		
		if(mouseOver(pickDeckBounds)) {
			clickOnPickDeckButton();
		}
		if(mouseOver(refuseDeckBounds)) {
			clickOnRefuseDeckButton();
		}
		if(mouseOver(refuseDeckBounds2)) {
			refuseCustomDeck();
		}
		if(mouseOver(createDeckBounds)) {
			createCustomDeck();
		}
		if(mouseOver(useCustomDeckBounds) && customDeckInPosition) {
			useCustomDeck();
		}
	}
	private void useCustomDeck() {
		String path = getPathFromFileChooser();
		if(path == null) {return;}
		game.makeCardDeck(path);
	}
	private void createCustomDeck() {
		if(customDeckInPosition) {
			Game.state = State.DECK_BUILDER;
			Game.goToDeckBuilder = true;
			Game.goToPickDeckMenu = false;
		}
	}
	private void refuseCustomDeck() {
		if(customDeckInPosition) {
			setVelocityVector(originalCustomDeckX,originalCustomDeckY,middleBounds.x,middleBounds.y,6);
			moveCustomDeck = true;
			showBasicButtons = false;
		}
	}
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
	}
	
}
