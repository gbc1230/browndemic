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
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.UIManager;

import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.disease.Perk;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;

public class UpgradePanel extends BrowndemicPanel {

	private static final long serialVersionUID = 687716354790239279L;
	private Disease _disease;
	private List<Perk> _availCopy;
	private List<Perk> _ownedCopy;
	private JLabel _perkName, _perkInfo, _points, _buysell, _addPoint;
	private Timer _timer;
	private PerkList _owned, _available;
	private Perk _selected;
	
	public UpgradePanel(Disease d) {
		super();
		_disease = d;
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
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Colors.TRANSPARENT);
		setOpaque(false);
		
//		_perkList = new JList<>(_perks);
//		_perkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		_perkList.setLayoutOrientation(JList.VERTICAL);
//		_perkList.addListSelectionListener(this);
//		_perkList.setBackground(Colors.MENU_BACKGROUND);
//		_perkList.setForeground(Colors.RED_TEXT);
//		_perkList.setFont(Fonts.NORMAL_TEXT);

		_owned = new PerkList(new ArrayList<Perk>(), this);
		_owned.setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		_available = new PerkList(new ArrayList<Perk>(), this);
		_available.setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		
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
		perks.addTab("Owned", _owned);
		perks.addTab("Available", _available);
		Utils.setDefaultLook(perks);
		
		add(perks);
		
		//JScrollPane list = new JScrollPane(_perkList);
		//add(list);
		
		JPanel info = new JPanel();
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		info.setBackground(Colors.MENU_BACKGROUND);
		info.setPreferredSize(new Dimension(250, 0));
		info.setMinimumSize(new Dimension(250, 0));
		info.setMaximumSize(new Dimension(250, UI.CONTENT_HEIGHT));
		
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
		_points.setText("Points: " + _disease.getPoints());
		
		info.add(_perkName);
		info.add(Box.createRigidArea(new Dimension(0, 20)));
		info.add(_perkInfo);
		info.add(Box.createGlue());
		info.add(_buysell);
		info.add(Box.createRigidArea(new Dimension(0, 20)));
		info.add(_points);
		info.add(_addPoint);
		
		add(info);
	}
	
	private void update() {
		_points.setText("Points: " + _disease.getPoints());
		if (!_disease.getAvailablePerks().equals(_availCopy) || !_disease.getOwnedPerks().equals(_ownedCopy)) {
			_availCopy = new ArrayList<>(_disease.getAvailablePerks());
			_ownedCopy = new ArrayList<>(_disease.getOwnedPerks());
//			_perks.clear();
//			for (Perk p : _ownedCopy) {
//				_perks.addElement(p);
//			}
//			for (Perk p : _availCopy) {
//				_perks.addElement(p);
//			}
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
		_perkName.setText(p.getName());
		setDescription(p.getDescription());
		_selected = p;
		if (p.isOwned()) {
			_buysell.setText("SELL (" + p.getCumSellPrice() + ")");
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
						_disease.sellCumPerk(_selected.getID());
						_buysell.setText("");
					} catch (IllegalAccessException e1) {
						// Don't sell it!
					}
				} else if (_selected.isAvail()) {
					try {
						_disease.buyPerk(_selected.getID());
						_buysell.setText("");
					} catch (IllegalAccessException e1) {
						// Don't buy it!
					}
				}
			}
		}
		if (e.getSource() == _addPoint) {
			_disease.addPoints(10);
		}
	}
	
	public void stop() {
		_timer.stop();
	}
}
