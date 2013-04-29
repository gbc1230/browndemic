package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Timer;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;
import edu.brown.cs32.browndemic.world.World;

public class InformationBar extends BrowndemicPanel {
	
	private static final long serialVersionUID = 5751262776229759464L;
	private World _world;
	private int _disease;
	
	private JLabel infected, dead, total; 

	public InformationBar(World w, int disease) {
		super();
		_world = w;
		makeUI();
		_disease = disease;
		new Timer(1000/3, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateInfo();
			}
		}).start();
	}
	
	private void makeUI() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Colors.MENU_BACKGROUND);
		setPreferredSize(new Dimension(UI.WIDTH, UI.TITLE_HEIGHT));
		setMaximumSize(new Dimension(UI.WIDTH, UI.TITLE_HEIGHT));
		setMinimumSize(new Dimension(UI.WIDTH, UI.TITLE_HEIGHT));

		infected = new JLabel();
		infected.setForeground(Colors.RED_TEXT);
		infected.setBackground(Colors.MENU_BACKGROUND);
		infected.setFont(Fonts.NORMAL_TEXT);
		
		dead = new JLabel();
		dead.setForeground(Colors.RED_TEXT);
		dead.setBackground(Colors.MENU_BACKGROUND);
		dead.setFont(Fonts.NORMAL_TEXT);
		
		total = new JLabel();
		total.setForeground(Colors.RED_TEXT);
		total.setBackground(Colors.MENU_BACKGROUND);
		total.setFont(Fonts.NORMAL_TEXT);
		
		add(Box.createGlue());
		add(infected);
		add(Box.createGlue());
		add(dead);
		add(Box.createGlue());
		add(total);
		add(Box.createGlue());
		
		updateInfo();
	}
	
	private void updateInfo() {
		infected.setText(Strings.INFO_INFECTED + _world.getInfected());
		dead.setText(Strings.INFO_DEAD + _world.getDead());
		total.setText(Strings.INFO_POPULATION + _world.getPopulation());
	}
	
	
	@Override
	public void mouseReleasedInside(MouseEvent e) {
		
	}
}
