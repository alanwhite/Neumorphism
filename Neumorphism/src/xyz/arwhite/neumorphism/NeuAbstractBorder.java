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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.border.Border;

public abstract class NeuAbstractBorder implements Border {

	private double scaleFactorX = 1.0f;
	private double scaleFactorY = 1.0f;

	private double ourScaleX = 1.0f;
	private double ourScaleY = 1.0f;

	protected Color riseColor = new Color(255,255,255,128);
	protected Color fallColor = new Color(0xd1,0xcd,0xc7, 128);

	/*
	 * Image containing neumorphic borders
	 */
	protected BufferedImage cachedImage;

	protected Color mergeColor = new Color(0xef, 0xee, 0xee, 0xff);

	private int borderSize = 16;
	private double adjBorderSize = (double)borderSize * scaleFactorX;

	private int marginSize = 5;

	private double adjMarginSize = (double)marginSize * scaleFactorX;

	private int shadowSize = 3;
	protected double adjShadowSize = (double)shadowSize * scaleFactorX;

	protected boolean blurShadows = true;

	protected double curves = 32.0f;
	private double adjCurves = curves * scaleFactorX;

	/*
	 * Rounded rectangles used to define overall shape
	 */
	Rectangle2D outerRect = new Rectangle2D.Double();
	RoundRectangle2D marginRect = new RoundRectangle2D.Double();
	RoundRectangle2D buttonRect = new RoundRectangle2D.Double();

	/*
	 * Dimensions adjusted for screen scale
	 */
	protected double adjWidth = 0.0f;
	protected double adjHeight = 0.0f;

	protected double adjMarginWidth = 0.0f;
	protected double adjMarginHeight = 0.0f;

	protected double adjButtonWidth = 0.0f;
	protected double adjButtonHeight = 0.0f;

	public NeuAbstractBorder(int borderSize, int marginSize) {
		this.borderSize = borderSize;
		this.adjMarginSize = marginSize;
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
				|| (int)Math.round(adjHeight) != cachedImage.getHeight() 
				|| (int)Math.round(adjWidth) != cachedImage.getWidth()) {

			/*
			 * Scale the border width and shadow offsets according the device needs
			 */
			adjBorderSize = (double)borderSize * scaleFactorX;
			adjMarginSize = (double)marginSize * scaleFactorX;
			adjShadowSize = (double)shadowSize * scaleFactorX;
			adjCurves = curves * scaleFactorX;

			/*
			 * Size our rectangle that defines the boundary of the margin
			 */
			adjMarginWidth = adjWidth - ( 2.0f * adjBorderSize );
			adjMarginHeight = adjHeight - ( 2.0f * adjBorderSize );

			/*
			 * Size our rectangle that defines the boundary of the button
			 */
			adjButtonWidth = adjMarginWidth - ( 2.0f * adjMarginSize );
			adjButtonHeight = adjMarginHeight - ( 2.0f * adjMarginSize );

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
		outerRect.setFrame(x, y, adjWidth ,adjHeight);

		/*
		 * Define the margin boundary of our border, scaled for the device
		 */
		marginRect.setRoundRect(x + adjBorderSize, y + adjBorderSize, adjMarginWidth ,adjMarginHeight, curves, curves);

		/*
		 * Define the boundary of the actual button we serve
		 */
		buttonRect.setRoundRect(x + adjBorderSize + adjMarginSize, y + adjBorderSize + adjMarginSize, adjButtonWidth ,adjButtonHeight, curves, curves);
		
		/* 
		 * Rebuild the image if required
		 */
		if ( cachedImage == null ) {
			buildImages(g2D);
		}

		/*
		 * Set the clip shape to only draw outside the actual button
		 */
		var saveTransform = g2D.getTransform();

		var saveStroke = g2D.getStroke();
		var saveColor = g2D.getColor();

		g2D.setStroke(new BasicStroke(0.1f));

		AffineTransform ourTransform = g2D.getTransform();
		ourTransform.scale(1.0f/ourScaleX, 1.0f/ourScaleY);
		g2D.setTransform(ourTransform);		

		Area allArea = new Area(outerRect);
		// Area buttonArea = new Area(buttonRect);
		Area marginArea = new Area(marginRect);
		allArea.subtract(marginArea);

		var saveClip = g2D.getClip();
		g2D.setClip(allArea);

		/*
		 * Finally render our neumorphic border image, clipped as needed
		 */
		g2D.drawImage(cachedImage, x, y, null);
//				g2D.setColor(Color.green);
//				g2D.draw(outerRect);
//				g2D.setColor(Color.red);
//				g2D.draw(marginRect);
//				g2D.setColor(Color.cyan);
//				g2D.draw(buttonRect);

		g2D.setColor(saveColor);
		g2D.setStroke(saveStroke);
		g2D.setTransform(saveTransform);
		g2D.setClip(saveClip);
	}

	public abstract void buildImages(Graphics2D g2D);

	@Override
	public Insets getBorderInsets(Component c) {
		int size = borderSize+marginSize;
		return new Insets(size,size,size,size);
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

	public int getBorderSize() {
		return borderSize;
	}

	public void setBorderSize(int borderSize) {
		if ( borderSize != this.borderSize )
			cachedImage = null;

		this.borderSize = borderSize;
	}

	public int getMarginSize() {
		return marginSize;
	}

	public void setMarginSize(int marginSize) {
		this.marginSize = marginSize;
	}

	public int getShadowOffset() {
		return shadowSize;
	}

	public void setShadowOffset(int shadowOffset) {
		if ( shadowOffset != this.shadowSize )
			cachedImage = null;

		this.shadowSize = shadowOffset;
	}
}
