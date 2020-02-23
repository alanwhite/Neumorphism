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

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

public class NeuButtonUI extends BasicButtonUI {


	private final static NeuButtonUI neuButtonUI = new NeuButtonUI();
	private NeuRaisedBorder raisedBorder;
	private Component parent;

	private int borderWidth = 16; 



	public NeuButtonUI() {
		raisedBorder = new NeuRaisedBorder(borderWidth);
	}

//	@Override
//	protected void paintIcon(Graphics g, JComponent c, Rectangle iconRect) {
//		// super.paintIcon(g, c, iconRect);
//		AbstractButton b = (AbstractButton) c;
//		// ButtonModel model = b.getModel();
//		Icon icon = b.getIcon();
//
//		if(icon == null) {
//			System.out.println("no icon");
//			return;
//		}
//
//		Graphics2D g2D = (Graphics2D) g;
//		var defTransform = g2D.getDeviceConfiguration().getDefaultTransform();
//
//		if ( defTransform.getScaleX() != 1.0f || defTransform.getScaleY() != 1.0f ) {
//			System.out.println("need to scale");
//			var saveTransform = g2D.getTransform();
//			g2D.scale(1.0f/defTransform.getScaleX(), 1.0f/defTransform.getScaleY());
//			icon.paintIcon(c, g2D, iconRect.x, iconRect.y);
//			g2D.setTransform(saveTransform);
//		} else
//			icon.paintIcon(c, g, iconRect.x, iconRect.y);
//	}


	public static ComponentUI createUI(JComponent c) {
		return neuButtonUI;
	}

	public void installUI(JComponent c) {
		super.installUI(c);

		c.setBorder(BorderFactory.createCompoundBorder(raisedBorder, new EmptyBorder(5,5,5,5)));
		parent = c;
		c.setFont(new Font("Roboto", Font.PLAIN, 16));
		c.setForeground(Color.DARK_GRAY);
		((AbstractButton)c).setIconTextGap(5);

		//		c.addMouseListener(this);
		//		c.addKeyListener(this);
	}

	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);

		//		c.removeMouseListener(this);
		//		c.removeKeyListener(this);
	}

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
}
