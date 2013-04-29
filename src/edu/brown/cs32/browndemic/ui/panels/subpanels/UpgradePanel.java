package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;

public class UpgradePanel extends BrowndemicPanel {

	private static final long serialVersionUID = 687716354790239279L;
	private Disease _disease;
	
	public UpgradePanel(Disease d) {
		super();
		_disease = d;
		makeUI();
	}
	
	private void makeUI() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Color.GREEN);
		setPreferredSize(new Dimension((int)(UI.WIDTH/1.5), 0));
		setMinimumSize(new Dimension((int)(UI.WIDTH/1.5), 0));
		
	}
}
