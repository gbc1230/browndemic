package edu.brown.cs32.browndemic.ui.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.world.World;

public class WorldMap extends JComponent implements MouseListener {

	private static final long serialVersionUID = -4481136165457141240L;
	
	private World _world;
	private BufferedImage _map, _regions;
	private Map<Integer, BufferedImage> _diseaseOverlays = new HashMap<>();
	private Map<Integer, BufferedImage> _highlightOverlays = new HashMap<>();
	private int _selected;
	
	public WorldMap(World world, BufferedImage map, BufferedImage regions) {
		super();
		_world = world;
		_map = map;
		_regions = regions;
		_selected = 0;
		addMouseListener(this);
		setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
		setMaximumSize(new Dimension(map.getWidth(), map.getHeight()));
		setMinimumSize(new Dimension(map.getWidth(), map.getHeight()));
		new Timer(30, new RepaintListener()).start();
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
					if (!_diseaseOverlays.containsKey(id)) {
						_diseaseOverlays.put(id, createRegion(id, Color.RED));
						_highlightOverlays.put(id, createRegion(id, new Color(255, 255, 175)));
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
	
	private BufferedImage createRegion(int id, Color c) {
		BufferedImage out = new BufferedImage(_regions.getWidth(), _regions.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < _regions.getWidth(); x++) {
			for (int y = 0; y < _regions.getHeight(); y++) {
				int pixel = getID(x, y);
				if (pixel == id) {
					out.setRGB(x, y, c.getRGB());
				}
			}
		}
		
		Kernel k = new Kernel(3, 3, new float[] { 	1f/14f, 2f/14f, 1f/14f,
													2f/14f, 4f/14f, 2f/14f,
													1f/14f, 2f/14f, 1f/14f });
		ConvolveOp op = new ConvolveOp(k);
		return op.filter(out, null);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.ORANGE);
		//g.drawRect(0, 0, getWidth(), getHeight());
		g.drawImage(_map, 0, 0, getWidth(), getHeight(), 0, 0, _map.getWidth(), _map.getHeight(), null);
		
		if (isValid(_selected)) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.abs((System.currentTimeMillis() % 3000) - 1500)/5000.0f + 0.2f));
			g2.drawImage(_highlightOverlays.get(_selected), 0, 0, getWidth(), getHeight(), 0, 0, _regions.getWidth(), _regions.getHeight(), null);
			
			drawInfoPanel(g2);
		}
	}
	
	private void drawInfoPanel(Graphics2D g2) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .9f));
		g2.setColor(Color.BLACK);
		g2.fillRect(0, getHeight() - 100, 225, 100);

		g2.setColor(Colors.RED_TEXT);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g2.setFont(Fonts.NORMAL_TEXT);
		g2.drawString("This is a long Country name", 5, getHeight() - 80);

		g2.drawString("Infected: 100,000 (10%)", 15, getHeight() - 55);
		g2.drawString("Dead: 20,000 (2%)", 15, getHeight() - 35);
		g2.drawString("Total: 1,000,000", 15, getHeight() - 15);
	}
	
	private class RepaintListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			repaint();
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
		System.out.println(p);
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
		System.out.println(id);
		repaint();
	}
	
	private int getID(int x, int y) {
		return _regions.getRGB(x, y) & 0x000000FF;
	}
}
