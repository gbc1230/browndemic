package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PerkPanel extends JPanel {
	private static final long serialVersionUID = 910341036679738006L;
	
	private JLabel _image;
	private JLabel _text;
	
	public PerkPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(_image = new JLabel());
		add(Box.createRigidArea(new Dimension(8, 0)));
		add(_text = new JLabel());
		add(Box.createGlue());
	}
	
	public PerkPanel setImage(Image img) {
		_image.setIcon(new ImageIcon(img));
		return this;
	}
	
	public PerkPanel setTextFont(Font f) {
		_text.setFont(f);
		return this;
	}
	
	public PerkPanel setText(String s) {
		_text.setText(s);
		return this;
	}
	
	public PerkPanel setTextColor(Color c) {
		_text.setForeground(c);
		return this;
	}
}
