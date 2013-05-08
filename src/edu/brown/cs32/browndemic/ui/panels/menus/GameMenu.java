package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.brown.cs32.browndemic.ui.BrowndemicFrame;
import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.ui.components.MarqueeLabel;
import edu.brown.cs32.browndemic.ui.components.WorldMap;
import edu.brown.cs32.browndemic.ui.interfaces.ChatServer;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.ChatPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.InformationBar;
import edu.brown.cs32.browndemic.ui.panels.subpanels.Leaderboard;
import edu.brown.cs32.browndemic.ui.panels.subpanels.NewsPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.RegionPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.StatPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.UpgradePanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.InGameTitleBar;
import edu.brown.cs32.browndemic.world.World;

public class GameMenu extends UIPanel implements ChangeListener {
	
	private static final long serialVersionUID = 3275157554958820602L;
	
	private World _world;
	private WorldMap _map;
	private boolean _loaded = false, _multiplayer;
	private int _disease, _prevTab = 0;
	private InformationBar _info;
	private NewsPanel _news;
	private RegionPanel _regions;
	private StatPanel _stats;
	private UpgradePanel _upgrade;
	private MarqueeLabel _ml;
	private Leaderboard _lb;
	private ChatPanel _chat;
	private JTabbedPane _botright;
	private Timer _timer;
	
	public GameMenu(World w, int disease, boolean multiplayer) {
		super();
		_world = w;
		_disease = disease;
		_multiplayer = multiplayer;
		_ml = new MarqueeLabel(0, 0, UI.WIDTH);
		_ml.setFont(Fonts.NORMAL_TEXT);
		_ml.setForeground(Colors.RED_TEXT);
		_ml.setBackground(new Color(0, 0, 0, 100));
	}
	
	private class ImagesDoneLoadingAction implements Action {
		BrowndemicFrame _parent;
		public ImagesDoneLoadingAction(BrowndemicFrame parent) {
			_parent = parent;
		}
		@Override
		public void doAction() {
			_map = new WorldMap(_world, Resources.getImage(Images.MAP), Resources.getImage(Images.REGIONS), _disease, _ml, _multiplayer);
			_parent.setPanel(new Loading(true, _map.new Loader(new RegionsDoneLoadingAction(_parent))));
		}
	}
	
	private class RegionsDoneLoadingAction implements Action {
		BrowndemicFrame _parent;
		public RegionsDoneLoadingAction(BrowndemicFrame parent) {
			_parent = parent;
		}
		@Override
		public void doAction() {
			makeUI();
			_loaded = true;
			_parent.setPanel(GameMenu.this);
		}
	}
	
	@Override
	protected void makeUI() {
		super.makeUI();
		
		add(_info = new InformationBar(_world, _disease, _map, _multiplayer));
		
		add(_map);
		
		JPanel bottom = new JPanel();
		bottom.setBackground(Colors.TRANSPARENT);
		bottom.setOpaque(false);
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
		
		bottom.add(_upgrade = new UpgradePanel(_world, _disease));

		UIManager.put("TabbedPane.selected", Colors.MENU_BACKGROUND);
		UIManager.put("TabbedPane.focus", Colors.MENU_BACKGROUND);
		UIManager.put("TabbedPane.selectHighlight", Colors.MENU_BACKGROUND);
		UIManager.put("TabbedPane.shadow", Colors.MENU_BACKGROUND);
		UIManager.put("TabbedPane.darkShadow", Colors.MENU_BACKGROUND);
		UIManager.put("TabbedPane.selected", Colors.MENU_BACKGROUND);
		UIManager.put("TabbedPane.borderHighlightColor", Colors.MENU_BACKGROUND);
		UIManager.put("TabbedPane.background", Colors.MENU_BACKGROUND);
		UIManager.put("TabbedPane.unselectedBackground", Colors.MENU_BACKGROUND);
		UIManager.put("TabbedPane.light", Colors.RED_TEXT);
		UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
		
		
		
		_botright = new JTabbedPane();
		_botright.setMaximumSize(new Dimension((int)(UI.WIDTH/2.52), UI.CONTENT_HEIGHT));
		_botright.setMinimumSize(new Dimension((int)(UI.WIDTH/2.52), 0));
		_botright.setPreferredSize(new Dimension((int)(UI.WIDTH/2.52), 0));
		_botright.addChangeListener(this);
		Utils.setDefaultLook(_botright);
		
		_botright.setForeground(Colors.RED_TEXT);
		_botright.setFont(Fonts.TITLE_BAR);
		if (_world instanceof ChatServer)
			_botright.addTab("Chat", _chat = new ChatPanel((ChatServer)_world));
		_botright.addTab("Stats", _stats = new StatPanel(_world, _disease));
		if (_multiplayer) {
			_botright.addTab("Players", _lb = new Leaderboard(_world));
		}
		_botright.addTab("News", _news = new NewsPanel(_world, _ml));
		_botright.addTab("Regions", _regions =  new RegionPanel(_world));
		for (int i = 0; i < _botright.getTabCount(); i++) {
			_botright.setBackgroundAt(i, Colors.MENU_BACKGROUND);
		}
		bottom.add(_botright);
		
		add(bottom);
	}

	@Override
	public void setupForDisplay() {
		if (_loaded) {
			if (_world.hostDisconnected()) {
				Utils.getParentFrame(this).setPanel(new MainMenu());
			}
			Utils.getParentFrame(this).setTitle(new InGameTitleBar(_world, !_multiplayer));
			_timer = new Timer(1000/3, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (_chat != null && _chat.hasNew()) {
						_botright.setBackgroundAt(0, new Color(80, 0, 0));
					}
					if (_multiplayer && _news.hasNew()) {
						_botright.setBackgroundAt(3, new Color(80, 0, 0));
					} else if (!_multiplayer && _news.hasNew()) {
						_botright.setBackgroundAt(1, new Color(80, 0, 0));
					}
				}
			});
			_timer.start();
			if (_chat != null)
				_chat.requestFocusInWindow();
			if (_world.getInfected() == 0)
				_map.setChooseMode(true);
			else
				_map.setChooseMode(false);
		} else {
			Utils.getParentFrame(this).setPanel(new Loading(true, new Loading.LoadImageWorker(new ImagesDoneLoadingAction(Utils.getParentFrame(this)), Images.GAME_IMAGES)));
		}
	}
	
	@Override
	public String toString() {
		return Strings.SINGLEPLAYER_GAME;
	}
	
    @Override
    public void stopPanel() {
		if (_loaded) {
			_news.stop();
			_info.stop();
			_regions.stop();
			_stats.stop();
			_upgrade.stop();
			_map.stop();
			if (_lb != null)
				_lb.stop();
			if (!_world.isGameOver())
				_world.leaveGame();
		}
    }

	@Override
	public void stateChanged(ChangeEvent e) {
		if (_chat != null && _botright.getSelectedIndex() == 0) {
			_chat.requestFocusInWindow();
			_botright.setBackgroundAt(0, Colors.MENU_BACKGROUND);
			_chat.clearNew();
		} else if (_chat != null && _prevTab == 0) {
			_botright.setBackgroundAt(0, Colors.MENU_BACKGROUND);
			_chat.clearNew();
		}
		if (_multiplayer && _botright.getSelectedIndex() == (_multiplayer ? 3 : 1)) {
			_botright.setBackgroundAt((_multiplayer ? 3 : 1), Colors.MENU_BACKGROUND);
			_news.clearNew();
		} else if (_prevTab == (_multiplayer ? 3 : 1)) {
			_botright.setBackgroundAt((_multiplayer ? 3 : 1), Colors.MENU_BACKGROUND);
			_news.clearNew();
		}
		_prevTab = _botright.getSelectedIndex();
	}
	
}
