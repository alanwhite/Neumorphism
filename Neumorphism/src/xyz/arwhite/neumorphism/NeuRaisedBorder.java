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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.border.Border;

public class NeuRaisedBorder implements Border {

	private static Color riseColor = new Color(0xff,0xff,0xff, 0x66);
	private static Color fallColor = new Color(0xd1,0xcd,0xc7, 0x66);
	
	private BufferedImage image;
	private BufferedImage blurredImage;
	private Graphics2D ig2D;
	
	private Color mergeColor = new Color(0xef, 0xee, 0xee, 0xff);
	
	private int borderWidth = 16;
	private int shadowOffset = 6;
	
	public NeuRaisedBorder() {
		
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2D = (Graphics2D) g;
		
		int bx = x + borderWidth;
		int by = y + borderWidth;
		int bw = width-(2*borderWidth);
		int bh = height-(2*borderWidth);
		
		if ( image == null || c.getHeight() != image.getHeight() || c.getWidth() != image.getWidth() ) {
			System.out.println(this.getClass().getName()+" creating raised border image");
			image = g2D.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
			blurredImage = g2D.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
			
			ig2D = image.createGraphics();
			
			/*
			 * Fill the image with the background of the surrounding UI element it's to merge with
			 */
			ig2D.setColor(mergeColor);
			ig2D.fillRect(x, y, width, height);
		
			/*
			 * Create the shapes for the highlight and drop shadow
			 */
			RoundRectangle2D riseRect = new RoundRectangle2D.Double(bx-shadowOffset,by-shadowOffset,bw,bh,12.0f,12.0f);
			RoundRectangle2D fallRect = new RoundRectangle2D.Double(bx+shadowOffset,by+shadowOffset,bw,bh,12.0f,12.0f);

			ig2D.setColor(riseColor);
			ig2D.fill(riseRect);
			
			ig2D.setColor(fallColor);
			ig2D.fill(fallRect);
			
			/*
			 * Blur the resulting image to blend with the background
			 */
			ImageUtils.blur2(image, blurredImage);
		}
		
		/*
		 * Set the clip shape to only draw outside the actual button
		 */
		RoundRectangle2D allRect = new RoundRectangle2D.Double(x, y, width, height, 12.0f, 12.0f);
		RoundRectangle2D panelRect = new RoundRectangle2D.Double(bx, by, bw, bh, 12.0f, 12.0f);
		Area allArea = new Area(allRect);
		Area panelArea = new Area(panelRect);
		allArea.subtract(panelArea);
		
		var saveClip = g2D.getClip();
		g2D.setClip(allArea);
		
		g2D.drawImage(blurredImage, x, y, null);
		
		g2D.setClip(saveClip);
		
		/*
		 * Design says there's a small transparent(ish) border round the button
		 * Note this depends on the button content not drawing a background as 
		 * this technically draws in it's space
		 */
		Stroke saveStroke = g2D.getStroke();
		Color saveColor = g2D.getColor();
		
		g2D.setStroke(new BasicStroke(1));
		g2D.setColor(new Color(1.0f,1.0f,1.0f,0.2f));
		g2D.draw(panelRect);
		
		g2D.setColor(saveColor);
		g2D.setStroke(saveStroke);
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(borderWidth,borderWidth,borderWidth,borderWidth);
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
	}
	
}
