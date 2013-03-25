package edu.brown.cs32.browndemic.ui.panels;

import java.awt.Container;

import javax.swing.JPanel;

import edu.brown.cs32.browndemic.ui.BrowndemicFrame;

@SuppressWarnings("serial")
public abstract class UIPanel extends JPanel {
	private BrowndemicFrame _parent;
	public UIPanel() {
		super();
	}
	
	protected BrowndemicFrame getParentFrame() {
		if (_parent != null) return _parent;
		Container c = this;
		while (!(c instanceof BrowndemicFrame)) {
			c = c.getParent();
		}
		_parent = (BrowndemicFrame) c;
		return _parent;
	}
}
