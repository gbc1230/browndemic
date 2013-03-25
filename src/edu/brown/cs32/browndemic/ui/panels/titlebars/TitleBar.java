package edu.brown.cs32.browndemic.ui.panels.titlebars;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.brown.cs32.browndemic.ui.UIConstants.UI;

public abstract class TitleBar extends JPanel {
	private static final long serialVersionUID = -4180938726228510101L;
	
	public TitleBar() {
		super();
		setPreferredSize(new Dimension(UI.WIDTH, UI.TITLE_HEIGHT));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
	
	protected JFrame getParentFrame() {
		Container c = this;
		while (! (c instanceof JFrame)) {
			c = c.getParent();
		}
		return (JFrame) c;
	}
}
