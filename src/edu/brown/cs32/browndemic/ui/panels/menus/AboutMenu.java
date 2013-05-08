package edu.brown.cs32.browndemic.ui.panels.menus;

import javax.swing.Box;
import javax.swing.JLabel;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.BackTitleBar;

public class AboutMenu extends UIPanel {
	
	public AboutMenu() {
		super();
		makeUI();
	}
	
	@Override
	public void makeUI() {
		super.makeUI();
		
		add(Box.createGlue());
		System.out.println(Strings.ABOUT_HTML);
		JLabel about = new JLabel(String.format(Strings.ABOUT_HTML, UI.WIDTH));
		about.setForeground(Colors.RED_TEXT);
		add(about);
		add(Box.createGlue());
	}

	@Override
	public void setupForDisplay() {
		Utils.getParentFrame(this).setTitle(new BackTitleBar(this, new MainMenu()));
	}

	@Override
	public String toString() {
		return Strings.ABOUT_MENU;
	}

}
