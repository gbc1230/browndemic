package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.interfaces.ChatServer;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.ChatPanel;
import edu.brown.cs32.browndemic.ui.panels.subpanels.Leaderboard;
import edu.brown.cs32.browndemic.ui.panels.titlebars.DefaultTitleBar;
import edu.brown.cs32.browndemic.world.World;

public class MultiplayerPostGameMenu extends UIPanel {
	private static final long serialVersionUID = -6130083033132116734L;
	private World _world;
	private int _disease;
	private Leaderboard _lb;
	private JLabel menu_, single_, multi_;

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
		left.setBackground(Colors.TRANSPARENT);
		left.setOpaque(false);
		
		menu_ = new HoverLabel(Strings.MAIN_MENU_CAPS, Fonts.BUTTON_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT);
		menu_.setAlignmentX(Component.CENTER_ALIGNMENT);
		menu_.addMouseListener(this);
		single_ = new HoverLabel(Strings.SINGLE_PLAYER, Fonts.BUTTON_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT); 
		single_.setAlignmentX(Component.CENTER_ALIGNMENT);
		single_.addMouseListener(this);
		multi_ = new HoverLabel(Strings.MULTI_PLAYER, Fonts.BUTTON_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT);
		multi_.setAlignmentX(Component.CENTER_ALIGNMENT);
		multi_.addMouseListener(this);

		left.add(Box.createRigidArea(new Dimension(0, UI.CONTENT_HEIGHT/8)));
		left.add(menu_);
		left.add(single_);
		left.add(multi_);
		left.add(Box.createRigidArea(new Dimension(0, UI.CONTENT_HEIGHT/8)));
		
		if (_world instanceof ChatServer)
			left.add(new ChatPanel((ChatServer)_world));
		left.add(_lb = new Leaderboard(_world));
		_lb.setMaximumSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT/4));
		_lb.setPreferredSize(new Dimension(UI.WIDTH/2, UI.CONTENT_HEIGHT/4));
		
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
		vicdefeat.setAlignmentX(CENTER_ALIGNMENT);
		right.add(vicdefeat);
		
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
		
		JTabbedPane stats = new JTabbedPane();
		stats.setForeground(Colors.RED_TEXT);
		stats.setFont(Fonts.TITLE_BAR);
		right.add(stats);
		
		Utils.setDefaultLook(stats);
		
		for (Disease d : _world.getDiseases()) {
			stats.addTab(d.getName(), generateStatsPanel(d.getID()));
		}
		
		add(left);
		add(right);
		
	}
	
	private JPanel generateStatsPanel(int disease) {
		JPanel out = new JPanel();
		out.setBackground(Colors.TRANSPARENT);
		out.setOpaque(false);
		out.setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		out.setLayout(new BoxLayout(out, BoxLayout.Y_AXIS));
		
		Disease d = _world.getDisease(disease);

		long dead = _world.getDead(disease);
		int perksBought = d.getNumPerksBought();
		int perksSold = d.getNumPerksSold();
		int randomPerks = d.getNumRandomPerksGot();
		int pointsEarned = d.getNumPointsEarned();
		int pointsSpent = d.getNumPointsUsed();
		
		NumberFormat nf = NumberFormat.getInstance();

		out.add(generateSingleStatPanel(Strings.PEOPLE_KILLED, nf.format(dead)));
		out.add(generateSingleStatPanel(Strings.PERKS_BOUGHT, nf.format(perksBought)));
		out.add(generateSingleStatPanel(Strings.PERKS_SOLD, nf.format(perksSold)));
		out.add(generateSingleStatPanel(Strings.RANDOM_PERKS, nf.format(randomPerks)));
		out.add(generateSingleStatPanel(Strings.POINTS_EARNED, nf.format(pointsEarned)));
		out.add(generateSingleStatPanel(Strings.POINTS_SPENT, nf.format(pointsSpent)));
		
		return out;
	}
	
	private JPanel generateSingleStatPanel(String left, String right) {
		JPanel out = new JPanel();
		out.setLayout(new BoxLayout(out, BoxLayout.X_AXIS));
		out.setBackground(Colors.MENU_BACKGROUND);
		
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

	@Override
	public void mouseReleasedInside(MouseEvent e) {
		if (e.getSource() == menu_) {
			Utils.getParentFrame(this).setPanel(new MainMenu());
		} else if (e.getSource() == single_) {
			Utils.getParentFrame(this).setPanel(new SinglePlayerMenu());
		} else if (e.getSource() == multi_) {
			Utils.getParentFrame(this).setPanel(new MultiplayerMenu());
		}
	}
}
