package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.brown.cs32.browndemic.ui.Settings;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.BackTitleBar;

public class SettingsMenu extends UIPanel implements ItemListener, DocumentListener {

	private static final long serialVersionUID = 5953169089564273910L;
	
	private JCheckBox _caching, _fps;
	private JTextField _name, _port;
	
	public SettingsMenu() {
		super();
		makeUI();
	}
	
	@Override
	protected void makeUI() {
		super.makeUI();

		_caching = new JCheckBox(Strings.SETTINGS_CACHING, Settings.getBoolean(Settings.CACHING));
		_caching.setBackground(Colors.TRANSPARENT);
		_caching.setForeground(Colors.RED_TEXT);
		_caching.setFont(Fonts.BIG_TEXT);
		_caching.setAlignmentX(Component.CENTER_ALIGNMENT);
		_caching.addItemListener(this);
		_caching.setOpaque(false);
		
		_fps = new JCheckBox(Strings.SETTINGS_FPS, Settings.getBoolean(Settings.FPS));
		_fps.setBackground(Colors.TRANSPARENT);
		_fps.setForeground(Colors.RED_TEXT);
		_fps.setFont(Fonts.BIG_TEXT);
		_fps.setAlignmentX(Component.CENTER_ALIGNMENT);
		_fps.addItemListener(this);
		_fps.setOpaque(false);
		
		JPanel name = new JPanel();
		name.setLayout(new BoxLayout(name, BoxLayout.X_AXIS));
		name.setBackground(Colors.TRANSPARENT);
		name.setAlignmentX(CENTER_ALIGNMENT);
		name.setMaximumSize(new Dimension(UI.WIDTH - 200, 200));
		name.setOpaque(false);
		
		JLabel nameLabel = new JLabel(Strings.SETTINGS_MULTIPLAYER_NAME);
		nameLabel.setFont(Fonts.BIG_TEXT);
		nameLabel.setForeground(Colors.RED_TEXT);
		
		_name = new JTextField(Settings.get(Settings.NAME));
		_name.setBackground(Colors.MENU_BACKGROUND);
		_name.setForeground(Colors.RED_TEXT);
		_name.setFont(Fonts.BIG_TEXT);
		_name.getDocument().addDocumentListener(this);
		
		name.add(Box.createGlue());
		name.add(nameLabel);
		name.add(_name);
		name.add(Box.createGlue());
		
		

		
		JPanel port = new JPanel();
		port.setLayout(new BoxLayout(port, BoxLayout.X_AXIS));
		port.setBackground(Colors.TRANSPARENT);
		port.setAlignmentX(CENTER_ALIGNMENT);
		port.setMaximumSize(new Dimension(UI.WIDTH - 600, 200));
		port.setOpaque(false);
		
		JLabel portLabel = new JLabel(Strings.ENTER_PORT);
		portLabel.setFont(Fonts.BIG_TEXT);
		portLabel.setForeground(Colors.RED_TEXT);
		
		_port = new JTextField(Integer.toString(Settings.getInt(Settings.PORT)));
		_port.setBackground(Colors.MENU_BACKGROUND);
		_port.setForeground(Colors.RED_TEXT);
		_port.setFont(Fonts.BIG_TEXT);
		_port.getDocument().addDocumentListener(this);
		
		Utils.setDefaultLook(_port, _name);
		
		port.add(Box.createGlue());
		port.add(portLabel);
		port.add(_port);
		port.add(Box.createGlue());

		add(Box.createGlue());
		add(_caching);
		add(_fps);
		add(name);
		add(port);
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
		} else if (e.getItemSelectable() == _fps) {
			Settings.set(Settings.FPS, selected);
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		if (e.getDocument() == _name.getDocument()) {
			Settings.set(Settings.NAME, _name.getText());
		} else if (e.getDocument() == _port.getDocument()) {
			try {
				int val = Integer.parseInt(_port.getText());
				Settings.set(Settings.PORT, val);
			} catch (NumberFormatException e1) {
				// Do nothing for now
			}
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		if (e.getDocument() == _name.getDocument()) {
			Settings.set(Settings.NAME, _name.getText());
		} else if (e.getDocument() == _port.getDocument()) {
			try {
				int val = Integer.parseInt(_port.getText());
				Settings.set(Settings.PORT, val);
			} catch (NumberFormatException e1) {
				// Do nothing for now
			}
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if (e.getDocument() == _name.getDocument()) {
			Settings.set(Settings.NAME, _name.getText());
		} else if (e.getDocument() == _port.getDocument()) {
			try {
				int val = Integer.parseInt(_port.getText());
				Settings.set(Settings.PORT, val);
			} catch (NumberFormatException e1) {
				// Do nothing for now
			}
		}
	}

}
