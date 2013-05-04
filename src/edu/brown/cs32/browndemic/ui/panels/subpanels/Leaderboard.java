package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;
import edu.brown.cs32.browndemic.world.World;

public class Leaderboard extends BrowndemicPanel {

	private static final long serialVersionUID = 1377699448513773334L;
	
	private World _world;
	private DefaultTableModel _data;
	private JTable _table;
	private Timer _timer;
	private static final String cols[] = { "Name", "Infected", "Killed" };
	
	public Leaderboard(World w) {
		super();
		_world = w;
		makeUI();
		_timer = new Timer(1000/3, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
		_timer.start();
	}
	
	@SuppressWarnings("serial")
	private void makeUI() {
		setBackground(Colors.MENU_BACKGROUND);
		setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		_data = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		_table = new JTable(_data);
		_table.setAutoCreateRowSorter(true);
		_table.setFillsViewportHeight(true);
		_table.setBackground(Colors.MENU_BACKGROUND);
		_table.setFont(Fonts.NORMAL_TEXT);
		_table.setForeground(Colors.RED_TEXT);
		_table.setGridColor(Colors.RED_TEXT);
		_table.getTableHeader().setReorderingAllowed(false);
		_table.getTableHeader().setResizingAllowed(false);
		_table.setRowSelectionAllowed(false);;
		
		JScrollPane scroll = new JScrollPane(_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		add(scroll);
	}
	
	private void update() {
		Object[][] data = new Object[_world.getDiseases().size()][cols.length];
		for (int i = 0; i < data.length; i++) {
			Disease d = _world.getDiseases().get(i);
			data[i] = new Object[] { d.getName(), _world.getInfected(d.getID()), _world.getDead(d.getID())};
		}
		_data.setDataVector(data, cols);
	}
	
	public void stop() {
		_timer.stop();
	}

}
