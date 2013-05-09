package edu.brown.cs32.browndemic.ui.panels.titlebars;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicMenuBarUI;

import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.components.MenuButtonItemIcon;
import edu.brown.cs32.browndemic.ui.components.SelectButton;
import edu.brown.cs32.browndemic.ui.components.WorldMap;
import edu.brown.cs32.browndemic.ui.components.WorldMap.Layer;
import edu.brown.cs32.browndemic.ui.panels.DragWindow;
import edu.brown.cs32.browndemic.ui.panels.menus.MainMenu;
import edu.brown.cs32.browndemic.ui.panels.subpanels.InformationBar;
import edu.brown.cs32.browndemic.ui.panels.subpanels.RegionPanel;
import edu.brown.cs32.browndemic.world.World;
import edu.brown.cs32.browndemic.world.WorldSP;

public class InGameTitleBar extends TitleBar implements ActionListener, ChangeListener {

	private static final long serialVersionUID = -8926559135273305855L;
	
	private JLabel minimize; 
	private SelectButton pause, play1, play2, play3;
	private JMenuItem quit, save, exit;
	private JMenu menu;
	private World _world;
	private boolean _multi;
	private JRadioButtonMenuItem _infdead, _pop, _infdeadother, _wealth, _inf, _dead, _infother, _deadother, _yours, _total;
	private JCheckBoxMenuItem _airports, _airplanes;
	private JMenu _layers;
	private WorldMap _wm;
	private InformationBar _ib;
	private JMenuBar menuBar;
	private RegionPanel _rp;
	
	public InGameTitleBar(World world, boolean singlePlayer, WorldMap wm, InformationBar ib, RegionPanel rp) {
		super();
		_world = world;
		_wm = wm;
		_multi = !singlePlayer;
		_ib = ib;
		_rp = rp;
		makeUI();
	}
	
	public void setMB() {
		//Utils.getParentFrame(this).setJMenuBar(menuBar);
	}
	
	private void makeUI() {
		setBackground(Colors.MENU_BACKGROUND);
		new DragWindow(this);

		menuBar = new JMenuBar();
		menuBar.setBackground(Colors.MENU_BACKGROUND);
		menuBar.setBorder(BorderFactory.createEmptyBorder());
		menuBar.setAlignmentY(CENTER_ALIGNMENT);
		menuBar.setUI(new BasicMenuBarUI () {
			@Override
			public void paint(Graphics g, JComponent c) {
				g.setColor(Colors.MENU_BACKGROUND);
				g.fillRect(0,  0, c.getWidth(), c.getHeight());
			}
		});

		menu = new JMenu("Menu");
		menu.setFont(Fonts.TITLE_BAR);
		menu.setBackground(Colors.MENU_BACKGROUND);
		menu.setForeground(Colors.RED_TEXT);

		quit = new JMenuItem("Quit to Main Menu");
		quit.addMouseListener(this);
		quit.setFont(Fonts.TITLE_BAR);
		quit.setBackground(Colors.MENU_BACKGROUND);
		quit.setForeground(Colors.RED_TEXT);
		quit.setOpaque(true);
		menu.add(quit);

		if (!_multi) {
			save = new JMenuItem("Save");
			save.addMouseListener(this);
			save.setFont(Fonts.TITLE_BAR);
			save.setBackground(Colors.MENU_BACKGROUND);
			save.setForeground(Colors.RED_TEXT);
			save.setOpaque(true);
			menu.add(save);
		}
			
		exit = new JMenuItem("Exit");
		exit.addMouseListener(this);
		exit.setFont(Fonts.TITLE_BAR);
		exit.setBackground(Colors.MENU_BACKGROUND);
		exit.setForeground(Colors.RED_TEXT);
		exit.setOpaque(true);
		menu.add(exit);
		
		UIManager.put("RadioButtonMenuItem.checkIcon", new MenuButtonItemIcon(Resources.getImage(Images.UNSELECTED), Resources.getImage(Images.SELECTED)));
		
		
		_layers = new JMenu("Options");
		_layers.setFont(Fonts.TITLE_BAR);
		_layers.setBackground(Colors.MENU_BACKGROUND);
		_layers.setForeground(Colors.RED_TEXT);
		
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
		_infdead.setOpaque(true);
		group.add(_infdead);
		_layers.add(_infdead);
		
		if (_multi) {
			_infdeadother = new JRadioButtonMenuItem("Infected + Dead (Other Diseases)");
			_infdeadother.addActionListener(this);
			_infdeadother.setFont(Fonts.NORMAL_TEXT);
			_infdeadother.setBackground(Colors.MENU_BACKGROUND);
			_infdeadother.setForeground(Colors.RED_TEXT);
			_infdeadother.setOpaque(true);
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
		_inf.setOpaque(true);
		group.add(_inf);
		_layers.add(_inf);
		
		if (_multi) {
			_infother = new JRadioButtonMenuItem("Infected (Other Diseases)");
			_infother.addActionListener(this);
			_infother.setFont(Fonts.NORMAL_TEXT);
			_infother.setBackground(Colors.MENU_BACKGROUND);
			_infother.setForeground(Colors.RED_TEXT);
			_infother.setOpaque(true);
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
		_dead.setOpaque(true);
		group.add(_dead);
		_layers.add(_dead);
		
		if (_multi) {
			_deadother = new JRadioButtonMenuItem("Dead (Other Diseases)");
			_deadother.addActionListener(this);
			_deadother.setFont(Fonts.NORMAL_TEXT);
			_deadother.setBackground(Colors.MENU_BACKGROUND);
			_deadother.setForeground(Colors.RED_TEXT);
			_deadother.setOpaque(true);
			group.add(_deadother);
			_layers.add(_deadother);
		}

		_pop = new JRadioButtonMenuItem("Population");
		_pop.addActionListener(this);
		_pop.setFont(Fonts.NORMAL_TEXT);
		_pop.setBackground(Colors.MENU_BACKGROUND);
		_pop.setForeground(Colors.RED_TEXT);
		_pop.setOpaque(true);
		group.add(_pop);
		_layers.add(_pop);
		
		_wealth = new JRadioButtonMenuItem("Wealth");
		_wealth.addActionListener(this);
		_wealth.setFont(Fonts.NORMAL_TEXT);
		_wealth.setBackground(Colors.MENU_BACKGROUND);
		_wealth.setForeground(Colors.RED_TEXT);
		_wealth.setOpaque(true);
		group.add(_wealth);
		_layers.add(_wealth);
		
		_layers.addSeparator();
		
		UIManager.put("CheckBoxMenuItem.checkIcon", new MenuButtonItemIcon(Resources.getImage(Images.UNCHECKED), Resources.getImage(Images.CHECKED)));
		
		_airports = new JCheckBoxMenuItem("Show Airports", true);
		_airports.setFont(Fonts.NORMAL_TEXT);
		_airports.setBackground(Colors.MENU_BACKGROUND);
		_airports.setForeground(Colors.RED_TEXT);
		_airports.addChangeListener(this);
		_airports.setOpaque(true);
		_layers.add(_airports);
		
		_airplanes = new JCheckBoxMenuItem("Show Airplanes", true);
		_airplanes.setFont(Fonts.NORMAL_TEXT);
		_airplanes.setBackground(Colors.MENU_BACKGROUND);
		_airplanes.setForeground(Colors.RED_TEXT);
		_airplanes.addChangeListener(this);
		_airplanes.setOpaque(true);
		_layers.add(_airplanes);
		

		if (_multi) {
			_layers.addSeparator();
			ButtonGroup group2 = new ButtonGroup();
			_yours = new JRadioButtonMenuItem("Show Your Disease Information");
			_yours.setSelected(true);
			_yours.setFont(Fonts.NORMAL_TEXT);
			_yours.setBackground(Colors.MENU_BACKGROUND);
			_yours.setForeground(Colors.RED_TEXT);
			_yours.addActionListener(this);
			_yours.setOpaque(true);
			group2.add(_yours);
			_layers.add(_yours);
	
			_total = new JRadioButtonMenuItem("Show Total Disease Information");
			_total.setFont(Fonts.NORMAL_TEXT);
			_total.setBackground(Colors.MENU_BACKGROUND);
			_total.setForeground(Colors.RED_TEXT);
			_total.addActionListener(this);
			_total.setOpaque(true);
			group2.add(_total);
			_layers.add(_total);
		}
		
		
		if (_multi) {
			//Utils.setDefaultLook(_infdeadother);
		}
		//Utils.setDefaultLook(quit, exit);
		try {
			//Utils.setDefaultLook(_infdead, _pop, _infdeadother, _wealth, _inf, _dead, _infother, _deadother, _yours, _total,_airports, _airplanes);
		} catch (NullPointerException e) {}
		menuBar.add(menu);
		menuBar.add(_layers);
		add(menuBar);

		add(Box.createRigidArea(new Dimension(UI.TITLE_HEIGHT, UI.TITLE_HEIGHT)));

		if (!_multi) {
			pause = new SelectButton(Resources.getImage(Images.PAUSE), Resources.getImage(Images.PAUSE_HOVER));
			play1 = new SelectButton(Resources.getImage(Images.PLAY1), Resources.getImage(Images.PLAY1_HOVER));
			play2 = new SelectButton(Resources.getImage(Images.PLAY2), Resources.getImage(Images.PLAY2_HOVER));
			play3 = new SelectButton(Resources.getImage(Images.PLAY3), Resources.getImage(Images.PLAY3_HOVER));
			play1.setSelected(true);
			pause.addOnSelectAction(new SelectAction(0, play1, play2, play3));
			play1.addOnSelectAction(new SelectAction(1, pause, play2, play3));
			play2.addOnSelectAction(new SelectAction(2, pause, play1, play3));
			play3.addOnSelectAction(new SelectAction(3, pause, play1, play2));
			add(pause);
			add(Box.createRigidArea(new Dimension(5,0)));
			add(play1);
			add(Box.createRigidArea(new Dimension(5,0)));
			add(play2);
			add(Box.createRigidArea(new Dimension(5,0)));
			add(play3);
		}
			
		add(Box.createHorizontalGlue());
		
		JLabel title = new JLabel(Strings.TITLE);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.revalidate();
		title.setForeground(Colors.RED_TEXT);
		title.setFont(Fonts.TITLE_BAR);
		new DragWindow(title);
		add(title);
		
		add(Box.createRigidArea(new Dimension(UI.WIDTH/2 - UI.TITLE_HEIGHT * 2 - 20,0)));
		
		minimize = new HoverLabel(Resources.getImage(Images.MINIMIZE_BUTTON), Resources.getImage(Images.MINIMIZE_BUTTON_HOVER));
		minimize.addMouseListener(this);
		minimize.setToolTipText(Strings.MINIMIZE);
		add(minimize);
	}
	
	private class SelectAction implements Action {
		
		private SelectButton[] other;
		private int speed;
		
		public SelectAction(int speed, SelectButton... other) {
			this.other = other;
			this.speed = speed;
		}

		@Override
		public void doAction() {
			for (SelectButton b : other) {
				b.deSelect();
			}
			if (speed == 0)
				_world.pause();
			else {
				_world.unpause();
				_world.setSpeed(speed);
			}
		}
		
	}
	
	@Override
	public void mouseReleasedInside(MouseEvent e) {
		if (e.getSource() == minimize) {
			Utils.getParentFrame(this).setState(JFrame.ICONIFIED);
		} else if (e.getSource() == quit) {
			int choice = JOptionPane.showOptionDialog(Utils.getParentFrame(this), 
										"Are you sure you want to quit?  Any unsaved progress will be lost", 
										"Confirm quit", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
										null, null, null);
			if (choice == 0) {
				_world.leaveGame();
				try {
					Utils.getParentFrame(this).setPanel(new MainMenu());
				} catch (NullPointerException ex) {
					menu.setVisible(false);
				}
			}
		} else if (e.getSource() == save) {
			_world.pause();
			pause.setSelected(true);
			File saves = new File("saves");
			saves.mkdir();
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(saves);
			
			if (fc.showSaveDialog(Utils.getParentFrame(this)) == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				WorldSP world = (WorldSP)_world;
				//save the world to a file
				try{
					OutputStream file = new FileOutputStream(f);
				    OutputStream buffer = new BufferedOutputStream(file);
				    ObjectOutput output = new ObjectOutputStream(buffer);
				    try{
				    	output.writeObject(world);
				    }
				    finally{
				    	output.close();
				    }
				}  
				catch(IOException ex){
					System.out.println("Couldn't save");
				}
			}
		} else if (e.getSource() == exit) {
			int choice = JOptionPane.showOptionDialog(Utils.getParentFrame(this), 
					"Are you sure you want to exit?  Any unsaved progress will be lost", 
					"Confirm exit", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
					null, null, null);
			if (choice == 0)
				System.exit(0);
		}
	}
	
	public void hideMenu() {
		menu.setVisible(false);
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
		} else if (e.getSource() == _yours || e.getSource() == _total) {
			_wm.setTotalData(_total.isSelected());
			_ib.setYours(_yours.isSelected());
			_rp.setTotal(_total.isSelected());
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
