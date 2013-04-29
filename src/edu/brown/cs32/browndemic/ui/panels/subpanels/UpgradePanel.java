package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import edu.brown.cs32.browndemic.disease.Perks;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;

public class UpgradePanel extends BrowndemicPanel implements ListSelectionListener {

	private static final long serialVersionUID = 687716354790239279L;
	private Disease _disease;
	//private JList<String> _perks;
	private DefaultListModel<Perk> _perks = new DefaultListModel<>();
	private JList<Perk> _perkList;
	private JLabel _perkName, _perkInfo;
	
	public UpgradePanel(Disease d) {
		super();
		_disease = d;
		makeUI();
		new Timer(1000/5, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
			
		}).start();
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
		
		for (Perk p : _disease.getPurchasablePerks()) {
			_perks.addElement(p);
		}
		
		JScrollPane list = new JScrollPane(_perkList);
		add(list);
		
		JPanel info = new JPanel();
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		info.setBackground(Colors.MENU_BACKGROUND);
		info.setPreferredSize(new Dimension(250, UI.HEIGHT));
		info.setMinimumSize(new Dimension(250, UI.HEIGHT));
		info.setMaximumSize(new Dimension(250, UI.HEIGHT));
		
		_perkName = new JLabel();
		_perkName.setFont(Fonts.TITLE_BAR);
		_perkName.setForeground(Colors.RED_TEXT);
		_perkInfo = new JLabel();
		_perkInfo.setFont(Fonts.NORMAL_TEXT);
		_perkInfo.setForeground(Colors.RED_TEXT);
		
		info.add(_perkName);
		info.add(Box.createRigidArea(new Dimension(0, 20)));
		info.add(_perkInfo);
		
		add(info);
	}
	
	private void update() {
		
	}
	
	private void setDescription(String description) {
		_perkInfo.setText(String.format("<html><body style='width: %dpx'>%s</body></html>", 200, description));
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) return;
		try {
			Perk selected = _perks.get(_perkList.getSelectedIndex());
			_perkName.setText(selected.getName());
			setDescription(selected.getDescription());
		} catch (IndexOutOfBoundsException e1) {
			_perkName.setText("");
			_perkInfo.setText("");
		}
	}
}
