package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.brown.cs32.browndemic.ui.BrowndemicFrame;
import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.components.SelectButton;
import edu.brown.cs32.browndemic.ui.listeners.OnSelectListener;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.BackTitleBar;

public class SinglePlayer extends UIPanel implements MouseListener {

	private static final long serialVersionUID = -1231229219691042468L;
	
	BrowndemicFrame _parent;
	JTextField _diseaseName;
	SelectButton _disease1, _disease2, _disease3;
	HoverLabel _start;
	
	public SinglePlayer() {
		super();
		makeUI();
	}
	
	@Override
	public void setupForDisplay() {
		Utils.getParentFrame(this).setTitle(new BackTitleBar(new MainMenu()));
	}
	
	private void makeUI() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(UI.WIDTH, UI.CONTENT_HEIGHT));
		setBackground(Colors.MENU_BACKGROUND);
		
		
		add(Box.createRigidArea(new Dimension(1, 100)));
		
		JPanel diseaseName = new JPanel();
		diseaseName.setLayout(new BoxLayout(diseaseName, BoxLayout.X_AXIS));
		diseaseName.setMaximumSize(new Dimension(UI.WIDTH-150, 200));
		diseaseName.setBackground(Colors.MENU_BACKGROUND);
		
		JLabel diseaseNameLabel = new JLabel(Strings.ENTER_DISEASE_NAME);
		diseaseNameLabel.setFont(Fonts.BIG_TEXT);
		diseaseNameLabel.setForeground(Colors.RED_TEXT);
		_diseaseName = new JTextField();
		_diseaseName.setFont(Fonts.BIG_TEXT);
		_diseaseName.setForeground(Colors.RED_TEXT);
		_diseaseName.setBackground(Colors.MENU_BACKGROUND);
		diseaseName.add(diseaseNameLabel);
		diseaseName.add(_diseaseName);
		add(diseaseName);
		
		add(Box.createGlue());
		
		JLabel selectDisease = new JLabel(Strings.SELECT_DISEASE);
		selectDisease.setFont(Fonts.BIG_TEXT);
		selectDisease.setForeground(Colors.RED_TEXT);
		selectDisease.setAlignmentX(CENTER_ALIGNMENT);
		add(selectDisease);
		
		JPanel disease = new JPanel();
		disease.setLayout(new BoxLayout(disease, BoxLayout.X_AXIS));
		disease.setBackground(Colors.MENU_BACKGROUND);

		_disease1 = new SelectButton(Resources.getImage(Images.DISEASE1), Resources.getImage(Images.DISEASE1_SELECTED));
		_disease2 = new SelectButton(Resources.getImage(Images.DISEASE2), Resources.getImage(Images.DISEASE2_SELECTED));
		_disease3 = new SelectButton(Resources.getImage(Images.DISEASE3), Resources.getImage(Images.DISEASE3_SELECTED));
		_disease2.addOnSelectListener(new SelectListener(_disease1, _disease3));
		_disease1.addOnSelectListener(new SelectListener(_disease2, _disease3));
		_disease3.addOnSelectListener(new SelectListener(_disease2, _disease1));

		disease.add(Box.createGlue());
		disease.add(_disease1);
		disease.add(Box.createGlue());
		disease.add(_disease2);
		disease.add(Box.createGlue());
		disease.add(_disease3);
		disease.add(Box.createGlue());
		add(disease);
		
		add(Box.createGlue());
		
		_start = new HoverLabel(Resources.getImage(Images.STARTGAME), Resources.getImage(Images.STARTGAME_HOVER));
		_start.addMouseListener(this);
		_start.setEnabled(false);
		_start.setAlignmentX(CENTER_ALIGNMENT);
		add(_start);
		
		add(Box.createGlue());
	}
	
	private class SelectListener implements OnSelectListener {
		
		private SelectButton[] other;
		
		public SelectListener(SelectButton... other) {
			this.other = other;
		}

		@Override
		public void doOnSelect() {
			_start.setEnabled(true);
			for (SelectButton b : other) {
				b.deSelect();
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1) return;
		if (!contains(e.getPoint())) return;
		
		if (e.getSource() == _start) {
			int disease;
			if (_disease1.isSelected())
				disease = 1;
			else if (_disease2.isSelected())
				disease = 2;
			else
				disease = 3;
			String name = _diseaseName.getText();
			if (name.equals("")) {
				//TODO: Error, invalid disease name
			}
			
			System.out.println("Start game with:\n\tDisease: " + disease + "\n\tName: " + name);
		}
	}
}
