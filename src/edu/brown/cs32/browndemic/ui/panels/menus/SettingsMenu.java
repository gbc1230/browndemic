package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JCheckBox;

import edu.brown.cs32.browndemic.ui.Settings;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.BackTitleBar;

public class SettingsMenu extends UIPanel implements ItemListener {

	private static final long serialVersionUID = 5953169089564273910L;
	
	JCheckBox _caching;
	
	public SettingsMenu() {
		super();
		makeUI();
	}
	
	@Override
	protected void makeUI() {
		super.makeUI();
		
		_caching = new JCheckBox(Strings.SETTINGS_CACHING, Settings.getBoolean(Settings.CACHING));
		_caching.setBackground(Colors.MENU_BACKGROUND);
		_caching.setForeground(Colors.RED_TEXT);
		_caching.setFont(Fonts.BIG_TEXT);
		_caching.setAlignmentX(Component.CENTER_ALIGNMENT);
		_caching.addItemListener(this);

		add(Box.createGlue());
		add(_caching);
		add(Box.createGlue());
	}

	@Override
	public void setupForDisplay() {
		Utils.getParentFrame(this).setTitle(new BackTitleBar(new MainMenu()));
	}

	@Override
	public String toString() {
		return Strings.SETTINGS;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		boolean selected = e.getStateChange() == ItemEvent.SELECTED;
		if (e.getItemSelectable() == _caching) {
			Settings.set(Settings.CACHING, selected);
		}
		try {
			Settings.writeSettings();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
