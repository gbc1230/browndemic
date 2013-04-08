package edu.brown.cs32.browndemic.ui.panels.titlebars;

import javax.swing.JLabel;

import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.components.BackButton;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;

public class BackTitleBar extends DefaultTitleBar {
	private static final long serialVersionUID = 7210369452410133172L;
	
	JLabel close, minimize, title;
	
	public BackTitleBar(UIPanel back) {
		super();
		makeUI(back);
	}
	
	@Override
	protected void makeUI() {
		
	}
	
	protected void makeUI(UIPanel back) {
		BackButton b = new BackButton(back);
		b.setToolTipText(Strings.GO_BACK + back.toString());
		add(b);
		super.makeUI();
	}
}
