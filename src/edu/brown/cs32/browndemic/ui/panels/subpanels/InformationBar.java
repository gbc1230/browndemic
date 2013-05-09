package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

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
	private Timer _timer;
	private boolean _multi, _yours = true;
	
	private JLabel infected, dead, total, healthy, cureProgress; 

	public InformationBar(World w, int disease, boolean multi) {
		super();
		_world = w;
		_disease = disease;
		_multi = multi;
		makeUI();
		_timer = new Timer(1000/10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateInfo();
			}
		});
		_timer.start();
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
		
		healthy = new JLabel();
		healthy.setForeground(Colors.RED_TEXT);
		healthy.setBackground(Colors.MENU_BACKGROUND);
		healthy.setFont(Fonts.NORMAL_TEXT);
		
		cureProgress = new JLabel();
		cureProgress.setForeground(Colors.RED_TEXT);
		cureProgress.setBackground(Colors.MENU_BACKGROUND);
		cureProgress.setFont(Fonts.NORMAL_TEXT);
		
		add(Box.createGlue());
		add(healthy);
		add(Box.createGlue());
		add(infected);
		add(Box.createGlue());
		add(dead);
		add(Box.createGlue());
		add(total);
		add(Box.createGlue());
		add(cureProgress);
		add(Box.createGlue());
		
		updateInfo();
	}
	
	private void updateInfo() {
		NumberFormat nf = NumberFormat.getInstance();
		healthy.setText(String.format("%s%s", Strings.INFO_HEALTHY, nf.format(_world.getHealthy())));
		if (_multi && _yours) {
			infected.setText(String.format("%s%s", Strings.INFO_INFECTED, nf.format(_world.getInfected(_disease))));
			dead.setText(String.format("%s%s", Strings.INFO_DEAD, nf.format(_world.getDead(_disease))));
		} else {
			infected.setText(String.format("%s%s", Strings.INFO_INFECTED, nf.format(_world.getInfected())));
			dead.setText(String.format("%s%s", Strings.INFO_DEAD, nf.format(_world.getDead())));
		}
		total.setText(Strings.INFO_POPULATION + nf.format(_world.getPopulation()));
		try {
			cureProgress.setText(String.format("%s%.2f%%", Strings.INFO_CURED, _world.getCurePercentage(_disease)));
		} catch (IndexOutOfBoundsException e) {
			cureProgress.setText(Strings.INFO_CURED);
		}
	}
	
	public void setYours(boolean b) {
		_yours = b;
	}
	
	public void stop() {
		_timer.stop();
	}
}
