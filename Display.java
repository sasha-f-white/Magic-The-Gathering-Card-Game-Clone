/*
 Brendan Aucoin
 2018/04/15
 this is the display class that make a frame and adds the game panel to it and displays the frame. 
 as well as starts the game thread.
 */
package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import game.Game;

public class Display {
	private JFrame f;
	private Game mainGUI;
	public static final int WIDTH = 1366;
	public static final int HEIGHT = 768;
	//public static final int WIDTH = 1000;
	//public static final int HEIGHT = 600;
	public Display() {
		mainGUI = new Game();
		f = GUI.setFrame(f, "Card Game", new Dimension(WIDTH,HEIGHT), new FlowLayout(), false);
		//f.setSize(1300,730);
		Image image = new ImageIcon(this.getClass().getResource("/images/dogIcon.png")).getImage();
		f.setIconImage(image);
		
		f.setVisible(true);
		f.add(mainGUI.getMainPanel());
		mainGUI.start();
	}
}
