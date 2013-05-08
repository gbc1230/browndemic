package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.components.MenuButtonItemIcon;
import edu.brown.cs32.browndemic.ui.components.WorldMap;
import edu.brown.cs32.browndemic.ui.components.WorldMap.Layer;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;
import edu.brown.cs32.browndemic.world.World;

public class InformationBar extends BrowndemicPanel implements ActionListener, ChangeListener {
	
	private static final long serialVersionUID = 5751262776229759464L;
	private World _world;
	private int _disease;
	private Timer _timer;
	private WorldMap _wm;
	private JRadioButtonMenuItem _infdead, _pop, _infdeadother, _wealth, _inf, _dead, _infother, _deadother;
	private JCheckBoxMenuItem _airports, _airplanes;
	private JMenu _layers;
	private boolean _multi;
	
	private JLabel infected, dead, total, healthy, cureProgress; 

	public InformationBar(World w, int disease, WorldMap wm, boolean multi) {
		super();
		_world = w;
		_disease = disease;
		_wm = wm;
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
		
		UIManager.put("RadioButtonMenuItem.checkIcon", new MenuButtonItemIcon(Resources.getImage(Images.UNSELECTED), Resources.getImage(Images.SELECTED)));
		
		JMenuBar mb = new JMenuBar();
		mb.setBackground(Colors.MENU_BACKGROUND);
		mb.setBorder(BorderFactory.createEmptyBorder());
		mb.setAlignmentY(CENTER_ALIGNMENT);
		
		_layers = new JMenu("Layers");
		_layers.setFont(Fonts.NORMAL_TEXT);
		_layers.setBackground(Colors.MENU_BACKGROUND);
		_layers.setForeground(Colors.RED_TEXT);
		mb.add(_layers);
		
		ButtonGroup group = new ButtonGroup();

		if (_multi) {
			_infdead = new JRadioButtonMenuItem("Infected + Dead (Your Disease)");
		} else {
			_infdead = new JRadioButtonMenuItem("Infected + Dead");
		}
		_infdead.addActionListener(this);
		_infdead.setSelected(true);
		_infdead.setFont(Fonts.NORMAL_TEXT);
		_infdead.setBackground(Colors.MENU_BACKGROUND);
		_infdead.setForeground(Colors.RED_TEXT);
		group.add(_infdead);
		_layers.add(_infdead);
		
		if (_multi) {
			_infdeadother = new JRadioButtonMenuItem("Infected + Dead (Other Diseases)");
			_infdeadother.addActionListener(this);
			_infdeadother.setFont(Fonts.NORMAL_TEXT);
			_infdeadother.setBackground(Colors.MENU_BACKGROUND);
			_infdeadother.setForeground(Colors.RED_TEXT);
			group.add(_infdeadother);
			_layers.add(_infdeadother);
		}
		
		if (_multi) {
			_inf = new JRadioButtonMenuItem("Infected (Your Disease)");
		} else {
			_inf = new JRadioButtonMenuItem("Infected");
		}
		_inf.addActionListener(this);
		_inf.setFont(Fonts.NORMAL_TEXT);
		_inf.setBackground(Colors.MENU_BACKGROUND);
		_inf.setForeground(Colors.RED_TEXT);
		group.add(_inf);
		_layers.add(_inf);
		
		if (_multi) {
			_infother = new JRadioButtonMenuItem("Infected (Other Diseases)");
			_infother.addActionListener(this);
			_infother.setFont(Fonts.NORMAL_TEXT);
			_infother.setBackground(Colors.MENU_BACKGROUND);
			_infother.setForeground(Colors.RED_TEXT);
			group.add(_infother);
			_layers.add(_infother);
		}
		
		if (_multi) {
			_dead = new JRadioButtonMenuItem("Dead (Your Disease)");
		} else {
			_dead = new JRadioButtonMenuItem("Dead");
		}
		_dead.addActionListener(this);
		_dead.setFont(Fonts.NORMAL_TEXT);
		_dead.setBackground(Colors.MENU_BACKGROUND);
		_dead.setForeground(Colors.RED_TEXT);
		group.add(_dead);
		_layers.add(_dead);
		
		if (_multi) {
			_deadother = new JRadioButtonMenuItem("Dead (Other Diseases)");
			_deadother.addActionListener(this);
			_deadother.setFont(Fonts.NORMAL_TEXT);
			_deadother.setBackground(Colors.MENU_BACKGROUND);
			_deadother.setForeground(Colors.RED_TEXT);
			group.add(_deadother);
			_layers.add(_deadother);
		}

		_pop = new JRadioButtonMenuItem("Population");
		_pop.addActionListener(this);
		_pop.setFont(Fonts.NORMAL_TEXT);
		_pop.setBackground(Colors.MENU_BACKGROUND);
		_pop.setForeground(Colors.RED_TEXT);
		group.add(_pop);
		_layers.add(_pop);
		
		_wealth = new JRadioButtonMenuItem("Wealth");
		_wealth.addActionListener(this);
		_wealth.setFont(Fonts.NORMAL_TEXT);
		_wealth.setBackground(Colors.MENU_BACKGROUND);
		_wealth.setForeground(Colors.RED_TEXT);
		group.add(_wealth);
		_layers.add(_wealth);
		
		_layers.addSeparator();
		
		UIManager.put("CheckBoxMenuItem.checkIcon", new MenuButtonItemIcon(Resources.getImage(Images.UNCHECKED), Resources.getImage(Images.CHECKED)));
		
		_airports = new JCheckBoxMenuItem("Show Airports", true);
		_airports.setFont(Fonts.NORMAL_TEXT);
		_airports.setBackground(Colors.MENU_BACKGROUND);
		_airports.setForeground(Colors.RED_TEXT);
		_airports.addChangeListener(this);
		_layers.add(_airports);
		
		_airplanes = new JCheckBoxMenuItem("Show Airplanes", true);
		_airplanes.setFont(Fonts.NORMAL_TEXT);
		_airplanes.setBackground(Colors.MENU_BACKGROUND);
		_airplanes.setForeground(Colors.RED_TEXT);
		_airplanes.addChangeListener(this);
		_layers.add(_airplanes);
		
		
		Utils.setDefaultLook(mb, _layers);
		if (_multi) {
			Utils.setDefaultLook(_infdeadother);
		}

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
		
		add(mb);
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
		healthy.setText(Strings.INFO_HEALTHY + NumberFormat.getInstance().format(_world.getHealthy()));
		infected.setText(Strings.INFO_INFECTED + NumberFormat.getInstance().format(_world.getInfected()));
		dead.setText(Strings.INFO_DEAD + NumberFormat.getInstance().format(_world.getDead()));
		total.setText(Strings.INFO_POPULATION + NumberFormat.getInstance().format(_world.getPopulation()));
		try {
			cureProgress.setText(String.format("%s%.2f%%", Strings.INFO_CURED, _world.getCurePercentage(_disease)));
		} catch (IndexOutOfBoundsException e) {
			cureProgress.setText(Strings.INFO_CURED);
		}
	}
	
	
	@Override
	public void mouseReleasedInside(MouseEvent e) {
		
	}
	
	public void stop() {
		_timer.stop();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == _infdead) {
			_wm.setLayer(Layer.INFECTED_DEAD);
		} else if (e.getSource() == _pop) {
			_wm.setLayer(Layer.POPULATION);
		} else if (e.getSource() == _infdeadother) {
			_wm.setLayer(Layer.INFECTED_DEAD_OTHER);
		} else if (e.getSource() == _wealth) {
			_wm.setLayer(Layer.WEALTH);
		} else if (e.getSource() == _inf) {
			_wm.setLayer(Layer.INFECTED);
		} else if (e.getSource() == _infother) {
			_wm.setLayer(Layer.INFECTED_OTHER);
		} else if (e.getSource() == _dead) {
			_wm.setLayer(Layer.DEAD);
		} else if (e.getSource() == _deadother) {
			_wm.setLayer(Layer.DEAD_OTHER);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == _airports) {
			_wm.setDrawAirports(_airports.isSelected());
		} else if (e.getSource() == _airplanes) {
			_wm.setDrawAirplanes(_airplanes.isSelected());
		}
	}
}
