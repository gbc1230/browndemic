package edu.brown.cs32.browndemic.ui.panels.menus;

import edu.brown.cs32.browndemic.network.LobbyMember;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.brown.cs32.browndemic.ui.DumbChatServer;
import edu.brown.cs32.browndemic.ui.Resources;
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
import java.util.ArrayList;
import java.util.List;

public class MultiplayerLobby extends UIPanel implements DocumentListener {
	private static final long serialVersionUID = -210420477317108195L;
	
	private boolean _isHost;
	private boolean _nameSelected = false;
	private boolean _diseaseSelected = false;
	private JPanel _players;
	private SelectButton _disease1, _disease2, _disease3;
	private JTextField _diseaseName;
	private JLabel _start;
        private ClientWorld _thisWorld;
        private ServerWorld _serverWorld;
//        private List<ClientWorld> _others;
        private List<LobbyMember> _lobby;
        private Timer _timer;
	
	public MultiplayerLobby(boolean isHost, ClientWorld cli, ServerWorld ser) {
		super();
		_isHost = isHost;
                _thisWorld = cli;
                _serverWorld = ser;
                _lobby = new ArrayList<>();
		makeUI();
		_timer = new Timer(1000/5, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
                _timer.start();
	}
	
	@Override
	protected void makeUI() {
		super.makeUI();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		_players = new JPanel();
		_players.setLayout(new BoxLayout(_players, BoxLayout.Y_AXIS));
		_players.setBackground(Colors.TRANSPARENT);
		//_players.setMinimumSize(new Dimension(UI.WIDTH/2, 0));
		//_players.setMaximumSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT));
			
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
                _players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(Box.createGlue());
		
		JScrollPane scrollPane = new JScrollPane(_players, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setMinimumSize(new Dimension(UI.WIDTH/2, 0));
		scrollPane.setPreferredSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT));
		scrollPane.setMaximumSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT));
		
		JPanel bot = new JPanel();
		bot.setBackground(Colors.TRANSPARENT);
		bot.setLayout(new BoxLayout(bot, BoxLayout.X_AXIS));
		bot.setOpaque(false);
		//bot.setMaximumSize(new Dimension(UI.WIDTH, UI.CONTENT_HEIGHT/2));
		//bot.setMinimumSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT));
		
		bot.add(scrollPane);
		bot.add(new ChatPanel(new DumbChatServer()));
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		top.setBackground(Colors.TRANSPARENT);
		top.setOpaque(false);
		//top.setMinimumSize(new Dimension(UI.WIDTH/2, 0));
		//top.setMaximumSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT));
		
		JPanel diseaseName = new JPanel();
		diseaseName.setLayout(new BoxLayout(diseaseName, BoxLayout.X_AXIS));
		diseaseName.setMaximumSize(new Dimension(UI.WIDTH-150, 200));
		diseaseName.setBackground(Colors.TRANSPARENT);
		
		JLabel diseaseNameLabel = new JLabel(Strings.ENTER_DISEASE_NAME);
		diseaseNameLabel.setFont(Fonts.BIG_TEXT);
		diseaseNameLabel.setForeground(Colors.RED_TEXT);
		_diseaseName = new JTextField();
		_diseaseName.setFont(Fonts.BIG_TEXT);
		_diseaseName.setForeground(Colors.RED_TEXT);
		_diseaseName.setBackground(Colors.MENU_BACKGROUND);
		_diseaseName.getDocument().addDocumentListener(this);
		diseaseName.add(diseaseNameLabel);
		diseaseName.add(_diseaseName);
		top.add(diseaseName);
		top.add(Box.createRigidArea(new Dimension(0, 25)));
		
		JLabel selectDisease = new JLabel(Strings.SELECT_DISEASE);
		selectDisease.setFont(Fonts.BIG_TEXT);
		selectDisease.setForeground(Colors.RED_TEXT);
		selectDisease.setAlignmentX(CENTER_ALIGNMENT);
		top.add(selectDisease);
		top.add(Box.createRigidArea(new Dimension(0, 25)));
		
		JPanel diseaseContainer = new JPanel();
		diseaseContainer.setLayout(new BoxLayout(diseaseContainer, BoxLayout.X_AXIS));
		diseaseContainer.setBackground(Colors.TRANSPARENT);

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
            _lobby = _thisWorld.getLobby();
            System.out.println(_lobby);
            if (_isHost)
                System.out.println(_serverWorld.allReady());
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
			_diseaseSelected = true;
                        _thisWorld.changeDiseasesPicked(id);
			for (SelectButton b : other) {
				b.deSelect();
			}
		}
		
	}

	@Override
	public void setupForDisplay() {
		Utils.getParentFrame(this).setTitle(new BackTitleBar(new MultiplayerMenu()));
	}

	@Override
	public String toString() {
		return Strings.MULTIPLAYER_LOBBY;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		if (e.getDocument() == _diseaseName.getDocument()) {
			if (_diseaseName.getText().equals("")) {
				_nameSelected = false;
			} else {
				_nameSelected = true;
			}
		}
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
	}
	
	@Override
	public void mouseReleasedInside(MouseEvent e) {
		if (e.getSource() == _start && _start.isEnabled()) {
			
		}
	}
        
        @Override
        public void stopPanel() {
            _timer.stop();
            _thisWorld.leaveLobby();
            if (_isHost) {
                
            }
        }
}
