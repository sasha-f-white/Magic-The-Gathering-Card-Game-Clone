/*
 Brendan Aucoin
 12/12/17
 this class only has static methods. for making specific components of the gui. 
 so you would never have an instance of this class.
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI {
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
	/*this method takes in a frame and sets all the charatersitcs to the basic stuff and the stuff you pass in.*/
	public static JFrame setFrame(JFrame f,String title,Dimension size,LayoutManager layout,boolean resizable) {
		f = new JFrame(title);
		f.setResizable(resizable);
		f.setSize(size.width,size.height);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setLayout(layout);
		return f;		
	}
	/*this method takes in a panel and sets all the characteristics through the parameters you pass in.*/
	public static JPanel setPanel(JPanel p,Color col,Dimension size,LayoutManager layout)
	{
		p = new JPanel();
		p.setBackground(col);
		p.setPreferredSize(size);
		p.setLayout(layout);
		return p;
	}
	/*this method takes in a button and sets all the characteristics through the parameters you pass in.*/
	public static JButton setButton(JButton b,String text,Dimension dim,Color backCol,Color frontCol,Font font,ActionListener al)
	{
		b = new JButton();
		b.setText(text);
		b.setPreferredSize(dim);
		b.setBackground(backCol);
		b.setForeground(frontCol);
		b.setFont(font);
		b.addActionListener(al);
		return b;
	}
	/*this method takes in a label and sets all the characteristics through the parameters you pass in.*/
	public static JLabel setLabel(JLabel l,String text,Font font,Color col)
	{
		l = new JLabel();
		l.setText(text);
		l.setFont(font);
		l.setForeground(col);
		return l;
	}

	/*this method takes in a text field and sets all the characteristics through the parameters you pass in.*/
	public static JTextField setTextField(JTextField tf,int length,Dimension dim,String text,boolean enabled)
	{
		tf = new JTextField(length);
		tf.setText(text);
		tf.setPreferredSize(dim);
		tf.setEnabled(enabled);
		return tf;
	}
	/*this method takes in a text area and sets all the characteristics through the parameters you pass in.*/
	public static JTextArea setTextArea(JTextArea ta,String text,Dimension dim,Font font,Color backCol,Color frontCol, boolean enabled)
	{
		ta = new JTextArea(text);
		ta.setPreferredSize(dim);
		ta.setFont(font);
		ta.setBackground(backCol);
		ta.setForeground(frontCol);
		ta.setEditable(enabled);
		return ta;
		
	}
}
