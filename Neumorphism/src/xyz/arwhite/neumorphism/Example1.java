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

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BaseMultiResolutionImage;
import java.awt.image.MultiResolutionImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;

@SuppressWarnings("serial")
public class Example1 extends JFrame {

	JButton btnCompose = new JButton("Compose ", new ImageIcon(loadNewBtnImage("new-score",32)));	
	JButton btnOpen = new JButton("Open ", new ImageIcon(loadNewBtnImage("open-score",32)));
	JToggleButton btnBold = new JToggleButton("Bold");
	AbstractButton[] buttons = { btnCompose, btnOpen, btnBold };

	ComponentSettingsCard buttonCard = new ComponentSettingsCard();
	ComponentSettingsCard panelCard = new ComponentSettingsCard();
	ComponentSettingsCard[] cards = { buttonCard, panelCard };

	Color backgroundColor = new Color(0xef, 0xee, 0xee, 0xff);

	String currentScreenDevice = "";
	double scaleX = 1.0f;
	double scaleY = 1.0f;

	/*
	 * Tersest possible Swingy stuff
	 */
	private JPanel createContentPane() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar(); contentPane.add(toolBar, BorderLayout.NORTH);
		JPanel mainPanel = new JPanel(); contentPane.add(mainPanel, BorderLayout.CENTER);
		JLabel props = new JLabel(">>>> Mega thank you to https://icons8.com for the royalty free icons <<<<");
		props.setFont(new Font("Roboto", Font.PLAIN, 16)); props.setForeground(Color.DARK_GRAY);
		props.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		contentPane.add(props, BorderLayout.SOUTH);

		toolBar.setBackground(backgroundColor); toolBar.setFloatable(false);
		toolBar.add(Box.createHorizontalGlue()); toolBar.add(btnCompose);
		toolBar.add(Box.createHorizontalGlue()); toolBar.add(btnOpen);
		toolBar.add(Box.createHorizontalGlue()); toolBar.add(btnBold);
		toolBar.add(Box.createHorizontalGlue());

		mainPanel.setLayout(new BorderLayout()); 
		JPanel mainRow1 = new JPanel(); 
		mainRow1.setOpaque(true); 
		mainRow1.setBackground(backgroundColor);
		mainPanel.add(mainRow1, BorderLayout.CENTER);

		buttonCard.getTitleLabel().setText("Button Settings");
		mainRow1.add(buttonCard);

		panelCard.getTitleLabel().setText("Panel Settings");
		mainRow1.add(panelCard);

		return contentPane;
	}

	public Example1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(createContentPane());

		btnCompose.setUI(new NeuButtonUI());
		btnOpen.setUI(new NeuButtonUI());
		btnBold.setUI(new NeuToggleButtonUI());

		btnCompose.addActionListener(System.out::println);
		btnOpen.addActionListener(System.out::println);
		btnBold.addActionListener(System.out::println);

		/*
		 * Button Customisation Controls
		 */
		buttonCard.setUI(new NeuPanelUI());
		buttonCard.getBorderSlider().addChangeListener(l -> {
			if (!buttonCard.getBorderSlider().getValueIsAdjusting()) {
				for ( int i = 0; i < buttons.length; i++ ) 
					((NeuButtonUI)buttons[i].getUI()).setBorderSize(buttonCard.getBorderSlider().getValue());
			}
		});

		buttonCard.getShadowSlider().addChangeListener(l -> {
			if (!buttonCard.getShadowSlider().getValueIsAdjusting()) {
				for ( int i = 0; i < buttons.length; i++ ) {
					NeuRaisedBorder border = (NeuRaisedBorder) buttons[i].getBorder();
					border.setShadowOffset(buttonCard.getShadowSlider().getValue());
					buttons[i].repaint();
				}
			}
		});

		buttonCard.getBlurCheckBox().addActionListener(l -> {
			for ( int i = 0; i < buttons.length; i++ ) {
				CompoundBorder cBorder = (CompoundBorder)buttons[i].getBorder();
				NeuRaisedBorder border = (NeuRaisedBorder) cBorder.getOutsideBorder();
				border.setBlurShadows(buttonCard.getBlurCheckBox().isSelected());
				buttons[i].repaint();
			}
		});

		/*
		 * Panel Customisation Controls
		 */
		panelCard.setUI(new NeuPanelUI());
		panelCard.getBorderSlider().addChangeListener(l -> {
			if (!panelCard.getBorderSlider().getValueIsAdjusting()) {
				for ( int i = 0; i < cards.length; i++ ) {
					((NeuPanelUI)cards[i].getUI()).setBorderSize(panelCard.getBorderSlider().getValue());
					//					NeuRaisedBorder border = (NeuRaisedBorder) cards[i].getBorder();
					//					border.setBorderWidth(panelCard.getBorderSlider().getValue());
					//					cards[i].repaint();
				}
			}
		});

		panelCard.getShadowSlider().addChangeListener(l -> {
			if (!panelCard.getShadowSlider().getValueIsAdjusting()) {
				for ( int i = 0; i < cards.length; i++ ) {
					CompoundBorder cBorder = (CompoundBorder)cards[i].getBorder();
					NeuRaisedBorder border = (NeuRaisedBorder) cBorder.getOutsideBorder();
					border.setShadowOffset(panelCard.getShadowSlider().getValue());
					cards[i].repaint();
				}
			}
		});

		panelCard.getBlurCheckBox().addActionListener(l -> {
			for ( int i = 0; i < cards.length; i++ ) {
				CompoundBorder cBorder = (CompoundBorder)cards[i].getBorder();
				NeuRaisedBorder border = (NeuRaisedBorder) cBorder.getOutsideBorder();
				border.setBlurShadows(panelCard.getBlurCheckBox().isSelected());
				cards[i].repaint();
			}
		});

		setSize(800,600);
		setVisible(true); 
	}

	/**
	 * Creates a MultiResolutionImage from resources using the provided name, suffixed by a "-" then the provided size, and also
	 * at sizes 1.25, 1.5 and 2.0 times larger. The filenames must be structured as per name-32.png, name-40.png, name-48.png and name-64.png
	 * for a given name of 'name' and size of '32'. 
	 * @param name the name of the image
	 * @param size the preferred starting size of the image
	 * @return
	 */
	private BaseMultiResolutionImage loadNewBtnImage(String name, int size) {
		List<Image> imgList = new ArrayList<Image>();
		int[] sizes = { size, size+(size/4), size+(size/2), size*2 };
		final MediaTracker mt = new MediaTracker(new Canvas());

		for ( int i = 0; i < sizes.length; i++ ) {
			try {
				Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(name+"-"+sizes[i]+".png"));
				imgList.add(img);
				mt.addImage(img, i);
			} catch (Exception e) {}
		}

		if ( imgList.isEmpty() )
			return null;

		try {
			mt.waitForAll();
		} catch (InterruptedException e) {
			throw new RuntimeException("Unexpected interrupt ", e);
		}

		if (mt.isErrorAny()) {
			throw new RuntimeException("Unexpected MediaTracker error " +
					Arrays.toString(mt.getErrorsAny()));
		}

		BaseMultiResolutionImage mrImage = new BaseMultiResolutionImage(imgList.toArray(new Image[0]));
		return mrImage;

	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getIDstring();
		var g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		var d = g.getScreenDevices();

		for ( int i = 0; i < d.length; i++ ) {
			var at = d[i].getDefaultConfiguration().getDefaultTransform();
			System.out.println("Device "+i+": default scale x="+at.getScaleX()+" y="+at.getScaleY());
			var db = d[i].getDefaultConfiguration().getBounds();

			System.out.println("\tSize "+db.width+"x"+db.height);
			System.out.println("\tReal "+at.getScaleX()*db.getWidth()+"x"+at.getScaleY()*db.getHeight());
		}

		SwingUtilities.invokeLater(() -> new Example1());
	}

}
