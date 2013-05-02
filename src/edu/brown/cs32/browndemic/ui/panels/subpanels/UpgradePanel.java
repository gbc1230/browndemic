package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.disease.Perk;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;

public class UpgradePanel extends BrowndemicPanel implements ListSelectionListener {

	private static final long serialVersionUID = 687716354790239279L;
	private Disease _disease;
	private DefaultListModel<Perk> _perks = new DefaultListModel<>();
	private JList<Perk> _perkList;
	private List<Perk> _availCopy;
	private List<Perk> _ownedCopy;
	private JLabel _perkName, _perkInfo, _points, _buysell, _addPoint;
	private Timer _timer;
	
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
		setBackground(Colors.MENU_BACKGROUND);
		setPreferredSize(new Dimension((int)(UI.WIDTH/1.5), UI.CONTENT_HEIGHT));
		setMinimumSize(new Dimension((int)(UI.WIDTH/1.5), 0));
		setMaximumSize(new Dimension((int)(UI.WIDTH/1.5), UI.CONTENT_HEIGHT));
		
		_perkList = new JList<>(_perks);
		_perkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_perkList.setLayoutOrientation(JList.VERTICAL);
		_perkList.addListSelectionListener(this);
		_perkList.setBackground(Colors.MENU_BACKGROUND);
		_perkList.setForeground(Colors.RED_TEXT);
		_perkList.setFont(Fonts.NORMAL_TEXT);
		
		JScrollPane list = new JScrollPane(_perkList);
		add(list);
		
		JPanel info = new JPanel();
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		info.setBackground(Colors.MENU_BACKGROUND);
		info.setPreferredSize(new Dimension(250, UI.CONTENT_HEIGHT));
		info.setMinimumSize(new Dimension(250, UI.CONTENT_HEIGHT));
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
			_perks.clear();
			for (Perk p : _ownedCopy) {
				_perks.addElement(p);
			}
			for (Perk p : _availCopy) {
				if (!_ownedCopy.contains(p))
					_perks.addElement(p);
			}
		}
	}
	
	private void setDescription(String description) {
		_perkInfo.setText(String.format("<html><body style='width: %dpx'><font size=%dpt family='%s'>%s</font></body></html>", 200, Fonts.NORMAL_TEXT.getSize(), Fonts.NORMAL_TEXT.getFamily(),description));
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) return;
		try {
			Perk selected = _perks.get(_perkList.getSelectedIndex());
			_perkName.setText(selected.getName());
			setDescription(selected.getDescription());
			if (selected.isOwned()) {
				_buysell.setText("SELL (" + selected.getSellPrice() + ")");
			} else if (selected.isAvail()) {
				_buysell.setText("BUY (" + selected.getCost() + ")");
			} else {
				_buysell.setText("");
			}
		} catch (IndexOutOfBoundsException e1) {
			_perkName.setText("");
			_perkInfo.setText("");
		}
	}
	
	@Override
	public void mouseReleasedInside(MouseEvent e) {
		if (e.getSource() == _buysell) {
			if (_perkList.getSelectedIndex() >= 0) {
				Perk selected = _perks.get(_perkList.getSelectedIndex());
				if (selected.isOwned()) {
					try {
						_disease.sellPerk(selected.getID());
						_buysell.setText("");
					} catch (IllegalAccessException e1) {
						// Don't sell it!
					}
				} else if (selected.isAvail()) {
					try {
						_disease.buyPerk(selected.getID());
						_buysell.setText("");
					} catch (IllegalAccessException e1) {
						// Don't buy it!
					}
				}
			}
		}
		if (e.getSource() == _addPoint) {
			_disease.addPoint();
		}
	}
	
	public void stop() {
		_timer.stop();
	}
}
