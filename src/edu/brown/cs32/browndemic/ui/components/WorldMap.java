package edu.brown.cs32.browndemic.ui.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import edu.brown.cs32.browndemic.region.AirTransmission;
import edu.brown.cs32.browndemic.region.Region;
import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.Settings;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.ui.panels.menus.MainMenu;
import edu.brown.cs32.browndemic.ui.panels.menus.PostGameMenu;
import edu.brown.cs32.browndemic.world.Airport;
import edu.brown.cs32.browndemic.world.World;

public class WorldMap extends JComponent implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = -4481136165457141240L;
	
	private static final int MAX_FPS = 60;
	
	private World _world;
	private BufferedImage _map, _regions;
	private Map<Integer, BufferedImage> _diseaseOverlays = new HashMap<>();
	private Map<Integer, BufferedImage> _highlightOverlays = new HashMap<>();
	private List<MovingObject> _objects = new ArrayList<>();
	private Map<Integer, Float> _highlights = new HashMap<>();
	private int _selected, _disease, _hover;
	private boolean _chooseMode, _drawAirports = true, _drawAirplanes = true, _multi, _totalData = false;
	private static GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	private double fps = 0.0f;
	private long lastUpdate = 0;
	private BufferedImage _regionCache;
	private BufferedImage _regionCache2;
	private MarqueeLabel _ml;
	private Timer _timer;
	private OverlayWorker _ow;
	private Layer _layer = Layer.INFECTED_DEAD;
	private long _largestCountry = -1;
	private double _largestWealth = -1;
	
	private static final double AIRPLANE_SPEED = 3.0;
	
	public enum Layer {
		INFECTED, POPULATION, INFECTED_OTHER, WEALTH,
		INFECTED_DEAD, DEAD, INFECTED_DEAD_OTHER, DEAD_OTHER,
	}
	
	public WorldMap(World _world2, BufferedImage map, BufferedImage regions, int disease, MarqueeLabel ml, boolean multi) {
		super();
		_world = _world2;
		_map = map;
		_regions = regions;
		_selected = 0;
		_disease = disease;
		_ml = ml;
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
		setMaximumSize(new Dimension(map.getWidth(), map.getHeight()));
		setMinimumSize(new Dimension(map.getWidth(), map.getHeight()));
		setDoubleBuffered(true);
		_multi = multi;
		_regionCache = gc.createCompatibleImage(map.getWidth(), map.getHeight(), Transparency.TRANSLUCENT);
		_regionCache2 = gc.createCompatibleImage(map.getWidth(), map.getHeight(), Transparency.TRANSLUCENT);
		_ow = new OverlayWorker();
		_timer = new Timer(1000/MAX_FPS, new RepaintListener());
		_timer.start();
	}
	
	public void setChooseMode(boolean chooseMode) {
		_chooseMode = chooseMode;
	}
	
	public void setLayer(Layer l) {
		_layer = l;
	}
	
	private float getInfectedDeadPercent(int id) {
		Region r = _world.getRegion(id);
		float percentInfected = 0f;
		if (r != null) {
			try {
				percentInfected = ((float)r.getInfected().get(_disease) + (float)r.getKilled().get(_disease)) / (float)r.getPopulation();
			} catch (IndexOutOfBoundsException e1) {
				percentInfected = 0f;
			}
			if (percentInfected > 0f && percentInfected < .2f) percentInfected = .2f;
		}
		return percentInfected/2.0f;
	}
	
	private float getInfectedPercent(int id) {
		Region r = _world.getRegion(id);
		float percentInfected = 0f;
		if (r != null) {
			try {
				percentInfected = (float)r.getInfected().get(_disease) / (float)r.getPopulation();
			} catch (IndexOutOfBoundsException e1) {
				percentInfected = 0f;
			}
			if (percentInfected > 0f && percentInfected < .2f) percentInfected = .2f;
		}
		return percentInfected/2.0f;
	}
	
	private float getDeadPercent(int id) {
		Region r = _world.getRegion(id);
		float percentInfected = 0f;
		if (r != null) {
			try {
				percentInfected = (float)r.getKilled().get(_disease) / (float)r.getPopulation();
			} catch (IndexOutOfBoundsException e1) {
				percentInfected = 0f;
			}
			if (percentInfected > 0f && percentInfected < .2f) percentInfected = .2f;
		}
		return percentInfected/2.0f;
	}
	
	private float getInfectedOtherPercent(int id) {
		Region r = _world.getRegion(id);
		float percentInfected = 0f;
		if (r != null) {
			percentInfected = (float)r.getOtherInfected(_disease) / (float)r.getPopulation();
			if (percentInfected > 0f && percentInfected < .2f) percentInfected = .2f;
		}
		return percentInfected/2.0f;
	}
	
	private float getDeadOtherPercent(int id) {
		Region r = _world.getRegion(id);
		float percentInfected = 0f;
		if (r != null) {
			try {
				long infected = 0;
				for (int i = 0; i < r.getInfected().size(); i++) {
					if (i == _disease) continue;
					infected += r.getKilled().get(i);
				}
				percentInfected = (float)infected / (float)r.getPopulation();
			} catch (IndexOutOfBoundsException e1) {
				percentInfected = 0f;
			}
			if (percentInfected > 0f && percentInfected < .2f) percentInfected = .2f;
		}
		return percentInfected/2.0f;
	}
	
	private float getInfectedDeadOtherPercent(int id) {
		Region r = _world.getRegion(id);
		float percentInfected = 0f;
		if (r != null) {
			percentInfected = (float)(r.getOtherInfected(_disease) + r.getTotalKilled()) / (float)r.getPopulation();
			if (percentInfected > 0f && percentInfected < .2f) percentInfected = .2f;
		}
		return percentInfected/2.0f;
	}
	
	private float getPopulationPercent(int id) {
		if (_largestCountry < 0) {
			for (Region r : _world.getRegions()) {
				_largestCountry = Math.max(_largestCountry, r.getPopulation());
			}
		}
		Region r = _world.getRegion(id);
		return (float)(Math.sqrt((double)(r.getPopulation() - r.getTotalKilled())/(double)_largestCountry)/1.5f);
	}
	
	private float getWealthPercent(int id) {
		if (_largestWealth < 0) {
			for (Region r : _world.getRegions()) {
				_largestWealth = Math.max(_largestWealth, r.getWealth());
			}
		}
		Region r = _world.getRegion(id);
		return (float)(r.getWealth()/_largestWealth)/1.5f;
	}
	
	private class OverlayWorker implements Runnable {
		boolean running = true;
		
		public void cancel() {
			running = false;
		}

		@Override
		public void run() {
			while (running) {
				Graphics2D cacheg2 = (Graphics2D) _regionCache2.getGraphics();
				
				cacheg2.setComposite(AlphaComposite.Clear);
				cacheg2.fillRect(0, 0, _regionCache2.getWidth(), _regionCache2.getHeight());
				for (Map.Entry<Integer, BufferedImage> e : _diseaseOverlays.entrySet()) {
					float percent = 0.0f;
					switch (_layer) {
						case INFECTED_DEAD:
							percent = getInfectedDeadPercent(e.getKey());
							break;
						case POPULATION:
							percent = getPopulationPercent(e.getKey());
							break;
						case INFECTED_DEAD_OTHER:
							percent = getInfectedDeadOtherPercent(e.getKey());
							break;
						case WEALTH:
							percent = getWealthPercent(e.getKey());
							break;
						case INFECTED:
							percent = getInfectedPercent(e.getKey());
							break;
						case DEAD:
							percent = getDeadPercent(e.getKey());
							break;
						case INFECTED_OTHER:
							percent = getInfectedOtherPercent(e.getKey());
							break;
						case DEAD_OTHER:
							percent = getDeadOtherPercent(e.getKey());
							break;
					}
					if (percent > 0) {
						if (percent > 1) {
							percent = 1;
							System.out.println("Alpha value too high.");
						}
						cacheg2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, percent));
						cacheg2.drawImage(e.getValue(), 0, 0, null);
					}
				}
				synchronized(_regionCache) {
					cacheg2 = (Graphics2D) _regionCache.getGraphics();
					cacheg2.setComposite(AlphaComposite.Clear);
					cacheg2.fillRect(0, 0, _regionCache.getWidth(), _regionCache.getHeight());
					cacheg2.setComposite(AlphaComposite.SrcOver);
					cacheg2.drawImage(_regionCache2, 0, 0, null);
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					break;
				}
			}
		}
		
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
						_highlights.put(id, 0f);
						File disease = new File("cache/disease" + id + ".png");
						File highlight = new File("cache/highlight" + id + ".png");
						if (Settings.getBoolean(Settings.CACHING) && disease.exists() && highlight.exists()) {
							// ImageIO.read is going to give us the wrong format making rendering way too slow
							BufferedImage diseaseFromFile = ImageIO.read(disease);
							BufferedImage highlightFromFile = ImageIO.read(highlight);
							BufferedImage convertedDisease = gc.createCompatibleImage(diseaseFromFile.getWidth(), diseaseFromFile.getHeight(), Transparency.TRANSLUCENT);//new BufferedImage(diseaseFromFile.getWidth(), diseaseFromFile.getHeight(), BufferedImage.TYPE_INT_ARGB);
							BufferedImage convertedHighlight = gc.createCompatibleImage(diseaseFromFile.getWidth(), diseaseFromFile.getHeight(), Transparency.TRANSLUCENT);//new BufferedImage(diseaseFromFile.getWidth(), diseaseFromFile.getHeight(), BufferedImage.TYPE_INT_ARGB);
							
							convertedDisease.getGraphics().drawImage(diseaseFromFile, 0, 0, null);
							convertedHighlight.getGraphics().drawImage(highlightFromFile, 0, 0, null);
							
							_diseaseOverlays.put(id, convertedDisease);
							_highlightOverlays.put(id, convertedHighlight);
						} else {
							BufferedImage[] overlays = createRegion(id, Color.red, new Color(255, 255, 175));
							_diseaseOverlays.put(id, overlays[0]);
							_highlightOverlays.put(id, overlays[1]);
							if (Settings.getBoolean(Settings.CACHING)) {
								ImageIO.write(overlays[0], "png", disease);
								ImageIO.write(overlays[1], "png", highlight);
							}
						}
						//_infected.put(id, 0f);
						//_composites.put(id, AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f));
					}
				}
				setProgress(x * 100 / (_regions.getWidth()-1));
			}
			return null;
		}
		
		@Override
		protected void done() {
			_done.doAction();
			new Thread(_ow, "Overlay Worker").start();
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
			transform = gc.createCompatibleImage(img.getWidth(), img.getHeight(), Transparency.TRANSLUCENT);//new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
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
			out[i] = gc.createCompatibleImage(_regions.getWidth(), _regions.getHeight(), Transparency.TRANSLUCENT);//new BufferedImage(_regions.getWidth(), _regions.getHeight(), BufferedImage.TYPE_INT_ARGB);
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
		if (_world.isGameOver()) {
			Utils.getParentFrame(this).setPanel(new PostGameMenu(_world, _disease, _multi));
		}
		if (_world.hostDisconnected()) {
			JOptionPane.showMessageDialog(this, "The host has disconnected.  Returning to Main Menu");
			Utils.getParentFrame(this).setPanel(new MainMenu());
		}
		for (Iterator<MovingObject> it = _objects.iterator(); it.hasNext();) {
			if (it.next().update().done) {
				it.remove();
			}
		}
		AirTransmission at;
		if ((at = _world.getTransmission()) != null) {
			Airport src = at.getStart(); Airport dest = at.getEnd();
			addAirplane(at.isInfected(), src.getX(), src.getY(), dest.getX(), dest.getY());
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if (lastUpdate != 0) {
			fps = (fps * 20 + 1.0 / ((System.nanoTime() - lastUpdate)/1000000000.0))/21.0;
		}
		lastUpdate = System.nanoTime();
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(_map, 0, 0, null);
		
		for (Map.Entry<Integer, BufferedImage> e : _diseaseOverlays.entrySet()) {
			
			float highlight = _highlights.get(e.getKey());
			if (highlight > 0) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, highlight));
				g2.drawImage(_highlightOverlays.get(e.getKey()), 0, 0, null);
			}
			if (e.getKey() == _hover)
				_highlights.put(e.getKey(), Math.min(0.5f, highlight + 0.035f));
			else
				_highlights.put(e.getKey(), Math.max(0, highlight - 0.015f));
		}
		
		synchronized (_regionCache) {
			g2.setComposite(AlphaComposite.SrcOver);
			g2.drawImage(_regionCache, 0, 0, null);
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

		if (_drawAirports) {
			BufferedImage airportOpen = Resources.getImage(Images.AIRPORT_OPEN);
			BufferedImage airportClosed = Resources.getImage(Images.AIRPORT_CLOSED);
			for (Airport a : _world.getAirports()) {
				g2.drawImage((a.isOpen() ? airportOpen : airportClosed), (int)(a.getX() - airportOpen.getWidth()/2), (int)(a.getY() - airportOpen.getHeight()/2), null);
			}
		}
		
		if (Settings.getBoolean(Settings.FPS)) {
			g.setFont(Fonts.NORMAL_TEXT);
			g.setColor(Colors.RED_TEXT);
			g.drawString(String.format("FPS: %.0f", fps), 20, 40);
		}
		
		if (_drawAirplanes) {
			for (MovingObject m : _objects) {
				m.draw(g);
			}
		}
		_ml.paintComponent(g);
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
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .8f));
		g2.setColor(Color.BLACK);
		g2.fillRect(400, getHeight() - 40, getWidth() - 510, 40);

		g2.setColor(Colors.RED_TEXT);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g2.setFont(Fonts.BIG_TEXT);
		g2.drawString("Choose a Starting Location", 410, getHeight() - 10);
	}
	
	private void drawInfoPanel(Graphics2D g2) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .8f));
		g2.setColor(Color.BLACK);
		g2.fillRect(0, getHeight() - 140, 295, 140);

		g2.setColor(Colors.RED_TEXT);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g2.setFont(Fonts.NORMAL_TEXT);
		Region r = _world.getRegion(_selected);
		String name = "";
		long infected = 0, dead = 0, total = 1, healthy = 0, cured = 0, tinfected = 0, tdead = 0, tcured = 0;
		long airports = 0, seaports = 0;
		
		if (r != null) {
			name = r.getName();
			infected = r.getInfected().get(_disease);
			dead = r.getKilled().get(_disease);
			cured = r.getCured().get(_disease);
			tinfected = r.getTotalInfectedNoOverlap();
			tcured = r.getTotalCuredNoOverlap();
			for (long l : r.getKilled()) {
				tdead += l;
			}
			healthy = r.getHealthy();
			total = r.getPopulation();
			airports = r.getAir();
			seaports = r.getSea();
		}
		g2.drawString(name, 5, getHeight() - 120);
		NumberFormat nf = NumberFormat.getInstance();
		
		if (_multi && !_totalData) {			
			g2.drawString(String.format("Healthy: %s (%.2f%%)", nf.format(healthy), 100*(double)healthy/(double)total), 15, getHeight() - 95);
			g2.drawString(String.format("Infected: %s (%.2f%%)", nf.format(infected), 100*(double)infected/(double)total), 15, getHeight() - 75);
			g2.drawString(String.format("Dead: %s (%.2f%%)", nf.format(dead), 100*(double)dead/(double)total), 15, getHeight() - 55);
			g2.drawString(String.format("Cured: %s (%.2f%%)", nf.format(cured), 100*(double)cured/(double)total), 15, getHeight() - 35);
			g2.drawString(String.format("Total: %s", nf.format(total)), 15, getHeight() - 15);

		} else {
			g2.drawString(String.format("Healthy: %s (%.2f%%)", nf.format(healthy), 100*(double)healthy/(double)total), 15, getHeight() - 95);
			g2.drawString(String.format("Infected: %s (%.2f%%)", nf.format(tinfected), 100*(double)tinfected/(double)total), 15, getHeight() - 75);
			g2.drawString(String.format("Dead: %s (%.2f%%)", nf.format(tdead), 100*(double)tdead/(double)total), 15, getHeight() - 55);
			g2.drawString(String.format("Cured: %s (%.2f%%)", nf.format(tcured), 100*(double)tcured/(double)total), 15, getHeight() - 35);
			g2.drawString(String.format("Total: %s", nf.format(total)), 15, getHeight() - 15);
		}
		
		if (airports > 0) {
			g2.drawImage(Resources.getImage(Images.AIRPORT_OPEN_BIG), 260, getHeight() - 85, null);
		} else {
			g2.drawImage(Resources.getImage(Images.AIRPORT_CLOSED_BIG), 260, getHeight() - 85, null);
		}
		if (seaports > 0) {
			g2.drawImage(Resources.getImage(Images.SEAPORT_OPEN_BIG), 260, getHeight() - 45, null);
		} else {
			g2.drawImage(Resources.getImage(Images.SEAPORT_CLOSED_BIG), 260, getHeight() - 45, null);
		}
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
                _world.introduceDisease(_disease, id-1);
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

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		_hover = getID(e.getPoint().x, e.getPoint().y);
	}
	
	public void stop() {
		_timer.stop();
		_ow.cancel();
	}
	
	public void setDrawAirports(boolean b) {
		_drawAirports = b;
	}
	
	public void setDrawAirplanes(boolean b) {
		_drawAirplanes = b;
	}
	
	private void addAirplane(boolean infected, int srcx, int srcy, int destx, int desty) {
		MovingObject airplane = new MovingObject(new Location(srcx, srcy), AIRPLANE_SPEED, (infected ? Resources.getImage(Images.AIRPLANE_INFECTED) : Resources.getImage(Images.AIRPLANE)));
		airplane.addWaypoint(new Location(destx, desty));
		_objects.add(airplane);
	}
	
	public void setTotalData(boolean b) {
		_totalData = b;
	}
}
