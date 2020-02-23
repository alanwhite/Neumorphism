package xyz.arwhite.neumorphism;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;

public class NeuPanelUI extends BasicPanelUI {

	private final static NeuPanelUI neuPanelUI = new NeuPanelUI();
	
	private NeuRaisedBorder raisedBorder;
	private Component parent;
	
	private int borderWidth = 16; 
	
	public NeuRaisedBorder getRaisedBorder() {
		return raisedBorder;
	}
	
	public int getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(int borderWidth) {
		raisedBorder.setBorderWidth(borderWidth);
		this.borderWidth = borderWidth;
		parent.revalidate();
	}
	
	public NeuPanelUI() {
		raisedBorder = new NeuRaisedBorder(borderWidth);
	}
	
	public static ComponentUI createUI(JComponent c) {
		return neuPanelUI;
	}
	
	public void installUI(JComponent c) {
		super.installUI(c);
		
		c.setBorder(BorderFactory.createCompoundBorder(raisedBorder, new EmptyBorder(5,5,5,5)));
		parent = c;

//		c.addMouseListener(this);
//		c.addKeyListener(this);
	}

	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
		
//		c.removeMouseListener(this);
//		c.removeKeyListener(this);
	}
}
