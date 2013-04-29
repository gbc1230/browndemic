package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Timer;

import edu.brown.cs32.browndemic.ui.BrowndemicFrame;
import edu.brown.cs32.browndemic.ui.DumbChatServer;
import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.ui.components.WorldMap;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.ChatPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.InformationBar;
import edu.brown.cs32.browndemic.ui.panels.subpanels.UpgradePanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.InGameTitleBar;
import edu.brown.cs32.browndemic.world.MainWorld;

public class SinglePlayerGame extends UIPanel {
	
	private static final long serialVersionUID = 3275157554958820602L;
	
	private MainWorld _world;
	private WorldMap _map;
	private boolean loaded = false;
	private int _disease;
	
	public SinglePlayerGame(MainWorld w, int disease) {
		super();
		_world = w;
		_disease = disease;
	}
	
	private class ImagesDoneLoadingAction implements Action {
		BrowndemicFrame _parent;
		public ImagesDoneLoadingAction(BrowndemicFrame parent) {
			_parent = parent;
		}
		@Override
		public void doAction() {
			_map = new WorldMap(_world, Resources.getImage(Images.MAP), Resources.getImage(Images.REGIONS), _disease);
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
			loaded = true;
			_parent.setPanel(SinglePlayerGame.this);
		}
	}
	
	@Override
	protected void makeUI() {
		super.makeUI();
		
		JPanel info = new InformationBar(_world, _disease);
		
		
		add(info);
		
		//add(Box.createGlue());
		
		add(_map);
		
		JPanel bottom = new JPanel();
		bottom.setBackground(Color.GREEN);
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
		
		bottom.add(new UpgradePanel(null));
		bottom.add(new ChatPanel(new DumbChatServer()));
		
		add(bottom);
	}

	@Override
	public void setupForDisplay() {
		if (loaded) {
			Utils.getParentFrame(this).setTitle(new InGameTitleBar(_world, true));
			new Timer(3000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					_map.addRandomPlane();
				}
			}).start();
			_map.setChooseMode(true);
		} else {
			Utils.getParentFrame(this).setPanel(new Loading(true, new Loading.LoadImageWorker(new ImagesDoneLoadingAction(Utils.getParentFrame(this)), Images.GAME_IMAGES)));
		}
	}
	
	@Override
	public String toString() {
		return Strings.SINGLEPLAYER_GAME;
	}
	
}
