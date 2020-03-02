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

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class NeuLoweredBorder extends NeuAbstractBorder {

	private RoundRectangle2D outerMargin = new RoundRectangle2D.Double();
	
	@Override
	public void buildImages(Graphics2D g2D) {
		buildRaisedImage(g2D);
	}

	private void buildRaisedImage(Graphics2D g2D) {

		BufferedImage rawImage = g2D.getDeviceConfiguration().createCompatibleImage(
				(int)Math.round(adjWidth), (int)Math.round(adjHeight), Transparency.TRANSLUCENT);
		var ig2D = rawImage.createGraphics();
		
		/*
		 * define the outside of the shadow area
		 */
		outerMargin.setRoundRect(
				marginRect.getX()-adjShadowSize, marginRect.getX()-adjShadowSize,
				marginRect.getWidth()+(2.0f*adjShadowSize), marginRect.getHeight()+(2.0f*adjShadowSize),
				curves, curves);
		
		/*
		 * top left color
		 */
		var shape = new Path2D.Double();
		shape.moveTo(outerMargin.getX(), outerMargin.getY());
		shape.lineTo(outerMargin.getMaxX(), outerMargin.getY());
		shape.lineTo(outerMargin.getX(), outerMargin.getMaxY());
		shape.closePath();
		
		ig2D.setColor(fallColor);
		ig2D.fill(shape);
		
		shape.reset();
		shape.moveTo(0, 0);
		shape.lineTo(outerRect.getMaxX(), 0);
		shape.lineTo(outerMargin.getMaxX(), outerMargin.getY());
		shape.lineTo(outerMargin.getX(), outerMargin.getY());
		shape.lineTo(outerMargin.getX(), outerMargin.getMaxY());
		shape.lineTo(0, outerRect.getMaxY());
		shape.closePath();
		ig2D.fill(shape);
		
		/*
		 * bottom right color
		 */
		shape.reset();
		shape.moveTo(outerMargin.getMaxX(), outerMargin.getY());
		shape.lineTo(outerMargin.getX(), outerMargin.getMaxY());
		shape.lineTo(outerMargin.getMaxX(), outerMargin.getMaxY());
		shape.closePath();
		
		ig2D.setColor(riseColor);
		ig2D.fill(shape);
		
		shape.reset();
		shape.moveTo(outerRect.getMaxX(), 0);
		shape.lineTo(outerMargin.getMaxX(), outerMargin.getY());
		shape.lineTo(outerMargin.getMaxX(), outerMargin.getMaxY());
		shape.lineTo(outerMargin.getX(), outerMargin.getMaxY());
		shape.lineTo(0, outerRect.getMaxY());
		shape.lineTo(outerRect.getMaxX(), outerRect.getMaxY());
		shape.closePath();
		ig2D.fill(shape);
		
		/*
		 * paint the central margin rect in the merge colour
		 */
		ig2D.setColor(mergeColor);
		ig2D.fill(marginRect);
		
		ig2D.dispose();
		
		/*
		 * blur it
		 */
		cachedImage = g2D.getDeviceConfiguration().createCompatibleImage(
				rawImage.getWidth(), rawImage.getHeight(), Transparency.TRANSLUCENT);

		if ( blurShadows )
			ImageUtils.blur2(rawImage, cachedImage);
		else
			cachedImage = rawImage;
		
		/*
		 * paint the outside of the shadow space
		 */

		Area allArea = new Area(outerRect);
		Area shadowArea = new Area(outerMargin);
		allArea.subtract(shadowArea);
		
		var cg2D = cachedImage.createGraphics();
		cg2D.setClip(allArea);
		cg2D.setColor(mergeColor);
		cg2D.fill(outerRect);
		cg2D.dispose();

		//		/*
		//		 * Add 1px border to the button
		//		 */
		//		borderRect = new RoundRectangle2D.Double(bx, by, bw, bh, 12.0f, 12.0f);
		//		Graphics2D blur2D = blurredImage.createGraphics();
		//		blur2D.fill(borderRect);
		//		blur2D.dispose();

	}

	public NeuLoweredBorder(int borderSize, int marginSize) {
		super(borderSize, marginSize);
	}



}
