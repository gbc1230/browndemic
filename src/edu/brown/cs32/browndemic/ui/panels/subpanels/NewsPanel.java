package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Timer;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;
import edu.brown.cs32.browndemic.world.MainWorld;
import edu.brown.cs32.browndemic.world.World;

public class NewsPanel extends BrowndemicPanel {

	private static final long serialVersionUID = 1350112995198953245L;
	
	private World _world;
	private List<String> _localCopy;
	
	public NewsPanel(MainWorld world) {
		super();
		_world = world;
		makeUI();
		new Timer(1000/3, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
	}
	
	private void makeUI() {
		setBackground(Colors.MENU_BACKGROUND);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		
		if (_world.getNews() != null) {
			_localCopy = new ArrayList<>(_world.getNews());
		} else {
			_localCopy = new ArrayList<>();
		}
				
		updateNews();
	}
	
	private void updateNews() {
		removeAll();
		for (String s : _localCopy) {
			JLabel newsItem = new JLabel(s);
			newsItem.setForeground(Colors.RED_TEXT);
			newsItem.setFont(Fonts.NORMAL_TEXT);
			add(newsItem);
		}
		revalidate();
	}
	
	private void update() {
		if (_world.getNews() != null && !_localCopy.equals(_world.getNews())) {
			_localCopy = new ArrayList<>(_world.getNews());
			updateNews();
		}
	}
	

}
