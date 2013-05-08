package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		
		JLabel about = new JLabel(String.format(Strings.ABOUT_HTML, UI.WIDTH));
		about.setForeground(Colors.RED_TEXT);

		JScrollPane sb = new JScrollPane(about, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sb.setBackground(Colors.TRANSPARENT);
		sb.setOpaque(false);
		sb.getViewport().setBackground(Colors.TRANSPARENT);
		sb.getViewport().setOpaque(false);
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
