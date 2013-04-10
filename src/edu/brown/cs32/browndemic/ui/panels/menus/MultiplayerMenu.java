package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	JTextField _host, _port, _port2;
	
	public MultiplayerMenu() {
		super();
		makeUI();
	}
	
	@Override
	protected void makeUI() {
		super.makeUI();

		JPanel host = new JPanel();
		host.setLayout(new BoxLayout(host, BoxLayout.X_AXIS));
		host.setBackground(Colors.MENU_BACKGROUND);
		host.setMaximumSize(new Dimension(UI.WIDTH/2, 200));
		
		JLabel hostLabel = new JLabel(Strings.ENTER_HOST);
		hostLabel.setToolTipText(Strings.ENTER_HOST_TOOLTIP);
		hostLabel.setFont(Fonts.BIG_TEXT);
		hostLabel.setForeground(Colors.RED_TEXT);
		
		_host = new JTextField();
		_host.setToolTipText(Strings.ENTER_HOST_TOOLTIP);
		_host.setFont(Fonts.BIG_TEXT);
		_host.setForeground(Colors.RED_TEXT);
		_host.setBackground(Colors.MENU_BACKGROUND);
		
		host.add(Box.createGlue());
		host.add(hostLabel);
		host.add(_host);
		host.add(Box.createGlue());

		JPanel port = new JPanel();
		port.setLayout(new BoxLayout(port, BoxLayout.X_AXIS));
		port.setBackground(Colors.MENU_BACKGROUND);
		port.setMaximumSize(new Dimension(UI.WIDTH/2, 200));
		
		JLabel portLabel = new JLabel(Strings.ENTER_PORT);
		portLabel.setToolTipText(Strings.ENTER_PORT_TOOLTIP);
		portLabel.setFont(Fonts.BIG_TEXT);
		portLabel.setForeground(Colors.RED_TEXT);

		_port = new JTextField();
		_port.setToolTipText(Strings.ENTER_PORT_TOOLTIP);
		_port.setColumns(6);
		_port.setMaximumSize(new Dimension(50, 200));
		_port.setFont(Fonts.BIG_TEXT);
		_port.setForeground(Colors.RED_TEXT);
		_port.setBackground(Colors.MENU_BACKGROUND);
		
		port.add(Box.createGlue());
		port.add(portLabel);
		port.add(_port);
		port.add(Box.createGlue());
		
		JPanel port2 = new JPanel();
		port2.setLayout(new BoxLayout(port2, BoxLayout.X_AXIS));
		port2.setBackground(Colors.MENU_BACKGROUND);
		port2.setMaximumSize(new Dimension(UI.WIDTH/2, 200));
		
		JLabel port2Label = new JLabel(Strings.ENTER_PORT);
		port2Label.setToolTipText(Strings.CREATE_PORT_TOOLTIP);
		port2Label.setFont(Fonts.BIG_TEXT);
		port2Label.setForeground(Colors.RED_TEXT);
		
		_port2 = new JTextField();
		_port2.setToolTipText(Strings.CREATE_PORT_TOOLTIP);
		_port2.setColumns(6);
		_port2.setMaximumSize(new Dimension(50, 200));
		_port2.setFont(Fonts.BIG_TEXT);
		_port2.setForeground(Colors.RED_TEXT);
		_port2.setBackground(Colors.MENU_BACKGROUND);
		
		port2.add(Box.createGlue());
		port2.add(port2Label);
		port2.add(_port2);
		port2.add(Box.createGlue());
		
		_join = new HoverLabel(Strings.JOIN_GAME, Fonts.BUTTON_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT);
		_join.setAlignmentX(CENTER_ALIGNMENT);
		_join.addMouseListener(this);
		_create = new HoverLabel(Strings.CREATE_GAME, Fonts.BUTTON_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT);
		_create.setAlignmentX(CENTER_ALIGNMENT);
		_create.addMouseListener(this);

		JLabel joinGame = new JLabel(Strings.CONNECT_TO_SERVER);
		joinGame.setFont(Fonts.BIG_TEXT);
		joinGame.setForeground(Colors.RED_TEXT);
		joinGame.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel hostGame = new JLabel(Strings.HOST_SERVER);
		hostGame.setFont(Fonts.BIG_TEXT);
		hostGame.setForeground(Colors.RED_TEXT);
		hostGame.setAlignmentX(CENTER_ALIGNMENT);
		
		add(Box.createGlue());
		add(Box.createGlue());
		add(joinGame);
		add(Box.createGlue());
		add(host);
		add(port);
		add(Box.createGlue());
		add(_join);
		add(Box.createGlue());
		add(Box.createGlue());
		add(hostGame);
		add(Box.createGlue());
		add(port2);
		add(Box.createGlue());
		add(_create);
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
			System.out.printf("Connect to %s:%s\n", _host.getText(), _port.getText());
			Utils.getParentFrame(this).setPanel(new MultiplayerLobby(false));
			//Utils.getParentFrame(this).setPanel(new MultiplayerJoinServer());
		} else if (e.getSource() == _create) {
			System.out.printf("Host on port %s\n", _port.getText());
			Utils.getParentFrame(this).setPanel(new MultiplayerLobby(true));
			//Utils.getParentFrame(this).setPanel(new MultiplayerCreateServer());
		}
	}

}
