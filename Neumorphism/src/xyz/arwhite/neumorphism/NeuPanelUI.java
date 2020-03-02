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
	
	private int borderSize = 16; 
	
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
	
	public NeuPanelUI() {
		raisedBorder = new NeuRaisedBorder(borderSize,5);
	}
	
	public static ComponentUI createUI(JComponent c) {
		return neuPanelUI;
	}
	
	public void installUI(JComponent c) {
		super.installUI(c);
		// c.setBorder(raisedBorder);
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
