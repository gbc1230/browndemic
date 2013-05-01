package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter.SortKey;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import edu.brown.cs32.browndemic.region.Region;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;
import edu.brown.cs32.browndemic.world.MainWorld;

public class RegionPanel extends BrowndemicPanel implements ListSelectionListener {

	private static final long serialVersionUID = -8537192795533968336L;

	private MainWorld _world;
	private JTable _healthy, _infected, _dead;
	private DefaultTableModel _tmhealthy, _tminfected, _tmdead;
	private TableRowSorter<DefaultTableModel> _shealthy, _sinfected, _sdead;
	
	public RegionPanel(MainWorld w) {
		super();
		_world = w;
		makeUI();
		new Timer(1000/3, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		}).start();
	}
	
	@SuppressWarnings("serial")
	private void makeUI() {
		setBackground(Colors.MENU_BACKGROUND);
		setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		_tmhealthy = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		_tminfected = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		_tmdead = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		_healthy = new JTable(_tmhealthy);
		_healthy.setFillsViewportHeight(true);
		_healthy.setBackground(Colors.MENU_BACKGROUND);
		_healthy.setFont(Fonts.NORMAL_TEXT);
		_healthy.setForeground(Colors.RED_TEXT);
		_healthy.setGridColor(Colors.RED_TEXT);
		_shealthy = new TableRowSorter<DefaultTableModel>(_tmhealthy);
		_healthy.setRowSorter(_shealthy);
		_healthy.getTableHeader().setReorderingAllowed(false);
		_healthy.getTableHeader().setResizingAllowed(false);
		_healthy.getSelectionModel().addListSelectionListener(this);
		_healthy.setRowSelectionAllowed(false);

		_infected = new JTable(_tminfected);
		_infected.setFillsViewportHeight(true);
		_infected.setBackground(Colors.MENU_BACKGROUND);
		_infected.setFont(Fonts.NORMAL_TEXT);
		_infected.setForeground(Colors.RED_TEXT);
		_infected.setGridColor(Colors.RED_TEXT);
		_sinfected = new TableRowSorter<DefaultTableModel>(_tminfected);
		_infected.setRowSorter(_sinfected);
		_infected.getTableHeader().setReorderingAllowed(false);
		_infected.getTableHeader().setResizingAllowed(false);
		_infected.getSelectionModel().addListSelectionListener(this);
		_infected.setRowSelectionAllowed(false);

		_dead = new JTable(_tmdead);
		_dead.setFillsViewportHeight(true);
		_dead.setBackground(Colors.MENU_BACKGROUND);
		_dead.setFont(Fonts.NORMAL_TEXT);
		_dead.setForeground(Colors.RED_TEXT);
		_dead.setGridColor(Colors.RED_TEXT);
		_sdead = new TableRowSorter<DefaultTableModel>(_tmdead);
		_dead.setRowSorter(_sdead);
		_dead.getTableHeader().setReorderingAllowed(false);
		_dead.getTableHeader().setResizingAllowed(false);
		_dead.getSelectionModel().addListSelectionListener(this);
		_dead.setRowSelectionAllowed(false);
		
		JPanel tables = new JPanel();
		tables.setLayout(new BoxLayout(tables, BoxLayout.X_AXIS));

		JPanel healthy = new JPanel();
		healthy.setLayout(new BoxLayout(healthy, BoxLayout.Y_AXIS));
		healthy.setBackground(Colors.MENU_BACKGROUND);
		
		JPanel infected = new JPanel();
		infected.setLayout(new BoxLayout(infected, BoxLayout.Y_AXIS));
		infected.setBackground(Colors.MENU_BACKGROUND);
		
		JPanel dead = new JPanel();
		dead.setLayout(new BoxLayout(dead, BoxLayout.Y_AXIS));
		dead.setBackground(Colors.MENU_BACKGROUND);

		healthy.add(_healthy.getTableHeader());
		healthy.add(_healthy);
		healthy.add(Box.createGlue());

		infected.add(_infected.getTableHeader());
		infected.add(_infected);
		infected.add(Box.createGlue());
		
		dead.add(_dead.getTableHeader());
		dead.add(_dead);
		dead.add(Box.createGlue());
		
		tables.add(healthy);
		tables.add(infected);
		tables.add(dead);
		
		JScrollPane scroll = new JScrollPane(tables, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		add(scroll);
	}
	
	private void update() {
		ArrayList<String> healthy = new ArrayList<>();
		ArrayList<String> infected = new ArrayList<>();
		ArrayList<String> dead = new ArrayList<>();
		
		try {
			for (Region r : _world.getRegions()) {
				if (r.getHealthy() == r.getPopulation()) {
					healthy.add(r.getName());
				} else if (r.getTotalInfected() > 0) {
					infected.add(r.getName());
				} else {
					dead.add(r.getName());
				}
			}
		} catch (NullPointerException e) {
			for (Region r : _world.getRegions())
				healthy.add(r.getName());
		}
		
		Object[][] healthydata = new Object[healthy.size()][1];
		Object[][] infecteddata = new Object[infected.size()][1];
		Object[][] deaddata = new Object[dead.size()][1];
		
		for (int i = 0; i < healthy.size(); i++)
			healthydata[i][0] = healthy.get(i);
		for (int i = 0; i < infected.size(); i++)
			infecteddata[i][0] = infected.get(i);
		for (int i = 0; i < dead.size(); i++)
			deaddata[i][0] = dead.get(i);


		List<? extends SortKey> healthykeys = _shealthy.getSortKeys();
		List<? extends SortKey> infectedkeys = _sinfected.getSortKeys();
		List<? extends SortKey> deadkeys = _sdead.getSortKeys();
		
		_tmhealthy.setDataVector(healthydata, new String[]{"Healthy"});
		_tminfected.setDataVector(infecteddata, new String[]{"Infected"});
		_tmdead.setDataVector(deaddata, new String[]{"Dead"});

		_shealthy = new TableRowSorter<DefaultTableModel>(_tmhealthy);
		_shealthy.setSortKeys(healthykeys);
		_sinfected = new TableRowSorter<DefaultTableModel>(_tminfected);
		_sinfected.setSortKeys(infectedkeys);
		_sdead = new TableRowSorter<DefaultTableModel>(_tmdead);
		_sdead.setSortKeys(deadkeys);
		
		_healthy.setRowSorter(_shealthy);
		_infected.setRowSorter(_sinfected);
		_dead.setRowSorter(_sdead);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		//_table.clearSelection();
		System.out.println(e.getLastIndex());
	}
}
