/*
	MIT License

	Copyright (c) 2020 Alan Raymond White (alan@whitemail.net)

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
 */

package xyz.arwhite.neumorphism;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicToggleButtonUI;

public class NeuToggleButtonUI extends BasicToggleButtonUI implements MouseListener {

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
	}

	public static Color dsBlue = new Color(4,113,186);
	public static Color dsRed = new Color(0xc2186b);

	private final static NeuToggleButtonUI neuButtonUI = new NeuToggleButtonUI();
	private NeuRaisedBorder raisedBorder;
	private NeuLoweredBorder loweredBorder;
	private Component parent;

	private int borderSize = 16; 
	private int marginSize =5;

	public NeuToggleButtonUI() {
		raisedBorder = new NeuRaisedBorder(borderSize, marginSize);
		loweredBorder = new NeuLoweredBorder(borderSize, marginSize);
	}

	public static ComponentUI createUI(JComponent c) {
		return neuButtonUI;
	}

	public void installUI(JComponent c) {
		super.installUI(c);

		if ( !(c instanceof JToggleButton) ) 
			return;
		
		setBorderByState((JToggleButton) c);
		parent = c;
		c.setFont(new Font("Roboto", Font.PLAIN, 16));
		c.setForeground(dsBlue);
		((AbstractButton)c).setIconTextGap(5);

		c.addMouseListener(this);
		//		c.addKeyListener(this);
	}
	
	private void setBorderByState(JToggleButton btn) {
		if ( btn.isSelected() )
			btn.setBorder(loweredBorder);
		else
			btn.setBorder(raisedBorder);
	}

	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);

		c.removeMouseListener(this);
		//		c.removeKeyListener(this);
	}

	public NeuRaisedBorder getRaisedBorder() {
		return raisedBorder;
	}

	public int getBorderSize() {
		return borderSize;
	}

	public void setBorderSize(int borderSize) {
		raisedBorder.setBorderSize(borderSize);
		this.borderSize = borderSize;
		parent.revalidate();
	}

	public int getMarginSize() {
		return marginSize;
	}

	public void setMarginSize(int marginSize) {
		this.marginSize = marginSize;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		setBorderByState((JToggleButton) e.getSource());
		// ((JComponent)e.getSource()).setBorder(loweredBorder);
	}
	

	@Override
	public void mouseReleased(MouseEvent e) {
		setBorderByState((JToggleButton) e.getSource());
		// ((JComponent)e.getSource()).setBorder(raisedBorder);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JToggleButton btn = (JToggleButton) e.getSource();
		if ( !btn.isSelected() ) {
			raisedBorder.setShadowOffset(
					raisedBorder.getShadowOffset() + 2);
			((Component)e.getSource()).setForeground(dsRed);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JToggleButton btn = (JToggleButton) e.getSource();
		if ( !btn.isSelected() ) 
			raisedBorder.setShadowOffset(
					raisedBorder.getShadowOffset() - 2);

		((Component)e.getSource()).setForeground(dsBlue);
	}

}
