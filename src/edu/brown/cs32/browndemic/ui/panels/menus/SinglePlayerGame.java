package edu.brown.cs32.browndemic.ui.panels.menus;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import edu.brown.cs32.browndemic.ui.BrowndemicFrame;
import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.ui.components.WorldMap;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.world.World;

public class SinglePlayerGame extends UIPanel {
	
	private static final long serialVersionUID = 3275157554958820602L;
	
	private World _world;
	private WorldMap _map;
	
	public SinglePlayerGame(World w) {
		super();
		_world = w;
	}
	
	private class ImagesDoneLoadingAction implements Action {
		BrowndemicFrame _parent;
		public ImagesDoneLoadingAction(BrowndemicFrame parent) {
			_parent = parent;
		}
		@Override
		public void doAction() {
			_map = new WorldMap(_world, Resources.getImage(Images.MAP), Resources.getImage(Images.REGIONS));
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
			_parent.setPanel(SinglePlayerGame.this, false);
		}
	}
	
	@Override
	protected void makeUI() {
		super.makeUI();
		
		JPanel info = new JPanel();
		info.setLayout(new BoxLayout(info, BoxLayout.X_AXIS));
		
		
		
		add(info);
		
		//add(Box.createGlue());
		
		add(_map);
		
		add(Box.createGlue());
	}

	@Override
	public void setupForDisplay() {
		//TODO: Set TitleBar
		Utils.getParentFrame(this).setPanel(new Loading(true, new Loading.LoadImageWorker(new ImagesDoneLoadingAction(Utils.getParentFrame(this)), Images.GAME_IMAGES)));
	}
	
	@Override
	public String toString() {
		return Strings.SINGLEPLAYER_GAME;
	}
	
}
