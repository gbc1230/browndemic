package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter.SortKey;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

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
	private TableRowSorter<DefaultTableModel> _sorter;
	
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
		_timer.setInitialDelay(0);
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
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex > 0) {
					return SortableNumberString.class;
				}
				return String.class;
			}
		};
		_table = new JTable(_data);
		_table.setFillsViewportHeight(true);
		_table.setBackground(Colors.MENU_BACKGROUND);
		_table.setFont(Fonts.NORMAL_TEXT);
		_table.setForeground(Colors.RED_TEXT);
		_table.setGridColor(Colors.RED_TEXT);
		_table.getTableHeader().setReorderingAllowed(false);
		_table.getTableHeader().setResizingAllowed(false);
		_table.setRowSelectionAllowed(false);;
		_sorter = new TableRowSorter<>(_data);
		_table.setRowSorter(_sorter);
		
		JScrollPane scroll = new JScrollPane(_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		add(scroll);
	}
	
	private void update() {
		Object[][] data = new Object[_world.getDiseases().size()][cols.length];
		for (int i = 0; i < data.length; i++) {
			Disease d = _world.getDiseases().get(i);
			data[i] = new Object[] { d.getName(), new SortableNumberString(_world.getInfected(d.getID())), new SortableNumberString(_world.getDead(d.getID()))};
		}
		
		List<? extends SortKey> keys = _sorter.getSortKeys();
		_data.setDataVector(data, cols);
		_sorter = new TableRowSorter<>(_data);
		_sorter.setSortKeys(keys);
		_table.setRowSorter(_sorter);
	}
	
	private class SortableNumberString implements Comparable<SortableNumberString> {
		private Long n;
		private String s;
		
		public SortableNumberString(long n) {
			this.n = n;
			s = NumberFormat.getInstance().format(n);
		}

		@Override
		public int compareTo(SortableNumberString o) {
			return n.compareTo(o.n);
		}
		@Override
		public String toString() {
			return s;
		}
	}
	
	public void stop() {
		_timer.stop();
	}

}
