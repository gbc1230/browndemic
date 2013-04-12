package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;

public class InformationBar extends BrowndemicPanel {
	
	private static final long serialVersionUID = 5751262776229759464L;

	public InformationBar() {
		super();
		makeUI();
	}
	
	private void makeUI() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Color.BLUE);
		setPreferredSize(new Dimension(UI.WIDTH, UI.TITLE_HEIGHT));
		setMaximumSize(new Dimension(UI.WIDTH, UI.TITLE_HEIGHT));
		setMinimumSize(new Dimension(UI.WIDTH, UI.TITLE_HEIGHT));
		
		JLabel test = new JLabel("INFO BAR");
		add(Box.createGlue());
		add(test);
		add(Box.createGlue());
		
	}
	
	
	@Override
	public void mouseReleasedInside(MouseEvent e) {
		
	}
}
