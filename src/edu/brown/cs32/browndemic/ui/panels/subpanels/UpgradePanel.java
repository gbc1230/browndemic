package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.UIManager;

import edu.brown.cs32.browndemic.disease.Perk;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;
import edu.brown.cs32.browndemic.world.World;

public class UpgradePanel extends BrowndemicPanel {

	private static final long serialVersionUID = 687716354790239279L;
	private int _disease;
	private List<Perk> _availCopy;
	private List<Perk> _ownedCopy;
	private JLabel _perkName, _perkInfo, _points, _buysell, _addPoint;
	private Timer _timer;
	private PerkList _owned, _available;
	private Perk _selected;
	private World _world;
	
	public UpgradePanel(World world, int disease) {
		super();
		_disease = disease;
		_world = world;
		makeUI();
		_timer = new Timer(1000/5, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
			
		});
		_timer.setInitialDelay(0);
		_timer.start();
	}
	
	private void makeUI() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Colors.TRANSPARENT);
		setOpaque(false);

		_owned = new PerkList(new ArrayList<Perk>(), this);
		JScrollPane owned = new JScrollPane(_owned, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		owned.setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		owned.getViewport().setBackground(Colors.MENU_BACKGROUND);
		owned.getVerticalScrollBar().setUnitIncrement(16);

		_available = new PerkList(new ArrayList<Perk>(), this);
		JScrollPane avail = new JScrollPane(_available, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		avail.setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		avail.getViewport().setBackground(Colors.MENU_BACKGROUND);
		avail.getVerticalScrollBar().setUnitIncrement(16);
		

		
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
		
		JTabbedPane perks = new JTabbedPane();
		perks.setFont(Fonts.TITLE_BAR);
		perks.setForeground(Colors.RED_TEXT);
		perks.addTab("Available", avail);
		perks.addTab("Owned", owned);
		Utils.setDefaultLook(perks);
		
		Utils.setOSLook(avail, owned);
		
		add(perks);
		
		JPanel infoContainer = new JPanel();
		infoContainer.setLayout(new BoxLayout(infoContainer, BoxLayout.Y_AXIS));
		infoContainer.setBackground(Colors.TRANSPARENT);
		infoContainer.setOpaque(false);
		infoContainer.setPreferredSize(new Dimension(250, 0));
		infoContainer.setMinimumSize(new Dimension(250, 0));
		infoContainer.setMaximumSize(new Dimension(250, UI.CONTENT_HEIGHT));
		
		infoContainer.add(Box.createRigidArea(new Dimension(0, 28)));
		
		JPanel info = new JPanel();
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		info.setBackground(Colors.MENU_BACKGROUND);
		info.setPreferredSize(new Dimension(250, 0));
		info.setMinimumSize(new Dimension(250, 0));
		info.setMaximumSize(new Dimension(250, UI.CONTENT_HEIGHT));
		info.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Colors.RED_TEXT));
		
		_perkName = new JLabel();
		_perkName.setFont(Fonts.TITLE_BAR);
		_perkName.setForeground(Colors.RED_TEXT);
		_perkInfo = new JLabel();
		_perkInfo.setFont(Fonts.NORMAL_TEXT);
		_perkInfo.setForeground(Colors.RED_TEXT);

		_buysell = new HoverLabel("", Fonts.TITLE_BAR, Colors.RED_TEXT, Colors.HOVER_TEXT);
		_buysell.addMouseListener(this);
		
		_addPoint = new HoverLabel("FREE POINTS!", Fonts.TITLE_BAR, Colors.RED_TEXT, Colors.HOVER_TEXT);
		_addPoint.addMouseListener(this);
		
		_points = new JLabel();
		_points.setFont(Fonts.TITLE_BAR);
		_points.setForeground(Colors.RED_TEXT);
		//_points.setText("Points: " + _world.getDisease(_disease).getPoints());
		
		info.add(_perkName);
		info.add(Box.createRigidArea(new Dimension(0, 20)));
		info.add(_perkInfo);
		info.add(Box.createGlue());
		info.add(_buysell);
		info.add(Box.createRigidArea(new Dimension(0, 20)));
		info.add(_points);
		info.add(_addPoint);
		
		infoContainer.add(info);
		
		add(infoContainer);
	}
	
	private void update() {
		_points.setText("Points: " + _world.getDisease(_disease).getPoints());
		if (!_world.getDisease(_disease).getAvailablePerks().equals(_availCopy) || !_world.getDisease(_disease).getOwnedPerks().equals(_ownedCopy)) {
			_availCopy = new ArrayList<>(_world.getDisease(_disease).getAvailablePerks());
			_ownedCopy = new ArrayList<>(_world.getDisease(_disease).getOwnedPerks());
			_owned.setList(_ownedCopy);
			List<Perk> _tempList = new ArrayList<>();
			for (Perk p : _availCopy) {
				if (!_ownedCopy.contains(p))
					_tempList.add(p);
			}
			_available.setList(_tempList);
		}
	}
	
	private void setDescription(String description) {
		_perkInfo.setText(String.format("<html><body style='width: %dpx'><font size=%dpt family='%s'>%s</font></body></html>", 200, Fonts.NORMAL_TEXT.getSize(), Fonts.NORMAL_TEXT.getFamily(),description));
	}
	
	public void setPerk(Perk p) {
		if (p == null) {
			_perkName.setText("");
			setDescription("");
			_buysell.setText("");
			return;
		}
		_perkName.setText(p.getName());
		setDescription(p.getDescription());
		_selected = p;
		if (p.isOwned()) {
			_buysell.setText("SELL");
		} else if (p.isAvail()) {
			_buysell.setText("BUY (" + p.getCost() + ")");
		} else {
			_buysell.setText("");
		}
	}
	
	@Override
	public void mouseReleasedInside(MouseEvent e) {
		if (e.getSource() == _buysell) {
			if (_selected != null) {
				if (_selected.isOwned()) {
					try {
						List<Perk> soldPerks = _selected.getWillBeSold();
						int val;
						String losegain = (_selected.getCumSellPrice() > 0 ? "gain" : "lose");
						if (soldPerks.isEmpty()) {
							val = JOptionPane.showConfirmDialog(Utils.getParentFrame(this), 
									String.format("Are you sure you want to sell '%s'?  You will %s %d points for selling this perk.",
											_selected.getName(), losegain, Math.abs(_selected.getCumSellPrice())), 
									"Confirm Sell", JOptionPane.YES_NO_OPTION);
						} else {
							String sell = "";
							for (Perk p : soldPerks) {
								sell += "\n" + p.getName();
							}
							sell.replaceFirst("\n", "");
							val = JOptionPane.showConfirmDialog(Utils.getParentFrame(this), 
									String.format("Are you sure you want to sell '%s'?" +
											" The following perks that depend on '%s' will also be sold: \n" +
											"%s\nYou will %s %d points for selling these perks.",
											_selected.getName(), _selected.getName(), sell, losegain, Math.abs(_selected.getCumSellPrice())), 
									"Confirm Sell", JOptionPane.YES_NO_OPTION);
						}
						if (val == JOptionPane.YES_OPTION) {
							_world.getDisease(_disease).sellCumPerk(_selected.getID());
							setPerk(null);
						}
					} catch (IllegalAccessException e1) {
						JOptionPane.showMessageDialog(Utils.getParentFrame(this), 
								String.format("Could not buy perk '%s' because %d points are required and you currently have %d points.", 
										_selected.getName(), _selected.getCumSellPrice(), _world.getDisease(_disease).getPoints()), 
								"Not Enough Points!", JOptionPane.PLAIN_MESSAGE);
					}
				} else if (_selected.isAvail()) {
					try {
						_world.getDisease(_disease).buyPerk(_selected.getID());
						setPerk(null);
					} catch (IllegalAccessException e1) {
						JOptionPane.showMessageDialog(Utils.getParentFrame(this), 
								String.format("Could not buy perk '%s' because %d points are required and you currently have %d points.", 
										_selected.getName(), _selected.getCost(), _world.getDisease(_disease).getPoints()), 
								"Not Enough Points!", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		}
		if (e.getSource() == _addPoint) {
			_world.getDisease(_disease).addPoints(10);
		}
	}
	
	public void stop() {
		_timer.stop();
	}
}
