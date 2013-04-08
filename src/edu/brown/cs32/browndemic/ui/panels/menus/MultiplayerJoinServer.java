package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.BackTitleBar;

public class MultiplayerJoinServer extends UIPanel {

	private static final long serialVersionUID = -820456096881483867L;
	
	public MultiplayerJoinServer() {
		makeUI();
	}
	
	public void makeUI() {
		setBackground(Colors.MENU_BACKGROUND);
		setPreferredSize(new Dimension(UI.WIDTH, UI.CONTENT_HEIGHT));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(Box.createGlue());
	}

	@Override
	public void setupForDisplay() {
		Utils.getParentFrame(this).setTitle(new BackTitleBar(new MultiplayerMenu()));
	}

	@Override
	public String toString() {
		return Strings.JOIN_GAME;
	}

}
