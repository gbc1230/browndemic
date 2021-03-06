package edu.brown.cs32.browndemic.ui.panels.titlebars;

import java.awt.Dimension;

import javax.swing.BoxLayout;

import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;

public abstract class TitleBar extends BrowndemicPanel {
	private static final long serialVersionUID = -4180938726228510101L;
	
	public TitleBar() {
		super();
		setPreferredSize(new Dimension(UI.WIDTH, UI.TITLE_HEIGHT));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
}
