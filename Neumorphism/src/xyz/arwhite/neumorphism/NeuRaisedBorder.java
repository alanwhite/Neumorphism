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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Dimension2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.border.Border;

public class NeuRaisedBorder implements Border {

	private double scaleFactorX = 1.0f;
	private double scaleFactorY = 1.0f;
	
	private double ourScaleX = 1.0f;
	private double ourScaleY = 1.0f;
	
	private Color riseColor = new Color(255,255,255,128);
	private Color fallColor = new Color(0xd1,0xcd,0xc7, 128);
	
	/*
	 * Image containing neumorphic borders
	 */
	private BufferedImage cachedImage;
	
	// RoundRectangle2D borderRect;
	private Graphics2D ig2D;
	
	private Color mergeColor = new Color(0xef, 0xee, 0xee, 0xff);
	// private Color mergeColor = Color.DARK_GRAY;

	private int borderWidth = 16;
	private double adjBorderWidth = (double)borderWidth * scaleFactorX;
	
	private int shadowOffset = 6;
	private double adjShadowOffset = (double)shadowOffset * scaleFactorX;
	
	private boolean blurShadows = true;
	
	private double adjWidth = 0.0f;
	private double adjHeight = 0.0f;
	
	private double adjInnerWidth = 0.0f;
	private double adjInnerHeight = 0.0f;

	public NeuRaisedBorder(int borderWidth) {
		this.borderWidth = borderWidth;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2D = (Graphics2D) g;

		/*
		 * Determine the scaling the system has directed by applied
		 */
		var deviceTransform = g2D.getDeviceConfiguration().getDefaultTransform();
		scaleFactorX = deviceTransform.getScaleX();
		scaleFactorY = deviceTransform.getScaleY();
		
		/*
		 * As we will be negating the scaling, we'll note the scaled width and height
		 */
		adjWidth = (double)width * scaleFactorX;
		adjHeight = (double)height * scaleFactorY; 
		
		/*
		 * Rebuild the image containing the border shadows if necessary
		 * 
		 * Reasons:
		 * 1. There is no cached image
		 * 2. We've been moved to a monitor with a different scale from the prior one
		 * 3. For some reason the component has changed shape
		 */
		if ( cachedImage == null 
				|| ourScaleX != scaleFactorX || ourScaleY != scaleFactorY 
				|| c.getHeight() != cachedImage.getHeight() 
				|| c.getWidth() != cachedImage.getWidth()) {
			
			/*
			 * Scale the border width and shadow offsets according the device needs
			 */
			adjBorderWidth = (double)borderWidth * scaleFactorX;
			adjShadowOffset = (double)shadowOffset * scaleFactorX;
			
			/*
			 * Size our rectangle that defines the innermost boundary of the border
			 */
			adjInnerWidth = adjWidth - ( 2.0f * adjBorderWidth );
			adjInnerHeight = adjHeight - ( 2.0f * adjBorderWidth );
			
			/*
			 * Note the device scale at which the image needs to be built
			 */
			ourScaleX = scaleFactorX; ourScaleY = scaleFactorY;
			
			/*
			 * Trigger rebuild of background image with new scale
			 */
			cachedImage = null;
		}
		
		/*
		 * Define the outer boundary of our border, scaled for the device
		 */
		RoundRectangle2D outerRect = new RoundRectangle2D.Double(
				x, y, adjWidth ,adjHeight, 32.0f, 32.0f);
		
		/*
		 * Define the inner boundary of our border, scaled for the device
		 */
		RoundRectangle2D innerRect = new RoundRectangle2D.Double(
				x + adjBorderWidth, y + adjBorderWidth, adjInnerWidth ,adjInnerHeight, 32.0f, 32.0f);
		
		/* 
		 * Rebuild the image if required
		 */
		if ( cachedImage == null ) {
			
			BufferedImage rawImage = g2D.getDeviceConfiguration().createCompatibleImage(
					(int)Math.round(adjWidth), (int)Math.round(adjHeight), Transparency.TRANSLUCENT);
			ig2D = rawImage.createGraphics();
			
			/*
			 * Fill the image with the background of the surrounding UI element it's to merge with
			 */
			ig2D.setColor(mergeColor);
			ig2D.fillRect(x, y, rawImage.getWidth(), rawImage.getHeight());

			/*
			 * Draw the background border shapes
			 */
			ig2D.setColor(riseColor);
			ig2D.translate(-adjShadowOffset, -adjShadowOffset);
			ig2D.fill(innerRect);
			
			ig2D.setColor(fallColor);
			ig2D.translate(2.0f*adjShadowOffset, 2.0*adjShadowOffset);
			ig2D.fill(innerRect);
					
			ig2D.dispose();
			
			/*
			 * Blur the resulting image to blend with the background
			 */
			cachedImage = g2D.getDeviceConfiguration().createCompatibleImage(
					rawImage.getWidth(), rawImage.getHeight(), Transparency.TRANSLUCENT);
			
			if ( blurShadows )
				ImageUtils.blur2(rawImage, cachedImage);
			else
				cachedImage = rawImage;
			
//			/*
//			 * Add 1px border to the button
//			 */
//			borderRect = new RoundRectangle2D.Double(bx, by, bw, bh, 12.0f, 12.0f);
//			Graphics2D blur2D = blurredImage.createGraphics();
//			blur2D.fill(borderRect);
//			blur2D.dispose();

		}
		
		/*
		 * Set the clip shape to only draw outside the actual button
		 */
		var saveTransform = g2D.getTransform();
		
		AffineTransform ourTransform = g2D.getTransform();
		ourTransform.scale(1.0f/ourScaleX, 1.0f/ourScaleY);
		g2D.setTransform(ourTransform);		

		Area allArea = new Area(outerRect);
		Area panelArea = new Area(innerRect);
		allArea.subtract(panelArea);
		
		var saveClip = g2D.getClip();
		g2D.setClip(allArea);

		/*
		 * Finally render our neumorphic border image, clipped as needed
		 */
		g2D.drawImage(cachedImage, x, y, null);
		
		g2D.setClip(saveClip);
		g2D.setTransform(saveTransform);
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(borderWidth,borderWidth,borderWidth,borderWidth);
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
	}
	
	public boolean isBlurShadows() {
		return blurShadows;
	}

	public void setBlurShadows(boolean blurShadows) {
		if ( blurShadows != this.blurShadows )
			cachedImage = null;
		
		this.blurShadows = blurShadows;
	}

	public Color getRiseColor() {
		return riseColor;
	}

	public void setRiseColor(Color riseColor) {
		if ( riseColor != this.riseColor )
			cachedImage = null;
		
		this.riseColor = riseColor;
	}

	public Color getFallColor() {
		return fallColor;
	}

	public void setFallColor(Color fallColor) {
		if ( fallColor != this.fallColor )
			cachedImage = null;
		
		this.fallColor = fallColor;
	}

	public Color getMergeColor() {
		return mergeColor;
	}

	public void setMergeColor(Color mergeColor) {
		if ( mergeColor != this.mergeColor )
			cachedImage = null;
		
		this.mergeColor = mergeColor;
	}

	public int getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(int borderWidth) {
		if ( borderWidth != this.borderWidth )
			cachedImage = null;
		
		this.borderWidth = borderWidth;
	}

	public int getShadowOffset() {
		return shadowOffset;
	}

	public void setShadowOffset(int shadowOffset) {
		if ( shadowOffset != this.shadowOffset )
			cachedImage = null;
		
		this.shadowOffset = shadowOffset;
	}
}
