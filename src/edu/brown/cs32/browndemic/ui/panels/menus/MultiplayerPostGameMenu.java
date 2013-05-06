package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.interfaces.ChatServer;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.ChatPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.Leaderboard;
import edu.brown.cs32.browndemic.ui.panels.titlebars.DefaultTitleBar;
import edu.brown.cs32.browndemic.world.World;

public class MultiplayerPostGameMenu extends UIPanel {
	private World _world;
	private int _disease;
	private Leaderboard _lb;

	public MultiplayerPostGameMenu(World world, int disease) {
		super();
		_world = world;
		_disease = disease;
		makeUI();
	}
	
	@Override
	public void makeUI() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setOpaque(false);
		
		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setMaximumSize(new Dimension(UI.WIDTH/3, UI.CONTENT_HEIGHT));
		if (_world instanceof ChatServer)
			left.add(new ChatPanel((ChatServer)_world));
		left.add(_lb = new Leaderboard(_world));
		
		JPanel right = new JPanel();
		right.setBackground(Colors.TRANSPARENT);
		right.setOpaque(false);
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		JLabel vicdefeat = new JLabel();
		vicdefeat.setFont(Fonts.HUGE_TEXT);
		vicdefeat.setForeground(Colors.RED_TEXT);
		if (_world.getWinners().contains(_disease)) {
			vicdefeat.setText(Strings.VICTORY);
		} else {
			vicdefeat.setText(Strings.DEFEAT);
		}
		right.add(vicdefeat);
		
		JTabbedPane stats = new JTabbedPane();
		right.add(stats);
		
		for (Disease d : _world.getDiseases()) {
			stats.addTab(d.getName(), generateStatsPanel(d.getID()));
		}
		
		add(left);
		add(right);
		
	}
	
	private JPanel generateStatsPanel(int disease) {
		Disease d = _world.getDisease(disease);
		JPanel out = new JPanel();
		out.setBackground(Colors.TRANSPARENT);
		out.setOpaque(false);
		out.setLayout(new BoxLayout(out, BoxLayout.Y_AXIS));
		
		long dead = _world.getDead(disease);
		
		out.add(generateSingleStatPanel(Strings.PEOPLE_KILLED, Long.toString(dead)));
		
		return out;
	}
	
	private JPanel generateSingleStatPanel(String left, String right) {
		JPanel out = new JPanel();
		out.setLayout(new BoxLayout(out, BoxLayout.X_AXIS));
		
		JLabel l = new JLabel(left);
		l.setFont(Fonts.BIG_TEXT);
		l.setForeground(Colors.RED_TEXT);
		
		JLabel r = new JLabel(right);
		r.setFont(Fonts.BIG_TEXT);
		r.setForeground(Colors.RED_TEXT);
		

		out.add(Box.createGlue());
		out.add(l);
		out.add(Box.createRigidArea(new Dimension(20, 0)));
		out.add(r);
		out.add(Box.createGlue());
		return out;
	}
	
	@Override
	public void setupForDisplay() {
		Utils.getParentFrame(this).setTitle(new DefaultTitleBar(this));
	}

	public String toString() {
		return Strings.POST_GAME_MENU;
	}
	
	@Override
	public void stopPanel() {
		_lb.stop();
	}

}
