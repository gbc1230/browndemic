package edu.brown.cs32.browndemic.ui.panels.menus;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.world.World;

public class SinglePlayerGame extends UIPanel {
	
	private static final long serialVersionUID = 3275157554958820602L;
	
	private World _world;
	
	public SinglePlayerGame(World w) {
		super();
		_world = w;
		makeUI();
	}
	
	private void makeUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel info = new JPanel();
		info.setLayout(new BoxLayout(info, BoxLayout.X_AXIS));
		
		
		
		add(info);
	}

	@Override
	public void setupForDisplay() {
		//TODO: Set TitleBar
	}
	
}
