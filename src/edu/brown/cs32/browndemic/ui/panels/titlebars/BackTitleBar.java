package edu.brown.cs32.browndemic.ui.panels.titlebars;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;

import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.components.BackButton;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;

public class BackTitleBar extends DefaultTitleBar {
	private static final long serialVersionUID = 7210369452410133172L;
	
	JLabel close, minimize, title;
	
	public BackTitleBar(UIPanel parent, UIPanel back) {
		super(parent);
		makeUI(back);
	}
	
	@Override
	protected void makeUI() {
		
	}
	
	protected void makeUI(UIPanel back) {
		BackButton b = new BackButton(back);
		b.setToolTipText(Strings.GO_BACK + back.toString());
		add(b);
		add(Box.createRigidArea(new Dimension(8, 0)));
		super.makeUI();
	}
}
