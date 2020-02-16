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

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

public class NeuButtonUI extends BasicButtonUI {

	private final static NeuButtonUI neuButtonUI = new NeuButtonUI();

	private int borderWidth = 16;
	
	@Override
	public Dimension getPreferredSize(JComponent c) {
		 var sz = super.getPreferredSize(c);
		 
		 if ( sz.width < 32 )
			 sz.width = 32;
		 
		 if ( sz.height < 32 )
			 sz.height = 32;
		 
		 sz.width += 2 * borderWidth;
		 sz.height += 2 * borderWidth;
		 
		 return sz;
	}

	public static ComponentUI createUI(JComponent c) {
		return neuButtonUI;
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		
		c.setBorder(new NeuRaisedBorder());

//		c.addMouseListener(this);
//		c.addKeyListener(this);
	}

	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
		
//		c.removeMouseListener(this);
//		c.removeKeyListener(this);
	}
}
