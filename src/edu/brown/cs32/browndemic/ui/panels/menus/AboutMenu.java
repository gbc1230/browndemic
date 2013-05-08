package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.BackTitleBar;

public class AboutMenu extends UIPanel {
	
	private static final long serialVersionUID = -1483762632451115346L;

	public AboutMenu() {
		super();
		makeUI();
	}
	
	@Override
	public void makeUI() {
		super.makeUI();
		
		JLabel about = new JLabel(String.format(Strings.ABOUT_HTML, (int)(UI.WIDTH * .9)));
		about.setForeground(Colors.RED_TEXT);

		JScrollPane sb = new JScrollPane(about, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sb.setBackground(new Color(0, 0, 0));
		sb.setOpaque(false);
		sb.getViewport().setBackground(new Color(0, 0, 0, 100));
		sb.getViewport().setOpaque(true);
		sb.getVerticalScrollBar().setUnitIncrement(16);
		sb.setBorder(BorderFactory.createEmptyBorder());
		add(sb);
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
