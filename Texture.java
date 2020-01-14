/*
 Brendan Aucoin
 2018/04/15
 this class loads in all the textures for the game. from spritesheets.
 */
package sprites;

import java.awt.image.BufferedImage;
/*this class consists of all the images that are static or arrays of images for spritesheets or animations.  */
public class Texture {
	public static BufferedImage[] manaSprites;
	public static BufferedImage backOfCard,backOfManaCard;
	private BufferedImageLoader loader;
	public static BufferedImage[] cardSprites;
	public static BufferedImage attackingIcon;
	public static BufferedImage playerIcon,opponentIcon;
	public static BufferedImage mainMenuBackground;
	public static BufferedImage boardBackground;
	public static BufferedImage handBackground;
	public static BufferedImage opponentHandBackground;
	public static BufferedImage discardBackImage;
	public static BufferedImage cardDisplayBackground;
	public static BufferedImage blackBackground;
	public static BufferedImage discardBackground;
	public static BufferedImage musicIcon;
	public static BufferedImage zappImage;
	public Texture() {
		this.loader = new BufferedImageLoader();
		loadManaSprites();
		backOfCard = loader.loadImage("images","deckBack.png");
		backOfManaCard = loader.loadImage("images","manaBack.png");
		discardBackImage = loader.loadImage("images","discardBack.png");
		attackingIcon = loader.loadImage("images","attackingIcon.png");
		playerIcon = loader.loadImage("images","playerIcon.png");
		opponentIcon = loader.loadImage("images","opponentIcon.png");
		mainMenuBackground = loader.loadImage("images","castle background.png");
		boardBackground = loader.loadImage("images","boardBackground.png");
		handBackground = loader.loadImage("images","handBackground.png");
		opponentHandBackground = loader.loadImage("images","opponentHandBackground.png");
		cardDisplayBackground = loader.loadImage("images","cardDisplayBackground.png");
		blackBackground = loader.loadImage("images","blackBackground.png");
		musicIcon = loader.loadImage("images","musicIcon.png");
		discardBackground = loader.loadImage("images","graveyardImage.png");
		zappImage = loader.loadImage("images","straight out of.png");
		loadCardSprites();
	}
	/*gets the mana sprites from a sheet specified*/
	private void loadManaSprites() {
		SpriteSheet manaSheet;
		BufferedImage manaSheetImage;
		manaSprites = new BufferedImage[5];
		manaSheetImage = loader.loadImage("images","mana spritesheet.png");
		manaSheet = new SpriteSheet(manaSheetImage);
		manaSprites = readFromSheet(manaSprites,manaSheet,1,5,30,45);
	}
	/*gets the card sprites from a sheet specified*/
	private void loadCardSprites() {
		SpriteSheet cardSheet;
		BufferedImage cardSheetImage;
		cardSprites = new BufferedImage[200];
		cardSheetImage = loader.loadImage("images", "cardSpriteSheet.png");
		cardSheet = new SpriteSheet(cardSheetImage);
		cardSprites = readFromSheet(cardSprites,cardSheet,5,10,60,90);
	}
	
	
	/*this method crops out images from a spritesheet assuming there is a constant width,height and you know how many rows and columns there are.*/
	  private BufferedImage[] readFromSheet(BufferedImage[] sprites, SpriteSheet sheet,int rows,int cols,int width,int height)
	  {
	    for(int i = 0; i < rows; i++)
	    {
	      for(int x = 0; x < cols; x++)
	      {
	        sprites[x + (i*cols)] = sheet.crop(x*width, i*height, width, height);
	      }
	    }
	    return sprites;
	  }
}
