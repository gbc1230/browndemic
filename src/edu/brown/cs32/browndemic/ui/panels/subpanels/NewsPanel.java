package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.components.MarqueeLabel;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;
import edu.brown.cs32.browndemic.world.World;

public class NewsPanel extends BrowndemicPanel {

	private static final long serialVersionUID = 1350112995198953245L;
	
	private World _world;
	private List<String> _localCopy;
	private Timer _timer;
	private JPanel _news;
	private MarqueeLabel _ml;
	private boolean _hasNew = false;
	
	public NewsPanel(World world, MarqueeLabel ml) {
		super();
		_world = world;
		_ml = ml;
		makeUI();
		_timer = new Timer(1000/5, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
		_timer.start();
	}
	
	private void makeUI() {
		setBackground(Colors.MENU_BACKGROUND);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		
		_news = new JPanel();
		_news.setLayout(new BoxLayout(_news, BoxLayout.Y_AXIS));
		_news.setBackground(Colors.MENU_BACKGROUND);
		
		JScrollPane scroll = new JScrollPane(_news, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getViewport().setBackground(Colors.MENU_BACKGROUND);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		add(scroll);
		
		if (_world.getNews() != null) {
			_localCopy = new ArrayList<>(_world.getNews());
		} else {
			_localCopy = new ArrayList<>();
		}
				
		updateNews();
	}
	
	private void updateNews() {
		_hasNew = _localCopy.size() > 0;
		_news.removeAll();
		Collections.reverse(_localCopy);
		for (String s : _localCopy) {
			
			JLabel newsItem = new JLabel(String.format("<html><body style='width: %dpx; padding: 4px'><font size=%dpt family='%s'>%s</font></body></html>", (int)(UI.WIDTH/3.6), Fonts.NORMAL_TEXT.getSize(), Fonts.NORMAL_TEXT.getFamily(), s));
			newsItem.setForeground(Colors.RED_TEXT);
			newsItem.setFont(Fonts.NORMAL_TEXT);
			_news.add(newsItem);
		}
		_news.revalidate();
		_news.repaint();
		Collections.reverse(_localCopy);
	}
	
	private void update() {
		if (_world.getNews() != null && !_localCopy.equals(_world.getNews())) {
			for (String s : _world.getNews()) {
				if (!_localCopy.contains(s)) {
					_ml.addString(s);
				}
			}
			_localCopy = new ArrayList<>(_world.getNews());
			updateNews();
		}
	}
	
	public void stop() {
		_timer.stop();
	}
	
	public boolean hasNew() {
		return _hasNew;
	}
	
	public void clearNew() {
		_hasNew = false;
	}
	

}
