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
import java.awt.image.BufferedImage;

public class NeuRaisedBorder extends NeuAbstractBorder {

	@Override
	public void buildImages(Graphics2D g2D) {
		buildRaisedImage(g2D);
	}

	private void buildRaisedImage(Graphics2D g2D) {

		BufferedImage rawImage = g2D.getDeviceConfiguration().createCompatibleImage(
				(int)Math.round(adjWidth), (int)Math.round(adjHeight), Transparency.TRANSLUCENT);
		Graphics2D ig2D = rawImage.createGraphics();
		
		/*
		 * Fill the image with the background of the surrounding UI element it's to merge with
		 */
		ig2D.setColor(mergeColor);
		// ig2D.setColor(Color.red);
		ig2D.fillRect(0, 0, rawImage.getWidth(), rawImage.getHeight());

		/*
		 * Make sure we don't paint inside the margin
		 */

		Area allArea = new Area(outerRect);
		Area marginArea = new Area(marginRect);
		allArea.subtract(marginArea);
		
		// ig2D.setClip(allArea);
		
		/*
		 * Draw the background border shapes
		 */
		ig2D.setColor(riseColor);
		ig2D.translate(-adjShadowSize, -adjShadowSize);
		ig2D.fill(marginRect);

		ig2D.setColor(fallColor);
		ig2D.translate(2.0f*adjShadowSize, 2.0*adjShadowSize);
		ig2D.fill(marginRect);

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

		//		/*
		//		 * Add 1px border to the button
		//		 */
		//		borderRect = new RoundRectangle2D.Double(bx, by, bw, bh, 12.0f, 12.0f);
		//		Graphics2D blur2D = blurredImage.createGraphics();
		//		blur2D.fill(borderRect);
		//		blur2D.dispose();

	}

	public NeuRaisedBorder(int borderSize, int marginSize) {
		super(borderSize, marginSize);
	}



}
