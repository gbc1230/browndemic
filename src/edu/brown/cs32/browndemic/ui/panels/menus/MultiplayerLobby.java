package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import edu.brown.cs32.browndemic.network.LobbyMember;
import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.Settings;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.components.SelectButton;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.ChatPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.MultiplayerLobbyPanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.BackTitleBar;
import edu.brown.cs32.browndemic.world.ClientWorld;
import edu.brown.cs32.browndemic.world.ServerWorld;

public class MultiplayerLobby extends UIPanel {
	private static final long serialVersionUID = -210420477317108195L;
	
	private boolean _isHost, _started = false;
	private JPanel _players, _chat;
	private SelectButton _disease1, _disease2, _disease3;
	private JLabel _start;
    private ClientWorld _thisWorld;
    private ServerWorld _serverWorld;
    private List<LobbyMember> _lobby;
    private Timer _timer;
    private String _name;
	
	public MultiplayerLobby(boolean isHost, ClientWorld cli, ServerWorld ser) {
		super();
		System.out.println("constructing lobby");
		_isHost = isHost;
        _thisWorld = cli;
        _serverWorld = ser;
        _lobby = new ArrayList<>();
        _name = Settings.get(Settings.NAME);
		makeUI();
	}
	
	@Override
	protected void makeUI() {
		super.makeUI();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		_players = new JPanel();
		_players.setLayout(new BoxLayout(_players, BoxLayout.Y_AXIS));
		_players.setBackground(Colors.TRANSPARENT);
		_players.setOpaque(false);
		_players.add(Box.createGlue());
		
		JScrollPane scrollPane = new JScrollPane(_players, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setMinimumSize(new Dimension(UI.WIDTH/2, 0));
		scrollPane.setPreferredSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT));
		scrollPane.setMaximumSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT));
		scrollPane.getViewport().setBackground(Colors.TRANSPARENT);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		
		JPanel bot = new JPanel();
		bot.setBackground(Colors.TRANSPARENT);
		bot.setLayout(new BoxLayout(bot, BoxLayout.X_AXIS));
		bot.setOpaque(false);
		
		bot.add(scrollPane);
		bot.add(_chat = new ChatPanel(_thisWorld));
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		top.setBackground(Colors.TRANSPARENT);
		top.setOpaque(false);
		
		JLabel selectDisease = new JLabel(Strings.SELECT_DISEASE);
		selectDisease.setFont(Fonts.BIG_TEXT);
		selectDisease.setForeground(Colors.RED_TEXT);
		selectDisease.setAlignmentX(CENTER_ALIGNMENT);
		top.add(selectDisease);
		top.add(Box.createRigidArea(new Dimension(0, 25)));
		
		JPanel diseaseContainer = new JPanel();
		diseaseContainer.setLayout(new BoxLayout(diseaseContainer, BoxLayout.X_AXIS));
		diseaseContainer.setBackground(Colors.TRANSPARENT);
		diseaseContainer.setOpaque(false);

		_disease1 = new SelectButton(Resources.getImage(Images.DISEASE1), Resources.getImage(Images.DISEASE1_SELECTED));
		_disease2 = new SelectButton(Resources.getImage(Images.DISEASE2), Resources.getImage(Images.DISEASE2_SELECTED));
		_disease3 = new SelectButton(Resources.getImage(Images.DISEASE3), Resources.getImage(Images.DISEASE3_SELECTED));
		_disease2.addOnSelectAction(new SelectAction(2, _disease1, _disease3));
		_disease1.addOnSelectAction(new SelectAction(1, _disease2, _disease3));
		_disease3.addOnSelectAction(new SelectAction(3, _disease2, _disease1));

		diseaseContainer.add(Box.createGlue());
		diseaseContainer.add(_disease1);
		diseaseContainer.add(Box.createGlue());
		diseaseContainer.add(_disease2);
		diseaseContainer.add(Box.createGlue());
		diseaseContainer.add(_disease3);
		diseaseContainer.add(Box.createGlue());
		
		top.add(diseaseContainer);
		if (_isHost) {
			_start = new HoverLabel(Strings.START_GAME, Fonts.BUTTON_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT);
			_start.setEnabled(false);
			_start.setAlignmentX(CENTER_ALIGNMENT);
			_start.addMouseListener(this);
			top.add(Box.createRigidArea(new Dimension(0, 25)));
			top.add(_start);
		}
		top.add(Box.createRigidArea(new Dimension(0, 25)));

		add(top);
		add(bot);
	}
	
	private void update() {
		if (_thisWorld.hostDisconnected()) {
			JOptionPane.showMessageDialog(this, "The host has disconnected.  Returning to Main Menu");
			Utils.getParentFrame(this).setPanel(new MainMenu());
			_timer.stop();
		}
		if (_lobby == null || !_lobby.equals(_thisWorld.getLobby())) {
			_lobby = _thisWorld.getLobby();
			_players.removeAll();
			if (_lobby == null)
				return;
			for (LobbyMember l : _lobby) {
			    _players.add(new MultiplayerLobbyPanel(l.getName(), l.getIP(), _isHost, null, l.isReady()));
			}
		    _players.add(Box.createGlue());
		    _players.revalidate();
		}
		if (_serverWorld != null) {
			_start.setEnabled(_serverWorld.allReady());
		}
		if (_thisWorld.isGameReady()) {
			_started = true;
			Utils.getParentFrame(this).setPanel(new GameMenu(_thisWorld, _thisWorld.getDiseaseID(), true));
			_timer.stop();
		}
		if (!_name.equals(Settings.get(Settings.NAME))) {
			_name = Settings.get(Settings.NAME);
			_thisWorld.setName(_name);
		}
	}
	
	private class SelectAction implements Action {
		
		private SelectButton[] other;
        private int id;
		
		public SelectAction(int id, SelectButton... other) {
			this.other = other;
            this.id = id;
		}

		@Override
		public void doAction() {
			_thisWorld.changeDiseasesPicked(id);
			for (SelectButton b : other) {
				b.deSelect();
			}
		}
		
	}

	@Override
	public void setupForDisplay() {
		update();
		System.out.println("TEST");
		if (_timer == null) {
			_timer = new Timer(1000/4, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					update();
				}
			});
			_timer.start();
		}
		_chat.requestFocusInWindow();
		Utils.getParentFrame(this).setTitle(new BackTitleBar(this, new MultiplayerMenu()));
	}

	@Override
	public String toString() {
		return Strings.MULTIPLAYER_LOBBY;
	}
	
	@Override
	public void mouseReleasedInside(MouseEvent e) {
		if (e.getSource() == _start && _start.isEnabled()) {
			_serverWorld.collectDiseases();
		}
	}
        
    @Override
    public void stopPanel() {
        _timer.stop();
        if (!_started) {
	        _thisWorld.leaveGame();
	        if (_isHost) {
	        	_serverWorld.killServer();
	        }
        }
    }
}
