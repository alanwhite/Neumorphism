package xyz.arwhite.neumorphism;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import java.awt.Color;

public class ComponentSettingsCard extends JPanel {
	private JSlider borderSlider;
	private JSlider shadowSlider;
	private JCheckBox blurCheckBox;
	private JLabel TitleLabel;

	/**
	 * Create the panel.
	 */
	public ComponentSettingsCard() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		TitleLabel = new JLabel("Button Settings");
		TitleLabel.setForeground(Color.DARK_GRAY);
		panel.add(TitleLabel, BorderLayout.NORTH);
		TitleLabel.setFont(new Font("Roboto Black", Font.PLAIN, 20));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel = new JLabel("Border Size");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		borderSlider = new JSlider();
		lblNewLabel.setLabelFor(borderSlider);
		borderSlider.setValue(16);
		borderSlider.setMaximum(64);
		borderSlider.setMinimum(8);
		GridBagConstraints gbc_borderSlider = new GridBagConstraints();
		gbc_borderSlider.insets = new Insets(0, 0, 5, 0);
		gbc_borderSlider.anchor = GridBagConstraints.WEST;
		gbc_borderSlider.gridx = 1;
		gbc_borderSlider.gridy = 0;
		panel_1.add(borderSlider, gbc_borderSlider);
		
		JLabel lblShadowDistance = new JLabel("Shadow Distance");
		lblShadowDistance.setForeground(Color.DARK_GRAY);
		lblShadowDistance.setLabelFor(lblShadowDistance);
		lblShadowDistance.setFont(new Font("Roboto", Font.PLAIN, 16));
		GridBagConstraints gbc_lblShadowDistance = new GridBagConstraints();
		gbc_lblShadowDistance.anchor = GridBagConstraints.EAST;
		gbc_lblShadowDistance.insets = new Insets(0, 0, 5, 5);
		gbc_lblShadowDistance.gridx = 0;
		gbc_lblShadowDistance.gridy = 1;
		panel_1.add(lblShadowDistance, gbc_lblShadowDistance);
		
		shadowSlider = new JSlider();
		shadowSlider.setValue(6);
		shadowSlider.setMaximum(60);
		GridBagConstraints gbc_shadowSlider = new GridBagConstraints();
		gbc_shadowSlider.insets = new Insets(0, 0, 5, 0);
		gbc_shadowSlider.anchor = GridBagConstraints.WEST;
		gbc_shadowSlider.gridx = 1;
		gbc_shadowSlider.gridy = 1;
		panel_1.add(shadowSlider, gbc_shadowSlider);
		
		JLabel lblBlur = new JLabel("Blur");
		lblBlur.setForeground(Color.DARK_GRAY);
		lblBlur.setFont(new Font("Roboto", Font.PLAIN, 16));
		GridBagConstraints gbc_lblBlur = new GridBagConstraints();
		gbc_lblBlur.anchor = GridBagConstraints.EAST;
		gbc_lblBlur.insets = new Insets(0, 0, 0, 5);
		gbc_lblBlur.gridx = 0;
		gbc_lblBlur.gridy = 2;
		panel_1.add(lblBlur, gbc_lblBlur);
		
		blurCheckBox = new JCheckBox("");
		lblBlur.setLabelFor(blurCheckBox);
		blurCheckBox.setSelected(true);
		blurCheckBox.setFont(new Font("Roboto", Font.PLAIN, 12));
		GridBagConstraints gbc_blurCheckBox = new GridBagConstraints();
		gbc_blurCheckBox.anchor = GridBagConstraints.WEST;
		gbc_blurCheckBox.gridx = 1;
		gbc_blurCheckBox.gridy = 2;
		panel_1.add(blurCheckBox, gbc_blurCheckBox);

	}

	public JSlider getBorderSlider() {
		return borderSlider;
	}
	public JSlider getShadowSlider() {
		return shadowSlider;
	}
	public JCheckBox getBlurCheckBox() {
		return blurCheckBox;
	}
	public JLabel getTitleLabel() {
		return TitleLabel;
	}
}
