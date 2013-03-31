package edu.brown.cs32.browndemic.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.SwingWorker;

import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.world.World;

public class WorldMap extends JComponent implements MouseListener {

	private static final long serialVersionUID = -4481136165457141240L;
	
	private World _world;
	private BufferedImage _map, _regions;
	private Map<Integer, BufferedImage> _overlays = new HashMap<>();
	private int _selected;
	
	public WorldMap(World world, BufferedImage map, BufferedImage regions) {
		super();
		_world = world;
		_map = map;
		_regions = regions;
		_selected = 0;
		addMouseListener(this);
		setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
	}
	
	public class Loader extends SwingWorker<Void, Void> {
		
		private Action _done;
		public Loader(Action a) {
			_done = a;
		}

		@Override
		protected Void doInBackground() throws Exception {
			for (int x = 0; x < _regions.getWidth(); x++) {
				for (int y = 0; y < _regions.getHeight(); y++) {
					int id = getID(x, y);
					if (!isValid(id)) continue;
					if (!_overlays.containsKey(id)) {
						_overlays.put(id, createRegion(id));
					}
				}
				setProgress(x * 100 / (_regions.getWidth()-1));
			}
			return null;
		}
		
		@Override
		protected void done() {
			_done.doAction();
		}
	}
	
	public void load() {
		for (int x = 0; x < _regions.getWidth(); x++) {
			for (int y = 0; y < _regions.getHeight(); y++) {
				int id = getID(x, y);
				if (!isValid(id)) continue;
				if (!_overlays.containsKey(id)) {
					_overlays.put(id, createRegion(id));
				}
			}
		}
	}
	
	private BufferedImage createRegion(int id) {
		BufferedImage out = new BufferedImage(_regions.getWidth(), _regions.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < _regions.getWidth(); x++) {
			for (int y = 0; y < _regions.getHeight(); y++) {
				int pixel = getID(x, y);
				if (pixel == id) {
					out.setRGB(x, y, new Color(255, 0, 0, 100).getRGB());
				}
			}
		}
		
		return out;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.ORANGE);
		//g.drawRect(0, 0, getWidth(), getHeight());
		g.drawImage(_map, 0, 0, getWidth(), getHeight(), 0, 0, _map.getWidth(), _map.getHeight(), null);
		
		if (isValid(_selected)) {
			g.drawImage(_overlays.get(_selected), 0, 0, getWidth(), getHeight(), 0, 0, _regions.getWidth(), _regions.getHeight(), null);
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
		Point p = e.getPoint();
		if (!contains(p)) return;
		if (e.getButton() != MouseEvent.BUTTON1) return;

		int id = getID(p.x, p.y);
		
		if (isValid(id)) {
			setSelection(id);
		} else {
			setSelection(0);
		}
	}
	
	private boolean isValid(int id) {
		return (id != 0);
	}
	
	private void setSelection(int id) {
		_selected = id;
		repaint();
	}
	
	private int getID(int x, int y) {
		return _regions.getRGB(x, y) & 0x000000FF;
	}
}
