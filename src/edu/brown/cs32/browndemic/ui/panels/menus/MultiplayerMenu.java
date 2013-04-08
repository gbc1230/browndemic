package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.BackTitleBar;

public class MultiplayerMenu extends UIPanel implements MouseListener {

	private static final long serialVersionUID = 5817770674394990192L;
	
	JLabel _join, _create;
	
	public MultiplayerMenu() {
		super();
		makeUI();
	}
	
	private void makeUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(UI.WIDTH, UI.CONTENT_HEIGHT));
		setBackground(Colors.MENU_BACKGROUND);
		add(Box.createGlue());

		_join = new HoverLabel(Strings.JOIN_GAME, Fonts.BUTTON_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT);
		_join.setAlignmentX(CENTER_ALIGNMENT);
		_join.addMouseListener(this);
		_create = new HoverLabel(Strings.CREATE_GAME, Fonts.BUTTON_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT);
		_create.setAlignmentX(CENTER_ALIGNMENT);
		_create.addMouseListener(this);
		
		add(Box.createGlue());
		add(Box.createGlue());
		add(Box.createGlue());
		add(_join);
		add(Box.createGlue());
		add(_create);
		add(Box.createGlue());
		add(Box.createGlue());
		add(Box.createGlue());
		add(Box.createGlue());
		add(Box.createGlue());
		add(Box.createGlue());
	}

	@Override
	public void setupForDisplay() {
		Utils.getParentFrame(this).setTitle(new BackTitleBar(new MainMenu()));
	}

	@Override
	public String toString() {
		return Strings.MULTIPLAYER_MENU;
	}
	
	@Override
	public void mouseReleasedInside(MouseEvent e) {
		if (e.getSource() == _join) {
			Utils.getParentFrame(this).setPanel(new MultiplayerJoinServer());
		} else if (e.getSource() == _create) {
			Utils.getParentFrame(this).setPanel(new MultiplayerCreateServer());
		}
	}

}
