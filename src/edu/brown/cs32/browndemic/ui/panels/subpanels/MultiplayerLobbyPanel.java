package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;

public class MultiplayerLobbyPanel extends BrowndemicPanel {

	private static final long serialVersionUID = -2843292745534214130L;
	
	private boolean _isHost;
	private String _name;
	private String _ip;
	private JLabel _kick;
	private Action _kickPlayerAction;

	public MultiplayerLobbyPanel(String name, String ip, boolean isHost, Action kickPlayerAction) {
		_isHost = isHost;
		_name = name;
		_ip = ip;
		_kickPlayerAction = kickPlayerAction;
		makeUI();
	}
	
	private void makeUI() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Colors.MENU_BACKGROUND);
		setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		
		JLabel name = new JLabel(_name + " (" + _ip + ")");
		name.setForeground(Colors.RED_TEXT);
		name.setFont(Fonts.BIG_TEXT);
		
		//setMaximumSize(new Dimension(UI.WIDTH, name.getHeight()));
		
		add(name);
		add(Box.createGlue());
		
		if (_isHost) {
			_kick = new HoverLabel(Strings.KICK_PLAYER, Fonts.BIG_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT);
			_kick.addMouseListener(this);
			add(_kick);
		}
	}
	
	@Override
	public void mouseReleasedInside(MouseEvent e) {
		if (e.getSource() == _kick && _isHost) {
			if (_kickPlayerAction != null)
				_kickPlayerAction.doAction();
		}
	}
}
