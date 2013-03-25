package edu.brown.cs32.browndemic.ui.panels;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class UIPanel extends JPanel {
	public UIPanel() {
		super();
	}
	
	public abstract void setupForDisplay();
}
