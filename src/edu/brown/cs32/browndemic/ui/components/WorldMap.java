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
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import edu.brown.cs32.browndemic.region.Region;
import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.Settings;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.world.MainWorld;

public class WorldMap extends JComponent implements MouseListener {

	private static final long serialVersionUID = -4481136165457141240L;
	
	private MainWorld _world;
	private BufferedImage _map, _regions;
	private Map<Integer, BufferedImage> _diseaseOverlays = new HashMap<>();
	private Map<Integer, BufferedImage> _highlightOverlays = new HashMap<>();
	private Map<Integer, Float> _infected = new HashMap<>();
	private Map<Integer, AlphaComposite> _composites = new HashMap<>();
	private ArrayList<Location> _airports = new ArrayList<>();
	private List<MovingObject> _objects = new ArrayList<>();
	private int _selected, _disease;
	private boolean _chooseMode;
	
	private static final double AIRPLANE_SPEED = 6.0;
	
	public WorldMap(MainWorld _world2, BufferedImage map, BufferedImage regions, int disease) {
		super();
		_world = _world2;
		_map = map;
		_regions = regions;
		_selected = 0;
		_disease = disease;
		addMouseListener(this);
		setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
		setMaximumSize(new Dimension(map.getWidth(), map.getHeight()));
		setMinimumSize(new Dimension(map.getWidth(), map.getHeight()));
		new Timer(1000/60, new RepaintListener()).start();
	}
	
	public void setChooseMode(boolean chooseMode) {
		_chooseMode = chooseMode;
	}
	
	public class Loader extends SwingWorker<Void, Void> {
		
		private Action _done;
		public Loader(Action a) {
			_done = a;
		}

		@Override
		protected Void doInBackground() throws Exception {
			if (Settings.getBoolean(Settings.CACHING)) {
				File cache = new File("cache");
				cache.mkdir();
			}
			
			for (int x = 0; x < _regions.getWidth(); x++) {
				for (int y = 0; y < _regions.getHeight(); y++) {
					int id = getID(x, y);
					if (!isValid(id)) continue;
					if (!_diseaseOverlays.containsKey(id)) {
						File disease = new File("cache/disease" + id + ".png");
						File highlight = new File("cache/highlight" + id + ".png");
						if (Settings.getBoolean(Settings.CACHING) && disease.exists() && highlight.exists()) {
							// ImageIO.read is going to give us the wrong format making rendering way too slow
							BufferedImage diseaseFromFile = ImageIO.read(disease);
							BufferedImage highlightFromFile = ImageIO.read(highlight);
							BufferedImage convertedDisease = new BufferedImage(diseaseFromFile.getWidth(), diseaseFromFile.getHeight(), BufferedImage.TYPE_INT_ARGB);
							BufferedImage convertedHighlight = new BufferedImage(diseaseFromFile.getWidth(), diseaseFromFile.getHeight(), BufferedImage.TYPE_INT_ARGB);

							convertedDisease.getGraphics().drawImage(diseaseFromFile, 0, 0, null);
							convertedHighlight.getGraphics().drawImage(highlightFromFile, 0, 0, null);
							
							_diseaseOverlays.put(id, convertedDisease);
							_highlightOverlays.put(id, convertedHighlight);
						} else {
							BufferedImage[] overlays = createRegion(id, Color.red, new Color(255, 255, 175));
							_diseaseOverlays.put(id, overlays[0]);
							_highlightOverlays.put(id, overlays[1]);
							ImageIO.write(overlays[0], "png", disease);
							ImageIO.write(overlays[1], "png", highlight);
						}
						_infected.put(id, 0f);
						_composites.put(id, AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f));
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
	
	private class MovingObject {
		double x, y, speed, xoffset, yoffset;
		boolean done = false;
		Queue<Location> waypoints = new LinkedList<>();
		Location currentWaypoint;
		BufferedImage original, transform;
		public MovingObject(Location l, double speed, BufferedImage img) { 
			this.x = l.x; this.y = l.y; this.speed = speed; this.original = img;
			transform = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
			xoffset = img.getWidth()/2;
			yoffset = img.getHeight()/2;
		}
		
		public MovingObject update() {
			if (currentWaypoint == null) {
				currentWaypoint = waypoints.poll();
				updateImage();
			}
			if (currentWaypoint == null)
				done = true;
			if (done) return this;
			
			double dx = currentWaypoint.x - x;
			double dy = currentWaypoint.y - y;
			double scale = speed / Math.sqrt(dx * dx + dy * dy);
			dx *= scale; dy *= scale;
			
			double newx = x + dx, newy = y + dy;
			
			if (Math.signum(currentWaypoint.x - newx) != Math.signum(dx) 
					|| Math.signum(currentWaypoint.y - newy) != Math.signum(dy)) {
				newx = currentWaypoint.x; newy = currentWaypoint.y;
				currentWaypoint = waypoints.poll();
				updateImage();
			}
			x = newx; y = newy;
			return this;
		}
		
		public void draw(Graphics g) {
			g.drawImage(transform, (int)Math.floor(x - xoffset), (int)Math.floor(y - yoffset), null);
		}
		
		private void updateImage() {
			if (currentWaypoint == null) return;
			double dx = currentWaypoint.x - x;
			double dy = currentWaypoint.y - y;
			double angle = Math.atan2(dy, dx);
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getRotateInstance(angle, original.getWidth()/2, original.getHeight()/2), AffineTransformOp.TYPE_BICUBIC);
			transform.getGraphics().drawImage(op.filter(original, null), 0, 0, null);
		}
		
		public void addWaypoint(Location l) {
			waypoints.add(l);
		}
	}
	
	private class Location {
		final double x,y;
		public Location(double x, double y) { this.x = x; this.y = y; }
	}
	
	private BufferedImage[] createRegion(int id, Color... c) {
		BufferedImage[] out = new BufferedImage[c.length];
		for (int i = 0; i < c.length; i++) {
			out[i] = new BufferedImage(_regions.getWidth(), _regions.getHeight(), BufferedImage.TYPE_INT_ARGB);
		}

		for (int x = 0; x < _regions.getWidth(); x++) {
			for (int y = 0; y < _regions.getHeight(); y++) {
				int pixel = getID(x, y);
				for (int i = 0; i < c.length; i++) {
					if (pixel == id)
						out[i].setRGB(x, y, c[i].getRGB());
					else
						out[i].setRGB(x, y, c[i].getRGB() & 0x00FFFFFF);
				}
			}
		}

		Kernel k = new Kernel(3, 3, new float[] { 	1f/9f, 1f/9f, 1f/9f,
													1f/9f, 1f/9f, 1f/9f,
													1f/9f, 1f/9f, 1f/9f });
		ConvolveOp op = new ConvolveOp(k);
		for (int i = 0; i < c.length; i++) {
			out[i] = op.filter(out[i], null);
		}
		return out;
	}
	
	private void update() {
		for (Iterator<MovingObject> it = _objects.iterator(); it.hasNext();) {
			if (it.next().update().done) {
				it.remove();
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(_map, 0, 0, null);
		
		if (!_chooseMode) {
			for (Map.Entry<Integer, BufferedImage> e : _diseaseOverlays.entrySet()) {
				Region r = _world.getRegion(e.getKey());
				float percentInfected = 0f;
				if (r != null) {
					try {
						percentInfected = (float)r.getInfected().get(_disease) / (float)r.getPopulation();
					} catch (IndexOutOfBoundsException e1) {
						percentInfected = 0f;
					}
					if (percentInfected > 0f && percentInfected < .2f) percentInfected = .2f;
				}
				if (percentInfected != _infected.get(e.getKey())) {
					_composites.put(e.getKey(), AlphaComposite.getInstance(AlphaComposite.SRC_OVER, percentInfected/2.0f));
					_infected.put(e.getKey(), percentInfected);
				}
				g2.setComposite(_composites.get(e.getKey()));
				g2.drawImage(e.getValue(), 0, 0, null);
	
			}
		}
			
		if (_chooseMode) {
			drawChoosePanel(g2);
		}
		
		if (isValid(_selected)) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.abs((System.currentTimeMillis() % 3000) - 1500)/5000.0f + 0.2f));
			g2.drawImage(_highlightOverlays.get(_selected), 0, 0, null);
			drawInfoPanel(g2);
		}
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		
		for (MovingObject m : _objects) {
			m.draw(g);
		}
	}
	
	public void addRandomPlane() {
		double x1 = Math.random() * _map.getWidth();
		double y1 = Math.random() * _map.getHeight();
		double x2 = Math.random() * _map.getWidth();
		double y2 = Math.random() * _map.getHeight();
		MovingObject airplane = new MovingObject(new Location(x1, y1), AIRPLANE_SPEED, Resources.getImage(Images.AIRPLANE));
		airplane.addWaypoint(new Location(x2, y2));
		_objects.add(airplane);
	}
	
	private void drawChoosePanel(Graphics2D g2) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .9f));
		g2.setColor(Color.BLACK);
		g2.fillRect(0, getHeight() - 40, getWidth(), 40);

		g2.setColor(Colors.RED_TEXT);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g2.setFont(Fonts.BIG_TEXT);
		g2.drawString("Choose a Starting Location", 250, getHeight() - 10);
	}
	
	private void drawInfoPanel(Graphics2D g2) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .9f));
		g2.setColor(Color.BLACK);
		g2.fillRect(0, getHeight() - 100, 275, 100);

		g2.setColor(Colors.RED_TEXT);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g2.setFont(Fonts.NORMAL_TEXT);
		Region r = _world.getRegion(_selected);
		String name = "";
		long infected = 0, dead = 0, total = 1;
		
		if (r != null) {
			name = r.getName();
			for (long l : r.getInfected())
				infected += l;
			for (long l : r.getKilled())
				dead += l;
			total = r.getPopulation();
		}
		g2.drawString(name, 5, getHeight() - 80);

		g2.drawString(String.format("Infected: %d (%.2f%%)", infected, (double)infected/(double)total), 15, getHeight() - 55);
		g2.drawString(String.format("Dead: %d (%.2f%%)", dead, (double)dead/(double)total), 15, getHeight() - 35);
		g2.drawString(String.format("Total: %d", total), 15, getHeight() - 15);
	}
	
	private class RepaintListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			update();
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
		if (isValid(id)) {
			if (_chooseMode) {
				_world.getRegion(id).introduceDisease(_world.getDiseases().get(_disease));
				_world.start();
				_chooseMode = false;
			}
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
		int color = _regions.getRGB(x, y);
		return (color & 0xFF000000) == 0xFF000000 ? color & 0x000000FF : 0;
	}
}
