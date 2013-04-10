package edu.brown.cs32.browndemic.ui.panels;

import java.awt.Dimension;

import javax.swing.BoxLayout;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;


@SuppressWarnings("serial")
public abstract class UIPanel extends BrowndemicPanel {
	public UIPanel() {
		super();
	}
	
	public abstract void setupForDisplay();
	
	protected void makeUI() {
		setBackground(Colors.MENU_BACKGROUND);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(UI.WIDTH, UI.CONTENT_HEIGHT));
	}
	
	@Override
	public abstract String toString();


}
