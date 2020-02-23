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

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class ImageUtils {
	
//	static float guassianKernel[] = {
//			0.00134f, 0.00408f, 0.00794f, 0.00992f, 0.00794f, 0.00408f, 0.00134f, 
//			0.00408f, 0.01238f, 0.02412f, 0.03012f, 0.02412f, 0.01238f, 0.00408f, 
//			0.00794f, 0.02412f, 0.04698f, 0.05867f, 0.04698f, 0.02412f, 0.00794f, 
//			0.00992f, 0.03012f, 0.05867f, 0.07327f, 0.05867f, 0.03012f, 0.00992f, 
//			0.00794f, 0.02412f, 0.04698f, 0.05867f, 0.04698f, 0.02412f, 0.00794f, 
//			0.00408f, 0.01238f, 0.02412f, 0.03012f, 0.02412f, 0.01238f, 0.00408f, 
//			0.00134f, 0.00408f, 0.00794f, 0.00992f, 0.00794f, 0.00408f, 0.00134f 
//	};
	
	static float guassianKernel[] = {	

			0.020013f, 	0.020209f, 	0.020328f, 	0.020367f, 	0.020328f, 	0.020209f, 	0.020013f, 
			0.020209f, 	0.020407f, 	0.020527f, 	0.020567f, 	0.020527f, 	0.020407f, 	0.020209f, 
			0.020328f, 	0.020527f, 	0.020648f, 	0.020688f, 	0.020648f, 	0.020527f, 	0.020328f, 
			0.020367f, 	0.020567f, 	0.020688f, 	0.020729f, 	0.020688f, 	0.020567f, 	0.020367f, 
			0.020328f, 	0.020527f, 	0.020648f, 	0.020688f, 	0.020648f, 	0.020527f, 	0.020328f, 
			0.020209f, 	0.020407f, 	0.020527f, 	0.020567f, 	0.020527f, 	0.020407f, 	0.020209f, 
			0.020013f, 	0.020209f, 	0.020328f, 	0.020367f, 	0.020328f, 	0.020209f, 	0.020013f

	};
	
	public static BufferedImage blur2(BufferedImage image, BufferedImage blurredImage) {
		
		Kernel kernel = new Kernel(7,7,guassianKernel);
		ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		convolve.filter(image, blurredImage);
		
		return blurredImage;
	}
}
