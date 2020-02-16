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
import java.awt.Color;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class Example1 extends JFrame {
	
	JButton btnButton = new JButton("Button 1");
	JButton btnButton_1 = new JButton("Button 2");
	JButton btnButton_2 = new JButton("Button 3");
	
	class ContentPane extends JPanel {
		
		public ContentPane() {
			setLayout(new BorderLayout(0, 0));
			setBackground(Color.white);
			
			JToolBar toolBar = new JToolBar();
			toolBar.setBackground(new Color(0xef, 0xee, 0xee, 0xff));
			toolBar.setFloatable(false);
			add(toolBar, BorderLayout.NORTH);
			
			toolBar.add(Box.createHorizontalGlue());
			toolBar.add(btnButton);
			toolBar.add(Box.createHorizontalGlue());
			toolBar.add(btnButton_1);
			toolBar.add(Box.createHorizontalGlue());
			toolBar.add(btnButton_2);
			toolBar.add(Box.createHorizontalGlue());
		}
	}

	public Example1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(new ContentPane());
		
		btnButton.setUI(new NeuButtonUI());
		btnButton_1.setUI(new NeuButtonUI());
		btnButton_2.setUI(new NeuButtonUI());
		
		btnButton.addActionListener(System.out::println);
		btnButton_1.addActionListener(System.out::println);
		btnButton_2.addActionListener(System.out::println);
		
		setSize(640,480);
		setVisible(true); 
	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Example1(); 
			}
		});
	}

}
