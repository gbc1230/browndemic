package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.ChatPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.MultiplayerLobbyPanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.BackTitleBar;

public class MultiplayerLobby extends UIPanel {
	private static final long serialVersionUID = -210420477317108195L;
	
	private boolean _isHost;
	JPanel _players;
	
	public MultiplayerLobby(boolean isHost) {
		super();
		_isHost = isHost;
		makeUI();
	}
	
	@Override
	protected void makeUI() {
		super.makeUI();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		_players = new JPanel();
		_players.setLayout(new BoxLayout(_players, BoxLayout.Y_AXIS));
		_players.setBackground(Colors.MENU_BACKGROUND);
		
		
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(Box.createGlue());
		
		JScrollPane scrollPane = new JScrollPane(_players, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setMaximumSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT/2));
		
		JPanel left = new JPanel();
		left.setBackground(Colors.MENU_BACKGROUND);
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setMaximumSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT));
		left.setMinimumSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT));
		
		JPanel chat = new JPanel();
		chat.setBackground(Color.BLUE);
		chat.setMaximumSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT/2));
		
		left.add(scrollPane);
		left.add(new ChatPanel());
		
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.setBackground(Color.GREEN);
		right.setMinimumSize(new Dimension(UI.WIDTH/2, 0));
		
		add(left);
		add(right);
	}

	@Override
	public void setupForDisplay() {
		Utils.getParentFrame(this).setTitle(new BackTitleBar(new MultiplayerMenu()));
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
