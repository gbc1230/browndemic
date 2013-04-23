package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import edu.brown.cs32.browndemic.ui.DumbChatServer;
import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.ui.components.SelectButton;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.ChatPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.MultiplayerLobbyPanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.BackTitleBar;

public class MultiplayerLobby extends UIPanel {
	private static final long serialVersionUID = -210420477317108195L;
	
	private boolean _isHost;
	JPanel _players;
	SelectButton _disease1, _disease2, _disease3;
	JTextField _diseaseName;
	
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
		_players.setMinimumSize(new Dimension(UI.WIDTH/2, 0));
		_players.setMaximumSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT/2));
		
		
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
		_players.add(new MultiplayerLobbyPanel("Test", "127.0.0.1", _isHost, null));
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
		scrollPane.setPreferredSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT/2));
		
		JPanel left = new JPanel();
		left.setBackground(Colors.MENU_BACKGROUND);
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setMaximumSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT));
		left.setMinimumSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT));
		
		left.add(scrollPane);
		left.add(new ChatPanel(new DumbChatServer()));
		
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.setBackground(Colors.MENU_BACKGROUND);
		right.setMinimumSize(new Dimension(UI.WIDTH/2, 0));
		right.setMaximumSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT));
		
		JPanel diseaseName = new JPanel();
		diseaseName.setLayout(new BoxLayout(diseaseName, BoxLayout.X_AXIS));
		diseaseName.setMaximumSize(new Dimension(UI.WIDTH-150, 200));
		diseaseName.setBackground(Colors.MENU_BACKGROUND);
		
		JLabel diseaseNameLabel = new JLabel(Strings.ENTER_DISEASE_NAME);
		diseaseNameLabel.setFont(Fonts.BIG_TEXT);
		diseaseNameLabel.setForeground(Colors.RED_TEXT);
		_diseaseName = new JTextField();
		_diseaseName.setFont(Fonts.BIG_TEXT);
		_diseaseName.setForeground(Colors.RED_TEXT);
		_diseaseName.setBackground(Colors.MENU_BACKGROUND);
		diseaseName.add(diseaseNameLabel);
		diseaseName.add(_diseaseName);
		right.add(diseaseName);
		
		JLabel selectDisease = new JLabel(Strings.SELECT_DISEASE);
		selectDisease.setFont(Fonts.BIG_TEXT);
		selectDisease.setForeground(Colors.RED_TEXT);
		selectDisease.setAlignmentX(CENTER_ALIGNMENT);
		right.add(selectDisease);

		_disease1 = new SelectButton(Resources.getImage(Images.DISEASE1), Resources.getImage(Images.DISEASE1_SELECTED));
		_disease2 = new SelectButton(Resources.getImage(Images.DISEASE2), Resources.getImage(Images.DISEASE2_SELECTED));
		_disease3 = new SelectButton(Resources.getImage(Images.DISEASE3), Resources.getImage(Images.DISEASE3_SELECTED));
		_disease2.addOnSelectAction(new SelectAction(_disease1, _disease3));
		_disease1.addOnSelectAction(new SelectAction(_disease2, _disease3));
		_disease3.addOnSelectAction(new SelectAction(_disease2, _disease1));

		right.add(Box.createGlue());
		right.add(_disease1);
		right.add(Box.createGlue());
		right.add(_disease2);
		right.add(Box.createGlue());
		right.add(_disease3);
		right.add(Box.createGlue());
		
		add(left);
		add(right);
	}
	
	private class SelectAction implements Action {
		
		private SelectButton[] other;
		
		public SelectAction(SelectButton... other) {
			this.other = other;
		}

		@Override
		public void doAction() {
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
		// TODO Auto-generated method stub
		return null;
	}
}
