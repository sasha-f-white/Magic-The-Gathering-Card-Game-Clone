/*
 Brendan Aucoin
 2018/05/23
 a class made so that a file chooser has a differnet style to look nicer and more user friendly.
 */
package file_chooser;

import java.awt.Color;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class JSystemFileChooser extends JFileChooser{
	public JSystemFileChooser(File file) {
		super(file);
	}
	   public void updateUI(){
	      LookAndFeel old = UIManager.getLookAndFeel();
	      try {
	    	  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	      } 
	      catch (Throwable ex) {
	         old = null;
	      } 

	      super.updateUI();

	      if(old != null){
	         Color background = UIManager.getColor("Label.background");
	         setBackground(background);
	         setOpaque(true);

	         try {
	            UIManager.setLookAndFeel(old);
	         } 
	         catch (UnsupportedLookAndFeelException ignored) {} // shouldn't get here
	      }
	   }
	}
